/* oMyInfoUpdate */
$(document).ready(function() {
    $("#nickname").on("input", function() {
        $('#btnNicknameCheck').attr('class','btn btn-outline-dark');
        $('#btnNicknameCheck').val("중복검사");
    });

    // 비밀번호 일치 여부, 형식 체크 함수
    function checkPassword() {
        const pwdValue = $('#pwd').val().trim();
        const pwdCheckValue = $('#pwdCheck').val().trim();
        const pwdreg = /^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/;

        // 비밀번호 일치 여부 체크
        if (pwdValue !== pwdCheckValue) {
            $("#opwdCheck1").css("display", "block");
        } else {
            $("#opwdCheck1").css("display", "none");
        }

        // 비밀번호 형식 체크
        if (pwdValue.length < 8 || pwdValue.length > 20 || !pwdreg.test(pwdValue)) {
            $("#opwdCheck2").css("display", "block");
        } else {
            $("#opwdCheck2").css("display", "none");
        }
    }
    // 비밀번호 일치 여부 체크
    $("#pwd, #pwdCheck").on("input", checkPassword);

    // 비밀번호 형식 체크
    $("#pwd").on("keyup", checkPassword);

    // 연락처 입력 창 형식
    $("#tel").keyup(function(){
        let telValue = $("#tel").val().trim();
        telValue = telValue.replace(/[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]|[^\w\s-]/g, "").replace(/-/g, "");
        $("#tel").val(telValue);
    });

    // 취소버튼 클릭 시 확인창
    $('#oCancelBtn').click(function() {
        let result = confirm('내정보를 수정을 취소하시겠습니까?\n내정보화면으로 이동합니다.');
        if(result) {
            location.href = '/member/qMyPage';
            return true;
        } else { return false; }
    });

   // 회원탈퇴 버튼 클릭 시 모달 열기
      $(document).ready(function() {
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
   });

   // 비밀번호 임시메일 발송
    $("#checkEmail").click(function () {
        const userEmail = $("#userEmail").val().trim();
        const sendEmail = document.forms["sendEmail"];
        const emailreg = /^[A-Za-z0-9_\.]+@[A-Za-z0-9]+\.[A-Za-z0-9]+/;

        if(userEmail === '') {
            alert('이메일을 입력해주세요.');
            return false;
        }
        if(!emailreg.test(userEmail)) {
            alert('이메일 형식으로 입력해주세요.');
            return false;
        }
        $.ajax({
            type: 'post',
            url: 'emailDuplication',
            data: {
                'userEmail': userEmail
            },
            dataType: "text",
            success: function (result) {
                if(result == "no"){
                    // 중복되는 것이 있다면 no == 일치하는 이메일이 있다!
                    alert('임시비밀번호를 전송 했습니다.');
                    sendEmail.submit();
                }else {
                    alert('가입되지 않은 이메일입니다.');
                }
            },error: function () {
                console.log('에러 체크!!')
            }
        })
    });
});

// SNS계정회원 탈퇴 submit 전 확인 취소
function oDeleteCheck() {
   let answer = confirm('정말 탈퇴하시겠습니까?\nSNS계정은 즉시 탈퇴처리됩니다.');
   return answer;
}

// oFindMemberInfo.html

// 이메일찾기 submit 전에 공백, 형식 체크
function oFindEmailCheck() {
    const name = $('#name').val().trim();
    const tel = $('#tel').val().trim();
    const telreg = /^\d{10,11}$/;

    if (name === '') {
        alert('이름을 입력해주세요.');
        return false;
    }
    if (tel === '' || !telreg.test(tel)) {
        alert('올바른 연락처 형식으로 입력해주세요.');
        return false;
    }
}

// 이메일찾기 ajax 구현
//function findEmail() {
//    let name = $('#name').value.trim();
//    let tel = $('#tel').value.trim();
//    const telreg = /^\d{10,11}$/;
//
//    if(name === '') {
//        alert('이름을 입력해주세요.');
//        return false;
//    }
//    if(tel === '') {
//        alert('연락처를 입력해주세요.');
//        return false;
//    }
//  if(!telreg.test(tel) {
//        alert('연락처 형식으로 입력해주세요.');
//        return false;
//    }
//    fetch('/qFindEmail', {
//        method: 'POST',
//        headers: {
//            'Content-Type': 'application/x-www-form-urlencoded',
//        },
//        body: 'name=' + encodeURIComponent(name) + '&tel=' + encodeURIComponent(tel),
//    })
//    .then(response => response.json())
//    .then(data => {
//        if (data.cnt === 1) {
//            alert('이메일: ' + data.findEmail);
//        } else {
//            alert('일치하는 회원이 없습니다.');
//        }
//    })
//    .catch(error => {
//        console.error('에러 발생:', error);
//    });
//    return false; // 폼 제출 방지
//}
