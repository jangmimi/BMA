
        function setActiveButton(button) {
          const buttons = document.querySelectorAll('.btn-group button');
          buttons.forEach(btn => {
              btn.classList.remove('active');
          });
          button.classList.add('active');
      }

      function showMonthRent() {
          document.getElementById("monthRentSection").classList.remove("hidden");
          document.getElementById("leaseSection").classList.add("hidden");
          document.getElementById("sellingSection").classList.add("hidden");
          setActiveButton(document.querySelector('.btn-group button:nth-child(1)'));
      }

      function showLease() {
          document.getElementById("monthRentSection").classList.add("hidden");
          document.getElementById("leaseSection").classList.remove("hidden");
          document.getElementById("sellingSection").classList.add("hidden");
          setActiveButton(document.querySelector('.btn-group button:nth-child(2)'));
      }

      function showSelling() {
          document.getElementById("monthRentSection").classList.add("hidden");
          document.getElementById("leaseSection").classList.add("hidden");
          document.getElementById("sellingSection").classList.remove("hidden");
          setActiveButton(document.querySelector('.btn-group button:nth-child(3)'));
      }

    // 주소 정보 입력란이 모두 채워지면 호출되는 함수
    function showBuildingInfo() {
        // 여기에서 주소 정보 입력란이 모두 채워진지를 확인하고, 그 결과에 따라 건물 정보 입력란을 보이게 합니다.
        var addressInputField = document.getElementById("addressInput");

        if (addressInputField.value !== "") {
            document.getElementById("buildingSection").style.display = "block";
        } else {
            document.getElementById("buildingSection").style.display = "none";
        }
    }

        // 건물 정보 입력란이 모두 채워지면 호출되는 함수
        function showPriceInfo() {
            var buildingInputField = document.getElementById("useInput");

            if (buildingInputField.value !== "") {
                document.getElementById("priceSection").style.display = "block";
            } else {
                document.getElementById("priceSection").style.display = "none";
            }
        }

     // 카카오 도로명 주소 검색 팝업을 열기 위한 함수
       function openKakaoAddressSearch(event) {
           new daum.Postcode({
               oncomplete: function(data) {
                   // 선택한 주소 정보를 입력 필드에 채우는 작업을 수행하세요.
                   document.getElementById('addressInput').value = data.address;
               }
           }).open();
       }


//내위치 기능

//         // 내 위치 정보 가져오기 함수
//            function getCurrentLocation() {
//                if ("geolocation" in navigator) {
//                    navigator.geolocation.getCurrentPosition(function(position) {
//                        // 현재 위치 정보를 처리하는 작업을 수행하세요.
//                        var latitude = position.coords.latitude;
//                        var longitude = position.coords.longitude;
//
//                        // 위도와 경도를 주소로 변환하는 함수 호출
//                        convertLatLongToAddress(latitude, longitude);
//                    });
//                } else {
//                    alert("브라우저가 위치 정보를 지원하지 않습니다.");
//                }
//            }
//
//            // 위도와 경도를 주소로 변환하는 함수
//            function convertLatLongToAddress(latitude, longitude) {
//                var geocoder = new google.maps.Geocoder();
//                var latlng = new google.maps.LatLng(latitude, longitude);
//
//                geocoder.geocode({ 'latLng': latlng }, function (results, status) {
//                    if (status == google.maps.GeocoderStatus.OK) {
//                        if (results[0]) {
//                            var formattedAddress = results[0].formatted_address;
//                            document.getElementById('addressInput').value = formattedAddress;
//                        } else {
//                            alert('주소를 찾을 수 없습니다.');
//                        }
//                    } else {
//                        alert('Geocoder에 실패했습니다: ' + status);
//                    }
//                });
//            }

        //팝업
    function showPopup(event) {
          var popup = document.getElementById("helpPopup");
          popup.style.display = "block";
      }

      function closePopup() {
          var popup = document.getElementById("helpPopup");
          popup.style.display = "none";
      }
