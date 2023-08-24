package com.ap4j.bma.batch;

import com.ap4j.bma.model.entity.apt.AptDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ApartmentApiWriter implements ItemWriter<List<AptDTO>> {


	private final ApartmentApiService apartmentApiService;

	@Override
	public void write(List<? extends List<AptDTO>> items) throws Exception {
		items.stream()
				.forEach(aptList -> apartmentApiService.fetchAptApi());
	}
}
