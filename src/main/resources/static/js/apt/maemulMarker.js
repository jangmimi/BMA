/** 매물 마커 생성 js */

// 카카오 지도 불러오기
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.535814, 127.008644), // 초기 맵 중심 좌표
    level: 5 // 초기 줌 레벨
};
var map = new kakao.maps.Map(container, options);

// 줌 컨트롤러 지도에 추가
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 클러스터
var clusterer = new kakao.maps.MarkerClusterer({
    map: map,
    gridSize: 300,
    averageCenter: false,
    minLevel: 14
});

// 생성된 마커 저장 객체
var existingMarkers = {};

// 마커 생성 함수
function createMarker(position, markerContent, responseData) {
    var markerKey = position.toString();

    var imageSrc = '/img/mapDetailAndAPTList/houseMarker.png',
        imageSize = new kakao.maps.Size(32, 32),
        imageOption = { offset: new kakao.maps.Point(20, 20) };
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

    // 마커가 생성되어 있지 않다면 마커 생성
    if (!existingMarkers[markerKey]) {
        var marker = new kakao.maps.Marker({
            position: position,
            image: markerImage,
            clickable: true
        });

        marker.responseData = responseData; // 첫 화면에서 사이드바 띄워줄 때 사용함

        var overlayContent = markerContent;

        // 오버레이 생성
        var overlay = new kakao.maps.CustomOverlay({
            content: overlayContent,
            map: map,
            position: marker.getPosition(),
            zIndex: 9999
        });

        marker.overlay = overlay;
        marker.overlay.setMap(null); // 오버레이는 초기에 닫혀있는 상태

        // 마커 클릭시 오버레이 열리는 이벤트
        kakao.maps.event.addListener(marker, 'click', function () {
            closeOtherOverlays(); // 다른 오버레이 닫기
            openOverlay(marker.overlay); // 오버레이 열기
            updateSidebar(responseData); // 사이드바에 데이터 전송
            updateTransactionTable(responseData.roadName); // 사이드바의 거래내역 테이블에 데이터 전송
        });

        clusterer.addMarker(marker); // 클러스터러에 마커 추가
        existingMarkers[markerKey] = marker; // 생성된 마커를 마커리스트에 저장
    }
}


// 맵 이동시 현재 맵의 경계를 기준으로 데이터 요청하고 그 범위에 속하는 행정동 또는 아파트 데이터의 마커 생성
kakao.maps.event.addListener(map, 'idle', function () {
    // 행정동 오버레이 초기화
    clearHJDOverlays();

    // 사이드바 초기화
    clearSidebar();

    var bounds = map.getBounds();
    var southWest = bounds.getSouthWest();
    var northEast = bounds.getNorthEast();
    var currentZoomLevel = map.getLevel(); // 현재 줌 레벨 가져오기

    var dataToSend = {
        southWestLat: southWest.getLat(),
        southWestLng: southWest.getLng(),
        northEastLat: northEast.getLat(),
        northEastLng: northEast.getLng(),
        zoomLevel: currentZoomLevel
    };

//    console.log(dataToSend);

    $.ajax({
        type: 'POST',
        url: '/map/map',
        data: dataToSend,
        success: function (response) {
//            console.log("줌레벨 " + currentZoomLevel);
//            console.log("통신중(매물리스트) " + response.maenulList);
//            console.log("통신중(행정동리스트) " + response.hjdList);
            if (response.maenulList && currentZoomLevel <= 5) {
                response.maenulList.forEach(function (maemul) {

                    var markerPosition = new kakao.maps.LatLng(maemul.latitude, maemul.longitude);
                    var markerKey = markerPosition.toString();
                    var markerContent = "<div class='e-marker'>" +
                        "<div class='e-markerTitle'>" +
                        "<h3>" + maemul.apt_name + "</h3>" +
                        "</div>" +
                        "<div class='e-markerContent'>" +
                        "<p>" + maemul.address + "</p>" +
                        "</div>" +
                        "</div>";

                    if (!existingMarkers[markerKey]) {
                        createMarker(markerPosition, markerContent, maemul);
                    }

                });
                updateSidebar(response.maenulList);
            }
            if (currentZoomLevel >= 6) {  // 위 if문뒤에 else로 조건 주면 오류남 따로 조건 줘야함
                // 줌레벨 6이상시 마커 삭제
                clusterer.clear();
                for (var key in existingMarkers) {
                    if (existingMarkers.hasOwnProperty(key)) {
                        var marker = existingMarkers[key];
                        if (marker.overlay) {
                            marker.overlay.setMap(null);
                        }
                    }
                }
                existingMarkers = {};

                // 행정동으로 클러스터 생성
                response.hjdList.forEach(function (hjd) {
                    var overlayPosition = new kakao.maps.LatLng(hjd.latitude, hjd.longitude);
                    var overlayContent = "<div class='e-hjdOverlay'>";
                    if (hjd.siDo && hjd.siGunGu && hjd.eupMyeonDong && hjd.eupMyeonRiDong) {
                        overlayContent += "<p>" + hjd.eupMyeonRiDong + "</p>";
                    } else if (hjd.siDo && hjd.siGunGu && hjd.eupMyeonDong) {
                        overlayContent += "<p>" + hjd.eupMyeonDong + "</p>";
                    } else if (hjd.siDo && hjd.siGunGu) {
                        overlayContent = "<div class='e-hdjOverlaySiGunGu'>" +
                                            "<p>" + hjd.siGunGu + "</p>";
                    } else if (hjd.siDo) {
                        overlayContent = "<div class='e-hdjOverlaySiDo'>" +
                                         "<p>" + hjd.siDo + "</p>";
                    }
                    overlayContent += "</div>";

                    var hjdOverlay = new kakao.maps.CustomOverlay({
                        position: overlayPosition,
                        content: overlayContent,
                        map: map
                    });
                    // 생성된 오버레이를 배열에 추가
                    hjdOverlays.push(hjdOverlay);
                });

            }
        }
    });
});

