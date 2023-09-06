$(document).ready(function() {
    var currentDate = new Date();
    var dateString = currentDate.toISOString().slice(0, 10);

    var dateLabel = $("label[name='date']");
    dateLabel.text(dateString);

    dateLabel.after('<input type="hidden" name="date" value="' + dateString + '">');
});


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
