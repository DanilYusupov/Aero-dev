package com.gdc.aerodev.external.spacex;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class LastLaunchGetter {

    public static Map<String, String> getData(JsonObject json){
        Map<String, String> map = new HashMap<>(7);
        map.put("flight_number", json.get("flight_number").getAsString());
        map.put("mission_name",json.get("mission_name").getAsString());
        map.put("telemetry",json.get("telemetry").getAsJsonObject().get("flight_club").getAsString());
        map.put("launch_success",json.get("launch_success").getAsString());
        map.put("mission_patch",json.get("links").getAsJsonObject().get("mission_patch").getAsString());
        map.put("wikipedia",json.get("links").getAsJsonObject().get("wikipedia").getAsString());
        map.put("video_link",json.get("links").getAsJsonObject().get("video_link").getAsString());
        return map;
    }

}
