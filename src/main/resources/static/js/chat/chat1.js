document.addEventListener("DOMContentLoaded", function () {
    let stompClient = null;

    function connect() {
        const socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.send("/app/connect", {}, JSON.stringify({}));

            stompClient.subscribe("/topic/chatting/status", function (userCountMessage) {
                const connectInfo = JSON.parse(userCountMessage.body);
                handleUserCountMessage(connectInfo.chatContent);
                console.log("사용자수=" + connectInfo.chatContent);
            });

            stompClient.subscribe("/topic/chatting/connectmessage", function (message) {
                const connectInfo = JSON.parse(message.body);
                if (!sessionStorage.getItem('connectTime')) {
                    openingComment(connectInfo.chatContent);
                    sessionStorage.setItem('connectTime', connectInfo.chatDate);
                    console.log(connectInfo.chatDate);
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
        var loadingScreen = document.getElementById("loadingScreen");
        loadingScreen.style.display = "flex";

        const email = document.querySelector("#s-chat-email").value;
        console.log(email);
        const clientTopic = "/topic/" + email;

        stompClient.unsubscribe(clientTopic);

        stompClient.send("/app/loadMessages", {}, JSON.stringify({ chatDate: sessionStorage.getItem('connectTime') }));
        stompClient.subscribe(clientTopic, function (messages) {
            const loadMessages = JSON.parse(messages.body);
            console.log(loadMessages);

            document.querySelector(".s-chat-textbox").innerHTML = '';

            function showMessageCallback() {
                loadingScreen.style.display = "none";
            }

            showMessagesWithCallback(loadMessages, 0, showMessageCallback);
        });
    }

    function showMessagesWithCallback(messages, index, callback) {
        if (index >= messages.length) {
            callback();
            return;
        }

        const message = {
            chatContent: messages[index][1],
            chatDate: messages[index][2],
            nickname: messages[index][3]
        };

        showMessages(message);

        setTimeout(function () {
            showMessagesWithCallback(messages, index + 1, callback);
        }, 0);
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
    const email = document.getElementById("s-chat-email");
    const textarea = document.querySelector("#s-chat-input");
    modal.style.display = "none";

    modalButton.addEventListener("click", function () {
        if(email.value){
            modal.style.display = "flex";
            modalButton.style.transform = "scale(0)";
            connect();
        }else {
            alert("채팅은 로그인 한 회원만 가능한 서비스입니다");
            window.location.href='/member/qLoginForm';
        }
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

    document.querySelector('.s-chat-sendbox button').addEventListener('click', (e) => {
        if (textarea.value.trim() !== '') {
            sendMessage();
            document.querySelector("#s-chat-input").value = '';
        }
    });

    function showMessages(message) {
        const chatTextBox = document.querySelector(".s-chat-textbox");
        requestAnimationFrame(() => {
            chatTextBox.scrollTo(0, chatTextBox.scrollHeight);
        });

        const messageBlock = document.createElement("div");
        messageBlock.className = "s-chat-message";

        const messageInfo = document.createElement("p");
        messageInfo.className = "s-chat-info";

        const messageNickname = document.createElement("span");
        messageNickname.textContent = message.nickname;
        const messageTime = document.createElement("span");
        messageNickname.className = "s-chat-nickname";
        messageTime.className = "s-chat-time";
        messageTime.textContent = sendMessageCurrentTime(message.chatDate);

        const messageText = document.createElement("p");
        messageText.className = "s-chat-text";
        messageText.textContent = message.chatContent;

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

    function sendMessageCurrentTime(chatDate) {
        const messageTime = new Date(chatDate);
        console.log("전송메세지시간확인" + messageTime);
        let hours = messageTime.getHours();
        let minutes = messageTime.getMinutes();
        let ampm = hours >= 12 ? "PM" : "AM";

        hours = hours % 12;
        hours = hours ? hours : 12;

        minutes = minutes < 10 ? "0" + minutes : minutes;

        let currentTime = hours + ":" + minutes + " " + ampm;
        return currentTime;
    }
});
