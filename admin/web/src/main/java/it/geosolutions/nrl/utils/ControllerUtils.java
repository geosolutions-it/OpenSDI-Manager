package it.geosolutions.nrl.utils;

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
}
