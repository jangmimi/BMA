        //글자수 카운팅
        function countCharacters(inputElement, countElementId, maxLength) {
         const countElement = document.getElementById(countElementId);
         const currentLength = inputElement.value.length;
         if (currentLength > maxLength) {
             inputElement.value = inputElement.value.slice(0, maxLength);
             countElement.textContent = `${maxLength}/${maxLength}`;
         } else {
             countElement.textContent = `${currentLength}/${maxLength}`;
         }
     }

     // 이미지
     function chooseImages() {
         const imageInput = document.getElementById("imageInput");
         const previewContainer = document.getElementById("previewContainer");

         // 이미지 개수 확인
         if (previewContainer.children.length >= 5) {
             alert("최대 5장까지 등록할 수 있습니다.");
             return;
         }

         // 파일 선택 다이얼로그 열기
         imageInput.click();
     }


  document.addEventListener("DOMContentLoaded", function () {
    const imageInput = document.getElementById("imageInput");
    const imagePreviews = document.getElementById("previewContainer");

    // 대표 사진 레이블 추가 함수
    function addRepresentativeLabel() {
        const representativeLabel = document.createElement("div");
        representativeLabel.classList.add("representative-label");
        representativeLabel.textContent = "대표사진";
        return representativeLabel;
    }

    // 파일 입력 필드의 변경 이벤트 감지
    imageInput.addEventListener("change", async function (event) {
        // 선택된 파일들을 순차적으로 처리
        for (const file of event.target.files) {
            if (imagePreviews.children.length >= 5) {
                alert("최대 5장까지 등록할 수 있습니다.");
                return;
            }

            // 선택한 파일이 이미지인지 확인
            if (file.type.startsWith("image/")) {
                const reader = new FileReader();

                // 비동기 코드를 동기적으로 처리하기 위해 await 사용
                await new Promise((resolve) => {
                    reader.onload = function (e) {
                        // 이미지와 X 버튼을 감싸는 부모 div 요소 생성
                        const imageContainer = document.createElement("div");
                        imageContainer.classList.add("image-container");

                        // 대표 사진 레이블 추가 (항상 첫 번째 이미지가 대표 사진)
                        if (imagePreviews.children.length === 0) {
                            imageContainer.appendChild(addRepresentativeLabel());
                        }

                        // 미리보기 이미지 생성 및 추가
                        const imagePreview = document.createElement("img");
                        imagePreview.classList.add("preview-image");
                        imagePreview.src = e.target.result;
                        imageContainer.appendChild(imagePreview);

                        // X 버튼 생성 및 스타일 적용
                        const deleteButton = document.createElement("div");
                        deleteButton.classList.add("delete-button");
                        deleteButton.textContent = "X";
                        deleteButton.onclick = function () {
                            // 이미지와 X 버튼을 감싸는 부모 div 요소 삭제
                            imagePreviews.removeChild(imageContainer);

                            // 대표 이미지 삭제 시 다음 이미지를 대표 이미지로 설정
                            if (imagePreviews.children.length > 0) {
                                const firstImageContainer = imagePreviews.children[0];
                                if (!firstImageContainer.querySelector(".representative-label")) {
                                    firstImageContainer.insertBefore(addRepresentativeLabel(), firstImageContainer.firstChild);
                                }
                            }
                        };
                        imageContainer.appendChild(deleteButton);

                        // 이미지와 X 버튼을 감싸는 div 요소를 미리보기 컨테이너에 추가
                        imagePreviews.appendChild(imageContainer);

                        // Promise를 사용하여 비동기 작업이 완료되었음을 알림
                        resolve();
                    };

                    reader.readAsDataURL(file);
                });
            } else {
                alert("이미지 파일을 선택하세요.");
            }
        }
    });
});
