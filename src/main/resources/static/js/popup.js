/**
 * Notification (success/error/warning/question/info alert)
 *
 * @param text      팝업 문구
 * @param type      팝업 종류 : success, error, warning, info
 * @param method   실행 메서드
 * @returns Swal.fire
 */
function notification(text, type, method) {
    // When text and type are not used
    if (text === null || type === null) {
        return Swal.fire({
            title: '알림',
            text: '시스템 오류가 발생했습니다. 관리자에게 문의해주세요.',
            icon: 'warning',
            confirmButtonText: '확인',
        });
    }
    // Common Alert
    return Swal.fire({
        title: '알림', // 알림
        text: text,
        icon: type,
        showCancelButton: type === 'question' ? true : false,
        confirmButtonText: '확인',
        cancelButtonText: '취소',
    }).then((result) => {
        if (result.isConfirmed) {
            if (typeof method === 'function') {
                method();
            }
        } else if (result.isDismissed) {
            notification('취소 하였습니다.', 'warning', null);
        }
    });
}