// 오버레이 열기
function openOverlay(overlay) {
    if (overlay) {
        overlay.setMap(map);
    }
}

// 다른 오버레이 닫아주는 함수
function closeOtherOverlays() {
    for (var key in existingMarkers) {
        if (existingMarkers.hasOwnProperty(key)) {
            var marker = existingMarkers[key];
            if (marker.overlay) {
                marker.overlay.setMap(null);
            }
        }
    }
}

// 행정동 오버레이 초기화 함수 정의
function clearHJDOverlays() {
    // 모든 오버레이 삭제
    hjdOverlays.forEach(function (hjdOverlay) {
        hjdOverlay.setMap(null);
    });
    hjdOverlays = []; // 오버레이 배열 초기화
}
// 행정동 오버레이 배열 초기화
var hjdOverlays = [];


// 사이드바 정보 업데이트
function updateSidebar(responseData) {

    // sellingPrice를 기준으로 responseData 배열을 내림차순 정렬
    responseData.sort(function (a, b) {
        return b.sellingPrice - a.sellingPrice;
    });

    // 사이드바 컨테이너
    var sidebarContainer = document.querySelector(".sideContents ul.list-group");

    // responseData 배열을 순회하며 사이드바 항목 생성
    responseData.forEach(function (maemul) {
        // 새로운 li 요소 생성
        var listItem = document.createElement("li");
        listItem.className = "list-group-item a";
        listItem.style = "";

        // 새로운 a 요소 생성
        var anchor = document.createElement("a");
        anchor.href = "/details/" + maemul.id ;
        anchor.className = "abox";
        anchor.target = "";

        // a 요소 내부의 내용 생성
        anchor.innerHTML = `
            <div class="aimg_area">
                <div class="athumb">
                    <img src="https://www.bdsplanet.com/common/thumbs.ytp?filepath=/COMP_20190619150536000003/upload/realty/RCODE_20230822163534000002/&amp;filename=1692689858337_106.jpg" alt="">
                </div>
            </div>
            <div class="ainfo_area">
                <div class="in">
                    <h5 class="ii loc_title">
                        <span class="payf_num_b">${maemul.sellingPrice}</span>
                    </h5>
                    <div class="ii loc_ii01">
                        <span class="type">아파트</span>
                        <span class="loc">${maemul.apt_name}</span>
                    </div>
                    <div class="ii etc_txt">
                        <span class="txt01">${maemul.supplyArea}/${maemul.privateArea}㎡</span>
                        <span class="txt01">${maemul.floorNumber}/${maemul.totalFloors}층</span>
                        <span class="txt01">방${maemul.numberOfRooms}/욕실${maemul.numberOfBathrooms}</span>
                    </div>
                    <div class="ii etc_info">
                        <span class="txt01">${maemul.additionalInfo}</span>
                    </div>
                </div>
                <div class="bd_hashtag">

                </div>
            </div>
        `;

        // 하트 버튼 추가
        var heartButton = document.createElement("div");
        heartButton.className = "aHeartBtnInList";
        heartButton.innerHTML = `
            <button class="aHeartBtn">
                <img style="width:15px;margin-bottom:2px;" src="/img/mapDetailAndAPTList/aHeartBtn.png">
            </button>
        `;

        // li 요소에 a 요소와 하트 버튼 추가
        listItem.appendChild(anchor);
        listItem.appendChild(heartButton);

        // li 요소를 사이드바 컨테이너에 추가
        sidebarContainer.appendChild(listItem);
    });
}

// 사이드바 초기화 함수 정의
function clearSidebar() {
    var sidebarContainer = document.querySelector(".sideContents ul.list-group");
    sidebarContainer.innerHTML = "";
}
