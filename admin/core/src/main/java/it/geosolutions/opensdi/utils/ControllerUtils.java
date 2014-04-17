package it.geosolutions.opensdi.utils;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;

public class ControllerUtils {

/**
 * DOT character "."
 */
public final static String DOT = ".";

public static String SUCCESS = "success";
public static String ROOT = "root";

private final static Logger LOGGER = Logger.getLogger(ControllerUtils.class);

public static void setCommonModel(ModelMap model) {
    Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();
    String name = auth.getName(); // get logged in username

    model.addAttribute("username", name);
}

/**
 * remove /../ and /./ from a path
 * 
 * @param dirString
 * @return
 */
public static String preventDirectoryTrasversing(String dirString) {
    // prevent directory traversing
    if (dirString == null)
        return null;
    dirString = dirString.replace("..", "");
    // clean path
    dirString = dirString.replace("/./", "/");
    dirString = dirString.replaceAll("/{2,}", "/");
    return dirString;
}

/**
 * Remove extension if exists in fileName
 * 
 * @param fileName to obtain the name
 * @return name without extension
 */
public static String removeExtension(String fileName) {
    if (fileName != null && fileName.lastIndexOf(DOT) >= 0)
        fileName = fileName.substring(0, fileName.lastIndexOf(DOT));
    return fileName;
}

public static String normalizePath(String folderPath) {
    folderPath = normalizeSeparator(folderPath);
    if (!folderPath.endsWith(File.separator)) {
        LOGGER.debug("[WARN] path not ending with file name separator ("
                + File.separator + "), appending one");
        folderPath = folderPath.concat(File.separator);
    }
    return folderPath;
}

public static String normalizeSeparator(String folderPath) {
    return folderPath.replace("/", File.separator)
            .replace("\\", File.separator);
}

}
