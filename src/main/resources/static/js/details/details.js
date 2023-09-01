

$(document).ready(function() {
	(function() {
		var script = document.createElement('script');
		script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=9a3ebc8549eeffd6c970f997b9f91c0c';
		script.async = true;
		document.getElementsByTagName('head')[0].appendChild(script);

		script.onload = function() {
			// Kakao 지도 스크립트가 로드되면 실행
			var container = $('.y-map'); // 지도를 담을 영역의 DOM 레퍼런스
			var options = { // 지도를 생성할 때 필요한 기본 옵션
				center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표.
				level: 3 // 지도의 레벨(확대, 축소 정도)
			};

			// 지도 초기화
			var map = new kakao.maps.Map(container.get(0), options);
		};
	})();

	// 초기에는 "편의시설" wrap만 표시되도록 설정
	$('.y-convenience').css('display', 'inline-block');
	$('.y-map-btn.y-safety-district, .y-map-btn.y-school-district').css('display', 'none');

	// 편의시설 버튼 클릭 시
	$('.y-qq').click(function() {
		// 편의시설 wrap 표시, 나머지 숨김
		$('.y-convenience').css('display', 'inline-block');
		$('.y-map-btn.y-safety-district, .y-map-btn.y-school-district').css('display', 'none');
	});

	// 안전시설 버튼 클릭 시
	$('.y-qw').click(function() {
		// 안전시설 wrap 표시, 나머지 숨김
		$('.y-map-btn.y-safety-district').css('display', 'inline-block');
		$('.y-convenience, .y-map-btn.y-school-district').css('display', 'none');
	});

	// 학군정보 버튼 클릭 시
	$('.y-qe').click(function() {
		// 학군정보 wrap 표시, 나머지 숨김
		$('.y-map-btn.y-school-district').css('display', 'inline-block');
		$('.y-convenience, .y-map-btn.y-safety-district').css('display', 'none');
	});
	// 편의시설 버튼을 초기 클릭 상태로 설정
	$('.y-map-list.y-qq button').css({
		'color': 'black',
		'font-weight': 'bold',
		'font-size': '16px',
		'border-bottom': '2px solid #000'
	});

	// 나머지 버튼을 초기 클릭 상태로 설정
	$('.y-map-list.y-qw button, .y-map-list.y-qe button').css({
		'color': '#AEAEAE',
	});


	// 모든 버튼 요소에 대한 클릭 이벤트 리스너
	$('.y-map-list button').click(function() {
		// 클릭한 버튼의 스타일을 저장
		const clickedButtonStyle = {
			'color': 'black',
			'font-weight': 'bold',
			'font-size': '16px',
			'border-bottom': '2px solid #000'
		};

		// 모든 버튼의 스타일 초기화
		$('.y-map-list button').css({
			'color': '#AEAEAE',
			'font-weight': 'normal',
			'font-size': 'inherit',
			'border-bottom': 'none'
		});

		// 클릭한 버튼에 스타일 적용
		$(this).css(clickedButtonStyle);
	});
});
