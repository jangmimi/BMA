package com.ap4j.bma.controller.api;

import com.ap4j.bma.model.entity.apt.AptDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Controller
public class ApiAptController {

    /** 아파트 실거래가 api 불러오기 */
    @GetMapping("csy")
    public String apt(Model model) {
        StringBuffer result = new StringBuffer();
        int pageNo = 1;

        ArrayList<AptDTO> aptList = null;
        try {
            String apiUrl = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?"
                        + "serviceKey=5C%2FnyAagqz6%2F%2BnYRGcZyRNpteaEeTlrNaMf1KtU0CWaSMRID13wEXSHVJ0J7WMvTl864DTzD3rwHM5GPX1aWtA%3D%3D&"
                        + "pageNo=1&"
                        + "numOfRows=5000";

            URL url = new URL(apiUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // 연결

            urlConnection.connect();

            BufferedInputStream bufferedInputStream = new BufferedInputStream((urlConnection.getInputStream()));

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;

            while ((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine + "\n");
            }

            JSONObject jsonObject = XML.toJSONObject(result.toString());

            // 필요한 데이터에 접근
            JSONObject aptJson = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");

            JSONArray jsonArray = aptJson.getJSONArray("item");

            aptList = new ArrayList<AptDTO>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String aptName = obj.getString("dutyName");
                String aptAddress = obj.getString("dutyAddr");
                String aptLat = obj.optString("wgs84Lat");
                String aptLng = obj.optString("wgs84Lon");


                AptDTO apt = new AptDTO(aptName, aptAddress, aptLng, aptLat);

                aptList.add(apt);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("aptList", aptList);

        return "/kakaoMap/aptMain";
    }


    
}
