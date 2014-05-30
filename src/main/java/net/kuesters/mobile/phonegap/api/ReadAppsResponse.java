package net.kuesters.mobile.phonegap.api;

import java.util.ArrayList;
import java.util.List;

import net.kuesters.mobile.phonegap.model.App;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The class to represent the data of a read apps response on the PhoneGap Build API.
 * 
 * @author <a href="http://www.kuesters.net">Jens K&uuml;sters</a>
 * @see App
 */
public class ReadAppsResponse implements PhoneGapBuildApiResponse {

	/** The link. */
	private String link;

	/** The apps. */
	private List<App> apps;

	/**
	 * Instantiates a new read apps response.
	 * 
	 * @param jsonObject
	 *            the JSON object
	 */
	public ReadAppsResponse(JSONObject jsonObject) {
		init(jsonObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * app.services.phonegap.api.PhoneGapBuildApiResponse#init(org.json.simple
	 * .JSONObject)
	 */
	@Override
	public void init(JSONObject jsonObject) {
		this.link = (String) jsonObject.get("link");

		this.apps = new ArrayList<App>();
		JSONArray appsArray = (JSONArray) jsonObject.get("apps");
		for (Object object : appsArray) {
			ReadAppResponse appResponse = new ReadAppResponse((JSONObject) object);
			this.apps.add(appResponse.getApp());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.services.phonegap.api.PhoneGapBuildApiResponse#getLink()
	 */
	@Override
	public String getLink() {
		return link;
	}

	/**
	 * Gets the list of apps.
	 * 
	 * @return the apps
	 */
	public List<App> getApps() {
		return apps;
	}

}
