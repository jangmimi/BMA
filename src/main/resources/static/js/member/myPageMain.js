$(document).ready(function() {
    // 초기 상태: 왼쪽 탭의 내용 표시
    $(".tab-content").hide();
    $("#left-content").show();

    // 왼쪽 탭 클릭 이벤트 핸들러
    $("#o-liked-link").click(function(e) {
        e.preventDefault(); // 기본 링크 동작 방지

        // 오른쪽 탭의 내용 숨김
        $("#o-tab-content2").hide();

        // 왼쪽 탭의 내용 표시
        $("#o-tab-content1").show();

        $("#o-recent-link").css("color", "#999999");
        $("#o-liked-link").css("color", "#25AAE1");
    });

    // 오른쪽 탭 클릭 이벤트 핸들러
    $("#o-recent-link").click(function(e) {
        e.preventDefault(); // 기본 링크 동작 방지

        // 왼쪽 탭의 내용 숨김
        $("#o-tab-content1").hide();

        // 오른쪽 탭의 내용 표시
        $("#o-tab-content2").show();

        $("#o-recent-link").css("color", "#25AAE1");
        $("#o-liked-link").css("color", "#999999");
    });
});

//// 마이페이지 대문 하단 Ajax 구현
//$(document).ready(function() {
//    // 초기 상태: Tab 1 활성화
//    $(".tab-content").hide();
//    $("#o-tab-content1").show();
//    $(".o-tab-l").click(function() {
//        // 선택된 탭의 ID를 가져옴
//        var tabId = $(this).attr("id");
//
//        // Ajax 요청을 사용하여 해당 탭에 대한 데이터 가져오기
//        $.ajax({
//            url: '/get-data', // 데이터를 가져올 서버 엔드포인트
//            data: { o-tab-l: tabId }, // 탭 정보를 서버에 전달
//            dataType: 'json', // 데이터 형식 (JSON 등)
//            success: function(data) {
//                // 서버로부터 데이터를 성공적으로 가져온 경우
//                // 해당 내용을 업데이트
//                $("#o-tab-content" + tabId.charAt(tabId.length - 1)).html(data.content);
//            },
//            error: function() {
//                // 데이터를 가져오지 못한 경우 에러 처리
//                $("#o-tab-content" + tabId.charAt(tabId.length - 1)).html("Failed to load data.");
//            }
//        });
//    });
//});
