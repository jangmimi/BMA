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
     const imageArray = []; // 이미지 정보를 저장할 배열

     // 대표 사진 레이블 추가 함수
     function addRepresentativeLabel() {
         const representativeLabel = document.createElement("div");
         representativeLabel.classList.add("representative-label");
         representativeLabel.textContent = "대표사진";
         return representativeLabel;
     }

     // 이미지를 서버로 업로드하는 함수
     async function uploadImage(imageFile) {
         const formData = new FormData();
         formData.append("imageFile", imageFile);

         try {
             const response = await fetch("/uploadImage", {
                 method: "POST",
                 body: formData,
             });

             if (response.ok) {
                 const data = await response.json();
                 // 이미지 업로드 성공 시 데이터베이스 저장 또는 관련 작업 수행
                 console.log("이미지 업로드 성공:", data);
             } else {
                 console.error("이미지 업로드 실패:", response.statusText);
             }
         } catch (error) {
             console.error("이미지 업로드 오류:", error);
         }
     }

     // 이미지 입력 필드의 변경 이벤트 감지
     imageInput.addEventListener("change", async function (event) {
         // 선택된 파일들을 순회하면서 미리보기 이미지를 생성하고 컨테이너에 추가
         for (const file of event.target.files) {
             if (imagePreviews.children.length >= 5) {
                 alert("최대 5장까지 등록할 수 있습니다.");
                 return;
             }

             // 선택한 파일이 이미지인지 확인
             if (file.type.startsWith("image/")) {
                 const reader = new FileReader();

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

                         // 이미지 배열에서 해당 이미지 제거
                         const index = imageArray.indexOf(file);
                         if (index > -1) {
                             imageArray.splice(index, 1);
                         }
                     };
                     imageContainer.appendChild(deleteButton);

                     // 이미지와 X 버튼을 감싸는 div 요소를 미리보기 컨테이너에 추가
                     imagePreviews.appendChild(imageContainer);

                     // 이미지 배열에 이미지 추가
                     imageArray.push(file);
                 };

                 reader.readAsDataURL(file);
             } else {
                 alert("이미지 파일을 선택하세요.");
             }
         }
     });

     // 등록 버튼 클릭 시 이미지 업로드 및 데이터베이스 저장
     const uploadButton = document.getElementById("uploadButton");
     uploadButton.addEventListener("click", async function () {
         for (const imageFiles of imageArray) {
             await uploadImage(imageFiles);
         }
     });
 });