package net.kuesters.mobile.phonegap.api;

import java.util.HashMap;
import java.util.Map;

import net.kuesters.mobile.phonegap.model.App;
import net.kuesters.mobile.phonegap.model.App.Platform;

import org.json.simple.JSONObject;

/**
 * The class to represent the data of a read app response on the PhoneGap Build API.
 * 
 * @author <a href="http://www.kuesters.net">Jens K&uuml;sters</a>
 * @see App
 */
public class ReadAppResponse implements PhoneGapBuildApiResponse {

	/** The link. */
	private String link;

	/** The app. */
	private App app;

	/**
	 * Instantiates a new read app response.
	 * 
	 * @param jsonObject
	 *            the JSON object
	 */
	public ReadAppResponse(JSONObject jsonObject) {
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

		String title = (String) jsonObject.get("title");
		long id = (Long) jsonObject.get("id");
		String packageName = (String) jsonObject.get("package");
		String version = (String) jsonObject.get("version");
		String repo = (String) jsonObject.get("repo");
		String description = (String) jsonObject.get("description");
		boolean debug = (Boolean) jsonObject.get("debug");
		boolean privateApp = (Boolean) jsonObject.get("private");
		long buildCount = jsonObject.get("build_count") != null ? (Long) jsonObject.get("build_count") : 0;
		// the following maps stay empty. The might be filled later.
		Map<Platform, String> status = new HashMap<App.Platform, String>();
		Map<Platform, String> download = new HashMap<App.Platform, String>();
		this.app = new App(title, id, packageName, version, repo, description, debug, privateApp, buildCount, status, download);
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
	 * Gets the app.
	 * 
	 * @return the app
	 */
	public App getApp() {
		return app;
	}

}
