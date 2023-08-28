

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
        // 회원탈퇴 submit 전 확인 취소
        function oDeleteCheck() {
            let answer = confirm('정말 탈퇴하시겠습니까?');
            if(answer) { return true; }
            else { return false; }
        }