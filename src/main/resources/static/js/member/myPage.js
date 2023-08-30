
//let name = $('#name').val();
//let tel = $('#tel').val();
//let emailTel = $('#emailTel').val();
//let emailPwd = $('#emailPwd').val();

/* oMyInfoUpdate */
$(document).ready(function() {
    $('#oCancelBtn').click(function() {
        let result = confirm('내정보를 수정을 취소하시겠습니까?\n내정보화면으로 이동합니다.');
        if(result) {
            location.href = '/member/qMyPage';
            return true;
        } else { return false; }
    });
});

// 내정보수정 submit 전에 공백 체크
function oUpdateCheck() {
    let name = $('#name').val();
    if(name === '') {
        alert('이름을 입력해주세요.');
        return false;
    }
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
    let emailpwd = $('#emailpwd').val();
    let telpwd = $('#telpwd').val();
    if(emailpwd === '') {
        alert('이메일 입력해주세요.');
        return false;
    }
    if(telpwd === '') {
        alert('연락처를 입력해주세요.');
        return false;
    }
}

// 이메일 찾기 ajax로
function qFindEmailCheck() {
    let email = $('#email').val();

    $.ajax({
        url: '/member/qFindEmailCheck',
        type: 'post',
        data: {email:email},
        success:function(findEmail) {
            if(find != null) {
                $('#btnEmailCheck').attr('class','btn btn-primary');
            } else if(cnt != 0) {
                $('#btnEmailCheck').attr('class','btn btn-danger');
                $('#btnEmailCheck').val("사용 불가");
                alert('이미 존재하는 이메일입니다.\n이메일을 다시 입력해주세요.');
            //    $('#email').val('');
            }
        },
        error:function() {
            alert("에러입니다.");
        }
    });
};






// 회원탈퇴 submit 전 확인 취소
function oDeleteCheck() {
    let answer = confirm('정말 탈퇴하시겠습니까?');
    if(answer) { return true; }
    else { return false; }
}