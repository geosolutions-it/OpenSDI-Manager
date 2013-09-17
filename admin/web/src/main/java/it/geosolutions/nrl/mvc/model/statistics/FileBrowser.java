package it.geosolutions.nrl.mvc.model.statistics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileBrowser {
	private String baseDir="";
	private String regex;
	
	@XmlElement
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public List<String> getFiles(){
		File dir = new File(baseDir);
		if( !dir.isDirectory() ) return null;
		File[] children = dir.listFiles();
		List<String> ret = new ArrayList<String>();
		for (int i=0;i<children.length;i++){
			String name = children[i].getName();
			if(regex!=null){
				Pattern pattern = Pattern.compile(regex);
				Matcher match = pattern.matcher(name);
				if(match.matches() && !children[i].isDirectory()){
					ret.add(children[i].getName());
				}
			}else{
				if(!children[i].isDirectory()){
					ret.add(children[i].getName());
				}
			}
		}
		return ret;
	}
	public List<String> getDirs(){
		File dir = new File(baseDir);
		if( !dir.isDirectory() ) return null;
		File[] children = dir.listFiles();
		List<String> ret = new ArrayList<String>();
		for (int i=0;i<children.length;i++){
			if(children[i].isDirectory()){
				ret.add(children[i].getName());
			}
		}
		return ret;
	}
	
	@XmlElement
	public String getBaseDir() {
		return baseDir;
	}
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
		
	}
	
}
