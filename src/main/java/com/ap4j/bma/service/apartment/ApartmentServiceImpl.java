package com.ap4j.bma.service.apt;

import com.ap4j.bma.model.entity.apt.AptDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Service
public class AptServiceImpl implements AptService {


	@Override
	public ArrayList<AptDTO> getAptLists() {
		StringBuffer result = new StringBuffer();
		int pageNo = 1;
		ArrayList<AptDTO> aptList = null;
		try {
			String apiUrl = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?"
					+ "serviceKey=hAiRtZlGRJEkgTG1oYFctUoiFjG785z0otfrJknzsP7CfC3evcfU%2FUzWT2gq54UUPegUJpmrMccLTodwZcSFMQ%3D%3D&"
					+ "LAWD_CD=11110&"
					+ "DEAL_YMD=202307";

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

			System.out.println("jsonObject = " + jsonObject);

			// 필요한 데이터에 접근
			JSONObject aptJson = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");

			JSONArray jsonArray = aptJson.getJSONArray("item");

			aptList = new ArrayList<AptDTO>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);

				String aptName = obj.getString("아파트");
				String aptAddress = obj.getString("도로명");
				String aptDealAmount = obj.getString("거래금액");

				AptDTO apt = new AptDTO(aptName, aptAddress, aptDealAmount);

				aptList.add(apt);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return aptList;
	}
}
