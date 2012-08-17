package com.hoiio.sdk.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hoiio.sdk.exception.HoiioException;
import com.hoiio.sdk.util.HApiUtil;
import com.hoiio.sdk.util.HStringUtil;

public class HSmsService extends HApiUtil {
	
	private static final String PARAM_DEST = "dest";
	private static final String PARAM_SENDER_NAME = "sender_name";
	private static final String PARAM_MSG = "msg";
	private static final String PARAM_TAG = "tag";
	private static final String PARAM_NOTIFY_URL = "notify_url";
	private static final String PARAM_FROM = "from";
	private static final String PARAM_TO = "to";
	private static final String PARAM_PAGE = "page";
	private static final String PARAM_TXN_REF = "txn_ref";
	
	private static final String URL_SMS_SEND = "sms/send";
	private static final String URL_SMS_GET_RATE = "sms/get_rate";
	private static final String URL_SMS_GET_HISTORY = "sms/get_history";
	private static final String URL_SMS_QUERY_STATUS = "sms/query_status";

	/**
	 * 
	 * @param secure
	 * @param appId
	 * @param accessToken
	 * @param dest
	 * @param senderName
	 * @param msg
	 * @param tag
	 * @param notifyUrl
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> send(boolean secure, String appId, String accessToken, String dest, 
			String senderName, String msg, String tag, String notifyUrl) throws HoiioException {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(initParam(appId, accessToken));
		
		map.put(PARAM_DEST, dest);
		map.put(PARAM_MSG, msg);
		map.put(PARAM_SENDER_NAME, senderName);
		map.put(PARAM_TAG, tag);
		map.put(PARAM_NOTIFY_URL, notifyUrl);
		
		return post(secure, URL_SMS_SEND, map);
	}
	
	/**
	 * 
	 * @param secure
	 * @param appId
	 * @param accessToken
	 * @param dest
	 * @param msg
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> getRate(boolean secure, String appId, String accessToken, 
			String dest, String msg) throws HoiioException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(initParam(appId, accessToken));
		
		map.put(PARAM_DEST, dest);
		map.put(PARAM_MSG, msg);
			
		return post(secure, URL_SMS_GET_RATE, map);		
	}
	
	/**
	 * 
	 * @param secure
	 * @param appId
	 * @param accessToken
	 * @param txnRef
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> queryStatus(boolean secure, String appId, String accessToken,  
			String txnRef) throws HoiioException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(initParam(appId, accessToken));
		
		map.put(PARAM_TXN_REF, txnRef);
			
		return post(secure, URL_SMS_QUERY_STATUS, map);		
	}
	
	/**
	 * 
	 * @param secure
	 * @param appId
	 * @param accessToken
	 * @param from
	 * @param to
	 * @param page
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> getHistory(boolean secure, String appId, String accessToken,
			Date from, Date to, Integer page) throws HoiioException {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.putAll(initParam(appId, accessToken));
				
		map.put(PARAM_FROM, HStringUtil.dateToString(from));
		map.put(PARAM_TO, HStringUtil.dateToString(to));
		map.put(PARAM_PAGE, page == null ? null : page.toString());
				
		return post(secure, URL_SMS_GET_HISTORY, map);
	}	
}