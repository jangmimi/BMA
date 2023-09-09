function validateForm() {
    var category = document.forms["qnaForm"]["category"].value;
    var title = document.forms["qnaForm"]["title"].value;
    var content = document.forms["qnaForm"]["content"].value;

    if (category === "choice" || title === "" || content === "") {
        alert("필수 항목을 입력하세요.");
        return false; // 폼 제출을 막음
    }
    return true; // 폼 제출을 허용
}