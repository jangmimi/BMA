  var container = document.getElementById('map');
  var options = {
      center: new kakao.maps.LatLng(35.099903, 129.02715),
      level: 3
  };

  var map = new kakao.maps.Map(container, options);

  var busanListElement = document.getElementById('busanList');
  var busanListData = JSON.parse(busanListElement.getAttribute('data-busan-list-th'));

  for (var i = 0; i < busanListData.length; i++) {
      var marker = new kakao.maps.Marker({
          map: map,
          position: new kakao.maps.LatLng(busanListData[i].LAT, busanListData[i].LNG)
      });
  }
