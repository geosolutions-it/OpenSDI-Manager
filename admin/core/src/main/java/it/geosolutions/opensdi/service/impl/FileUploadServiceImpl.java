/*
 *  OpenSDI Manager
 *  Copyright (C) 2014 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.opensdi.service.impl;

import it.geosolutions.opensdi.service.FileUploadService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.multipart.MultipartFile;

/**
 * File upload service handling based on concurrent hash maps and disk storage
 * 
 * @author adiaz
 *
 */
public class FileUploadServiceImpl implements FileUploadService {

private final static Logger LOGGER = Logger
        .getLogger(FileUploadServiceImpl.class);

/**
 * Map to handle file uploading chunked
 */
private Map<String, List<String>> uploadedChunksByFile = new ConcurrentHashMap<String, List<String>>();

/**
 * Pending chunks on last review and his size
 */
private Map<String, Integer> pendingChunksByFile = new ConcurrentHashMap<String, Integer>();

/**
 * Private time for the last check
 */
private Date lastCheck;

/**
 * Minimum interval to check incomplete uploads
 */
private long minInterval = 1000000000;

/**
 * Max of upload files with the same name
 */
private int maxSimultaneousUpload = 100;

/**
 * Temporary folder for the file uploads chunks. By default is <code>System.getProperty("java.io.tmpdir")</code>
 */
private String temporaryFolder = System.getProperty("java.io.tmpdir");

/**
 * Add a chunk of a file upload
 * 
 * @param name of the file
 * @param chunks total for the file
 * @param chunk number on this upload
 * @param file with the content uploaded
 * @return current list of byte arrays for the file
 * @throws IOException if no more uploads are available
 */
public Entry<String, List<String>> addChunk(String name, int chunks, int chunk,
        MultipartFile file) throws IOException {
    Entry<String, List<String>> entry = null;
    try {
        entry = getChunk(name, chunks, chunk);
        if (LOGGER.isTraceEnabled())
            LOGGER.trace("entry [" + entry.getKey() + "] found ");
        List<String> uploadedChunks = entry.getValue();
        String tmpFile = createTemporalFile(entry.getKey(), file.getBytes(),
                entry.getValue().size());
        // add chunk on its position
        uploadedChunks.add(chunk, tmpFile);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("uploadedChunks size[" + entry.getKey() + "] --> "
                    + uploadedChunks.size());
        }
    } catch (IOException e) {
        LOGGER.error("Error on file upload", e);
    }

    return entry;
}

/**
 * Create a temporal file with a byte array
 * 
 * @param key of the file
 * @param bytes to write
 * @param i index by the file name
 * @return absolute path to the file
 * @throws IOException
 */
public String createTemporalFile(String key, byte[] bytes, int i)
        throws IOException {
    String filePath = temporaryFolder + File.separator
            + (key + i);
    if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Writing temporal content to " + filePath);
    }
    FileOutputStream fos = new FileOutputStream(filePath);
    for (byte b : bytes) {
        fos.write(b);
    }
    fos.close();
    return filePath;
}

/**
 * Get a chunk of a file upload
 * 
 * @param name of the file
 * @param chunks total for the file
 * @param chunk number on this upload
 * @param file with the content uploaded
 * @return current entry for the file
 * @throws IOException if no more uploads are available
 */
public Entry<String, List<String>> getChunk(String name, int chunks, int chunk)
        throws IOException {
    Integer key = null;

    // init bytes for the chunk upload
    List<String> uploadedChunks = uploadedChunksByFile.get(name);
    if (chunk == 0) {
        if (uploadedChunks != null) {
            key = -1;
            while (uploadedChunks != null) {
                key++;
                uploadedChunks = uploadedChunksByFile.get(name + "_" + key);
            }
        }
        uploadedChunks = new LinkedList<String>();
    } else if (uploadedChunks == null || uploadedChunks.size() != chunk) {
        key = -1;
        while ((uploadedChunks == null || uploadedChunks.size() != chunk)
                && key < maxSimultaneousUpload) {
            key++;
            uploadedChunks = uploadedChunksByFile.get(name + "_" + key);
        }
        if (uploadedChunks == null || uploadedChunks.size() != chunk) {
            LOGGER.error("Incorrent chunk. Can't found previous chunks");
            throw new IOException(
                    "Incorrent chunk. Can't found previous chunks");
        }
    }

    // save and return entry
    String mapKey = key != null ? name + "_" + key : name;
    uploadedChunksByFile.put(mapKey, uploadedChunks);
    Entry<String, List<String>> entry = null;
    for (Entry<String, List<String>> mapEntry : uploadedChunksByFile.entrySet()) {
        if (mapEntry.getKey().equals(mapKey)) {
            entry = mapEntry;
            break;
        }
    }

    return entry;
}

