package com.ap4j.bma.batch;

import com.ap4j.bma.model.entity.apt.AptDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ApartmentApiReader implements ItemReader<List<AptDTO>> {


	private final ApartmentApiService apartmentApiService;

	@Override
	public List<AptDTO> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		log.info("ApartmentApiReader의 read() 실행 됐다");
		List<AptDTO> aptApiList = apartmentApiService.fetchAptApi();
		log.info("값들 {}", aptApiList);

		return aptApiList;
	}
}
