package com.ap4j.bma.batch;

import com.ap4j.bma.model.entity.apt.AptBatchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApartmentApiService {


	private final WebClient webClient;

	public ApartmentApiService(){
		log.info(">>>>> ApartmentApiService 의 ApartmentApiService기본생성자() 실행.");
		this.webClient = WebClient.builder()
				.baseUrl("http://localhost:8083/")
				.build();
	}

	public List<AptBatchDTO> fetchDataFromApi() {
		log.info(">>>>> ApartmentApiService 의 fetchDataFromApi 실행. 이게 잘만 실행되면 끝나는 건데..");
		return webClient.get()
				.uri("/getApartments")
				.retrieve()
				.bodyToFlux(AptBatchDTO.class) // 여기서 Flux를 List로 변환
				.collectList() // Flux를 List로 수집
				.block(); // block() 메서드로 동기적으로 처리
	}

}
