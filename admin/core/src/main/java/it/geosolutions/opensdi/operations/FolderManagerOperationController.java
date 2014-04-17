/*
 *  OpenSDI Manager
 *  Copyright (C) 2013 GeoSolutions S.A.S.
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
package it.geosolutions.opensdi.operations;

import it.geosolutions.opensdi.utils.GeoBatchRunInfoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * Folder manager to extend file browser functionality. Allows to create/delete
 * folders and download files
 * 
 * @author adiaz
 */
@Controller
public class FolderManagerOperationController extends UserOperation implements
        ApplicationContextAware, ResponseManagerOperation {

private final static Logger LOGGER = Logger
        .getLogger(FolderManagerOperationController.class);

/**
 * Key for the operation to execute
 */
private static final String FOLDER_OPERATION_KEY = "folderOperation";

/**
 * Key for file/folder parameter
 */
private static final String FILE_NAME_KEY = "fileName";

/**
 * Known operation: Delete folder
 */
private static final String DELETE_FOLDER_KEY = "delete";

/**
 * Known operation: Download file
 */
private static final String DOWNLOAD_KEY = "download";

/**
 * Known operation: Create folder
 */
private static final String CREATE_FOLDER_KEY = "create";

// parameters from Operation
private String operationName;
private String operationRestPath;
private String operationJSP;
protected String defaultBaseDir;

/**
 * File browser to manage
 */
protected FileBrowserOperationController fileBrowserOperationController;

/**
 * Create a folder manager controller for a file browser
 * 
 * @param operationName for the manager
 * @param fileBrowserOperationController file browser to manage
 */
public FolderManagerOperationController(String operationName,
        FileBrowserOperationController fileBrowserOperationController) {
    super();
    this.operationName = operationName;
    this.operationRestPath = this.operationName;
    this.fileBrowserOperationController = fileBrowserOperationController;
    this.operationJSP = fileBrowserOperationController.getJsp();
    this.defaultBaseDir = fileBrowserOperationController.getDefaultBaseDir();
}

/**
 * Obtain a file to download
 */
public void getFile(ModelMap model, HttpServletRequest request,
        List<MultipartFile> files, HttpServletResponse response) {

    String folderOperation = request.getParameter(FOLDER_OPERATION_KEY);
    String folderName = request.getParameter(FILE_NAME_KEY);
    String subFolder = request
            .getParameter(FileBrowserOperationController.DIRECTORY_KEY);

    if (folderOperation != null && folderName != null) {
        if (DOWNLOAD_KEY.equals(folderOperation)
                && fileBrowserOperationController.getCanDownloadFiles()) {
            downloadFile(folderName, subFolder, response);
        } else {
            LOGGER.error("Unknown operation " + subFolder);
        }
    }
}

/**
 * Manage operation on the folder and read again with
 * this.fileBrowserOperationController
 */
public String getJsp(ModelMap model, HttpServletRequest request,
        List<File> files) {

    String folderOperation = request.getParameter(FOLDER_OPERATION_KEY);
    String folderName = request.getParameter(FILE_NAME_KEY);
    String subFolder = request
            .getParameter(FileBrowserOperationController.DIRECTORY_KEY);

    if (folderOperation != null && folderName != null
            && fileBrowserOperationController.getCanManageFolders()) {
        if (DELETE_FOLDER_KEY.equals(folderOperation)) {
            deleteFolder(folderName, subFolder);
        } else if (CREATE_FOLDER_KEY.equals(folderOperation)) {
            createFolder(folderName, subFolder);
        } else {
            LOGGER.error("Unknown operation " + subFolder);
        }
    }

    fileBrowserOperationController.getJsp(model, request, files);

    return operationJSP;
}

/**
 * Download the file
 * 
 * @param fileName
 * @param subFolder
 * @param response
 */
private void downloadFile(String fileName, String subFolder,
        HttpServletResponse response) {
    try {
        String filePath = getFilePath(fileName, subFolder);
        download(response, fileName, filePath);
    } catch (Exception ex) {
        LOGGER.info("Error writing file to output stream. Filename was '"
                + fileName + "'");
        throw new RuntimeException("IOError writing file to output stream");
    }
}

/**
 * Download a file with a stream
 * 
 * @param resp
 * @param fileName
 * @param filePath
 * @return
 */
@SuppressWarnings("resource")
private ResponseEntity<byte[]> download(HttpServletResponse resp,
        String fileName, String filePath) {

    final HttpHeaders headers = new HttpHeaders();
    File toServeUp = new File(filePath);
    InputStream inputStream = null;

    try {
        inputStream = new FileInputStream(toServeUp);
    } catch (FileNotFoundException e) {

        // Also useful, this is a good was to serve down an error message
        String msg = "ERROR: Could not find the file specified.";
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<byte[]>(msg.getBytes(), headers,
                HttpStatus.NOT_FOUND);

    }

    resp.setContentType("application/octet-stream");
    resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName
            + "\"");

    Long fileSize = toServeUp.length();
    resp.setContentLength(fileSize.intValue());

    OutputStream outputStream = null;

    try {
        outputStream = resp.getOutputStream();
    } catch (IOException e) {
        String msg = "ERROR: Could not generate output stream.";
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<byte[]>(msg.getBytes(), headers,
                HttpStatus.NOT_FOUND);
    }

    byte[] buffer = new byte[1024];

    int read = 0;
    try {

        while ((read = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, read);
        }

        // close the streams to prevent memory leaks
        outputStream.flush();
        outputStream.close();
        inputStream.close();

    } catch (Exception e) {
        String msg = "ERROR: Could not read file.";
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<byte[]>(msg.getBytes(), headers,
                HttpStatus.NOT_FOUND);
    }

    return null;
}

