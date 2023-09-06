$(document).ready(function(){
    // 전체 선택 체크박스
    const selectAllCheckbox = $("input[name='selectAll']");
    // 개별 체크박스들
    const requiredCheckboxes = $("input[name='agree'][data-required]");
    const choice1Checkbox = $("#choice1");
    const choice2Checkbox = $("#choice2");

    // 개별 체크박스가 변경될 때
    requiredCheckboxes.add(choice1Checkbox).add(choice2Checkbox).on("change", function () {
        updateSelectAllCheckbox();
    });

    // 전체 선택 체크박스가 변경될 때
    selectAllCheckbox.on("change", function () {
        const isChecked = $(this).prop("checked");
        requiredCheckboxes.add(choice1Checkbox).add(choice2Checkbox).prop("checked", isChecked);
    });

    // 개별 체크박스의 상태에 따라 전체 선택 체크박스 상태 갱신
    function updateSelectAllCheckbox() {
        const requiredAllChecked = requiredCheckboxes.filter(":checked").length === requiredCheckboxes.length;
        const choice1Checked = choice1Checkbox.prop("checked");
        const choice2Checked = choice2Checkbox.prop("checked");

        if (requiredAllChecked && choice1Checked && choice2Checked) {
            selectAllCheckbox.prop("checked", true);
        } else if (!requiredAllChecked || !choice1Checked || !choice2Checked) {
            selectAllCheckbox.prop("checked", false);
        }
    }

    // 비밀번호/비밀번호 확인 일치 여부 체크
    $("#pwd, #pwdCheck").focusout(function(){
        let pwdValue = $('#pwd').val();
        let pwdCheckValue = $('#pwdCheck').val();
        if (pwdValue !== pwdCheckValue) {
            $("#opwdCheck1").css("display","block");
        } else {
            $("#opwdCheck1").css("display","none");q
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

    // 이메일 형식 체크
    $("#email").keyup(function() {
        let emailValue = $('#email').val();
        let reg = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
        if (reg.test(emailValue) == false) {
            $("#emailErr").text("이메일 형식으로 입력해주세요.");
            $("#emailErr").css("color", "red");
        } else {
            $("#emailErr").text("");
        }
    });

    // 연락처 입력 창 형식
    $("#tel").keyup(function(){
        let telValue = $("#tel").val();
        telValue = telValue.replace(/[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]|[^\w\s-]/g, "").replace(/-/g, "");
        $("#tel").val(telValue);
    });

    // 회원가입 취소 버튼
    $("#oJoinCancelBtn").click(function() {
        let cancelA = confirm('회원가입을 취소하시겠습니까? 메인화면으로 이동합니다.');
        if(cancelA) {
            location.href = '/';
            return true;
        } else { return false; }
    });
});

// 이메일 중복 체크
function checkEmail() {
    let email = $('#email').val();

    $.ajax({
        url: '/member/qEmailCheck',
        type: 'post',
        data: {email:email},
        success:function(cnt) {
            if(cnt == 0 && email.length != 0) {
                $('#btnEmailCheck').attr('class','btn btn-primary');
                $('#btnEmailCheck').val("사용 가능");
                alert('이메일 사용이 가능합니다.');
            } else if(cnt != 0) {
                $('#btnEmailCheck').attr('class','btn btn-danger');
                $('#btnEmailCheck').val("사용 불가");
                alert('이미 존재하는 이메일입니다.\n이메일을 다시 입력해주세요.');
            //    $('#email').val('');
            } else if(email.length == 0) {
                alert('이메일을 입력해주세요.');
            }
        },
        error:function() {
            alert("에러입니다.");
        }
    });
};

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
};

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

    checkEmail()
    checkNickname()

    if(email === '') {
        alert('이메일을 입력해주세요.');
        return false;
    }
    if(name === '') {
        alert('이름을 입력해주세요.');
        return false;
    }
    if(pwd === '') {
        alert('비밀번호를 입력해주세요.');
        return false;
    }
    if(pwdCheck === '') {
        alert('비밀번호를 확인해주세요.');
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
    if (reg.test(email) == false) {
        alert('이메일 형식으로 입력해주세요.');
        return false;
    }
    if (telreg.test(tel) == false) {
        alert('연락처 형식으로 입력해주세요.');
        return false;
    }
    if (!pwdreg.test(pwd)) {
        alert('비밀번호 형식으로 입력해주세요.');
        return false;
    }
    if(checkedboxes.length !== $(".oMustAgree").length) {
        alert('필수항목을 모두 체크해주세요.');
        return false;
    }
    if(!(emailCheckOk == '사용 가능' && nicknameCheckOk == '사용 가능')) {
        alert('중복체크를 확인해주세요.');
        return false;
    }
}



// 회원가입 및 체크 ajax 로 변경
//function checkJoinInfo() {
//    let email = $('#email').val();
//    let name = $('#name').val();
//    let pwd = $('#pwd').val();
//    let pwdCheck = $('#pwdCheck').val();
//
//    $.ajax({
//        url: '/member/qEmailCheck',
//        type: 'post',
//        data: {email:email},
//        success:function(cnt) {
//            if(email === '') alert('이메일을 입력해주세요.');
//            if(name === '') alert('이름을 입력해주세요');
//            if(pwd === '') alert('비밀번호를 입력해주세요.');
//            if(pwdCheck === '') alert('비밀번호를 확인해주세요.');
//
//        },
//        error:function() {
//            alert("에러입니다.");
//        }
//    });
//};
