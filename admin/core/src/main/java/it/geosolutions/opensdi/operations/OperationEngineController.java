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

import it.geosolutions.geobatch.services.rest.GeoBatchRESTClient;
import it.geosolutions.geobatch.services.rest.RESTFlowService;
import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import it.geosolutions.opensdi.model.FileUpload;
import it.geosolutions.opensdi.service.FileUploadService;
import it.geosolutions.opensdi.service.GeoBatchClient;
import it.geosolutions.opensdi.utils.ControllerUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class OperationEngineController implements ApplicationContextAware {

private ApplicationContext applicationContext;

private final static Logger LOGGER = Logger
        .getLogger(OperationEngineController.class);

@Autowired
private GeoBatchClient geoBatchClient;

@Autowired
private FileUploadService fileUploadService;

/**
 * Encapsulate operation Jsp into the template
 * 
 * @param operationId
 * @param gotParam
 * @param request
 * @param model
 * @return String template jsp to display
 */
@RequestMapping(value = "/operationManager/{operationId}/{gotParam:.+}", method = RequestMethod.GET)
public String issueGetToTemplate(
        @PathVariable(value = "operationId") String operationId,
        @PathVariable(value = "gotParam") String gotParam,
        HttpServletRequest request, ModelMap model) {

    String opJsp = issueGetToOperation(operationId, gotParam, request, model);
    model.addAttribute("context", opJsp);

    return "template";
}

/**
 * Obtain a file from a ResponseManagerOperation
 * 
 * @param operationId
 * @param request
 * @param model
 * @param response
 */
@RequestMapping(value = "/download/{operationId}", method = RequestMethod.GET)
public void issueGetFile(
        @PathVariable(value = "operationId") String operationId,
        HttpServletRequest request, ModelMap model, HttpServletResponse response) {
    issueGetFile(operationId, null, request, model, response);
}

/**
 * Obtain a file from a ResponseManagerOperation
 * 
 * @param operationId
 * @param gotParam
 * @param request
 * @param model
 * @param response
 */
@RequestMapping(value = "/download/{operationId}/{gotParam:.+}", method = RequestMethod.GET)
public void issueGetFile(
        @PathVariable(value = "operationId") String operationId,
        @PathVariable(value = "gotParam") String gotParam,
        HttpServletRequest request, ModelMap model, HttpServletResponse response) {
    if (LOGGER.isInfoEnabled()) {
        LOGGER.info("Handling by issueGetFile : OperationEngine GET");
        LOGGER.info("Operation:" + operationId);
        LOGGER.info("Path parameter:" + gotParam);
    }
    // TODO: check gotParam for security

    if (applicationContext.containsBean(operationId)
            && applicationContext.isTypeMatch(operationId, Operation.class)) {

        Operation op = (Operation) applicationContext.getBean(operationId);

        if (gotParam != null) {
            model.addAttribute("gotParam", gotParam);
        }
        
        if (op instanceof ResponseManagerOperation) {
            ((ResponseManagerOperation) op).getFile(model,
                    request, null, response);
        } else {
            LOGGER.error("Incorrect call. The operation can handle the response");
        }

    } else {
        LOGGER.error("Operation:" + operationId + " not found");
    }
}

/**
 * Proxy operationManager requests without params
 * 
 * @param operationId
 * @param gotParam
 * @param request
 * @param model
 * @return String template jsp to display
 */
@RequestMapping(value = "/operationManager/{operationId}", method = RequestMethod.GET)
public String issueGetToTemplate(
        @PathVariable(value = "operationId") String operationId,
        HttpServletRequest request, ModelMap model) {
    return issueGetToTemplate(operationId, null, request, model);
}

/**
 * @param operationId
 * @param gotParam
 * @param request
 * @param model
 * @return String template jsp to display
 */
@RequestMapping(value = "/operation/{operationId}/{gotParam:.+}", method = RequestMethod.GET)
public String issueGetToOperation(
        @PathVariable(value = "operationId") String operationId,
        @PathVariable(value = "gotParam") String gotParam,
        HttpServletRequest request, ModelMap model) {
    if (LOGGER.isInfoEnabled()) {
        LOGGER.info("Handling by issueGetToOperation : OperationEngine GET");
        LOGGER.info("Operation:" + operationId);
        LOGGER.info("Path parameter:" + gotParam);
    }
    // TODO: check gotParam for security

    if (applicationContext.containsBean(operationId)
            && applicationContext.isTypeMatch(operationId, Operation.class)) {

        Operation op = (Operation) applicationContext.getBean(operationId);

        if (gotParam != null) {
            model.addAttribute("gotParam", gotParam);
        }

        String operationJsp = op.getJsp(model, request, null);

        // model.addAttribute("context", operationJsp);
        // Geosolutions authentication
        ControllerUtils.setCommonModel(model);

        // TODO: make this folder parametric
        return "context/" + operationJsp;

    } else {
        LOGGER.error("Operation:" + operationId + " not found");
        model.addAttribute("messageType", "error");
        model.addAttribute("operationMessage", "Operation not found");
        return "common/messages";
    }

}

/**
 * Proxy function for issueGetToOperation with path variable
 * 
 * @param operationId
 * @param request
 * @param model
 * @return template jsp to display
 */
@RequestMapping(value = "/operation/{operationId}", method = RequestMethod.GET)
public String issueGetToOperation(
        @PathVariable(value = "operationId") String operationId,
        HttpServletRequest request, ModelMap model) {
    return issueGetToOperation(operationId, null, request, model);
}

/**
 * Handler for rest operations
 * 
 * @param operationId
 * @param gotHeaders
 * @param file uploaded
 * @param request
 * @param model
 * @return
 * @throws IOException
 */
@RequestMapping(value = "/operation/{operationId}/rest", method = {
        RequestMethod.POST, RequestMethod.GET })
public @ResponseBody
Map<String, Object> upload(
        @PathVariable(value = "operationId") String operationId,
        @RequestHeader HttpHeaders gotHeaders,
        @RequestParam(required = false) MultipartFile file,
        @RequestParam(required = false) String name,
        @RequestParam(required = false, defaultValue = "-1") int chunks,
        @RequestParam(required = false, defaultValue = "-1") int chunk,
        HttpServletRequest request, ModelMap model) throws IOException {
    Map<String, Object> response = new HashMap<String, Object>();
    try {
        if (applicationContext.containsBean(operationId)
                && applicationContext.isTypeMatch(operationId, Operation.class)) {
            Operation operation = (Operation) applicationContext
                    .getBean(operationId);
            response = operation.getRestResponse(
                    model,
                    request,
                    getFileUpload(operationId, gotHeaders, file, name, chunks,
                            chunk, request, model).getFiles());
        } else {
            response.put(ControllerUtils.SUCCESS, false);
            response.put(ControllerUtils.ROOT, "Unknown operation ["
                    + operationId + "]");
        }
    } catch (Exception e) {
        LOGGER.error("Error uploading files", e);
        response.put(ControllerUtils.SUCCESS, false);
        response.put(ControllerUtils.ROOT, e.getLocalizedMessage());
    }
    return response;
}

/**
 * Handler for upload files
 * 
 * @param operationId
 * @param gotHeaders
 * @param file uploaded
 * @param request
 * @param model
 * @return
 * @throws IOException 
 */
@RequestMapping(value = "/operation/{operationId}/upload", method = RequestMethod.POST)
public String uploadAndRefresh(
        @PathVariable(value = "operationId") String operationId,
        @RequestHeader HttpHeaders gotHeaders,
        @RequestParam MultipartFile file, @RequestParam String name,
        @RequestParam(required = false, defaultValue = "-1") int chunks,
        @RequestParam(required = false, defaultValue = "-1") int chunk,
        HttpServletRequest request, ModelMap model) throws IOException {
    
    return issuePostToOperation(operationId, null, gotHeaders, getFileUpload(operationId, gotHeaders, file, name, chunks, chunk, request, model),
            request, model);
}

/**
 * Get FileUpload on the request
 * 
 * @param operationId
 * @param gotHeaders
 * @param file uploaded
 * @param request
 * @param model
 * @return
 * @throws IOException
 */
public FileUpload getFileUpload(String operationId, HttpHeaders gotHeaders,
        MultipartFile file, String name, int chunks, int chunk,
        HttpServletRequest request, ModelMap model) throws IOException {

    if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("upload (name, chunks, chunk) --> " + name + "," + chunks
                + "," + chunk);
        LOGGER.debug("Uploading " + fileUploadService.size() + " files");
    }

    FileUpload uploadFile = new FileUpload();
    List<File> files = new LinkedList<File>();
    if (chunks > 0) {
        // init bytes for the chunk upload
        Entry<String, ?> entry = fileUploadService.addChunk(name, chunks,
                chunk, file);
        if (entry == null) {
            String msg = "Expired file upload dor file " + name;
            LOGGER.error(msg);
            throw new IOException(msg);
        }
        if (chunk == chunks - 1) {
            // Create the upload file to be handled
            files.add(fileUploadService.getCompletedFile(name, entry));
            uploadFile.setFiles(files);
        }
    } else {
        // Create the upload file to be handled
        files.add(fileUploadService.getCompletedFile(name, file));
        uploadFile.setFiles(files);
    }

    return uploadFile;
}

