package com.hoiio.sdk.services;

import java.util.HashMap;
import java.util.Map;

import com.hoiio.sdk.exception.HoiioException;
import com.hoiio.sdk.util.HApiUtil;

public class HNumberService extends HApiUtil {
	public enum Mode {
		VOICE, FAX
	}

	private static final String PARAM_NUMBER = "number";
	private static final String PARAM_IN_FORWARD_TO_URL = "forward_to";
	private static final String PARAM_SMS_FORWARD_TO_URL = "forward_to_sms";
	private static final String PARAM_MODE = "mode";

	private static final String URL_UPDATE_FORWARDING = "number/update_forwarding";
	private static final String URL_GET_ACTIVE = "number/get_active";

	/**
	 * Update Forwarding URL
	 * @param appId
	 *            Application ID assigned to your application.
	 * @param accessToken
	 *            Access Token assigned to your application.
	 * @param hoiioNumber
	 *            The Hoiio Number to configure. Phone numbers should start with
	 *            a "+" and country code (E.164 format), e.g. +6511111111.
	 * @param inForwardToUrl
	 *            (optional) A fully-qualified HTTP/S URL on your web server to
	 *            be notified when there is an incoming voice call/fax. See IVR
	 *            API - Incoming Calls and FAX API - Receive Fax for details.
	 * @param smsForwardToUrl
	 *            (optional) A fully-qualified HTTP/S URL on your web server to
	 *            be notified when there is an incoming SMS. See Receive SMS for
	 *            details.
	 * @param mode
	 *            (optional) Indicate whether to use the number for incoming
	 *            voice call or fax. By default, numbers are configured for
	 *            incoming voice. Possible values are: voice, fax.
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> updateForwarding(boolean secure, String appId,
			String accessToken, String hoiioNumber, String inForwardToUrl,
			String smsForwardToUrl, Mode mode) throws HoiioException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(initParam(appId, accessToken));

		map.put(PARAM_NUMBER, hoiioNumber);
		map.put(PARAM_IN_FORWARD_TO_URL, inForwardToUrl);
		map.put(PARAM_SMS_FORWARD_TO_URL, smsForwardToUrl);
		map.put(PARAM_MODE, mode.toString().toLowerCase());

		return post(secure,URL_UPDATE_FORWARDING, map);
	}

	/**
	 * Get Active
	 * @param appId Application ID assigned to your application.
	 * @param accessToken Access Token assigned to your application.
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> getActive(boolean secure,String appId,
			String accessToken) throws HoiioException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(initParam(appId, accessToken));

		return post(secure, URL_GET_ACTIVE, map);
	}
}
