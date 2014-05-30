package net.kuesters.mobile.phonegap;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import net.kuesters.mobile.MobileApp;
import net.kuesters.mobile.phonegap.api.CreateOrUpdateAppRequest;
import net.kuesters.mobile.phonegap.api.CreateOrUpdateAppResponse;
import net.kuesters.mobile.phonegap.api.ReadAppResponse;
import net.kuesters.mobile.phonegap.api.ReadAppsResponse;
import net.kuesters.mobile.phonegap.model.App;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

/**
 * A client class to handle the communication with the <a
 * href="http://build.phonegap.com/docs/api">PhoneGap Build API</a>.
 * 
 * @author <a href="http://www.kuesters.net">Jens K&uuml;sters</a>
 */
public class PhoneGapBuildClient {

	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(PhoneGapBuildClient.class);

	/** The Constant URL_TOKEN. */
	private static final String URL_TOKEN = "https://build.phonegap.com/token";

	/** The Constant URL_APPS. */
	private static final String URL_APPS = "https://build.phonegap.com/api/v1/apps";

	/** The token. */
	private final String token;

	/**
	 * Instantiates a new phone gap build client.
	 * 
	 * @param username
	 *            the user name
	 * @param password
	 *            the password
	 * @throws NoClientCreatedException
	 *             the no client created exception
	 */
	public PhoneGapBuildClient(String username, String password) throws NoClientCreatedException {
		try {
			this.token = getToken(username, password);
			if (StringUtils.isBlank(this.token))
				throw new NoClientCreatedException();
		} catch (Exception e) {
			LOG.fatal("Could not create a new client object.", e);
			throw new NoClientCreatedException();
		}
	}

	/**
	 * Gets a list of all apps.
	 * 
	 * @return the apps
	 */
	public List<App> getApps() {
		try {
			HttpClient httpClient = getHttpClient();
			HttpGet httpGet = new HttpGet(URL_APPS + "?auth_token=" + this.token);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity responseEntity = response.getEntity();

			if (response.getStatusLine().getStatusCode() >= 500) {
				LOG.warn("Response code: " + response.getStatusLine().getStatusCode());
				LOG.warn("Reason: " + response.getStatusLine().getReasonPhrase());
				return null;
			}

			if (responseEntity != null) {
				JSONObject jsonObject = (JSONObject) JSONValue.parse(EntityUtils.toString(responseEntity));
				return !hasErrors(jsonObject) ? new ReadAppsResponse(jsonObject).getApps() : null;
			}
		} catch (Exception e) {
			LOG.fatal("Could not get apps", e);
		}

		return null;
	}

	/**
	 * Gets an app.
	 * 
	 * @param id
	 *            the ID of the app
	 * @return the read app response object
	 */
	public ReadAppResponse getApp(long id) {
		try {
			HttpClient httpClient = getHttpClient();
			HttpGet httpGet = new HttpGet(URL_APPS + "/" + id + "?auth_token=" + this.token);
			HttpResponse response = httpClient.execute(httpGet);

			if (response.getStatusLine().getStatusCode() == 404) {
				LOG.warn("The app with ID " + id + " does not exist.");
				return null;
			}

			if (response.getStatusLine().getStatusCode() >= 500) {
				LOG.warn("Response code: " + response.getStatusLine().getStatusCode());
				LOG.warn("Reason: " + response.getStatusLine().getReasonPhrase());
				return null;
			}

			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				JSONObject jsonObject = (JSONObject) JSONValue.parse(EntityUtils.toString(responseEntity));
				return !hasErrors(jsonObject) ? new ReadAppResponse(jsonObject) : null;
			}
		} catch (Exception e) {
			LOG.fatal("Could not get app with id " + id, e);
		}

