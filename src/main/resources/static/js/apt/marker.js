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

            var title = responseData.complexName;
            var roadName = responseData.roadName;

            var asideBar = document.querySelector('.asideBarAll');
            var titleElement = asideBar.querySelector('.title');
            var roadNameElement = asideBar.querySelector('.roadName');

            titleElement.textContent = title;
            roadNameElement.textContent = roadName;
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
        northEastLng: northEast.getLng()
    };

    $.ajax({
        type: 'POST',
        url: '/map/main',
        data: dataToSend,
        success: function (response) {
            if (response && response.length > 0) {
                for (var i = 0; i < response.length; i++) {
                    var markerPosition = new kakao.maps.LatLng(response[i].latitude, response[i].longitude);
                    var markerKey = markerPosition.toString();
                    var markerContent = "<div class='e-marker'>" +
                        "<div class='e-markerTitle'>" +
                        "<h3>" + response[i].complexName + "</h3>" +
                        "</div>" +
                        "<div class='e-markerContent'>" +
                        "<p>" + response[i].roadName + "</p>" +
                        "</div>" +
                        "</div>"

                    if (!existingMarkers[markerKey]) { // 이미 생성된 마커인지 확인
                        createMarker(markerPosition, markerContent, response[i]);
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