/**
 * Create a new folder
 * 
 * @param folderName
 * @param subFolder
 */
private void createFolder(String folderName, String subFolder) {
    String folderPath = getFilePath(folderName, subFolder);
    LOGGER.debug("Creating folder '" + folderPath + "'");
    File file = new File(folderPath);
    if (!file.exists()) {
        file.mkdir();
    } else {
        LOGGER.error("Folder '" + folderPath + "' already exists");
    }
}

/**
 * Delete a folder
 * 
 * @param folderName
 * @param subFolder
 */
private void deleteFolder(String folderName, String subFolder) {
    String folderPath = getFilePath(folderName, subFolder);
    LOGGER.debug("Deleting folder '" + folderPath + "'");
    File file = new File(folderPath);
    if (file.exists()) {
        if (file.canWrite()) {
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                LOGGER.error("Error deleting '" + folderPath + "' folder");
            }
        } else {
            LOGGER.error("Incorrect permissions on folder '" + folderPath
                    + "'. We can't delete it");
        }
    } else {
        LOGGER.error("Folder '" + folderPath + "' not exists");
    }
}

/**
 * Obtain file path for a file in a folder inside defaultFolderDir
 * 
 * @param fileName
 * @param subFolder
 * @return
 */
private String getFilePath(String fileName, String subFolder) {
    String filePath = defaultBaseDir;
    if (subFolder != null) {
        filePath += subFolder + GeoBatchRunInfoUtils.SEPARATOR;
    }
    filePath += fileName;
    filePath = GeoBatchRunInfoUtils.cleanDuplicateSeparators(filePath);
    return filePath;
}

@Override
public String getName() {
    return operationName;
}

/**
 * Getter for operationName
 * 
 * @return
 */
public String getOperationName() {
    return operationName;
}

/**
 * Setter for operationName
 * 
 * @param newName
 */
public void setOperationName(String newName) {
    this.operationName = newName;
}

@Override
public String getRESTPath() {
    return operationRestPath;
}

/**
 * @param operationRestPath the operationRestPath to set
 */
public void setRESTPath(String operationRestPath) {
    this.operationRestPath = operationRestPath;
}

@Override
public String getJsp() {
    return operationJSP;
}

/**
 * @return the defaultBaseDir
 */
public String getDefaultBaseDir() {
    return defaultBaseDir;
}

/**
 * @param defaultBaseDir the defaultBaseDir to set
 */
public void setDefaultBaseDir(String defaultBaseDir) {
    this.defaultBaseDir = defaultBaseDir;
}

public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {
    // Nothing
}

}