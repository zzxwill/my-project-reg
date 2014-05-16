package com.ncut.reg.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 12-2-29
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 */
public class JSONHelper {
    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();
        return gson;
    }
}
