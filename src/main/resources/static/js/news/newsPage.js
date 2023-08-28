
$(document).ready(function() {

    $("#search-button").click(function validateForm(){
        let searchInput = $("#search-input").val().trim();

        if (searchInput === "" || searchInput === null) {
            alert("입력 필수입니다.");
            return false;
        }

        return true;
    });

    var itemsEmpty = $("#news-result").val()/* Get the value of "itemsEmpty" from your model */;
    if (itemsEmpty) {
        // items가 비어있는 경우, 팝업을 띄움
        alert("검색 결과가 없습니다.");
    }

});


