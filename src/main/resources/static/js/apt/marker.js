/** DB에 저장된 좌표를 가지고 사용자가 보고 있는 화면의 좌표값과 비교하여 그 범위에만 마커를 찍어주는 javascript 파일입니다. */
/** 불필요한 데이터를 가져오지 않아서 성능향상에 도움이 됩니다. */

/** kakaoMapApi map 생성 */
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.55437, 126.974063),
    level: 3
};
var map = new kakao.maps.Map(container, options);
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 클러스터러 생성
var clusterer = new kakao.maps.MarkerClusterer({
    map: map, // 클러스터러를 지도에 표시
    gridSize: 200, // 클러스터 범위를 더 넓히기 위해 설정
    averageCenter: false,
    minLevel: 5 // 클러스터링을 시작할 지도 레벨
});

// 이미 생성된 마커들을 관리하기 위한 객체
var existingMarkers = {};

// 마커 생성 함수
function createMarker(position, markerContent, responseData) {
    var markerKey = position.toString();

    var imageSrc = '/img/mapDetailAndAPTList/houseMarker.png', // 마커이미지의 주소입니다
        imageSize = new kakao.maps.Size(32, 32), // 마커이미지의 크기입니다
        imageOption = { offset: new kakao.maps.Point(20, 20) };
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

    if (!existingMarkers[markerKey]) { // 이미 생성된 마커인지 확인
        var marker = new kakao.maps.Marker({
            position: position,
            image: markerImage,
            clickable: true
        });

        var overlayContent = markerContent; // 오버레이 내용

        var overlay = new kakao.maps.CustomOverlay({
            content: overlayContent,
            map: map,
            position: marker.getPosition(),
            zIndex: 9999
        });
        marker.overlay = overlay; // 오버레이를 마커 객체에 저장

        marker.overlay.setMap(null);

        // 클로저 내부에서 responseData를 사용할 수 있도록 파라미터로 전달
        kakao.maps.event.addListener(marker, 'click', function () {
            closeOtherOverlays();
            if (marker.overlay) {
                if (marker.overlay.getMap()) {
                    marker.overlay.setMap(null);
                } else {
                    marker.overlay.setMap(map);
                }
            }

            // 사이드바 미니 지도 (staticMap)
            var staticMapContainer = document.getElementById('staticMap');
                staticMapContainer.innerHTML = ''; // 초기화 코드
            var markerPosition = marker.getPosition();
            var staticMapOption = {
                center: markerPosition,
                level: 4,
                marker: marker
            };
            var staticMap = new kakao.maps.StaticMap(staticMapContainer, staticMapOption);

            // 사이드바 정보 최신화
            var title = responseData.complexName; // 아파트 이름
            var roadName = responseData.roadName; // 도로명 주소
            var address1 = responseData.district // 구주소
            var address2 = responseData.address // 지번

            // 사이드바 상단 타이틀
            var asideBar = document.querySelector('.asideBarAll');
            var titleElement = asideBar.querySelector('.title');

            // 사이드바 내용 부분
            var mapInfo = document.querySelector('.map-info');
            var mapInfoTitleElement = mapInfo.querySelector('.title');
            var mapInfoAddressElement = mapInfo.querySelector('.address');
            var mapInfoRoadNameElement = mapInfo.querySelector('.roadName');

            titleElement.textContent = title;

            mapInfoTitleElement.textContent = title;
            mapInfoAddressElement.textContent = "구주소 : " + address1 + " " + address2;
            mapInfoRoadNameElement.textContent  = "도로명주소 : " + roadName;

            $(".tbl tbody").empty();
            // 도로명 controller에 전달
            $.ajax({
                type: 'POST',
                url: '/map/main',
                data: {
                    roadName: roadName
                },
                success: function (response) {
                    var tableBody = $(".tbl tbody"); // 테이블의 tbody 요소 선택
                            if (response && response.aptDetailDTOList && response.aptDetailDTOList.length > 0) {
                                for (var i = 0; i < response.aptDetailDTOList.length; i++) {
                                    var detailItem = response.aptDetailDTOList[i];
                                    // transactionAmount를 1억과 1억 미만의 금액으로 분리
                                    var amount = detailItem.transactionAmount.toString();

                                    var amountSliceFirst = amount.slice(0, amount.length - 1);
                                    var amountSliceLast = amount.slice(-1)
                                    // 테이블 행 생성 및 데이터 추가
                                    var row = $("<tr>");
                                    $("<td>").text(detailItem.area).appendTo(row);
                                    $("<td>").text(detailItem.floor).appendTo(row);
                                    $("<td>").text(detailItem.contractYearMonth).appendTo(row);
                                    if(amountSliceLast != 0) {
                                        $("<td>").text(amountSliceFirst + "억 " + amountSliceLast + "000만원").appendTo(row);
                                    } else {
                                        $("<td>").text(amountSliceFirst + "억").appendTo(row);
                                    }

                                    // 테이블에 행 추가
                                    tableBody.append(row);
                                }
                    } else {
                        console.log("표시할 aptDetailList 데이터가 없습니다.");
                    }
                }
            });
        });


        clusterer.addMarker(marker); // 클러스터에 마커 추가
        existingMarkers[markerKey] = marker; // 생성된 마커를 객체에 추가
    }
}

