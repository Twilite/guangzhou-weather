package com.weather.maven.webAPIClient;
import java.text.MessageFormat;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class App 
{
    public static void main(String[] args)
    {
    	Unirest.config()
    		.connectTimeout(3000)
    		.socketTimeout(1800)
    		.setDefaultHeader("Accept", "application/json");
    	// 初始化完成
    	try {
    		HttpResponse<JsonNode> result = Unirest.get("https://api.darksky.net/forecast/${token}/${latitude},${longitude}?lang={langcode}&units=auto&exclude=hourly%2Cminutely%2Cflags")
    					.asJson();
    				if (result.getStatus() != 200) {
    					throw new RuntimeException("HTTP 未返回 200 状态码，错误");
    				}
    		JSONObject weather = result.getBody().getObject();			// 得到 JSON 对象
    		System.out.println("目前天气情况：" + weather.getJSONObject("currently").getString("summary"));
    		System.out.println("温度：" + weather.getJSONObject("currently").getString("temperature") + "°C");
    		System.out.println("湿度：" + MessageFormat.format("{0,number,percent}" , Double.parseDouble(weather.getJSONObject("currently").getString("humidity"))));
    		System.out.println("降水概率：" + MessageFormat.format("{0,number,percent}" , Double.parseDouble(weather.getJSONObject("currently").getString("precipProbability"))));
    		System.out.println();
    		System.out.println("未来天气情况：" + weather.getJSONObject("daily").getString("summary"));
    		System.out.println("明天" + weather.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("summary") + "温度 " + weather.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureMax") + "°C — " + weather.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureMin") + "°C，降水概率为 " + MessageFormat.format("{0,number,percent}" , Double.parseDouble(weather.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("precipProbability"))));
    		System.out.println("后天" + weather.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("summary") + "温度 " + weather.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureMax") + "°C — " + weather.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureMin") + "°C，降水概率为 " + MessageFormat.format("{0,number,percent}" , Double.parseDouble(weather.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("precipProbability"))));
    	} 
    	catch (Exception e) {
            System.out.println("发生异常: - " + e);
    	}
    }
}
