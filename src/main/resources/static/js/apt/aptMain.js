// [지도 불러오기]
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(35.099903, 129.02715),
    level: 3
};
var map = new kakao.maps.Map(container, options);

// [지도에 확대 축소 컨트롤 생성]
var zoomControl = new kakao.maps.ZoomControl();
// [지도의 우측에 확대 축소 컨트롤 추가]
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
map.setMaxLevel(8);

// [마커 및 클러스터 생성]
var markers = []; // 마커들을 담을 배열

// 인포윈도우를 표시하는 클로저를 만드는 함수
function makeOverListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수
function makeOutListener(infowindow) {
    return function() {
        infowindow.close();
    };
}

/** 마커 삭제하는 함수 */
function removeMarker() {
    var cnt = markers.length;
    for (var i = 0; i < cnt; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

/** 마커 생성 함수 */
function viewMarker() {
    removeMarker();

    var bounds = map.getBounds();
    var southWest = bounds.getSouthWest();
    var northEast = bounds.getNorthEast();

    var lat1 = southWest.getLat();  // 수정
    var lng1 = southWest.getLng();  // 수정
    var lat2 = northEast.getLat();  // 수정
    var lng2 = northEast.getLng();  // 수정

    console.log(lat1);
    console.log(lng1);
    console.log(lat1);
    console.log(lng1);

    for (var i = 0; i < aptList.length; i++) {

        var aptLat = aptList[i].aptLat;
        var aptLng = aptList[i].aptLng;

        if (aptLat >= lat1 && aptLat <= lat2 && aptLng >= lng1 && aptLng <= lng2) {
            // [받아온 데이터에 마커 표시]
            var marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(aptLat, aptLng)
            });

            // 마커를 배열에 추가
            markers.push(marker);

            // [마커에 인포윈도우 표시]
            var infowindow = new kakao.maps.InfoWindow({
                content: aptList[i].aptName // 인포윈도우에 표시할 내용
            });

            // [마커에 이벤트 등록]
            kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
            kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
        }
    }

    var clusterer = new kakao.maps.MarkerClusterer({
        map: map,
        averageCenter: true,
        minLevel: 6,
        markers: markers
    });
}

// 확대축소
kakao.maps.event.addListener(map, 'zoom_changed', function() {
    chkArea();
});
// 화면이동
kakao.maps.event.addListener(map, 'dragend', function() {
    chkArea();
});

function chkArea() {
    removeMarker();
    viewMarker();
}

// 초기화
viewMarker();


