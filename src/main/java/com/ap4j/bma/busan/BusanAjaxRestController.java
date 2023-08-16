package com.ap4j.bma.busan;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class BusanAjaxRestController {

    @GetMapping("/kim_ajaxr")
    public String mapPage(){
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;
        try {
            String apiUrl = "http://apis.data.go.kr/6260000/FoodService/getFoodKr?" +
                    "&serviceKey=HIVZzhQpW%2BXuIwc8TdHtjjITL8zkdjbLHMe80yY8Ub21YCfA%2BvEK2RUqTXp7OycPYmwRrFEsQisnLuJrSY24JQ%3D%3D"+
                    "&&numOfRows=10&pageNo=1";

            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }

            JSONObject jsonObject = XML.toJSONObject(result.toString());
            jsonPrintString = jsonObject.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonPrintString);
        return jsonPrintString;
    }

}
