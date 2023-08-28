document.addEventListener("DOMContentLoaded", function () {

    let stompClient = null;

    //알림메세지 개수
    let notificationCount = 0;

    //서버시간
    let serverTime;

    const sender = document.getElementById("s-chat-sender").value;
    const textarea = document.querySelector("#s-chat-input");
    const image = document.querySelector("#s-chat-input-img");
    image.src = "/sc/free-icon-sent-mail-71746.png";

    /** 모달 기능 */
    const modalButton = document.getElementById("s-chat-connect-button");
    const modal = document.querySelector(".s-chatbox");
    const closeButton = document.querySelector(".s-chat-close");
    let isClicked = false; // 버튼 클릭 여부


    // 처음에 모달 컨텐츠를 숨김
    modal.style.display = "none";

    // 모달 버튼 클릭 시 모달 창 토글
    modalButton.addEventListener("click", function () {
        if (sender === '') {
            alert("채팅기능은 로그인하셔야 이용 하실 수 있습니다");
            window.location.href = '/member/qLoginForm';
        } else {
            modal.style.display = "flex";
            modalButton.style.transform = "scale(0)";
            //버튼을 누르면 웹통신 시작
            connect();
            resetNotificationCount(); // 알림 메시지 개수 초기화
        }
    });

    // x 버튼 클릭시 모달 닫기
    closeButton.addEventListener("click", function () {
        modal.style.display = "none";
        modalButton.style.transform = "scale(1)";
    });

    //웹소켓 연결 (통신 코드)
function connect() {
    const socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        if(!sessionStorage.getItem('startTime')){

                stompClient.send("/app/openingComment", {}, JSON.stringify(sender));
                stompClient.subscribe('/topic/openingComment', function (messages) {
                    openingComment(messages);
                });

                stompClient.subscribe("/topic/serverTime", function (data) {
                serverTime = JSON.parse(data.body); // 서버에서 받은 JSON 데이터 파싱
                sessionStorage.setItem('startTime', serverTime.toString().slice(0, -1));
            });
        }
        const startTime = sessionStorage.getItem('startTime');

        console.log(startTime);

        // 서버 시간을 받아온 후에 dbMessages로 요청 보내기
        stompClient.send("/app/dbMessages", {}, startTime);

        stompClient.subscribe('/topic/dbMessages', function (messages) {
            const messageAll = JSON.parse(messages.body);
            for (const message of messageAll) {
                showMessages(message);
            }
        });

        stompClient.subscribe('/topic/messages', function (messages) {
            const message = JSON.parse(messages.body);
            showMessages(message);
        });

        // 서버 시간을 요청
        stompClient.send("/app/getservertime", {}, JSON.stringify());
    });
};

    function openingComment(messages) {
                const chatTextBox = document.querySelector(".s-chat-textbox");
                chatTextBox.scrollTo(0, chatTextBox.scrollHeight);
                const messageBlock = document.createElement("div");
                messageBlock.className = "s-chat-message";
                const messageInfo = document.createElement("p");
                messageInfo.className = "s-chat-info";
                messageInfo.textContent = messages.body;
                messageBlock.appendChild(messageInfo);
                chatTextBox.appendChild(messageBlock);
    }

    //클라이언트가 작성한 메세지를 controller로 보낸다
    //messageMapping(경로)로 보낸다
    function sendMessage() {

        //메세지 내용 (정규식으로 줄바꿈 문자까지 없애주고 보낸다)
        const content = textarea.value.replace(/\n/g, "");

        //messge객체를 만들어준다
        const chatMessage = {
            //아이디
            'chatSender': sender,
            //내용
            'chatContent': content
        }

        //controller의 messageMapping경로로 messge객체를 JSON타입으로 변환해서 보내준다
        stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessage));
    }

    //전송 버튼을 누르면 메세지가 보내지는 기능
    document.querySelector("#s-chat-input-img").addEventListener("click", function () {
        if (stompClient && textarea.value.trim() !== '') {
            sendMessage();
            textarea.value = '';
            textarea.style.height = '62px';
            image.src = "/sc/free-icon-sent-mail-71746.png";
        } else {
            console.log("Stomp client not initialized yet.");
        }
    });

    //textarea에서 엔터키를 쳤을때 메세지가 보내지는 기능
    document.querySelector('#s-chat-input').addEventListener('keyup', (e) => {
        if (e.keyCode === 13) {
            if (stompClient && textarea.value.trim() !== '') {
                sendMessage();
                document.querySelector("#s-chat-input").value = '';
                textarea.style.height = '62px';
                image.src = "/sc/free-icon-sent-mail-71746.png";
            } else {
                console.log("Stomp client not initialized yet.");
            }
        }
    });


    //내 메세지 보기 (HTML에 태그랑 클래스이름 넣어주고 내용 넣어주는 코드)
    function showMessages(message) {
        const chatTextBox = document.querySelector(".s-chat-textbox");
        chatTextBox.scrollTo(0, chatTextBox.scrollHeight);


        const messageBlock = document.createElement("div");
        messageBlock.className = "s-chat-message";

        const messageInfo = document.createElement("p");
        messageInfo.className = "s-chat-info";

        const messageNickname = document.createElement("span");

        //이메일에서 아이디값만 추출
        messageNickname.textContent = sender.split("@")[0];
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

        if(message.chatSender !== sender){
            notificationCount++;
            updateNotificationCount();
        }
    }

    //알림개수 업데이트
    function updateNotificationCount() {
        const notificationBadge = document.getElementById("s-notification-badge");
        if (notificationBadge) {
            notificationBadge.textContent = notificationCount > 0 ? notificationCount.toString() : '';
        }
    }

    //알림개수 초기화
    function resetNotificationCount() {
        notificationCount = 0;
        updateNotificationCount();
    }

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


    //메세지보낼때 길이 반응형
    //send 아이콘 이미지 교체
    textarea.addEventListener("input", function () {
        this.style.height = `${this.scrollHeight}px`;
        image.src = "/sc/free-icon-sent-blue-mail-71746.png";
    });

});