// 화면의 좌표값 가져와서 controller에 전송 후 그 값에 맞는 데이터를 다시 가지고 와서 마커 찍어준다.
kakao.maps.event.addListener(map, 'idle', function () {
    var bounds = map.getBounds();
    var southWest = bounds.getSouthWest();
    var northEast = bounds.getNorthEast();

    var dataToSend = {
        southWestLat: southWest.getLat(),
        southWestLng: southWest.getLng(),
        northEastLat: northEast.getLat(),
        northEastLng: northEast.getLng(),
    };

    $.ajax({
        type: 'POST',
        url: '/map/main',
        data: dataToSend,
        success: function (response) {
            if (response && response.aptList && response.aptList.length > 0) {
                for (var i = 0; i < response.aptList.length; i++) {
                    var roadName = response.aptList[i].roadName; // controller에 넘겨주기위해 만든 객체

                    var markerPosition = new kakao.maps.LatLng(response.aptList[i].latitude, response.aptList[i].longitude);
                    var markerKey = markerPosition.toString();
                    var markerContent = "<div class='e-marker'>" +
                        "<div class='e-markerTitle'>" +
                        "<h3>" + response.aptList[i].complexName + "</h3>" +
                        "</div>" +
                        "<div class='e-markerContent'>" +
                        "<p>" + response.aptList[i].roadName + "</p>" +
                        "</div>" +
                        "</div>"

                    if (!existingMarkers[markerKey]) { // 이미 생성된 마커인지 확인
                        createMarker(markerPosition, markerContent, response.aptList[i]);
                    }
                }
            } else {
                console.log("표시할 마커가 없습니다. (좌표값 누락)");
            }
        }
    });
});


function closeOtherOverlays() {
    for (var key in existingMarkers) {
        if (existingMarkers.hasOwnProperty(key)) {
            var marker = existingMarkers[key];
            if (marker.overlay && marker.overlay.getMap()) {
                marker.overlay.setMap(null);
            }
        }
    }
}


function checkEnter(event) {
    if (event.key === 'Enter') {  // 엔터 키를 눌렀을 때만 작업을 수행
        var keyword = document.querySelector('.aSearchInput').value;  // 입력 필드의 값 가져오기

        // 검색 키워드를 서버로 전송하여 검색 수행
        $.ajax({
            type: 'POST',
            url: '/map/main',  // 검색을 수행하는 서버의 URL
            data: {
                keyword: keyword
            },
            success: function (response) {
                // 검색 결과에 따라 마커를 생성하고 지도에 표시하기
                if (response && response.aptDetailDTOList && response.aptDetailDTOList.length > 0) {
                    for (var i = 0; i < response.aptDetailDTOList.length; i++) {
                        var result = response.aptDetailDTOList[i];

                        var markerPosition = new kakao.maps.LatLng(result.latitude, result.longitude);
                        var markerContent = "<div class='e-marker'>" +
                            "<div class='e-markerTitle'>" +
                            "<h3>" + result.complexName + "</h3>" +
                            "</div>" +
                            "<div class='e-markerContent'>" +
                            "<p>" + result.roadName + "</p>" +
                            "</div>" +
                            "</div>";

                        // 이미 생성된 마커가 아니라면 마커 생성 함수를 호출하여 마커를 생성하고 클릭 이벤트를 등록합니다.
                        if (!existingMarkers[markerPosition.toString()]) {
                            createMarker(markerPosition, markerContent, result);
                        }
                    }
                } else {
                    console.log("검색 결과가 없습니다.");
                }
            }
        });
    }
}

