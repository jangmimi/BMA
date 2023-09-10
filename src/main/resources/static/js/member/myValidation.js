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

// 닉네임 중복검사
let previousNickname = $('#nickname').val().trim();    // 기존 닉네임 저장
function checkNickname() {
    let nickname = $('#nickname').val().trim();
        if (nickname === '') {
            alert('닉네임을 입력해주세요.');
            return;
        }
        performNicknameCheck(nickname);
    }
    // 이전 닉네임과 새로운 닉네임을 비교하여 변경 여부 확인
    if (previousNickname !== nickname) {
        $.ajax({
            url: '/member/qNicknameDuplicationCheck',
            type: 'post',
            data: {nickname:nickname},
            success: function (cnt) {
                if (cnt === 0) {
                    handleNicknameAvailable();
                } else {
                    handleNicknameUnavailable();
                }
            },
            error: function () {
                alert("에러입니다.");
            }
        });
    } else {
        alert('변경사항이 없습니다.');
    }
}

function handleNicknameAvailable() {
    $('#btnNicknameCheck').attr('class', 'btn btn-primary');
    $('#btnNicknameCheck').val("사용 가능");
    alert('닉네임 사용이 가능합니다.');
}

function handleNicknameUnavailable() {
    $('#btnNicknameCheck').attr('class', 'btn btn-danger');
    $('#btnNicknameCheck').val("사용 불가");
    alert('이미 존재하는 닉네임입니다.\n닉네임을 다시 입력해주세요.');
}

// 내정보수정 submit 전에 공백 및 유효성 체크
function oUpdateCheck() {
    const telreg = /^\d{10,11}$/;
    const pwdreg = /^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/;
    let name = $('#name').val().trim();
    let nickname = $('#nickname').val().trim();
    let tel = $('#tel').val().trim();
    let pwdValue = $('#pwd').val();
    let nicknameCheckOk = $("#btnNicknameCheck").val() === '사용가능';

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
    if (telreg.test(tel) == false) {
        alert('연락처 형식으로 입력해주세요.');
        return false;
    }
    if(pwdValue !== '') {    /* 비밀번호창 입력이 있을 경우에만 체크 */
        if (pwdValue.length < 8 || pwdValue.length > 20) {
            alert('비밀번호는 8자 이상, 20자 이하로 입력해주세요.');
            return false;
        }
        // 비밀번호 형식 체크 - 숫자와 영문 조합
        if (!pwdreg.test(pwdValue)) {
            alert('비밀번호는 숫자와 영문 조합으로 입력해주세요.');
            return false;
        }
    }
     if (!nicknameCheckOk) {
        alert('닉네임 중복 검사를 확인해주세요.');
        return false;
    }
    let confirmUpdate = confirm('입력한 정보로 수정하시겠습니까?');
    if(!confirmUpdate) return false;
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


/* 회원가입 유효성 검사 */
function oJoinCheck() {
    const reg = /^[A-Za-z0-9_\.]+@[A-Za-z0-9]+\.[A-Za-z0-9]+/;
    const telreg = /^\d{10,11}$/;
    const pwdreg = /^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/;
    let email = $('#email').val();
    let name = $('#name').val();
    let pwd = $('#pwd').val();
    let pwdCheck = $('#pwdCheck').val();
    let nickname = $('#nickname').val();
    let tel = $('#tel').val();
    let checkedboxes = $("input.oMustAgree:checked");
    let allChecked = true;
    let emailCheckOk = $("#btnEmailCheck").val();
    let nicknameCheckOk = $("#btnNicknameCheck").val();

     let errorMessage = '';

    if(email === '') {
        errorMessage = '이메일을 입력해주세요.';
    } else if (reg.test(email) == false) {
        errorMessage = '이메일 형식으로 입력해주세요.';
    } else if(name === '') {
        errorMessage = '이름을 입력해주세요.';
    } else if(pwd === '') {
        errorMessage = '비밀번호를 입력해주세요.';
    } else if (!pwdreg.test(pwd)) {
        errorMessage = '비밀번호 형식으로 입력해주세요.';
    } else if(pwdCheck === '') {
        errorMessage = '비밀번호를 확인해주세요.';
    } else if(nickname === '') {
        errorMessage = '닉네임을 입력해주세요.';
    } else if(tel === '') {
        errorMessage = '연락처를 입력해주세요.';
    } else if (telreg.test(tel) == false) {
        errorMessage = '연락처 형식으로 입력해주세요.';
    } else if(checkedboxes.length !== $(".oMustAgree").length) {
        errorMessage = '필수항목을 모두 체크해주세요.';
    } else if(emailCheckOk == '중복검사' || emailCheckOk == '사용불가' ||
                nicknameCheckOk == '중복검사'|| nicknameCheckOk == '사용불가' ) {
        errorMessage = '중복체크를 확인해주세요.';
    }

    if (errorMessage !== '') {
        alert(errorMessage);
        return false;
    }
     alert('회원가입이 완료되었습니다!');
    return true; // 모든 조건 통과
}

/* 비밀번호 표시 토글 */
$(document).ready(function(){
    $('.form-floating i').on('click',function(){
        $('#pwd').toggleClass('active');
        if($('#pwd').hasClass('active')){
            $(this).attr('class',"fa fa-eye-slash fa-lg")
            .prev('#pwd').attr('type',"text");
            $(".form-floating i").css("color","#26ABED");
        }else{
            $(this).attr('class',"fa fa-eye fa-lg")
            .prev('#pwd').attr('type','password');
            $(".form-floating i").css("color","silver");
        }
    });
});
