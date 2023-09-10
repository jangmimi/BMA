
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
   }

});

// 전세 라디오 버튼의 변경 이벤트 리스너
yearRadio.addEventListener("change", function () {
   if (yearRadio.checked) {
       monthRentSection.style.display = "none"; // 월세 섹션을 숨김
       leaseSection.style.display = "table"; // 전세 섹션을 표시
       sellingSection.style.display = "none"; // 매매 섹션을 숨김
   }

});

// 매매 라디오 버튼의 변경 이벤트 리스너
sellRadio.addEventListener("change", function () {
   if (sellRadio.checked) {
       monthRentSection.style.display = "none"; // 월세 섹션을 숨김
       leaseSection.style.display = "none"; // 전세 섹션을 숨김
       sellingSection.style.display = "table"; // 매매 섹션을 표시
   }

});

  //팝업 열기
    function showPopup(event) {
          var popup = document.getElementById("helpPopup");
          popup.style.display = "block";
      }

 // 팝업 닫기
 function closePopup(event) {
     var popup = document.getElementById("helpPopup");
     popup.style.display = "none";

     // 이벤트의 기본 동작(새로고침)을 막음
     event.preventDefault();
 }


  // 숫자를 입력하는 곳에 음수를 입력할 때 경고 메시지를 표시하는 함수
     function checkNonNegativeInput(inputElement, fieldName) {
         const inputValue = parseFloat(inputElement.value);
         if (isNaN(inputValue) || inputValue < 0) {
             alert(fieldName + "에는 음수를 입력할 수 없습니다.");
             inputElement.value = ''; // 입력 값을 비웁니다.
         }
     }

     // 방 수 입력 필드
     const numberOfRoomsInput = document.getElementById('useInput');
     numberOfRoomsInput.addEventListener('blur', function () {
         checkNonNegativeInput(numberOfRoomsInput, '방 수');
     });

     // 욕실 수 입력 필드
     const numberOfBathroomsInput = document.getElementsByName('numberOfBathrooms')[0];
     numberOfBathroomsInput.addEventListener('blur', function () {
         checkNonNegativeInput(numberOfBathroomsInput, '욕실 수');
     });

     // 해당 층 수 입력 필드
     const floorNumberInput = document.getElementsByName('floorNumber')[0];
     floorNumberInput.addEventListener('blur', function () {
         checkNonNegativeInput(floorNumberInput, '해당 층 수');
     });

     // 건물 층 수 입력 필드
     const totalFloorsInput = document.getElementsByName('totalFloors')[0];
     totalFloorsInput.addEventListener('blur', function () {
         checkNonNegativeInput(totalFloorsInput, '건물 층 수');
     });

        document.getElementById("maemulForm").addEventListener("submit", function(event) {
             // 선택한 라디오 버튼의 값을 가져옵니다.
             const selectedTradeType = document.querySelector('input[name="tradeType"]:checked');

             // 선택한 라디오 버튼이 없다면 경고 메시지를 표시하고 폼 제출을 취소합니다.
             if (!selectedTradeType) {
                 alert("거래 유형을 선택해주세요.");
                 event.preventDefault(); // 폼 제출을 취소합니다.
             }
         });



