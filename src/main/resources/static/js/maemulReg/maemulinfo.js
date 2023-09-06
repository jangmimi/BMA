<<<<<<< HEAD
   // HTML 요소를 가져옵니다.
   var monthRadio = document.getElementById("month");
   var yearRadio = document.getElementById("year");
   var sellRadio = document.getElementById("sell");

   var monthRentSection = document.getElementById("monthRentSection");
   var leaseSection = document.getElementById("leaseSection");
   var sellingSection = document.getElementById("sellingSection");

   // 라디오 버튼의 변경 이벤트 리스너를 추가합니다.
   monthRadio.addEventListener("change", function () {
       if (monthRadio.checked) {
           monthRentSection.style.display = "table"; // 월세 섹션을 표시
           leaseSection.style.display = "none"; // 전세 섹션을 숨김
           sellingSection.style.display = "none"; // 매매 섹션을 숨김

           // 월세 라디오 버튼을 선택한 경우, 전세와 매매 관련 필드의 값을 초기화
           document.getElementsByName("depositForLease")[0].value = "";
           document.getElementsByName("d_managementFee")[0].value = "";
           document.getElementsByName("SellingPrice")[0].value = "";
       }
   });

   yearRadio.addEventListener("change", function () {
       if (yearRadio.checked) {
           monthRentSection.style.display = "none"; // 월세 섹션을 숨김
           leaseSection.style.display = "table"; // 전세 섹션을 표시
           sellingSection.style.display = "none"; // 매매 섹션을 숨김

           // 전세 라디오 버튼을 선택한 경우, 월세와 매매 관련 필드의 값을 초기화
           document.getElementsByName("monthlyForRent")[0].value = "";
           document.getElementsByName("monthlyRent")[0].value = "";
           document.getElementsByName("m_managementFee")[0].value = "";
           document.getElementsByName("SellingPrice")[0].value = "";
       }
   });

   sellRadio.addEventListener("change", function () {
       if (sellRadio.checked) {
           monthRentSection.style.display = "none"; // 월세 섹션을 숨김
           leaseSection.style.display = "none"; // 전세 섹션을 숨김
           sellingSection.style.display = "table"; // 매매 섹션을 표시

           // 매매 라디오 버튼을 선택한 경우, 월세와 전세 관련 필드의 값을 초기화
           document.getElementsByName("monthlyForRent")[0].value = "";
           document.getElementsByName("monthlyRent")[0].value = "";
           document.getElementsByName("m_managementFee")[0].value = "";
           document.getElementsByName("depositForLease")[0].value = "";
           document.getElementsByName("d_managementFee")[0].value = "";
       }
   });

    // 주소 정보 입력란이 모두 채워지면 호출되는 함수
    function showBuildingInfo() {
        // 여기에서 주소 정보 입력란이 모두 채워진지를 확인하고, 그 결과에 따라 건물 정보 입력란을 보이게 합니다.
        var addressInputField = document.getElementById("addressInput");

        if (addressInputField.value !== "") {
            document.getElementById("buildingSection").style.display = "block";
        } else {
            document.getElementById("buildingSection").style.display = "none";
        }
=======
// HTML 요소
var monthRadio = document.getElementById("month");
var yearRadio = document.getElementById("year");
var sellRadio = document.getElementById("sell");

var monthRentSection = document.getElementById("monthRentSection");
var leaseSection = document.getElementById("leaseSection");
var sellingSection = document.getElementById("sellingSection");

// 월세 라디오 버튼의 변경 이벤트 리스너
monthRadio.addEventListener("change", function () {
    if (monthRadio.checked) {
        monthRentSection.style.display = "table"; // 월세 섹션을 표시
        leaseSection.style.display = "none"; // 전세 섹션을 숨김
        sellingSection.style.display = "none"; // 매매 섹션을 숨김

        // 월세 라디오 버튼을 선택한 경우, 전세와 매매 관련 필드의 값을 초기화
        document.getElementById("depositForLease").value = "";
        document.getElementById("d_managementFee").value = "";
        document.getElementById("SellingPrice").value = "";
>>>>>>> d173260d09cb0d2a50e8cf98086e309dfe778a1b
    }
});

// 전세 라디오 버튼의 변경 이벤트 리스너
yearRadio.addEventListener("change", function () {
    if (yearRadio.checked) {
        monthRentSection.style.display = "none"; // 월세 섹션을 숨김
        leaseSection.style.display = "table"; // 전세 섹션을 표시
        sellingSection.style.display = "none"; // 매매 섹션을 숨김

        // 전세 라디오 버튼을 선택한 경우, 월세와 매매 관련 필드의 값을 초기화
        document.getElementById("monthlyForRent").value = "";
        document.getElementById("monthlyRent").value = "";
        document.getElementById("m_managementFee").value = "";
        document.getElementById("SellingPrice").value = "";
    }
});

// 매매 라디오 버튼의 변경 이벤트 리스너
sellRadio.addEventListener("change", function () {
    if (sellRadio.checked) {
        monthRentSection.style.display = "none"; // 월세 섹션을 숨김
        leaseSection.style.display = "none"; // 전세 섹션을 숨김
        sellingSection.style.display = "table"; // 매매 섹션을 표시

        // 매매 라디오 버튼을 선택한 경우, 월세와 전세 관련 필드의 값을 초기화
        document.getElementById("monthlyForRent").value = "";
        document.getElementById("monthlyRent").value = "";
        document.getElementById("m_managementFee").value = "";
        document.getElementById("depositForLease").value = "";
        document.getElementById("d_managementFee").value = "";
    }
});

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