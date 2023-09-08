/* oMyInfoUpdate */
$(document).ready(function() {
    $('#oCancelBtn').click(function() {
        let result = confirm('내정보를 수정을 취소하시겠습니까?\n내정보화면으로 이동합니다.');
        if(result) {
            location.href = '/member/qMyPage';
            return true;
        } else { return false; }
    });

    // 비밀번호/비밀번호 확인 일치 여부 체크, 비밀번호 형식 체크
    $("#pwd, #pwdCheck").focusout(function(){
        let pwdValue = $('#pwd').val();
        let pwdCheckValue = $('#pwdCheck').val();
        if (pwdValue !== pwdCheckValue) {
            $("#opwdCheck1").css("display","block");
        } else {
            $("#opwdCheck1").css("display","none");
        }
    });
    $("#pwd").keyup(function(){
    // 비밀번호 형식 체크 : 8자 이상, 20자 이하, 숫자와 영문 조합
        let pwdValue = $(this).val();
        let pwdreg = /^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/;
        if (pwdValue.length < 8 || pwdValue.length > 20 || !pwdreg.test(pwdValue)) {
            $("#opwdCheck2").css("display","block");
        } else {
            $("#opwdCheck2").css("display","none");
        }
    });

   // 회원탈퇴 버튼 클릭 시 모달 열기
      $(document).ready(function() {
          $('#leaveBtn').click(function() {
              $('#pwdLeave').val(''); // 모달 열 때 입력한 비밀번호 초기화
              $('#pwdCheckModal').modal('show');
          });

       // 확인 버튼 클릭 시
      $('#confirmBtn').click(function() {
           var password = $('#pwdLeave').val();
           if(password === null || password === '') {
                alert('비밀번호를 입력해주세요.');
           }
           // AJAX를 사용하여 비밀번호를 컨트롤러로 전송
           $.ajax({
               type: 'POST',
               url: '/member/qLeaveMember2', // 컨트롤러 URL 수정 필요
               data: {
                   password: password
               },
               success: function(result) {
                   // 성공적으로 처리된 경우
                   // 처리 결과에 따라 적절한 동작 수행
                   if (result === 1) {
                       alert('탈퇴가 완료되었습니다.');
                       window.location.href = '/';
                   } else {
                       alert('아이디 또는 비밀번호를 다시 확인해주세요.');
                       window.location.href=window.location.href;
                   }
               },
               error: function() {
                   // 오류 처리
                   alert('오류');
               }
           });

           // 모달 창 닫기
           $('#pwdCheckModal').modal('hide');
       });
   });

   // 비밀번호 임시메일 발송
    $("#checkEmail").click(function () {
        const userEmail = $("#userEmail").val();
        const sendEmail = document.forms["sendEmail"];
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

// 내정보수정 submit 전에 공백 체크
function oUpdateCheck() {
    let name = $('#name').val();
    let nickname = $('#nickname').val();
    let tel = $('#tel').val();

    if(name === '') {
        alert('이름을 입력해주세요.');
        return false;
    }
    if(nickname === '') {
        alert('닉네임을 입력해주세요.');
        return false;
    }
    if(tel === '') {
        alert('연락처를 입력해주세요.');
        return false;
    }
    let confirmUpdate = confirm('입력한 정보로 수정하시겠습니까?');
    if(!confirmUpdate) return false;
}

// 회원탈퇴 submit 전 확인 취소
function oDeleteCheck() {
    let answer = confirm('정말 탈퇴하시겠습니까?');
    if(answer) { return true; }
    else { return false; }
}

/* oFindMemberInfo */
function findEmail() {
    let name = $('#name').value;
    let tel = $('#tel').value;

    fetch('/qFindEmail', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'name=' + encodeURIComponent(name) + '&tel=' + encodeURIComponent(tel),
    })
    .then(response => response.json())
    .then(data => {
        if (data.cnt === 1) {
            alert('이메일: ' + data.findEmail);
        } else {
            alert('일치하는 회원이 없습니다.');
        }
    })
    .catch(error => {
        console.error('에러 발생:', error);
    });

    return false; // 폼 제출 방지
}

// 이메일/비밀번호 찾기 submit 전에 공백 체크
function oFindEmailCheck() {
    let name = $('#name').val();
    let tel = $('#tel').val();
    if(name === '') {
        alert('이름을 입력해주세요.');
        return false;
    }
    if(tel === '') {
        alert('연락처를 입력해주세요.');
        return false;
    }
}
function oFindPwdCheck() {
    let userEmail = $('#userEmail').val();
//    let telpwd = $('#telpwd').val();
    if(userEmail === '') {
        alert('이메일 입력해주세요.');
        return false;
    }
//    if(telpwd === '') {
//        alert('연락처를 입력해주세요.');
//        return false;
//    }
}

// 닉네임 중복검사
function checkNickname() {
    let nickname = $('#nickname').val();

    $.ajax({
        url: '/member/qNicknameDuplicationCheck',
        type: 'post',
        data: {nickname:nickname},
        success:function(cnt) {
            if(cnt == 0 && nickname.length != 0) {
                $('#btnNicknameCheck').attr('class','btn btn-primary');
                $('#btnNicknameCheck').val("사용 가능");
                alert('닉네임 사용이 가능합니다.');
            } else if(cnt != 0) {
                $('#btnNicknameCheck').attr('class','btn btn-danger');
                $('#btnNicknameCheck').val("사용 불가");
                alert('이미 존재하는 닉네임입니다.\n닉네임을 다시 입력해주세요.');
            //    $('#email').val('');
            } else if(nickname.length == 0) {
                alert('닉네임을 입력해주세요.');
            }
        },
        error:function() {
            alert("에러입니다.");
        }
    });
}



// 이메일 찾기 submit
//function findEmail(event) {
//    let name = $('#name').value;
//    let tel = $('#tel').value;
//
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

//test
//function findEmail(event) {
//    event.preventDefault(); // 폼 제출 방지
//    let name = $('#name').val();
//    let tel = $('#tel').val();
//
//    fetch('/qFindEmailtest', {
//        method: 'POST',
//        headers: {
//            'Content-Type': 'application/x-www-form-urlencoded',
//        },
//        body: 'name=' + encodeURIComponent(name) + '&tel=' + encodeURIComponent(tel),
//    })
//    .then(response => response.json())
//    .then(data => {
//        if (data.findEmail) {
//            // 결과를 현재 페이지에 표시
//            $('#resultContainer').html('이메일: ' + data.findEmail);
//        } else {
//            // 결과가 없는 경우 메시지 표시
//            alert('이메일 : ' + data.findEmail);
//            $('#resultContainer').html('일치하는 회원이 없습니다.');
//        }
//    })
//    .catch(error => {
//        console.error('에러 발생:', error);
//    });
//}

