$(document).ready(function() {
    // 회원가입 페이지
    // 회원가입 취소 버튼
    $("#oJoinCancelBtn").click(function() {
        let cancelA = confirm('회원가입을 취소하시겠습니까? 메인화면으로 이동합니다.');
        if(cancelA) {
            location.href = '/';
            return true;
        } else { return false; }
    });

    // 내정보수정 취소 버튼
    $('#oCancelBtn').click(function() {
        let result = confirm('내정보를 수정을 취소하시겠습니까?\n내정보화면으로 이동합니다.');
        if(result) {
            location.href = '/member/qMyPage';
            return true;
        } else { return false; }
    });

    // 회원탈퇴 버튼 클릭 시 모달 열기
    $('#leaveBtn').click(function() {
        $('#pwdLeave').val(''); // 모달 열 때 입력한 비밀번호 초기화
        $('#pwdCheckModal').modal('show');
    });

     // 확인 버튼 클릭 시
     $('#confirmBtn').click(function() {
        var password = $('#pwdLeave').val().trim();
        if(password === '') {
            alert('비밀번호를 입력해주세요.');
            return;
        }
        // AJAX를 사용하여 비밀번호를 컨트롤러로 전송
        $.ajax({
           type: 'POST',
           url: '/member/qLeaveMember2',
           data: {
               password: password
           },
           success: function(result) {
               if (result === 1) {
                   alert('탈퇴가 완료되었습니다.');
                   window.location.href = '/';
               } else {
                   alert('아이디 또는 비밀번호를 다시 확인해주세요.');
               }
           },
           error: function() {
               alert('오류');
           }
        });
        // 모달 창 닫기
        $('#pwdCheckModal').modal('hide');
     });

     // 로그인 버튼 클릭 시 Ajax로 처리
      $('#loginForm').submit(function (event) {
         event.preventDefault();

         var email = $('#email').val().trim();
         var pwd = $('#pwd').val().trim();
         let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

         if(email === '') {
              alert('이메일을 입력해주세요.');
              return false;
          }
         else if(!emailPattern.test(email)) {
             alert('올바른 이메일형식이 아닙니다.');
             return false;
         }
         else if(pwd === '') {
             alert('비밀번호를 입력해주세요.');
             return false;
         }

         // JSON 형식의 데이터 생성
         var oSaveId = $('#oSaveIdCheckbox').is(':checked');

         // AJAX를 사용하여 데이터를 컨트롤러로 전송(로그인 요청)
         $.ajax({
            type: 'POST',
            url: '/member/qLoginBasic',
            contentType: 'application/json',    // Content-Type 설정
            data: JSON.stringify({ email: email, pwd: pwd, oSaveId: oSaveId }),
            success: function(result) {
                if (result === 1) {
                    // 쿠키 생성 여부 처리
                    var d = new Date();
                    d.setTime(d.getTime() + (30 * 24 * 60 * 60 * 1000)); // 30일을 밀리초로 계산

                    if (oSaveId) {
                        // 아이디를 저장할 쿠키 생성
                        document.cookie = "rememberedEmail=" + email + "; expires=" + d.toUTCString() + "; path=/";
                    } else {
                        // 아이디 저장을 원치 않는 경우 쿠키 삭제
                        document.cookie = "rememberedEmail=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
                    }
                    // 메인화면으로
                    window.location.href = '/';
                } else if(result === 0) {
                    alert('아이디 또는 비밀번호를 다시 확인해주세요.');
                }
            },
            error: function() {
                alert('오류');
            }
         });
      });
});

// SNS계정회원 탈퇴 submit 전 확인 취소
function oDeleteCheck() {
   let answer = confirm('정말 탈퇴하시겠습니까?\nSNS계정은 즉시 탈퇴처리됩니다.');
   return answer;
}

// 매물삭제 확인
function deleteMMCheck() {
   let answer = confirm('정말 삭제하시겠습니까?');
   return answer;
}

// 로그인 페이지
// 로그인 및 유효성(공백) 체크 (form 태그에서 onsubmit으로 호출해 submit 하기 전 체크)
//function oLoginCheck() {
//    let email = $('#email').val();
//    let pwd = $('#pwd').val();
//    let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
//
//    if(email === '') {
//        alert('이메일을 입력해주세요.');
//        return false;
//    }
//    if(pwd === '') {
//        alert('비밀번호를 입력해주세요.');
//        return false;
//    }
//    if(!emailPattern.test(email)) {
//        alert('올바른 이메일형식이 아닙니다.');
//        return false;
//    }
//}


//function deleteMMCheck() {
//    if (confirm('정말 삭제하시겠습니까?')) {
//        $.ajax({
//            type: 'POST',
//            url: '/member/qDeleteRecent',
//            data: {
//                 'maemul_id': $('#num').val()
//            },
//            success: function (result) {
//                alert('삭제가 완료되었습니다.');
//                window.location.href = '/member/qRecent'; // 삭제 후 리다이렉트
//            },
//            error: function () {
//                alert('삭제 중 오류가 발생했습니다.');
//            }
//        });
//    }
//    return false; // 폼 제출 방지
//}