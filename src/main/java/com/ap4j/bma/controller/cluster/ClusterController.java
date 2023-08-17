package com.ap4j.bma.controller.cluster;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClusterController {

	@RequestMapping("/clusterMarker")
	public String clusterMarker(){
		return "kakaoMap/cluster_marker";
	}
	@RequestMapping("/clusterMap")
	public String clusterMap(){
		return "누구맵인지 확인하고 이름 변경해주세요";
	}
}

