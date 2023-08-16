  var container = document.getElementById('map');
  var options = {
      center: new kakao.maps.LatLng(35.099903, 129.02715),
      level: 3
  };

  var map = new kakao.maps.Map(container, options);

  console.log(busanList);

  for (var i = 0; i < busanList.length; i++) {
      var marker = new kakao.maps.Marker({
          map: map,
          position: new kakao.maps.LatLng(busanList[i].lat, busanList[i].lng)
      });
  }
