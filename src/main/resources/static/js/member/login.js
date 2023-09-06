// 로그인 및 유효성(공백) 체크 (form 태그에서 onsubmit으로 호출해 submit 하기 전 체크)
function oLoginCheck() {
    let email = $('#email').val();
    let pwd = $('#pwd').val();
    let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    if(email === '') {
        alert('이메일을 입력해주세요.');
        return false;
    }
    if(pwd === '') {
        alert('비밀번호를 입력해주세요.');
        return false;
    }
    if(!emailPattern.test(email)) {
        alert('올바른 이메일형식이 아닙니다.');
        return false;
    }
};





/* 로그인 체크 */
//function checkLogin() {
//    let email = $('#email').val();
//    let pwd = $('#pwd').val();
//
//    $.ajax({
//        url: '/member/qLoginCheck',
//        type: 'post',
//        data: {email:email},
//        success:function(cnt) {
//            if(cnt == 0 && email.length != 0) {
//                $('#btnEmailCheck').attr('class','btn btn-primary');
//                $('#btnEmailCheck').val("사용 가능");
//                alert('이메일 사용이 가능합니다.');
//            } else if(cnt != 0) {
//                $('#btnEmailCheck').attr('class','btn btn-danger');
//                $('#btnEmailCheck').val("사용 불가");
//                alert('이미 존재하는 이메일입니다.\n이메일을 다시 입력해주세요.');
//            //    $('#email').val('');
//            } else if(email.length == 0) {
//                alert('이메일을 입력해주세요.');
//            }
//        },
//        error:function() {
//            alert("에러입니다.");
//        }
//    });
//};

//var naver_id_login = new naver_id_login("KxGhFHZ7Xp74X_5IZ23h", "http://localhost:8081/member/qLoginNaverCallback");
//var state = naver_id_login.getUniqState();
//naver_id_login.setButton("green", 4,45);
//naver_id_login.setDomain("http://localhost:8081/member/testnaverLogin");
//naver_id_login.setState(state);
//naver_id_login.setPopup();
//naver_id_login.init_naver_id_login();
