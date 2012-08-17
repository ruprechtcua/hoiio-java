package com.hoiio.sdk.services;

import java.util.HashMap;
import java.util.Map;

import com.hoiio.sdk.exception.HoiioException;
import com.hoiio.sdk.util.HApiUtil;

public class HAccountService extends HApiUtil {
	private static final String URL_GET_INFO = "user/get_info";
	private static final String URL_GET_BALANCE = "user/get_balance";
	
	/**
	 * Hoiio get_info api
	 * @param appId
	 * @param accessToken
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> getInfo(boolean secure, String appId, String accessToken) throws HoiioException {
		HashMap<String, String> map = initParam(appId, accessToken);
		
		return post(secure, URL_GET_INFO, map);
	}
	
	/**
	 * Hoiio get balance api
	 * @param appId
	 * @param accessToken
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> getBalance(boolean secure, String appId, String accessToken) throws HoiioException {
		HashMap<String, String> map = initParam(appId, accessToken);
		
		return post(secure,URL_GET_BALANCE, map);
	}
}
