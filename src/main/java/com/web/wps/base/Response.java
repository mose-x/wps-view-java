package com.web.wps.base;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zm
 * @since 1.0
 * @version 1.0 2019年7月14日
 */
public class Response {
	// keys
	private final static String MSG_KEY = "msg";
	private final static String STATUS_KEY = "status";
	private final static String CODE_KEY = "code";
	private final static String DATA_KEY = "data";

	// msg
	private final static String SUCCESS_MSG = "ok";

	// value
	private final static String SUCCESS_VALUE = "success";

	/**
	 * 请求成功,并返回请求结果集
	 *
	 * @param data
	 *            返回到客户端的对象
	 * @return Spring mvc ResponseEntity
	 */
	public static ResponseEntity<Object> success(Map<String, Object> data, String msg) {
		return getObjectResponseEntity(data, msg);
	}

	public static ResponseEntity<Object> success(String msg) {
		Map<String, Object> result = new HashMap<String, Object>(){
			{
				put(STATUS_KEY, SUCCESS_VALUE);
				put(MSG_KEY, msg);
				put(CODE_KEY, HttpStatus.OK.value());
			}
		};
		return getEntity(result);
	}

	public static ResponseEntity<Object> success() {
		Map<String, Object> result = new HashMap<String, Object>(){
			{
				put(STATUS_KEY, SUCCESS_VALUE);
				put(MSG_KEY, SUCCESS_MSG);
				put(CODE_KEY, HttpStatus.OK.value());
			}
		};
		return getEntity(result);
	}

	/**
	 * 请求成功,并返回请求结果集
	 *
	 * @param data
	 *            返回到客户端的对象
	 * @return Spring mvc ResponseEntity
	 */
	public static ResponseEntity<Object> success(Map<String, Object> data) {
		return getObjectResponseEntity(data, SUCCESS_MSG);
	}

	public static ResponseEntity<Object> success(Object data) {
		Map<String, Object> result = new HashMap<String, Object>(){
			{
				put(STATUS_KEY, SUCCESS_VALUE);
				put(MSG_KEY, SUCCESS_MSG);
				put(CODE_KEY, HttpStatus.OK.value());
				put(DATA_KEY,data);
			}
		};
		return getEntity(result);
	}

	public static ResponseEntity<Object> bad(String msg) {
		Map<String, Object> result = new HashMap<String, Object>(){
			{
				put(STATUS_KEY, SUCCESS_VALUE);
				put(MSG_KEY, msg);
				put(CODE_KEY, HttpStatus.BAD_REQUEST.value());
			}
		};
		return getEntity(result);
	}

	private static ResponseEntity<Object> getObjectResponseEntity(Map<String, Object> data, String msg) {
		Map<String, Object> result = new HashMap<String, Object>(){
			{
				put(STATUS_KEY, SUCCESS_VALUE);
				put(MSG_KEY, msg);
				put(CODE_KEY, HttpStatus.OK.value());
				for (Map.Entry<String, Object> entry : data.entrySet()) {
					put(entry.getKey(),entry.getValue());
				}
			}
		};
		return getEntity(result);
	}

	private static ResponseEntity<Object> getEntity(Object body) {
		List<String> contentType = new ArrayList<String>(){
			{
				add("application/json;charset=utf-8");
			}
		};
		MultiValueMap<String, String> headers = new HttpHeaders(){
			{
				put("Content-Type", contentType);
			}
		};
		return new ResponseEntity<>(body, headers, HttpStatus.OK);
	}

}
