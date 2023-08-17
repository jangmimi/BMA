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
    public String test(Model model) {
        StringBuffer result = new StringBuffer();
        int pageNo = 1;
        String jsonPrintString = null;

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

            // 인코딩 같이함
            // 중간에 데이터를 임시 저장 공안인 버퍼에 저장한다.
            // 저장한 내용을 한꺼번에 가지고 들어온다.
            // 1byte 가져오면 속도가 느리고 데이턷의 용량이 크면 시간이 꾀 오래걸린다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;

            while ((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine + "\n");
            }
            // json
            // json 파일을 스프링부트에서 사용 할 수 있도록 특정값을 가지고 오는 내용을 작성

            // Jsonparser 객체의 도움을 받는다.
            // 1. Jsonparser 객체 생성
            // 2. reader를 이용해서 json 파일을 읽어온다.
            // 3. Array json코드가 [] 감싸고 있을 경우 List 형식으로 index값으로 불러온다.
            // 4. Object json 코드가 {}로 감싸고 있을 경우 Key : Value 형식으로 저장되어 있는 값을 불러온다. map형식

            JSONObject jsonObject = XML.toJSONObject(result.toString());
            
            // 아이템 리스트 접근
            JSONObject busan = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");
            System.out.println(busan);


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

            for (int i = 0; i < busanList.size(); i++) {
                System.out.println(busanList.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("busanList", busanList);

        return "kakaoMap/busan";
    }
}
