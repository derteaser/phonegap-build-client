package net.kuesters.mobile.phonegap.api;

import org.json.simple.JSONObject;

/**
 * The class to represent the data of a create or an update response on the PhoneGap Build API.
 * 
 * @author <a href="http://www.kuesters.net">Jens K&uuml;sters</a>
 */
public class CreateOrUpdateAppResponse extends ReadAppResponse {

	/**
	 * Instantiates a new create or update app response object from a JSON response.
	 * 
	 * @param jsonObject
	 *            the JSON object
	 */
	public CreateOrUpdateAppResponse(JSONObject jsonObject) {
		super(jsonObject);
	}

}
