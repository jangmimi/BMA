
(function() {
	var script = document.createElement('script');
	script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?appkey=9a3ebc8549eeffd6c970f997b9f91c0c&libraries=services'; // 여기에 자신의 API 키를 입력하세요
	script.async = true;
	document.getElementsByTagName('head')[0].appendChild(script);

	script.onload = function() {
		// Kakao Maps API 로드 완료 후 실행되는 부분
		var mapContainer = document.getElementById('y-map');
		var mapOption = {
			center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심 좌표
			level: 3 // 지도의 확대 레벨
		};

		// 지도를 생성합니다
		var map = new kakao.maps.Map(mapContainer, mapOption);

		// 장소 검색 객체를 생성합니다
		var ps = new kakao.maps.services.Places(map);

		// 카테고리로 은행을 검색합니다
		ps.categorySearch('BK9', placesSearchCB, { useMapBounds: true });

		// 키워드 검색 완료 시 호출되는 콜백함수
		function placesSearchCB(data, status, pagination) {
			if (status === kakao.maps.services.Status.OK) {
				for (var i = 0; i < data.length; i++) {
					displayMarker(data[i]);
				}
			}
		}

		// 지도에 마커를 표시하는 함수
		function displayMarker(place) {
			// 마커를 생성하고 지도에 표시합니다
			var marker = new kakao.maps.Marker({
				map: map,
				position: new kakao.maps.LatLng(place.y, place.x)
			});

			// 마커에 클릭 이벤트를 등록합니다
			kakao.maps.event.addListener(marker, 'click', function() {
				// 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
				var infowindow = new kakao.maps.InfoWindow({
					content: '<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>'
				});
				infowindow.open(map, marker);
			});
		}
	};
})();