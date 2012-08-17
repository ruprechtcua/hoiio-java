package com.hoiio.sdk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.hoiio.sdk.exception.HoiioException;

public class HApiUtil {
	private static final Logger logger = Logger.getLogger(HApiUtil.class);
	
	private static final String PARAM_APP_ID = "app_id";
	private static final String PARAM_ACCESS_TOKEN = "access_token";
	
	private static final String HTTP = "http://";
	private static final String HTTPS = "https://";

	private static final String API_BASE_URL = "secure.hoiio.com/open/";
	
	protected static final String INTERNAL_SERVER_EXCEPTION = "internal_server_exception";
	protected static final String API_OUT_STATUS = "status";
	
	protected static final String API_OUT_SUCCESS = "success_ok";
	
	protected static Map<String, Object> post(boolean secure, String urlString, HashMap<String, String> map) throws HoiioException {
		if(secure){
			return doHttpsPost(urlString, map);
		}
		return doHttpPost(urlString, map);
	}
	
	protected static HashMap<String, String> initParam(String appId, String accessToken) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(PARAM_APP_ID, appId);
		map.put(PARAM_ACCESS_TOKEN, accessToken);
		
		return map;
	}
	
	
	private static Map<String, Object> doHttpsPost(String urlString, HashMap<String, String> map) throws HoiioException {
		String output = "";
		HttpsURLConnection urlConn = null;

		String content = HStringUtil.convertMapToUrlEncodedString(map);
		
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());

			// Create the socket connection and open it to the secure remote web server
			String completeUrl = HTTPS + API_BASE_URL + urlString;
			URL url = new URL(completeUrl);
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			urlConn = (HttpsURLConnection) url.openConnection();

			// Once the connection is open to the remote server we have to replace the default HostnameVerifier
			// with one of our own since we want the client to bypass the peer and submitted host checks even
			// if they are not equal. If this routine were not here, then this client would claim that the submitted
			// host and the peer host are not equal.
			urlConn.setHostnameVerifier(
					new HostnameVerifier() {

						@Override
						public boolean verify(String rserver, SSLSession sses) {
							return true;
						}
					});

			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			output = doHttpURLConnectionPost(urlConn, content);
			
			JSONObject json = JSONObject.fromObject(output);
			
			if (json == null) {
				throw new HoiioException(INTERNAL_SERVER_EXCEPTION);
			}
			
			String status = json.getString(API_OUT_STATUS);
			if (status == null) {
				throw new HoiioException(INTERNAL_SERVER_EXCEPTION);
			}
			
			logRequest(completeUrl, content, output);
			
			return parseResult(json);
		} catch (NoSuchAlgorithmException e) {
			throw new HoiioException(INTERNAL_SERVER_EXCEPTION);
		} catch (KeyManagementException e) {
			throw new HoiioException(INTERNAL_SERVER_EXCEPTION);
		} catch (IOException e) {
			throw new HoiioException(INTERNAL_SERVER_EXCEPTION);
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}

	}

	private static Map<String, Object> doHttpPost(String urlString, HashMap<String, String> map) throws HoiioException {
		
		String output = "";
		
		HttpURLConnection urlConn = null;
		try {
			String completeUrl = HTTP + API_BASE_URL + urlString;
			URL url = new URL(completeUrl);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			String content = HStringUtil.convertMapToUrlEncodedString(map);
			
			output = doHttpURLConnectionPost(urlConn, content);
			
			JSONObject json = JSONObject.fromObject(output);
			
			if (json == null) {
				throw new HoiioException(INTERNAL_SERVER_EXCEPTION);
			}
			
			String status = json.getString(API_OUT_STATUS);
			if (status == null) {
				throw new HoiioException(INTERNAL_SERVER_EXCEPTION);
			}
			
			logRequest(completeUrl, content, output);
			
			return parseResult(json);
		} catch (java.io.IOException e) {
			throw new HoiioException(INTERNAL_SERVER_EXCEPTION);
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
	}
	
	private static String doHttpURLConnectionPost(HttpURLConnection con, String content) throws HoiioException {
		String output = "";

		BufferedReader br = null;
		InputStream is = null;

		OutputStream os = null;

		try {
			con.setConnectTimeout(60000);
			con.setReadTimeout(60000);

			os = con.getOutputStream();
			os.write(content.getBytes("UTF-8"));

			if (con.getResponseCode() >= 400) {
				is = con.getErrorStream();
			} else {
				is = con.getInputStream();
			}

			br = new BufferedReader(new InputStreamReader(is));

			StringBuilder sb = new StringBuilder();
			String str;
			while (null != ((str = br.readLine()))) {
				sb.append(str);
			}
			output = sb.toString();
		} catch (java.net.MalformedURLException e) {
			throw new HoiioException(INTERNAL_SERVER_EXCEPTION);
		} catch (java.io.IOException e) {
			throw new HoiioException(INTERNAL_SERVER_EXCEPTION);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (os != null) {
				try {
					os.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return output;
	}
	
	private static Map<String, Object> parseResult(JSONObject json) throws HoiioException {
		String status = json.getString(API_OUT_STATUS);
		
		if (!API_OUT_SUCCESS.equals(status)) {
			throw new HoiioException(status);
		}
		
		return HStringUtil.jsonToMap(json);
	}
	
	private static void logRequest(String url, String input, String output){
		logger.debug("\n-------------------- HTTP POST --------------------"
				+ "\nPOST URL: [" + url + "]" + "\nINPUT: ["
				+ input + "]" + "\nOUPUT: [" + output + "]"
				+ "\n----------------------- END -----------------------");
	}
	
	static TrustManager[] trustAllCerts = new TrustManager[] {
		new X509TrustManager() {

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[0];
			}

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		}
};
}
