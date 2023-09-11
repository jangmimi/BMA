$(()=>{
	let 거주용 = $('.z-top-seperate1');
	let 업무용 = $('.z-top-seperate2');
	let 거주용_카운트 = $('.z-top-seperate1 .z-count');
	let 업무용_카운트 = $('.z-top-seperate2 .z-count');
	let 거주용_매물 = $('.z-home');
	let 업무용_매물 = $('.z-work');

	업무용_카운트.css('color', '#888');

	거주용.css('border-bottom', '2px solid #000'); // 거주용 아래 밑줄
	업무용.css('border-bottom', 'none'); // 업무용 아래 밑줄 없앰
	거주용_매물.css('display', 'block');
	업무용_매물.css('display', 'none');

	var is업무용Clicked = false;
	var is거주용Clicked = false;

    // 거주용 버튼 클릭 시
	거주용.click(()=>{
		if(!is거주용Clicked){
			업무용.css('border-bottom', 'none');
			업무용_카운트.css('color', '#888');
			거주용.css('border-bottom', '2px solid #000');
			거주용_카운트.css('color', '#129ded');
			거주용_매물.css('display', 'block');
			업무용_매물.css('display', 'none');
		}
	})

	// 업무용 버튼 클릭 시
	업무용.click(()=>{
		if(!is업무용Clicked){
			거주용.css('border-bottom', 'none');
			거주용_카운트.css('color', '#888');
			업무용.css('border-bottom', '2px solid #000');
			업무용_카운트.css('color', '#129ded');
			거주용_매물.css('display', 'none');
			업무용_매물.css('display', 'block');
		}
	})
})