		return null;
	}

	/**
	 * Gets an app.
	 * 
	 * @param mobileApp
	 *            the mobile app
	 * @return the read app response object
	 */
	public ReadAppResponse getApp(MobileApp mobileApp) {
		return getApp(mobileApp.getPhoneGapBuildId());
	}

	/**
	 * Creates or updates an app.
	 * 
	 * @param id
	 *            the ID of the app, an ID <= 0 will create a new app
	 * @param request
	 *            the request object
	 * @return the response object
	 */
	public CreateOrUpdateAppResponse createOrUpdateApp(long id, CreateOrUpdateAppRequest request, byte[] zipData, String zipFilename) {
		try {
			MultipartEntity requestEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			requestEntity.addPart("data", new StringBody(request.getData().toJSONString()));
			if (zipData != null && StringUtils.isNotBlank(zipFilename))
				requestEntity.addPart("file", new ByteArrayBody(zipData, zipFilename));

			HttpClient httpClient = getHttpClient();
			HttpResponse response;

			if (id > 0) {
				// Update
				HttpPut httpPut = new HttpPut(URL_APPS + "/" + id + "?auth_token=" + this.token);
				httpPut.setEntity(requestEntity);
				response = httpClient.execute(httpPut);

				if (response.getStatusLine().getStatusCode() == 404) {
					LOG.warn("The app with ID " + id + " does not exist.");
					return null;
				}
			} else {
				// Create
				HttpPost httpPost = new HttpPost(URL_APPS + "?auth_token=" + this.token);
				httpPost.setEntity(requestEntity);
				response = httpClient.execute(httpPost);
			}

			if (response.getStatusLine().getStatusCode() >= 500) {
				LOG.warn("Response code: " + response.getStatusLine().getStatusCode());
				LOG.warn("Reason: " + response.getStatusLine().getReasonPhrase());
				return null;
			}

			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				JSONObject jsonObject = (JSONObject) JSONValue.parse(EntityUtils.toString(responseEntity));
				return !hasErrors(jsonObject) ? new CreateOrUpdateAppResponse(jsonObject) : null;
			}
		} catch (Exception e) {
			LOG.fatal(id > 0 ? "Could not update app with id " + id : "Could not create new app", e);
		}

		return null;
	}

	/**
	 * Creates or updates an app.
	 * 
	 * @param mobileApp
	 *            the mobile app
	 * @param debug
	 *            true to enable the debug mode
	 * @param privateApp
	 *            true to make the app private
	 * @param phoneGapVersion
	 *            the PhoneGap version to be used by PhoneGap Build
	 * @return the response object
	 */
	public CreateOrUpdateAppResponse createOrUpdateApp(MobileApp mobileApp, boolean debug, boolean privateApp, String phoneGapVersion) {
		CreateOrUpdateAppRequest request = new CreateOrUpdateAppRequest(mobileApp.getName(), mobileApp.getPackageName(), mobileApp.getAppVersion(), mobileApp.getDescription(), debug, privateApp,
				phoneGapVersion);
		return createOrUpdateApp(mobileApp.getPhoneGapBuildId(), request, mobileApp.getContentsZip(), mobileApp.getContentsZipFileName());
	}

	/**
	 * Deletes an app.
	 * 
	 * @param id
	 *            the ID of the app to be deleted
	 * @return true, if successful
	 */
	public boolean deleteApp(long id) {
		try {
			HttpClient httpClient = getHttpClient();
			HttpDelete httpDelete = new HttpDelete(URL_APPS + "/" + id + "?auth_token=" + this.token);
			HttpResponse response = httpClient.execute(httpDelete);

			if (response.getStatusLine().getStatusCode() == 202) {
				LOG.info("The app with ID " + id + " has been deleted.");
				return true;
			} else if (response.getStatusLine().getStatusCode() == 404) {
				LOG.warn("The app with ID " + id + " does not exist.");
				return false;
			}

		} catch (Exception e) {
			LOG.fatal("Could not delete app with id " + id, e);
		}
		return false;
	}

	/**
	 * Deletes an app.
	 * 
	 * @param mobileApp
	 *            the mobile app to be deleted
	 * @return true, if successful
	 */
	public boolean deleteApp(MobileApp mobileApp) {
		return deleteApp(mobileApp.getPhoneGapBuildId());
	}

	/**
	 * Gets a token to append to all API request URLs.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the token
	 * @throws KeyManagementException
	 *             the key management exception
	 * @throws UnrecoverableKeyException
	 *             the unrecoverable key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws KeyStoreException
	 *             the key store exception
	 * @throws ClientProtocolException
	 *             the client protocol exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParseException
	 *             the parse exception
	 * @throws ParseException
	 *             the parse exception
	 */
	private String getToken(String username, String password) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, ClientProtocolException,
			IOException, org.apache.http.ParseException, ParseException {
		AbstractHttpClient httpClient = getHttpClient();
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
		httpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);

		HttpPost httpPost = new HttpPost(URL_TOKEN);
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity responseEntity = response.getEntity();

		String token = StringUtils.EMPTY;
		if (responseEntity != null) {
			JSONObject jsonObject = (JSONObject) JSONValue.parse(EntityUtils.toString(responseEntity));
			if (!hasErrors(jsonObject) && jsonObject.containsKey("token"))
				token = (String) jsonObject.get("token");
			else
				LOG.fatal("Colud not retrieve token.");
		}
		return token;
	}

	/**
	 * Gets an HTTP client on which requests can be executed.
	 * <p>
	 * Because PhoneGap Build's server certificate is not part of Java's default
	 * keystore, the returned client will accept SSL connections with self
	 * signed certificates from all hostnames.
	 * </p>
	 * 
	 * @return the HTTP client
	 * @throws KeyManagementException
	 *             the key management exception
	 * @throws UnrecoverableKeyException
	 *             the unrecoverable key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws KeyStoreException
	 *             the key store exception
	 */
	private AbstractHttpClient getHttpClient() throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
		SSLSocketFactory sslFactory = new SSLSocketFactory(new TrustSelfSignedStrategy(), SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("https", 443, sslFactory));
		BasicClientConnectionManager cm = new BasicClientConnectionManager(schemeRegistry);

		return new DefaultHttpClient(cm);
	}

	/**
	 * Checks if a JSON response contains a key named "error".
	 * <p>
	 * If the JSON contains an error key, it's value will be logged.
	 * </p>
	 * 
	 * @param jsonObject
	 *            the JSON response object
	 * @return true if the object contains a key named "error", otherwise false
	 */
	private boolean hasErrors(JSONObject jsonObject) {
		if (jsonObject == null) {
			LOG.fatal("JSON object is null");
			return true;
		}
		if (jsonObject.containsKey("error") && jsonObject.get("error") instanceof String && StringUtils.isNotBlank((String) jsonObject.get("error"))) {
			LOG.fatal(jsonObject.get("error"));
			return true;
		} else {
			return false;
		}
	}
}
