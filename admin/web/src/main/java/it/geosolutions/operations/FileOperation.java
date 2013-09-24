package it.geosolutions.operations;

import java.util.List;

public interface FileOperation extends Operation {
	
	/**
	 * List of Strings representing all the extensions this Operation can work on.
	 * Extensions can be specified multiple times if isMultiple == true,
	 * meaning that this Operation needs more than one file to run.
	 * IE: {'shp', 'shp', 'dxf'}
	 * @return 
	 */
	public List<String> getExtensions();
	
	/**
	 * Tells if all the specified Extensions are necessary to run
	 * @return
	 */
	public boolean isMultiple();
}
