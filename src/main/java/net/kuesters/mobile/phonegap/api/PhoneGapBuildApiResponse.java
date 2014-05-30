package net.kuesters.mobile.phonegap.api;

import org.json.simple.JSONObject;

/**
 * The basic interface for responses from the PhoneGap Build API.
 * 
 * @author <a href="http://www.kuesters.net">Jens K&uuml;sters</a>
 */
public interface PhoneGapBuildApiResponse {
	/**
	 * Initializes the response values from a {@link JSONObject}.
	 * 
	 * @param jsonObject
	 *            the JSON object
	 */
	public void init(JSONObject jsonObject);

	/**
	 * Gets the link in the PhoneGap Build API where this response came from.
	 * 
	 * @return the API link
	 */
	public String getLink();
}
