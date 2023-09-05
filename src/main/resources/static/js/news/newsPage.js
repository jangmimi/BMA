
$(document).ready(function() {

    $("#search-button").click(function validateForm(){
        let searchInput = $("#search-input").val().trim();

        if (searchInput === "" || searchInput === null) {
            alert("입력 필수입니다.");
            // notification("입력 필수입니다.", "error");
            return false;
        }

        return true;
    });

});


