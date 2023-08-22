var container = document.getElementById('map');
var options = {
	center: new kakao.maps.LatLng(37.55437, 126.974063),
	level: 3
};
var map = new kakao.maps.Map(container, options);

var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
map.setMaxLevel(8);

var markers = [];
var infowindows = {};

function makeOverListener(marker, infowindow) {
	return function() {
		infowindow.open(map, marker);
	};
}

function makeOutListener(infowindow) {
	return function() {
		infowindow.close();
	};
}

var clusterer = new kakao.maps.MarkerClusterer({
	map: map,
	averageCenter: false,
	minLevel: 6
});

kakao.maps.event.addListener(map, 'idle', function() {
	var bounds = map.getBounds();
	var southWest = bounds.getSouthWest();
	var northEast = bounds.getNorthEast();

	var visibleAptList = aptList.filter(function(apt) {
		return (
			apt.aptLat >= southWest.getLat() &&
			apt.aptLat <= northEast.getLat() &&
			apt.aptLng >= southWest.getLng() &&
			apt.aptLng <= northEast.getLng()
		);
	});

	updateMarkers(visibleAptList);
});

function updateMarkers(visibleAptList) {
	markers.forEach(function(marker) {
		marker.setMap(null);
	});
	markers = [];

	visibleAptList.forEach(function(apt) {
		var marker = new kakao.maps.Marker({
			map: map,
			position: new kakao.maps.LatLng(apt.aptLat, apt.aptLng)
		});

		markers.push(marker);

		var infowindow = new kakao.maps.InfoWindow({
			content: apt.aptName
		});

		kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(marker, infowindow));
		kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));

		infowindows[apt.aptName] = infowindow;
	});

	clusterer.clear();
	clusterer.addMarkers(markers);
	clusterer.redraw();
}