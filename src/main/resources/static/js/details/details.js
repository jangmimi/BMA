

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
	// view 전용 js 끝

	console.log("멤버는 밑에");
	if (member) {
		console.log("1번 ", member.name);
		console.log('2번', member);
		console.log("3번", member.nickname);
		console.log('4번', maemulList.id);
	} else {
		console.log("멤버 데이터가 없습니다.");
	}


});


function like() {
	console.log("like() 실행");
	if (member && member.nickname !== null) {
		// 클라이언트에서 즉시 버튼 색상 변경
		toggleHeartIcon();

		$.ajax({
			url: "/details/like",
			type: "POST",
			data: { 'id': maemulList.id, 'nickname': member.nickname },
			success: function (response) {
				console.log("성공");
				// 서버 응답 후 아이콘 변경은 필요하지 않음
			},
			error: function (jqXHR, textStatus, errorThrown) {
				console.log("에러");
				alert('로그인 후 이용 가능합니다.');
				// 에러 발생 시 버튼 색상 원래대로 되돌리기
				toggleHeartIcon();
			}
		});
	} else {
		alert('로그인 후 이용 가능합니다.');
	}
}

function toggleHeartIcon() {
	// 현재 하트 아이콘의 fill 속성 값을 확인합니다.
	var heartIcon = $('.fav').find('svg');
	var currentFill = heartIcon.find('path').css('fill');

	// 클라이언트에서는 좋아요 여부에 따라 fill 속성을 변경합니다.
	if (currentFill === 'red' || currentFill === 'rgb(255, 0, 0)') {
		// 빨간색 -> 기본색 (복원)
		heartIcon.find('path').css('fill', 'none');
		heartIcon.find('path').css('stroke', 'black');
	} else {
		// 기본색 -> 빨간색
		heartIcon.find('path').css('fill', 'red');
		heartIcon.find('path').css('stroke', 'red');
	}
}

// 페이지 로드 시 좋아요 여부에 따라 아이콘 색상 설정
$(document).ready(function () {
	// 좋아요 여부를 서버에서 가져옴
	$.get("/details/like", { 'id': maemulList.id, 'nickname': member.nickname }, function (response) {
		if (response.liked) {
			console.log(response.liked);
			console.log(response);
			// 좋아요한 경우, 아이콘을 빨간색으로 설정

			$('.fav').find('svg').find('path').css('fill', 'none');
			$('.fav').find('svg').find('path').css('stroke', 'black');
		} else{
			$('.fav').find('svg').find('path').css('fill', 'red');
			$('.fav').find('svg').find('path').css('stroke', 'red');
		}
	});
});