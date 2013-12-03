package it.geosolutions.opensdi.mvc.model.statistics;

import it.geosolutions.opensdi.dto.GeobatchRunInfo;
import it.geosolutions.opensdi.model.JSPFile;
import it.geosolutions.opensdi.operations.LocalOperation;
import it.geosolutions.opensdi.operations.Operation;
import it.geosolutions.opensdi.service.GeoBatchClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Add Geobatch run information support
 * 
 * @author adiaz
 */
public class ExtendedFileBrowser extends FileBrowser {

protected GeoBatchClient geoBatchClient;

protected HashMap<String, List<Operation>> availableOperations;

protected Boolean updateStatus = false;

// Readed files
List<JSPFile> files;

/**
 * @return the geoBatchClient
 */
public GeoBatchClient getGeoBatchClient() {
    return geoBatchClient;
}

/**
 * @param geoBatchClient the geoBatchClient to set
 */
public void setGeoBatchClient(GeoBatchClient geoBatchClient) {
    this.geoBatchClient = geoBatchClient;
}

/**
 * Utility to provide a list of JSPFile inside the basedir
 * 
 * @param scanDirectories
 * @return
 */
public List<JSPFile> getFiles() {
    // Generate files just once when write JSP page!!
    if (files != null) {
        return files;
    } else {
        files = super.getFiles();
        return files;
    }
}

/**
 * Obtain JSP file from a path
 * 
 * @param path
 * @return JSPFile with last runInfo
 */
protected JSPFile getJSPFile(String path) {
    JSPFile jspFile = new JSPFile(path);
    Map<String, GeobatchRunInfo> runStatus = new HashMap<String, GeobatchRunInfo>();
    // Obtain runStatus for each operation available
    if (availableOperations != null) {
        for (String operation : availableOperations.keySet()) {
            if (path.endsWith(operation)) {
                for (Operation op : availableOperations.get(operation)) {
                    String filePath = path;
                    // Virtualize path
                    if (op instanceof LocalOperation) {
                        filePath = ((LocalOperation) op).getVirtualPath(filePath);
                    }
                    runStatus.put(op.getName(), geoBatchClient.getLastRunInfo(
                            updateStatus, filePath, op.getName()));
                }
            }
        }
    }
    jspFile.setRunInfo(runStatus);
    return jspFile;
}

public HashMap<String, List<Operation>> getAvailableOperations() {
    return availableOperations;
}

public void setAvailableOperations(
        HashMap<String, List<Operation>> availableOperations) {
    this.availableOperations = availableOperations;
}

/**
 * @return the updateStatus
 */
public Boolean getUpdateStatus() {
    return updateStatus;
}

/**
 * @param updateStatus the updateStatus to set
 */
public void setUpdateStatus(Boolean updateStatus) {
    this.updateStatus = updateStatus;
}

}
