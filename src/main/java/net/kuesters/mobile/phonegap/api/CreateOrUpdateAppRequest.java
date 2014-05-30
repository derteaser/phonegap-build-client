package net.kuesters.mobile.phonegap.api;

import net.kuesters.mobile.phonegap.model.App;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;

/**
 * The class to represent the data for a create or an update request on the PhoneGap Build API.
 * 
 * @author <a href="http://www.kuesters.net">Jens K&uuml;sters</a>
 */
public class CreateOrUpdateAppRequest implements PhoneGapBuildApiRequest {

	/** The title. */
	private String title;

	/** The package name. */
	private String packageName;

	/** The version. */
	private String version;

	/** The description. */
	private String description;

	/** The debug. */
	private boolean debug;

	/** The private app. */
	private boolean privateApp;

	/** The phone gap version. */
	private String phoneGapVersion;

	/** The create method. */
	private String createMethod;

	/**
	 * Instantiates a new creates the or update app request.
	 * 
	 * @param title
	 *            the title
	 * @param packageName
	 *            the package name
	 * @param version
	 *            the version
	 * @param description
	 *            the description
	 * @param debug
	 *            the debug
	 * @param privateApp
	 *            the private app
	 * @param phoneGapVersion
	 *            the phone gap version
	 */
	public CreateOrUpdateAppRequest(String title, String packageName, String version, String description, boolean debug, boolean privateApp, String phoneGapVersion) {
		this.title = title;
		this.packageName = packageName;
		this.version = version;
		this.description = description;
		this.debug = debug;
		this.privateApp = privateApp;
		this.phoneGapVersion = phoneGapVersion;
		this.createMethod = "file";
	}

	/**
	 * Instantiates a new creates the or update app request.
	 * 
	 * @param app
	 *            the app
	 */
	public CreateOrUpdateAppRequest(App app, String phoneGapVersion) {
		this.title = app.getTitle();
		this.packageName = app.getPackage();
		this.version = app.getVersion();
		this.description = app.getDescription();
		this.debug = app.isDebug();
		this.privateApp = app.isPrivate();
		this.phoneGapVersion = phoneGapVersion;
		this.createMethod = "file";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.services.phonegap.api.PhoneGapBuildApiRequest#getData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getData() {
		JSONObject data = new JSONObject();
		data.put("title", getTitle());
		data.put("create_method", getCreateMethod());
		if (StringUtils.isNotBlank(getPackage()))
			data.put("package", getPackage());
		data.put("version", StringUtils.isNotBlank(getVersion()) ? getVersion() : "0.0.1");
		if (StringUtils.isNotBlank(getDescription()))
			data.put("description", getDescription());
		data.put("debug", new Boolean(isDebug()));
		data.put("private", new Boolean(isPrivate()));
		return data;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the package.
	 * 
	 * @return the package
	 */
	public String getPackage() {
		return packageName;
	}

	/**
	 * Sets the package.
	 * 
	 * @param packageName
	 *            the new package
	 */
	public void setPackage(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks if is debug.
	 * 
	 * @return true, if is debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * Sets the debug.
	 * 
	 * @param debug
	 *            the new debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * Checks if is private.
	 * 
	 * @return true, if is private
	 */
	public boolean isPrivate() {
		return privateApp;
	}

	/**
	 * Sets the private.
	 * 
	 * @param privateApp
	 *            the new private
	 */
	public void setPrivate(boolean privateApp) {
		this.privateApp = privateApp;
	}

	/**
	 * Gets the phone gap version.
	 * 
	 * @return the phone gap version
	 */
	public String getPhoneGapVersion() {
		return phoneGapVersion;
	}

	/**
	 * Sets the phone gap version.
	 * 
	 * @param phoneGapVersion
	 *            the new phone gap version
	 */
	public void setPhoneGapVersion(String phoneGapVersion) {
		this.phoneGapVersion = phoneGapVersion;
	}

	/**
	 * Gets the create method.
	 * 
	 * @return the create method that will always be <code>file</code>
	 */
	public String getCreateMethod() {
		return createMethod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreateOrUpdateAppRequest [title=" + title + ", packageName=" + packageName + ", version=" + version + ", description=" + description + ", debug=" + debug + ", privateApp="
				+ privateApp + ", phoneGapVersion=" + phoneGapVersion + ", createMethod=" + createMethod + "]";
	}

}
