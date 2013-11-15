package it.geosolutions.opensdi.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultLandingPageController {

	
	/**
	 * Default controller
	 * just a proxy to the real Home
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String landingPage(ModelMap model, HttpServletRequest request ) {
	    if(request.isUserInRole("ROLE_ADMIN")){
	        
	        return "redirect:users";
            
            
        }
		return "redirect:login";
 
	}

        /**
         * Proxy to the real about
         * 
         * @return about page
         */
        @RequestMapping(value="/about", method = RequestMethod.GET)
        public String aboudPage() {
                return "about";
        }
	
}