package it.geosolutions.nrl.mvc.model.statistics;

import java.util.HashMap;
import java.util.Map;

public class FileBrowserManager {
	private Map<String,String> baseDirs;
	private Map<String,FileBrowser> fileBrowsers;
	
	public FileBrowser getFileBrowser(String name){
		if(!baseDirs.containsKey(name)){
			return null;
		}
		String dir = baseDirs.get(name);
		if(fileBrowsers == null){
			fileBrowsers = new HashMap<String,FileBrowser>();
		}
		if(!fileBrowsers.containsKey(name)){
			FileBrowser fb = new FileBrowser();
			fb.setBaseDir(dir);
			fileBrowsers.put(name,fb);
		}
		return fileBrowsers.get(name);
		
		
	}
	public Map<String, String> getBaseDirs() {
		return baseDirs;
	}

	public void setBaseDirs(Map<String, String> baseDirs) {
		this.baseDirs = baseDirs;
	}
}
