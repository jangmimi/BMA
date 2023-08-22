package com.ap4j.bma.service.apartment;

import com.ap4j.bma.model.entity.apt.Apt2DTO;
import com.ap4j.bma.model.entity.apt.AptDTO;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface ApartmentService {

	public ArrayList<Apt2DTO> getAptLists();
	public CompletableFuture<ArrayList<Apt2DTO>> getAptListsAsync();
}
