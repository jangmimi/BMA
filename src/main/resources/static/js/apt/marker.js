var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.55437, 126.974063),
    level: 3
};
var map = new kakao.maps.Map(container, options);

var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
//map.setMaxLevel(10);

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
function createMarker(position) {
    var markerKey = position.toString();
    if (!existingMarkers[markerKey]) { // 이미 생성된 마커인지 확인
        var marker = new kakao.maps.Marker({
            position: position
        });
        clusterer.addMarker(marker); // 클러스터에 마커 추가
        existingMarkers[markerKey] = true; // 생성된 마커를 객체에 추가
    }
}

// 화면의 좌표값 가져와서 controller에 전송 후 그 값에 맞는 데이터를 다시 가지고 와서 마커 찍어준다.
kakao.maps.event.addListener(map, 'idle', function() {
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
        url: '/markers',
        data: dataToSend,
        success: function(response) {
            if (response && response.length > 0) {
                for (var i = 0; i < response.length; i++) {
                    var markerPosition = new kakao.maps.LatLng(response[i].latitude, response[i].longitude);
                    var markerKey = markerPosition.toString();

                    if (!existingMarkers[markerKey]) { // 이미 생성된 마커인지 확인
                        createMarker(markerPosition);
                    }
                }
            } else {
                console.log("No markers to display.");
            }
        }
    });
});