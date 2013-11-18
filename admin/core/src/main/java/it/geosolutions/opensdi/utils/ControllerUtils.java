package it.geosolutions.opensdi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;

public class ControllerUtils {
	public static void setCommonModel(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String name = auth.getName(); // get logged in username

		model.addAttribute("username", name);
	}
	/**
	 * remove /../ and /./ from a path
     * @param dirString
     * @return
     */
    public static String preventDirectoryTrasversing(String dirString) {
        // prevent directory traversing
        if(dirString == null) return null;
        dirString = dirString.replace("..", "");
        // clean path
        dirString = dirString.replace("/./", "/");
        dirString = dirString.replaceAll("/{2,}", "/");
        return dirString;
    }
}
