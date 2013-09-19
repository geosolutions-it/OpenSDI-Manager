package it.geosolutions.nrl.model;

import java.io.File;
import java.text.SimpleDateFormat;

public class JSPFile extends File{

	/**
	 * File utility class to provide JSP compliant methods
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static final long serialVersionUID = 6112519219077616669L;
	
	public JSPFile(String path) {
		super(path);
	}

	public long getSize() {
		return this.length();
	}
	public String getLastModified() {
		return sdf.format(this.lastModified());
	}
}
