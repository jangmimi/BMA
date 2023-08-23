document.addEventListener("DOMContentLoaded", function () {

       /** 모달 기능 */
        const modalButton = document.getElementById("e-modalButton");
        const modal = document.querySelector(".e-modalContent");
        const closeButton = document.getElementById("e-close");
        let isClicked = false; // 버튼 클릭 여부

        // 처음에 모달 컨텐츠를 숨김
        modal.style.display = "none";

        // 모달 버튼 클릭 시 모달 창 토글
        modalButton.addEventListener("click", function () {
            modal.style.display = modal.style.display === "block" ? "none" : "block"; //
            isClicked = !isClicked;
            if (isClicked) {
                modalButton.style.transform = "scale(1.0)";
            } else {
                modalButton.style.transform = "scale(0.9)";
            }
        });

        // x 버튼 클릭시 모달 닫기
        closeButton.addEventListener("click", function () {
            modal.style.display = "none";
        });


    let stompClient = null;
    const clientId = Math.random().toString(36).substr(2, 9); // 랜덤한 고유 식별자 생성

    connect();

    function connect() {
        const socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/connectedClients', function (message) {
                const connectedClients = JSON.parse(message.body);
                updateConnectedClientsUI(connectedClients);
            });
            stompClient.subscribe('/topic/messages', function (messages) {
                const message = JSON.parse(messages.body);

                //if문으로 내메세지인지 상대방메세지인지 가려낸다(clientId값으로)
                if (message.clientId === clientId) {
                    showMessages(message);
                } else {
                    showOtherMessages(message)
                }
            });
        });
    }

    //메세지 서버로 보내기
    function sendMessage() {
        stompClient.send("/app/chatting", {}, JSON.stringify({
            'sender': document.querySelector("#s-sender").value,
            'content': document.querySelector("#s-sendText").value,
            'clientId': clientId
        }));
    }

    //전송 버튼을 누르면 메세지가 보내지는 기능
    document.querySelector("#s-submitBtn").addEventListener("click", function () {
        if (stompClient) {
            sendMessage();
            document.querySelector("#s-sendText").value = '';
        } else {
            console.log("Stomp client not initialized yet.");
        }
    });

    //textarea에서 엔터키를 쳤을때 메세지가 보내지는 기능
    document.querySelector('#s-sendText').addEventListener('keyup', (e) => {
        if (e.keyCode === 13) {
            if (stompClient) {
                sendMessage();
                document.querySelector("#s-sendText").value = '';
            } else {
                console.log("Stomp client not initialized yet.");
            }
        }
    });


    //내 메세지 보기 (HTML에 태그랑 클래스이름 넣어주고 내용 넣어주는 코드)
    function showMessages(message) {
        const chatTextBox = document.querySelector(".s-chatTextBox");

        const messageBlock = document.createElement("div");
        messageBlock.className = "s-my-profile";

        const nicknameAndText = document.createElement("div");
        nicknameAndText.className = "s-nicknameAndText s-my-message";

        const myText = document.createElement("div");
        myText.className = "s-my-text";
        myText.textContent = message.content;

        nicknameAndText.appendChild(myText);
        messageBlock.appendChild(nicknameAndText);

        chatTextBox.appendChild(messageBlock);
    }

    //상대방 메세지 보기 (HTML에 태그랑 클래스이름 넣어주고 내용 넣어주는 코드)
    function showOtherMessages(message) {
        const chatTextBox = document.querySelector(".s-chatTextBox");

        const messageBlock = document.createElement("div");
        messageBlock.className = "s-others-profile";

        const profileImage = document.createElement("img");
        profileImage.src = "sc/chattinglogo.png";
        profileImage.alt = "";

        const nicknameAndText = document.createElement("div");
        nicknameAndText.className = "s-nicknameAndText";

        const nicknameSpan = document.createElement("span");
        nicknameSpan.id = "s-nickname";
        nicknameSpan.textContent = message.sender;

        const othersText = document.createElement("div");
        othersText.className = "s-others-text";
        othersText.textContent = message.content;

        nicknameAndText.appendChild(nicknameSpan);
        nicknameAndText.appendChild(othersText);

        messageBlock.appendChild(profileImage);
        messageBlock.appendChild(nicknameAndText);

        chatTextBox.appendChild(messageBlock);
    }

    //현재 접속자 몇명인지 HTML에 작성해주는 코드
    function updateConnectedClientsUI(count) {
            const connectedClientsElement = document.getElementById("s-connectedClients");
            connectedClientsElement.textContent = count;
    }

});
