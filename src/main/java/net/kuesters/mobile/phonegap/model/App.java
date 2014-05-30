package net.kuesters.mobile.phonegap.model;

import java.util.Map;

/**
 * A model class representing an app as defined by the PhoneGap Build API.
 * 
 * @author <a href="http://www.kuesters.net">Jens K&uuml;sters</a>
 */
public class App {

	/**
	 * The available mobile platforms.
	 */
	public enum Platform {

		/** The Android platform. */
		ANDROID,
		/** The Blackberry platform. */
		BLACKBERRY,
		/** The iOS platform. */
		IOS,
		/** The Symbian platform. */
		SYMBIAN,
		/** The webOS platform. */
		WEBOS,
		/** The Windows Phone platform. */
		WINPHONE;
	}

	/** The title. */
	private String title;

	/** The id. */
	private long id;

	/** The package name. */
	private String packageName;

	/** The current version. */
	private String version;

	/** The repository address. */
	private String repo;

	/** The description. */
	private String description;

	/** The debug mode selection. */
	private boolean debug;

	/** The private app selection. */
	private boolean privateApp;

	/** The build count. */
	private long buildCount;

	/** The map with the status of the apps for every {@link Platform}. */
	private Map<Platform, String> status;

	/** The map with downloads of the built apps for every {@link Platform}. */
	private Map<Platform, String> download;

	/**
	 * Instantiates a new app.
	 * 
	 * @param title
	 *            the title
	 * @param id
	 *            the id
	 * @param packageName
	 *            the package name
	 * @param version
	 *            the version
	 * @param repo
	 *            the repository address
	 * @param description
	 *            the description
	 * @param debug
	 *            the debug mode
	 * @param privateApp
	 *            the private app state
	 * @param buildCount
	 *            the build count
	 * @param status
	 *            the map of status
	 * @param download
	 *            the map of downloads
	 */
	public App(String title, long id, String packageName, String version, String repo, String description, boolean debug, boolean privateApp, long buildCount, Map<Platform, String> status,
			Map<Platform, String> download) {
		this.title = title;
		this.id = id;
		this.packageName = packageName;
		this.version = version;
		this.repo = repo;
		this.description = description;
		this.debug = debug;
		this.privateApp = privateApp;
		this.buildCount = buildCount;
		this.status = status;
		this.download = download;
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
	 * Gets the ID.
	 * 
	 * @return the ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the ID.
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(long id) {
		this.id = id;
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
	 * Gets the repository address.
	 * 
	 * @return the repo
	 */
	public String getRepo() {
		return repo;
	}

	/**
	 * Sets the repository address.
	 * 
	 * @param repo
	 *            the new repo
	 */
	public void setRepo(String repo) {
		this.repo = repo;
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
	 * Checks if the app is in debug mode.
	 * 
	 * @return true, if is in debug mode
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * Sets if the app is in debug mode.
	 * 
	 * @param debug
	 *            the new debug mode
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * Checks if the app is private.
	 * 
	 * @return true, if is private
	 */
	public boolean isPrivate() {
		return privateApp;
	}

	/**
	 * Sets if the app is private.
	 * 
	 * @param privateApp
	 *            the new private
	 */
	public void setPrivate(boolean privateApp) {
		this.privateApp = privateApp;
	}

	/**
	 * Gets the builds count.
	 * 
	 * @return the builds count
	 */
	public long getBuildCount() {
		return buildCount;
	}

	/**
	 * Gets the status map.
	 * 
	 * @return the status map
	 */
	public Map<Platform, String> getStatus() {
		return status;
	}

	/**
	 * Gets the downloads map.
	 * 
	 * @return the downloads map
	 */
	public Map<Platform, String> getDownload() {
		return download;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "App [title=" + title + ", id=" + id + ", package=" + packageName + ", version=" + version + "]";
	}

}
