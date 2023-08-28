package com.ap4j.bma.batch;

import com.ap4j.bma.model.entity.apt.AptDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ApartmentApiReader implements ItemReader<List<AptDTO>> {


	private final ApartmentApiService apartmentApiService;
	private boolean read = false;

	@Override
	public List<AptDTO> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (!read) {
			log.info("ApartmentApiReader의 read() 실행 됐다");
			read = true;
			return apartmentApiService.fetchDataFromApi(); // 데이터 한 번만 읽어오도록 수정
		} else {
			return null; // 더 이상 데이터가 없다면 null 반환
		}
	}
}
