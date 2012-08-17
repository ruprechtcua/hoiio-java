package com.hoiio.sdk.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hoiio.sdk.exception.HoiioException;
import com.hoiio.sdk.util.HApiUtil;
import com.hoiio.sdk.util.HStringUtil;

public class HCallService extends HApiUtil {
	
	private static final String PARAM_DEST1 = "dest1";
	private static final String PARAM_DEST2 = "dest2";
	private static final String PARAM_CALLER_ID = "caller_id";
	private static final String PARAM_TAG = "tag";
	private static final String PARAM_NOTIFY_URL = "notify_url";
	private static final String PARAM_FROM = "from";
	private static final String PARAM_TO = "to";
	private static final String PARAM_PAGE = "page";
	private static final String PARAM_TXN_REF = "txn_ref";
	private static final String PARAM_DEST = "dest";
	private static final String PARAM_ROOM = "room";
	
	private static final String URL_VOICE_CALL = "voice/call";
	private static final String URL_VOICE_GET_RATE = "voice/get_rate";
	private static final String URL_VOICE_GET_HISTORY = "voice/get_history";
	private static final String URL_VOICE_QUERY_STATUS = "voice/query_status";
	private static final String URL_VOICE_CONFERENCE = "voice/conference";
	private static final String URL_VOICE_HANGUP = "voice/hangup";

	/**
	 * 
	 * @param secure
	 * @param appId
	 * @param accessToken
	 * @param dest1
	 * @param dest2
	 * @param callerId
	 * @param tag
	 * @param notifyUrl
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> call(boolean secure, String appId, String accessToken, String dest1, String dest2,
			String callerId, String tag, String notifyUrl) throws HoiioException {
		
		HashMap<String, String> map = initParam(appId, accessToken);
		
		map.put(PARAM_DEST2, dest2);
		map.put(PARAM_DEST1, dest1);
		map.put(PARAM_CALLER_ID, callerId);
		map.put(PARAM_TAG, tag);
		map.put(PARAM_NOTIFY_URL, notifyUrl);
		
		return post(secure, URL_VOICE_CALL, map);
	}
	
	/**
	 * 
	 * @param secure
	 * @param appId
	 * @param accessToken
	 * @param dest1
	 * @param dest2
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> getRate(boolean secure, String appId, String accessToken, 
			String dest1, String dest2) throws HoiioException {
		HashMap<String, String> map = initParam(appId, accessToken);
		
		map.put(PARAM_DEST2, dest2);		
		map.put(PARAM_DEST1, dest1);
			
		return post(secure, URL_VOICE_GET_RATE, map);
		
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
		HashMap<String, String> map = initParam(appId, accessToken);
		
		map.put(PARAM_TXN_REF, txnRef);
			
		return post(secure, URL_VOICE_QUERY_STATUS, map);		
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
		
		HashMap<String, String> map = initParam(appId, accessToken);
				
		map.put(PARAM_FROM, HStringUtil.dateToString(from));
		map.put(PARAM_TO, HStringUtil.dateToString(to));
		map.put(PARAM_PAGE, page == null ? null : page.toString());
				
		return post(secure, URL_VOICE_GET_HISTORY, map);
	}
	
	/**
	 * 
	 * @param secure
	 * @param appId
	 * @param accessToken
	 * @param dest
	 * @param room
	 * @param callerId
	 * @param tag
	 * @param notifyUrl
	 * @return
	 * @throws HoiioException
	 */
	public static Map<String, Object> conference(boolean secure, String appId, String accessToken,
			String dest, String room, String callerId, String tag, 
			String notifyUrl) throws HoiioException {
		
		HashMap<String, String> map = initParam(appId, accessToken);
				
		map.put(PARAM_DEST, dest);
		map.put(PARAM_ROOM, room);
		map.put(PARAM_CALLER_ID, callerId);
		map.put(PARAM_TAG, tag);
		map.put(PARAM_NOTIFY_URL, notifyUrl);
				
		return post(secure, URL_VOICE_CONFERENCE, map);
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
	public static Map<String, Object> hangup(boolean secure, String appId, String accessToken,
			String txnRef) throws HoiioException {
		HashMap<String, String> map = initParam(appId, accessToken);
		
		map.put(PARAM_TXN_REF, txnRef);
				
		return post(secure, URL_VOICE_HANGUP, map);
	}
}
