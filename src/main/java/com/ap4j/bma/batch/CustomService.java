package com.ap4j.bma.batch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomService {

	public void businessLogic(){
		for(int i = 0; i < 10; i++){
			log.info("비즈니스로직 {} 번 실행", i);
		}
	}
}
