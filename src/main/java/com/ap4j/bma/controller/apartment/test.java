package com.ap4j.bma.controller.apartment;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

public class test {
	public static void main(String[] args) {
			// JSON 객체 생성
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", "John");
			jsonObject.put("age", 30);

			// JSON 배열 생성
			JSONArray jsonArray = new JSONArray();
			jsonArray.put("apple");
			jsonArray.put("banana");
			jsonArray.put("cherry");

			// JSON 객체를 문자열로 변환
			String jsonString = jsonObject.toString();
			System.out.println("JSON String (org.json): " + jsonString);

			// JSON 문자열을 객체로 파싱
			JSONObject parsedJsonObject = new JSONObject(jsonString);
			System.out.println("Parsed JSON Object (org.json): " + parsedJsonObject);

			// JSON 배열을 문자열로 변환
			String jsonArrayString = jsonArray.toString();
			System.out.println("JSON Array String (org.json): " + jsonArrayString);

			// JSON 배열 문자열을 배열로 파싱
			JSONArray parsedJsonArray = new JSONArray(jsonArrayString);
			System.out.println("Parsed JSON Array (org.json): " + parsedJsonArray);


			// Gson 객체 생성
			Gson gson = new Gson();

			// 객체를 JSON 문자열로 변환
		String jsonGson = gson.toJson(jsonArrayString);
		System.out.printf(jsonGson);

		Person test = gson.fromJson(jsonString, Person.class);
		System.out.println(test);

		}
	static class Person {
		private String name;
		private int age;

		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person{" +
					"name='" + name + '\'' +
					", age=" + age +
					'}';
		}
	}
}
