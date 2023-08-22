package com.ap4j.bma.controller.apartment;
import com.ap4j.bma.model.entity.apt.Apt2DTO;
import com.ap4j.bma.service.apartment.ApartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Controller
public class ApiAptController {

	@Autowired
	ApartmentServiceImpl apartmentServiceImpl;


	/** 아파트 실거래가 api 불러오기 */
	@GetMapping("data")
	public String apt(Model model) {
		CompletableFuture<ArrayList<Apt2DTO>> future = apartmentServiceImpl.getAptListsAsync();

		ArrayList<Apt2DTO> aptList = apartmentServiceImpl.getAptLists();

		model.addAttribute("aptList", future);

		return "/kakaoMap/aptMain";
	}
}


