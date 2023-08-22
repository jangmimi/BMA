package com.ap4j.bma.service.apartment;

import com.ap4j.bma.model.entity.apt.Apt2DTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Slf4j
@EnableAsync(proxyTargetClass = true)
@Service
public class ApartmentServiceImpl implements ApartmentService {


	// 아파트 실거래가 공공데이터 받아오기.
//	@Override
//	public ArrayList<AptDTO> getAptLists() {
//		Thread ct = Thread.currentThread();
//		log.info("현재 쓰레드 {}", ct.getName());
//
//		String threadName = Thread.currentThread().getName();
//		if (threadName.equals("main")) {
//			log.info("현재 스레드는 메인 스레드입니다.");
//		} else {
//			log.info("현재 스레드는 메인 스레드가 아닙니다.");
//		}
//
//		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
//		if (threadGroup.getName().equals("main")) {
//			log.info("현재 스레드는 메인 스레드 그룹에 속해 있습니다.");
//		} else {
//			log.info("현재 스레드는 메인 스레드 그룹에 속해 있지 않습니다.");
//		}
//
//		StringBuilder result = new StringBuilder();
//		int pageNo = 1;
//		ArrayList<AptDTO> aptList = null;
//		try {
//			String apiUrl = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?"
//					+ "serviceKey=hAiRtZlGRJEkgTG1oYFctUoiFjG785z0otfrJknzsP7CfC3evcfU%2FUzWT2gq54UUPegUJpmrMccLTodwZcSFMQ%3D%3D&"
//					+ "LAWD_CD=11110&"
//					+ "DEAL_YMD=202307";
//
//			URL url = new URL(apiUrl);
//
//			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // 연결
//
//			urlConnection.connect();
//
//			BufferedInputStream bufferedInputStream = new BufferedInputStream((urlConnection.getInputStream()));
//
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
//
//			String returnLine;
//
//			while ((returnLine = bufferedReader.readLine()) != null) {
//				result.append(returnLine + "\n");
//			}
//
//			JSONObject jsonObject = XML.toJSONObject(result.toString());
//
//			log.info("jsonObject = " + jsonObject);
//
//			// 필요한 데이터에 접근
//			JSONObject aptJson = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");
//
//			JSONArray jsonArray = aptJson.getJSONArray("item");
//
//			aptList = new ArrayList<AptDTO>();
//
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject obj = jsonArray.getJSONObject(i);
//
//				String aptName = obj.getString("아파트");
//				String aptAddress = obj.getString("도로명");
//				String aptDealAmount = obj.getString("거래금액");
//
//				AptDTO apt = new AptDTO(aptName, aptAddress, aptDealAmount);
//
//				aptList.add(apt);
//			}
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return aptList;
//	}


	@Override
	public ArrayList<Apt2DTO> getAptLists() {
		Thread ct = Thread.currentThread();
		log.info("현재 쓰레드 {}", ct.getName());

		String threadName = Thread.currentThread().getName();
		if (threadName.equals("main")) {
			log.info("현재 스레드는 메인 스레드입니다.");
		} else {
			log.info("현재 스레드는 메인 스레드가 아닙니다.");
		}

		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		if (threadGroup.getName().equals("main")) {
			log.info("현재 스레드는 메인 스레드 그룹에 속해 있습니다.");
		} else {
			log.info("현재 스레드는 메인 스레드 그룹에 속해 있지 않습니다.");
		}

		StringBuilder result = new StringBuilder();
		int pageNo = 1;

		ArrayList<Apt2DTO> aptList = null;

		try {
			String apiUrl = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?"
					+ "serviceKey=5C%2FnyAagqz6%2F%2BnYRGcZyRNpteaEeTlrNaMf1KtU0CWaSMRID13wEXSHVJ0J7WMvTl864DTzD3rwHM5GPX1aWtA%3D%3D&"
					+ "pageNo=1&"
					+ "numOfRows=10000";

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

			aptList = new ArrayList<>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);

				String aptName = obj.getString("dutyName");
				String aptAddress = obj.getString("dutyAddr");
				String aptLat = obj.optString("wgs84Lat");
				String aptLng = obj.optString("wgs84Lon");


				Apt2DTO apt = new Apt2DTO(aptName, aptAddress, aptLng, aptLat);

				aptList.add(apt);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return aptList;
	}

	@Async
	@Override
	public CompletableFuture<ArrayList<Apt2DTO>> getAptListsAsync() {
		Thread ct = Thread.currentThread();
		log.info("현재 쓰레드 {}", ct.getName());

		String threadName = Thread.currentThread().getName();
		if (threadName.equals("main")) {
			log.info("현재 스레드는 메인 스레드입니다.");
		} else {
			log.info("현재 스레드는 메인 스레드가 아닙니다.");
		}

		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		if (threadGroup.getName().equals("main")) {
			log.info("현재 스레드는 메인 스레드 그룹에 속해 있습니다.");
		} else {
			log.info("현재 스레드는 메인 스레드 그룹에 속해 있지 않습니다.");
		}

		StringBuilder result = new StringBuilder();
		int pageNo = 1;

		ArrayList<Apt2DTO> aptList = null;

		try {
			String apiUrl = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?"
					+ "serviceKey=5C%2FnyAagqz6%2F%2BnYRGcZyRNpteaEeTlrNaMf1KtU0CWaSMRID13wEXSHVJ0J7WMvTl864DTzD3rwHM5GPX1aWtA%3D%3D&"
					+ "pageNo=1&"
					+ "numOfRows=10000";

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

			aptList = new ArrayList<>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);

				String aptName = obj.getString("dutyName");
				String aptAddress = obj.getString("dutyAddr");
				String aptLat = obj.optString("wgs84Lat");
				String aptLng = obj.optString("wgs84Lon");


				Apt2DTO apt = new Apt2DTO(aptName, aptAddress, aptLng, aptLat);

				aptList.add(apt);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return CompletableFuture.completedFuture(aptList);
	}

}
