
    function validateForm() {
        var title = document.forms["writeForm"]["title"].value;
        var content = document.forms["writeForm"]["content"].value;

        if (title === null || title === "") {
            alert("제목을 입력하세요.");
            return false;
        }

        if (content === null || content === "") {
            alert("내용을 입력하세요.");
            return false;
        }

        return true;
    }
