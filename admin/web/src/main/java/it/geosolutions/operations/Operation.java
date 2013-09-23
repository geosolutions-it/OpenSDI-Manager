package it.geosolutions.operations;

public interface Operation {
	
	/**
	 * @return the name of this Operation
	 */
	public String getName();
	
	/**
	 * @return the REST path where to send GET and POST requests
	 */
	public String getRESTPath();
	
	/**
	 * @return the Jsp name to build the GUI
	 */
	public String getJsp();

}
