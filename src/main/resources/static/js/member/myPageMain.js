$(document).ready(function() {
    // 초기 상태: 왼쪽 탭의 내용 표시
    $(".o-tab-content").hide();
    $("#o-tab-left").show();

    // 왼쪽 탭 클릭 이벤트 핸들러
    $("#o-liked-link").click(function(e) {
        e.preventDefault(); // 기본 링크 동작 방지

        // 오른쪽 탭의 내용 숨김
        $("#o-tab-right").hide();

        // 왼쪽 탭의 내용 표시
        $("#o-tab-left").show();

        $("#o-recent-link").css("color", "#999999");
        $("#o-liked-link").css("color", "#25AAE1");
    });

    // 오른쪽 탭 클릭 이벤트 핸들러
    $("#o-recent-link").click(function(e) {
        e.preventDefault(); // 기본 링크 동작 방지

        // 왼쪽 탭의 내용 숨김
        $("#o-tab-left").hide();

        // 오른쪽 탭의 내용 표시
        $("#o-tab-right").show();

        $("#o-recent-link").css("color", "#25AAE1");
        $("#o-liked-link").css("color", "#999999");
    });

    // 탭상태에 따른 전체보기 클릭 시 링크 이동
     $("#o-allView").click(function(e) {
        e.preventDefault(); // 기본 링크 동작 방지

        if($("#o-tab-left").is(":visible")) {
            window.location.href = "/member/liked";
        } else  if($("#o-tab-right").is(":visible")) {
            window.location.href = "/member/qRecent";
        }
    });
});
