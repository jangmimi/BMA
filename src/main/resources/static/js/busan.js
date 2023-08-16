  var container = document.getElementById('map');
  var options = {
      center: new kakao.maps.LatLng(35.099903, 129.02715),
      level: 3
  };

  var map = new kakao.maps.Map(container, options);

  for (var i = 0; i < busanList.length; i++) {
      // 타임리프로 가져온 busanList의 매개변수값이 소문자로 표기되서 대문자로 변경하는 코드
      // 변수명 통일을 위해 대문자로 변경함
      var convertedData = {};
          for (var key in busanList[i]) {
              var upperCaseKey = key.toUpperCase();
              convertedData[upperCaseKey] = busanList[i][key];
          }

      var marker = new kakao.maps.Marker({
          map: map,
          position: new kakao.maps.LatLng(convertedData.LAT, convertedData.LNG)
      });

      var infowindow = new kakao.maps.InfoWindow({
              content: convertedData.MAIN_TITLE // 인포윈도우에 표시할 내용
          });

      kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
      kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));

      // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
      function makeOverListener(map, marker, infowindow) {
          return function() {
              infowindow.open(map, marker);
          };
      }

      // 인포윈도우를 닫는 클로저를 만드는 함수입니다
      function makeOutListener(infowindow) {
          return function() {
              infowindow.close();
          };
      }
  }