/**
 * @return pending upload files size
 */
public int size() {
    return uploadedChunksByFile.size();
}

/**
 * Remove a file upload
 * 
 * @param key
 */
public void remove(String key) {
    if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Removing uploading file "+ key);
    }
    // remove temporal content
    for (String filePath : uploadedChunksByFile.get(key)) {
        File tmpFile = new File(filePath);
        tmpFile.delete();
    }
    uploadedChunksByFile.remove(key);

    // remove it from pending chunks
    if (pendingChunksByFile.containsKey(key))
        pendingChunksByFile.remove(key);
}

/**
 * This method cleans concurrent uploading files in two executions. It's ready
 * to be called on a cronable method to check if there are pending incomplete
 * files without changes in the interval.
 */
public void cleanup() {
    Date date = new Date();
    if (lastCheck == null) {
        lastCheck = date;
    }
    // remove incomplete
    if (date.getTime() - lastCheck.getTime() > minInterval) {
        if (LOGGER.isInfoEnabled())
            LOGGER.info("Cleaning pending incomplete uploads");
        lastCheck = date;
        for (String key : pendingChunksByFile.keySet()) {
            if (uploadedChunksByFile.get(key) != null) {
                Integer size = uploadedChunksByFile.get(key).size();
                if (pendingChunksByFile.get(key).equals(size)) {
                    if (LOGGER.isInfoEnabled())
                        LOGGER.info("Removing incomplete upload [" + key + "]");
                    // remove
                    remove(key);
                } else {
                    pendingChunksByFile.put(key, size);
                }
            } else {
                pendingChunksByFile.remove(key);
            }
        }
    }
    // save size
    for (String key : uploadedChunksByFile.keySet()) {
        pendingChunksByFile.put(key, uploadedChunksByFile.get(key).size());
    }
}

/**
 * Obtain a temporal file item with chunked bytes
 * 
 * @param file
 * @param uploadedChunks
 * @param name
 * @param entry
 * @return
 */
@SuppressWarnings("unchecked")
public File getCompletedFile(String name, Entry<String, ?> entry) {
    // Temporal file to write chunked bytes
    File outFile = new File(temporaryFolder, name);
    if(LOGGER.isTraceEnabled()){
        LOGGER.trace("Merging uploaded chunks in " + outFile.getAbsolutePath());
    }
    
    try {
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile));

        // write bytes
        for (String filePath : (List<String>) entry.getValue()) {
            File tmpFile = new File(filePath);
            
            if(LOGGER.isTraceEnabled()){
                LOGGER.trace("Merging uploaded from " + tmpFile.getAbsolutePath());
            }
            FileInputStream fis = new FileInputStream(tmpFile);
            int c;

            while ((c = fis.read()) != -1) {
                outputStream.write(c);
            }
            fis.close();
            
            // flush written data
            outputStream.flush();
        }

        // close the file
        outputStream.close();
        
    } catch (IOException e) {
        LOGGER.error("Error writing final file", e);
    } finally {
        // Remove bytes from memory
        remove(entry.getKey());
    }

    return outFile;
}

/**
 * Get a file from a single multipart file
 * 
 * @param name of the file
 * @param file with the content uploaded
 * @return File
 * @throws IOException if something occur while file generation
 */
public File getCompletedFile(String name, MultipartFile file)
        throws IOException {
    String filePath = temporaryFolder + File.separator + name;
    File outFile = new File(filePath);
    if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Writing complete content to " + filePath);
    }
    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile));
    for (byte b : file.getBytes()) {
        outputStream.write(b);
        outputStream.flush();
    }
    outputStream.close();
    return outFile;
}

/**
 *  Scheduled once a day. If an user stop an upload, it will be removed from memory 
 */
@Scheduled(cron = "0 0 4 * * ?")
public void cleanupUploadedFiles(){
    cleanup();
}

/**
 * @return the minInterval
 */
public long getMinInterval() {
    return minInterval;
}

/**
 * @return the maxSimultaneousUpload
 */
public int getMaxSimultaneousUpload() {
    return maxSimultaneousUpload;
}

/**
 * @return the temporaryFolder
 */
public String getTemporaryFolder() {
    return temporaryFolder;
}

/**
 * @param minInterval the minInterval to set
 */
public void setMinInterval(long minInterval) {
    this.minInterval = minInterval;
}

/**
 * @param maxSimultaneousUpload the maxSimultaneousUpload to set
 */
public void setMaxSimultaneousUpload(int maxSimultaneousUpload) {
    this.maxSimultaneousUpload = maxSimultaneousUpload;
}

/**
 * @param temporaryFolder the temporaryFolder to set
 */
public void setTemporaryFolder(String temporaryFolder) {
    this.temporaryFolder = temporaryFolder;
}

}
