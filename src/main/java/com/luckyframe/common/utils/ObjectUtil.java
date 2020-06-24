package com.luckyframe.common.utils;

import java.lang.reflect.Field;
import java.util.Map;

public class ObjectUtil {

	public static void fillNullValue(Map<String,Object> map){
		for(String key : map.keySet()){
			if(null == map.get(key)){
				map.put(key, " ");
			}
		}
	}
}
