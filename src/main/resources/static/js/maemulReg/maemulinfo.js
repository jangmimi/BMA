
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

        // 카카오 도로명 api
        function getRoadAddress() {
        new daum.Postcode({
        oncomplete: function(data) {
        document.getElementById("addressInput").value = data.roadAddress;
        }
        }).open();
        }

        function getCurrentLocation() {
            if ("geolocation" in navigator) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    const latitude = position.coords.latitude;
                    const longitude = position.coords.longitude;

                    const geocoder = new daum.maps.services.Geocoder();
                    geocoder.coord2Address(longitude, latitude, function(result, status) {
                        if (status === daum.maps.services.Status.OK) {
                            const fullAddress = result[0].address.address_name;
                            document.getElementById("addressInput").value = fullAddress;
                        }
                    });
                });
            } else {
                alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
            }
        }

        //팝업
        const helpLink = document.getElementById("helpLink");

        // 이벤트 리스너 연결
        helpLink.addEventListener("click", function() {
            helpPopup.style.display = "block";
        });

        // 팝업 닫기 함수
        function closePopup() {
           const helpPopup = document.getElementById("helpPopup");
            document.getElementById("helpPopup").style.display = "none";
        }
