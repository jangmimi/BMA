

$(document).ready(function() {



	$('.y-ibtn').hover(function() {
		// 마우스 오버 시에 실행되는 코드
		$(this).css('border-bottom', '2px solid black');
	}, function() {
		// 마우스 아웃 시에 실행되는 코드
		$(this).css('border-bottom', 'none'); // 다시 원래 스타일로 변경
	});

	// 초기에는 "편의시설" wrap만 표시되도록 설정
	$('.y-station').css('background', 'url("/img/details/station.png") center center / cover no-repeat transparent'); // 클릭된 이미지
	$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent'); // 클릭 안 된 이미지
	$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent'); // 클릭 안 된 이미지
	$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent'); // 클릭 안 된 이미지
	$('.y-hospital').css('background-color', '#ECECEC'); // 클릭 안 된 색상
	$('.y-hospital').css('color', '#979797'); // 클릭 안 된 색상
	$('.y-gas-station').css('background-color', '#ECECEC'); // 클릭 안 된 색상
	$('.y-gas-station').css('color', '#979797'); // 클릭 안 된 색상


	var isSubwayClicked = false;
	var isConvenienceClicked = false;
	var isCafeClicked = false;
	var isBankClicked = false;
	var isHospitalClicked = false;
	var isGasStationClicked = false;


	// 지하철 버튼 클릭 시
	$('.y-j-subway').click(function() {
		if(isSubwayClicked){
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-gas-station').css('background-color', '#ECECEC');
			isSubwayClicked = true;
			isConvenienceClicked = false;
			isCafeClicked = false;
			isBankClicked =false;
			isHospitalClicked = false;
			isGasStationClicked = false;
		} else {
			$('.y-station').css('background', 'url("/img/details/station.png") center center / cover no-repeat transparent');
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-hospital').css('color', '#979797');
			$('.y-gas-station').css('background-color', '#ECECEC');
			$('.y-gas-station').css('color', '#979797');
			isSubwayClicked = false;
		}
	});

	// 편의점 버튼 클릭 시
	$('.y-j-convenience').click(function() {
		if(isConvenienceClicked){
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-gas-station').css('background-color', '#ECECEC');
			isSubwayClicked = false;
			isConvenienceClicked = true;
			isCafeClicked = false;
			isBankClicked =false;
			isHospitalClicked = false;
			isGasStationClicked = false;
		} else {
			$('.y-store').css('background', 'url("/img/details/store.png") center center / cover no-repeat transparent');
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-hospital').css('color', '#979797');
			$('.y-gas-station').css('background-color', '#ECECEC');
			$('.y-gas-station').css('color', '#979797');
			isConvenienceClicked = false;
		}
	});

	// 카페 버튼 클릭 시
	$('.y-j-cafe').click(function() {
		if(isConvenienceClicked){
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-gas-station').css('background-color', '#ECECEC');
			isSubwayClicked = false;
			isConvenienceClicked = false;
			isCafeClicked = true;
			isBankClicked =false;
			isHospitalClicked = false;
			isGasStationClicked = false;
		} else {
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-hospital').css('color', '#979797');
			$('.y-gas-station').css('background-color', '#ECECEC');
			$('.y-gas-station').css('color', '#979797');
			isCafeClicked = false;
		}
	});

	// 은행 버튼 클릭 시
	$('.y-j-bank').click(function() {
		if(isConvenienceClicked){
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-gas-station').css('background-color', '#ECECEC');
			isSubwayClicked = false;
			isConvenienceClicked = false;
			isCafeClicked = false;
			isBankClicked =true;
			isHospitalClicked = false;
			isGasStationClicked = false;
		} else {
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-hospital').css('color', '#979797');
			$('.y-gas-station').css('background-color', '#ECECEC');
			$('.y-gas-station').css('color', '#979797');
			isBankClicked = false;
		}
	});

	// 병원 버튼 클릭 시
	$('.y-j-hospital').click(function() {
		if(isConvenienceClicked){
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-gas-station').css('background-color', '#ECECEC');
			isSubwayClicked = false;
			isConvenienceClicked = false;
			isCafeClicked = false;
			isBankClicked =false;
			isHospitalClicked = true;
			isGasStationClicked = false;
		} else {
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#854141');
			$('.y-hospital').css('color', 'white');
			$('.y-gas-station').css('background-color', '#ECECEC');
			$('.y-gas-station').css('color', '#979797');

			isHospitalClicked = false;
		}
	});

	// 주유소 버튼 클릭 시
	$('.y-j-gas-staion').click(function() {
		if(isConvenienceClicked){
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-gas-station').css('background-color', '#ECECEC');
			isSubwayClicked = false;
			isConvenienceClicked = false;
			isCafeClicked = false;
			isBankClicked =false;
			isHospitalClicked = false;
			isGasStationClicked = true;
		} else {
			$('.y-store').css('background', 'url("/img/details/store_n.png") center center / cover no-repeat transparent');
			$('.y-station').css('background', 'url("/img/details/station_n.png") center center / cover no-repeat transparent');
			$('.y-cafe').css('background', 'url("/img/details/cafe_n.png") center center / cover no-repeat transparent');
			$('.y-bank').css('background', 'url("/img/details/bank_n.png") center center / cover no-repeat transparent');
			$('.y-hospital').css('background-color', '#ECECEC');
			$('.y-hospital').css('color', '#979797');
			$('.y-gas-station').css('background-color', '#A78E62');
			$('.y-gas-station').css('color', 'white');
			isGasStationClicked = false;
		}
	});



});

