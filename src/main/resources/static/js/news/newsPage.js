
$(document).ready(function() {

    $("#search-button").click(function validateForm(){
        let searchInput = $("#search-input").val().trim();

        if (searchInput === "" || searchInput === null) {
            alert("입력 필수입니다.");
            return false;
        }

        return true;
    });

    // 검색 결과 없을때 팝업
    var Empty = $("#noResult").val();

    if (Empty === "") {
        showEmptyResultsPopup();
    }

    function showEmptyResultsPopup() {
        alert("검색 결과가 없습니다.");
    }

});


