document.addEventListener("DOMContentLoaded", function () {

    let stompClient = null;

    function connect() {

        const socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {

            stompClient.send("/app/connect", {}, JSON.stringify({}))

            stompClient.subscribe("/topic/chatting/status", function (userCountMessage) {
                const connectInfo = JSON.parse(userCountMessage.body);
                // 사용자 수 메시지를 처리
                handleUserCountMessage(connectInfo.chatContent);
                console.log("사용자수="+connectInfo.chatContent);
            });
            stompClient.subscribe("/topic/chatting/connectmessage", function (message) {
                const connectInfo = JSON.parse(message.body);
                if (!sessionStorage.getItem('connectTime')) {
                    openingComment(connectInfo.chatContent);
                    sessionStorage.setItem('connectTime', connectInfo.chatDate);
                } else {
                    document.querySelector(".s-chat-textbox").innerHTML = '';
                    loadMessages();
                }
            });

            stompClient.subscribe("/topic/chatting", function (data) {
                const message = JSON.parse(data.body);
                showMessages(message);
                console.log(message);
            });
        });
    }

    document.querySelector(".n-header-login-logout").addEventListener("click", function () {
        sessionStorage.clear();
    });

    function disconnectWebSocket() {
        if (stompClient !== null) {
            stompClient.disconnect((frame) => {
                console.log('웹소켓 연결이 종료되었습니다.');
            });
        }
    }

    function handleUserCountMessage(content) {
        const userCountElement = document.querySelector(".s-chat-closebox-count");
        if (userCountElement) {
            userCountElement.textContent = content;
        }
    }

    function loadMessages() {
        const email = document.querySelector("#s-chat-email").value;
        console.log(email);
        const clientTopic = "/topic/" + email;

        // 이전에 구독한 메시지를 해지합니다.
        stompClient.unsubscribe(clientTopic);

        stompClient.send("/app/loadMessages", {}, JSON.stringify({ chatDate: sessionStorage.getItem('connectTime') }));
        stompClient.subscribe(clientTopic, function (messages) {
            const loadMessages = JSON.parse(messages.body);
            console.log(loadMessages);
            // 기존 메시지를 모두 지웁니다.
            document.querySelector(".s-chat-textbox").innerHTML = '';
            for (const loadMessage of loadMessages) {
                message = {
                    chatContent: loadMessage[1],
                    chatDate: loadMessage[2],
                    nickname: loadMessage[4]
                }
                showMessages(message);
            }
            console.log(message);
        });
    }

    function sendMessage() {
        const textarea = document.querySelector("#s-chat-input");
        const content = textarea.value.replace(/\n/g, "");
        const chatMessage = {
            'chatContent': content
        }
        stompClient.send("/app/saveMessage", {}, JSON.stringify(chatMessage));
    }

    const modalButton = document.getElementById("s-chat-connect-button");
    const modal = document.querySelector(".s-chatbox");
    const closeButton = document.querySelector(".s-chat-close");

    const textarea = document.querySelector("#s-chat-input");
    modal.style.display = "none";

    modalButton.addEventListener("click", function () {
        modal.style.display = "flex";
        modalButton.style.transform = "scale(0)";
        connect();
    });

    closeButton.addEventListener("click", function () {
        modal.style.display = "none";
        modalButton.style.transform = "scale(1)";
        document.querySelector(".s-chat-textbox").innerHTML = '';
        disconnectWebSocket();
    });

    document.querySelector('#s-chat-input').addEventListener('keyup', (e) => {
        if (e.keyCode === 13) {
            if (textarea.value.trim() !== '') {
                sendMessage();
                document.querySelector("#s-chat-input").value = '';
            }
        }
    });

    function showMessages(message) {

////        const chatTextBox = document.querySelector(".s-chat-textbox");
////        chatTextBox.scrollTo(0, chatTextBox.scrollHeight);
//const chatTextBox = document.querySelector(".s-chat-textbox");
//chatTextBox.scrollTop = chatTextBox.scrollHeight+10;
const chatTextBox = document.querySelector(".s-chat-textbox");
requestAnimationFrame(() => {
  chatTextBox.scrollTo(0, chatTextBox.scrollHeight);
});

        const messageBlock = document.createElement("div");
        messageBlock.className = "s-chat-message";

        const messageInfo = document.createElement("p");
        messageInfo.className = "s-chat-info";

        const messageNickname = document.createElement("span");

        //이메일에서 아이디값만 추출
        messageNickname.textContent = message.nickname;
        const messageTime = document.createElement("span");

        messageNickname.className = "s-chat-nickname";

        messageTime.className = "s-chat-time";
        messageTime.textContent = sendMessageCurrentTime(message.chatDate);

        const messageText = document.createElement("p");
        messageText.className = "s-chat-text";
        messageText.textContent = message.chatContent; // 메시지 내용 변경

        messageInfo.appendChild(messageNickname);
        messageInfo.appendChild(messageTime);

        messageBlock.appendChild(messageInfo);
        messageBlock.appendChild(messageText);

        chatTextBox.appendChild(messageBlock);

    }

    function openingComment(message) {
        const chatTextBox = document.querySelector(".s-chat-textbox");
        chatTextBox.scrollTo(0, chatTextBox.scrollHeight);
        const messageBlock = document.createElement("div");
        messageBlock.className = "s-chat-message";
        const messageInfo = document.createElement("p");
        messageInfo.className = "s-chat-info";
        messageInfo.textContent = message;
        messageBlock.appendChild(messageInfo);
        chatTextBox.appendChild(messageBlock);
    }

//    textarea.addEventListener("input", function () {
//        this.style.height = `${this.scrollHeight}px`;
//    });

    //Date를 간단한 시간으로 변환해주는 코드
    function sendMessageCurrentTime(chatDate) {
        const messageTime = new Date(chatDate);
        let hours = messageTime.getHours();
        let minutes = messageTime.getMinutes();
        let ampm = hours >= 12 ? "PM" : "AM";

        hours = hours % 12;
        hours = hours ? hours : 12; // 0시일 경우 12시로 변경

        minutes = minutes < 10 ? "0" + minutes : minutes; // 분이 10 미만일 경우 앞에 0 추가

        let currentTime = hours + ":" + minutes + " " + ampm;
        return currentTime;
    }
});
