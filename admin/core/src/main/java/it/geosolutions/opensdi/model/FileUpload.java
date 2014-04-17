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
package it.geosolutions.opensdi.model;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Model to hold uploaded files
 * 
 * @author Lorenzo Pini
 * @author adiaz change Multipart fie to files
 */
public class FileUpload {

private List<File> files;
private MultipartFile file;

public FileUpload() {
}

/**
 * @return the files
 */
public List<File> getFiles() {
    return files;
}

/**
 * @param files the files to set
 */
public void setFiles(List<File> files) {
    this.files = files;
}

/**
 * @return the file
 */
public MultipartFile getFile() {
    return file;
}

/**
 * @param file the file to set
 */
public void setFile(MultipartFile file) {
    this.file = file;
}

}
