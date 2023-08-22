/** 맵 생성 */
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.55437, 126.974063),
    level: 3
};
var map = new kakao.maps.Map(container, options);

/** 확대축소 컨트롤러 생성 */
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
map.setMaxLevel(8);

/** 주소 -> 좌표 변환 객체 생성 */
var geocoder = new kakao.maps.services.Geocoder();

/** 주소로 좌표 검색 해서 마커 찍기 */
// 주소로 좌표 검색 및 마커 생성 함수
function searchAddressAndAddMarker(address, name, amount) { // 주소값, 아파트이름, 아파트실거래가
    // 주소로 좌표 변환 요청
    geocoder.addressSearch(address, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 마커 생성
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords,
                title: name
            });

            // 마커 클릭 시 인포윈도우 생성
            kakao.maps.event.addListener(marker, 'click', function() {
                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="padding:10px;">' + name + '</div>'
                            + '<div style="padding:10px;">' + amount + '만원</div>'

                });
                infowindow.open(map, marker);
            });
        }
    });
}

// aptList에 저장된 아파트 정보를 순회하며 주소 검색 및 마커 생성
for (var i = 0; i < aptList.length; i++) {
    var apt = aptList[i];
    searchAddressAndAddMarker(apt.aptAddress, apt.aptName, apt.aptDealAmount);
}