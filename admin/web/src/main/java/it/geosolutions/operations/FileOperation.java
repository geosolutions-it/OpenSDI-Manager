package it.geosolutions.operations;

import java.util.List;

public interface FileOperation extends Operation {
	
	/**
	 * @return List of Strings
	 */
	public List<String> getExtensions();
	public boolean isMultiple();
}