@RequestMapping(value = "/operation/{operationId}", method = RequestMethod.POST)
public String issuePostToOperation(
        @PathVariable(value = "operationId") String operationId,
        @RequestHeader HttpHeaders gotHeaders,
        @ModelAttribute("uploadFile") FileUpload uploadFile,
        HttpServletRequest request, ModelMap model) {
    return issuePostToOperation(operationId, null, gotHeaders, uploadFile,
            request, model);
}

/**
 * Issue a POST request to the desired Operation
 * 
 * @param operationId
 * @param gotParam
 * @param gotHeaders
 * @param uploadFile
 * @param request
 * @param model
 * @return template jsp to display returned message
 */
@RequestMapping(value = "/operation/{operationId}/{gotParam:.+}", method = RequestMethod.POST)
public String issuePostToOperation(
        @PathVariable(value = "operationId") String operationId,
        @PathVariable(value = "gotParam") String gotParam,
        @RequestHeader HttpHeaders gotHeaders,
        @ModelAttribute("uploadFile") FileUpload uploadFile,
        HttpServletRequest request, ModelMap model) {

    LOGGER.info("Handling by issuePostToOperation : OperationEngine POST");

    if (applicationContext.containsBean(operationId)
            && applicationContext.isTypeMatch(operationId, Operation.class)) {

        Operation operation = (Operation) applicationContext
                .getBean(operationId);

        if (operation instanceof GeoBatchOperation) {

            String response = "Operation running"; // Default text
            try {
                GeoBatchRESTClient client = new GeoBatchRESTClient();
                client.setGeostoreRestUrl(((GeoBatchOperation) operation)
                        .getGeobatchRestUrl());
                client.setUsername(((GeoBatchOperation) operation)
                        .getGeobatchUsername());
                client.setPassword(((GeoBatchOperation) operation)
                        .getGeobatchPassword());

                // TODO: check ping to GeoBatch (see test)

                RESTFlowService service = client.getFlowService();

                if (operation instanceof RemoteOperation) {
                    // TODO: better implementation
                    byte[] blob = (byte[]) ((RemoteOperation) operation)
                            .getBlob(uploadFile.getFiles().get(0), null);
                    // TODO: fastFail or not?
                    // TODO: move fastfail to interfaces
                    response = service.run(
                            ((RemoteOperation) operation).getFlowID(), true,
                            blob);
                    // save run
                    geoBatchClient.saveRunInfo(response, (RemoteOperation) operation, uploadFile);
                } else if (operation instanceof LocalOperation) {

                    // TODO: implement request parameters instead of gotParam
                    RESTRunInfo runInfo;
                    if (gotParam != null) {
                        runInfo = (RESTRunInfo) ((LocalOperation) operation)
                                .getBlob(gotParam, request);
                    } else {

                        @SuppressWarnings("unchecked")
                        Map<String, String[]> parameters = request
                                .getParameterMap();
                        runInfo = (RESTRunInfo) ((LocalOperation) operation)
                                .getBlob(parameters, null);
                    }

                    // TODO: fastFail or not?
                    // TODO: move fastfail to interfaces
                    response = service.runLocal(
                            ((LocalOperation) operation).getFlowID(), true,
                            runInfo);
                    // save run
                    geoBatchClient.saveRunInfo(response, (LocalOperation) operation, runInfo);
                } else {

                    // TODO: refactor this
                    Object[] obj = new Object[3];
                    obj[0] = service;
                    obj[1] = request;
                    obj[2] = model;

                    response = (String) ((GeoBatchOperation) operation)
                            .getBlob(obj, null);
                    // save run
                    geoBatchClient.saveRunInfo(obj, (GeoBatchOperation) operation);
                    return response;

                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                model.addAttribute("messageType", "error");
                model.addAttribute("operationMessage", "Couldn't run "
                        + operation.getName());
                return "common/messages";

            }
            if (response == null) {
                LOGGER.error("the run operation retourned null");
                model.addAttribute("messageType", "error");
                model.addAttribute("operationMessage", "Couldn't run "
                        + operation.getName());

            } else {
                model.addAttribute("messageType", "success");
                model.addAttribute("operationMessage", response);
            }
            return "common/messages";

        }

        // Get file(s)
        List<File> files = uploadFile.getFiles();
        if ((files == null 
                || files.size() == 0) 
                && uploadFile.getFile() != null) {
            try {
                files = new LinkedList<File>();
                files.add(fileUploadService.getCompletedFile(uploadFile.getFile().getOriginalFilename(), uploadFile.getFile()));
            } catch (Exception e) {
                LOGGER.error("Error uploading file", e);
            }
        }

        @SuppressWarnings("unchecked")
        Map<String, String[]> parameters = request.getParameterMap();

        if(LOGGER.isTraceEnabled()){
            for (String key : parameters.keySet()) {
                LOGGER.trace(key);
                String[] vals = parameters.get(key);
                for (String val : vals)
                    LOGGER.trace(" -> " + val);
            }
        }

        if (null != files && files.size() > 0) {
            for (File multipartFile : files) {
                if (multipartFile == null) {
                    continue;
                }
                /*
                 * if(!"".equalsIgnoreCase(fileName)){ //Handle file content -
                 * multipartFile.getInputStream() try {
                 * multipartFile.transferTo(new File(baseDir + fileName)); }
                 * catch (IllegalStateException e) { e.printStackTrace(); }
                 * catch (IOException e) { e.printStackTrace(); }
                 * fileNames.add(fileName); }
                 */

            }
        }

        // TODO: getJSP should modify Model adding it's own attributes
        // Maybe the setCommonModel can be called from within the Operation ?

        model.addAttribute("gotParam", gotParam);
        String operationJsp = operation.getJsp(model, request, files);

        model.addAttribute("context", "context/" + operationJsp);
        // Geosolutions authentication
        ControllerUtils.setCommonModel(model);

        return "template";

    } else {
        model.addAttribute("messageType", "error");
        model.addAttribute("operationMessage", "Operation not found");
        return "common/messages";
    }

}

@Override
public void setApplicationContext(ApplicationContext arg0)
        throws BeansException {
    this.applicationContext = arg0;

}

}