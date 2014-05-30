package net.kuesters.mobile.phonegap.api;

import org.json.simple.JSONObject;

/**
 * The basic interface for requests on the PhoneGap Build API.
 * 
 * @author <a href="http://www.kuesters.net">Jens K&uuml;sters</a>
 */
public interface PhoneGapBuildApiRequest {

	/**
	 * Gets the data needed for the request as JSON.
	 * 
	 * @return the data
	 */
	public JSONObject getData();
}
