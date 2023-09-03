// 전체 동의 버튼 요소와 각 항목의 동의 체크박스 요소를 가져옴
var agreementAllCheckbox = document.getElementById('agreementall');
var individualCheckboxes = document.querySelectorAll('.agreement input[type="checkbox"]');
var nextButton = document.querySelector('.next a');

// 전체 동의 버튼의 클릭 이벤트 처리
agreementAllCheckbox.addEventListener('click', function () {
    // 전체 동의 버튼의 상태에 따라 각 항목의 동의 상태를 변경
    for (var i = 0; i < individualCheckboxes.length; i++) {
        individualCheckboxes[i].checked = agreementAllCheckbox.checked;
    }
});

// 각 항목의 동의 체크박스의 상태를 모니터링하여 전체 동의 버튼 상태 변경
for (var i = 0; i < individualCheckboxes.length; i++) {
    individualCheckboxes[i].addEventListener('change', function () {
        // 모든 항목의 동의 상태를 확인하여 전체 동의 버튼 상태 변경
        var allChecked = true;
        for (var j = 0; j < individualCheckboxes.length; j++) {
            if (!individualCheckboxes[j].checked) {
                allChecked = false;
                break;
            }
        }
        agreementAllCheckbox.checked = allChecked;
    });
}

// 다음 버튼 클릭 시 이벤트 처리
nextButton.addEventListener('click', function (event) {
    // 전체 동의 버튼이 체크되어 있지 않으면 이동하지 않음
    if (!agreementAllCheckbox.checked) {
        event.preventDefault(); // 다음 페이지로의 이동을 막음
        alert('이용약관에 동의해주세요.'); // 경고 메시지 표시
    }
});