    function deleteMMCheck() {
       let answer = confirm('정말 삭제하시겠습니까?');
       return answer;
    }

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