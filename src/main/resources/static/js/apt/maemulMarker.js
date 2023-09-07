/** 매물 마커 생성 js */

// 카카오 지도 불러오기
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.535814, 127.008644), // 초기 맵 중심 좌표
    level: 5 // 초기 줌 레벨
};
var map = new kakao.maps.Map(container, options);

// 줌 컨트롤을 생성하고 커스텀 컨트롤로 사용할 div를 지정합니다.
            var zoomControlContainer = document.getElementById('zoomControl');

            // 줌 인 버튼을 생성하고 이벤트 핸들러를 추가합니다.
            var zoomInButton = document.getElementById('buttonp');
            zoomInButton.textContent = '+';
            zoomInButton.addEventListener('click', function () {
                map.setLevel(map.getLevel() - 1, { animate: true });
            });

            // 줌 아웃 버튼을 생성하고 이벤트 핸들러를 추가합니다.
            var zoomOutButton = document.getElementById('buttonm');
            zoomOutButton.textContent = '-';
            zoomOutButton.addEventListener('click', function () {
                map.setLevel(map.getLevel() + 1, { animate: true });
            });

            // 버튼을 컨테이너에 추가합니다.
            zoomControlContainer.appendChild(zoomInButton);
            zoomControlContainer.appendChild(zoomOutButton);

            // 커스텀 줌 컨트롤을 생성하고 맵에 추가합니다.
            var customZoomControl = new kakao.maps.CustomControl(zoomControlContainer, {
                position: kakao.maps.ControlPosition.TOPLEFT
            });
            map.addControl(customZoomControl);

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

// 맵 최초 로드시 마커 생성 해주는 함수
var onlyOneStart = false; // 한 번만 실행하기 위한 변수
// 맵 로드가 완료되면 실행

var tradeType = []; // 선택된 거래 유형을 담을 배열

// 체크박스의 변경 이벤트를 감지하고 선택된 거래 유형을 배열에 추가 또는 제거합니다.
$("#flexCheckAll").change(function () {
    updateSelectedTradeTypes(null, this.checked);
});

$("#flexCheckSale").change(function () {
    updateSelectedTradeTypes("매매", this.checked);
});

$("#flexCheckLease").change(function () {
    updateSelectedTradeTypes("전세", this.checked);
});

$("#flexCheckMonthly").change(function () {
    updateSelectedTradeTypes("월세", this.checked);
});

$("#flexCheckShortTerm").change(function () {
    updateSelectedTradeTypes("단기임대", this.checked);
});

function updateSelectedTradeTypes(type, isChecked) {
    if (isChecked) {
        // 체크된 경우 배열에 추가
        tradeType.push(type);
    } else {
        // 체크 해제된 경우 배열에서 제거
        tradeType = tradeType.filter(item => item !== type);
    }



}

kakao.maps.event.addListener(map, 'tilesloaded', function () {
    // 이미 실행된 경우 함수 종료
    if (onlyOneStart) {
        return;
    }
    onlyOneStart = true; // 변수 업데이트

    var tradeTypeString = tradeType.join(",");

    var bounds = map.getBounds();
    var southWest = bounds.getSouthWest();
    var northEast = bounds.getNorthEast();
    var currentZoomLevel = map.getLevel(); // 현재 줌 레벨 가져오기
    console.log("실행시 줌레벨 : " + currentZoomLevel);
    console.log(tradeType);
    var dataToSend = {
        southWestLat: southWest.getLat(),
        southWestLng: southWest.getLng(),
        northEastLat: northEast.getLat(),
        northEastLng: northEast.getLng(),
        zoomLevel: currentZoomLevel,
        tradeType: tradeTypeString

    };

    $.ajax({
        type: 'POST',
        url: '/map/map',
        data: dataToSend,
        success: function (response) {
            if(response.maenulList) {
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
        }
    });
});
// 맵 이동시 현재 맵의 경계를 기준으로 데이터 요청하고 그 범위에 속하는 행정동 또는 아파트 데이터의 마커 생성
kakao.maps.event.addListener(map, 'idle', function () {
    // 행정동 오버레이 초기화
    clearHJDOverlays();

    // 사이드바 초기화
    clearSidebar();

    var tradeTypeString = tradeType.join(",");

    var bounds = map.getBounds();
    var southWest = bounds.getSouthWest();
    var northEast = bounds.getNorthEast();
    var currentZoomLevel = map.getLevel(); // 현재 줌 레벨 가져오기

    var dataToSend = {
        southWestLat: southWest.getLat(),
        southWestLng: southWest.getLng(),
        northEastLat: northEast.getLat(),
        northEastLng: northEast.getLng(),
        zoomLevel: currentZoomLevel,
        tradeType: tradeTypeString
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

//    // sellingPrice를 기준으로 responseData 배열을 내림차순 정렬
//    responseData.sort(function (a, b) {
//        return b.sellingPrice - a.sellingPrice;
//    });

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

        heartButton.querySelector("button").setAttribute("data-isButton", "false");

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

// 하트 버튼을 클릭하면 매물 id 전송
$(document).on("click", ".aHeartBtn", function() {
    var listItem = $(this).closest("li"); // 클릭한 하트 버튼이 속한 li 요소 찾기
    var maemulId = listItem.find(".abox").attr("href").split("/").pop(); // a 요소의 href의 maemulId 추출
    var isButton = listItem.data("isButton"); // 해당 버튼의 boolean 값 가져옴

    // 해당 li 내의 버튼만 스타일 변경
    var heartBtnInList = listItem.find(".aHeartBtnInList");
    if (!isButton) {
        heartBtnInList.css("opacity", 1); // 불투명
    } else {
        heartBtnInList.css("opacity", 0.16); // 16% 투명도
    }

    $.ajax({
        url: "/member/qLiked", //
        type: "POST", //
        data: { maemulId: maemulId }, //
        success: function(response) {
            console.log("Ajax 요청 성공");
            console.log("매물 아이디" + maemulId);

            listItem.data("isButton", !isButton);
        },
        error: function(xhr, status, error) {
            console.error("Ajax 요청 실패: " + error);
        }
    });


});