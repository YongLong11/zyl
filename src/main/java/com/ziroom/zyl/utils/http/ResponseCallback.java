package com.ziroom.zyl.utils.http;


/**
 * Created by lyy on 16/4/14.
 */
public interface ResponseCallback<T>{

	T onResponse(int resultCode, String resultJson);
}
