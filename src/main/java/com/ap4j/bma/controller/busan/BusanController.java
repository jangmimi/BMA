package com.ap4j.bma.controller.busan;

import com.ap4j.bma.model.entity.busan.BusanDTO;
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
public class BusanController {

    @GetMapping("busan")
    public String busan(Model model) {
        StringBuffer result = new StringBuffer();
        int pageNo = 1;

        ArrayList<BusanDTO> busanList = null;
        try {
            String apiUrl = "http://apis.data.go.kr/6260000/FoodService/getFoodKr?"
                    + "serviceKey=5C%2FnyAagqz6%2F%2BnYRGcZyRNpteaEeTlrNaMf1KtU0CWaSMRID13wEXSHVJ0J7WMvTl864DTzD3rwHM5GPX1aWtA%3D%3D"
                    + "&numOfRows=300"
                    + "&pageNo=" + pageNo;

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
            JSONObject busan = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");

            busanList = new ArrayList<>();

            // json을 ArrayList로 변환
            JSONArray jsonArray = busan.getJSONArray("item");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString("MAIN_TITLE");
                double lng = obj.getDouble("LNG");
                double lat = obj.getDouble("LAT");

                BusanDTO busanDTO = new BusanDTO();
                busanDTO.setMAIN_TITLE(title);
                busanDTO.setLNG(lng);
                busanDTO.setLAT(lat);

                busanList.add(busanDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("busanList", busanList);

        return "kakaoMap/busan";
    }
}
