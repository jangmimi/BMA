var naver_id_login = new naver_id_login("KxGhFHZ7Xp74X_5IZ23h", "http://localhost:8081/member/qLoginNaverCallback");
var state = naver_id_login.getUniqState();
naver_id_login.setButton("green", 4,45);
naver_id_login.setDomain("http://localhost:8081/member/testnaverLogin");
naver_id_login.setState(state);
naver_id_login.setPopup();
naver_id_login.init_naver_id_login();

// 로그인 및 유효성(공백) 체크 (form 태그 내 onsubmit에서 return oLoginCheck()을 통해 submit 하기 전 체크!)
function oLoginCheck() {
    let email = $('#email').val();
    let pwd = $('#pwd').val();
    let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    if(email === '') {
        alert('이메일을 입력해주세요.');
        return false;
    }
    if(pwd === '') {
        alert('비밀번호를 입력해주세요.');
        return false;
    }
    if(!emailPattern.test(email)) {
        alert('올바른 이메일형식이 아닙니다.');
        return false;
    }
};

<!-- 로그인 체크 -->
//function checkLogin() {
//    let email = $('#email').val();
//    let pwd = $('#pwd').val();
//
//    $.ajax({
//        url: '/member/qLoginCheck',
//        type: 'post',
//        data: {email:email},
//        success:function(cnt) {
//            if(cnt == 0 && email.length != 0) {
//                $('#btnEmailCheck').attr('class','btn btn-primary');
//                $('#btnEmailCheck').val("사용 가능");
//                alert('이메일 사용이 가능합니다.');
//            } else if(cnt != 0) {
//                $('#btnEmailCheck').attr('class','btn btn-danger');
//                $('#btnEmailCheck').val("사용 불가");
//                alert('이미 존재하는 이메일입니다.\n이메일을 다시 입력해주세요.');
//            //    $('#email').val('');
//            } else if(email.length == 0) {
//                alert('이메일을 입력해주세요.');
//            }
//        },
//        error:function() {
//            alert("에러입니다.");
//        }
//    });
//};

<!--        $(function() {-->
<!--               fnInit();-->
<!--         });-->

<!--         function frm_check(){-->
<!--             saveid();-->
<!--         }-->

<!--        function fnInit(){-->
<!--            var cookieid = getCookie("saveid");-->
<!--            console.log(cookieid);-->
<!--            if(cookieid !=""){-->
<!--                $("input:checkbox[id='oSaveId']").prop("checked", true);-->
<!--                $('#email').val(cookieid);-->
<!--            }-->
<!--        }-->

<!--        function setCookie(name, value, expiredays) {-->
<!--            var todayDate = new Date();-->
<!--            todayDate.setTime(todayDate.getTime() + 0);-->
<!--            if(todayDate > expiredays){-->
<!--                document.cookie = name + "=" + escape(value) + "; path=/; expires=" + expiredays + ";";-->
<!--            }else if(todayDate < expiredays){-->
<!--                todayDate.setDate(todayDate.getDate() + expiredays);-->
<!--                document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";";-->
<!--            }-->
<!--            console.log(document.cookie);-->
<!--        }-->

<!--        function getCookie(Name) {-->
<!--            var search = Name + "=";-->
<!--            console.log("search : " + search);-->

<!--            if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면-->
<!--                offset = document.cookie.indexOf(search);-->
<!--                console.log("offset : " + offset);-->
<!--                if (offset != -1) { // 쿠키가 존재하면-->
<!--                    offset += search.length;-->
<!--                    // set index of beginning of value-->
<!--                    end = document.cookie.indexOf(";", offset);-->
<!--                    console.log("end : " + end);-->
<!--                    // 쿠키 값의 마지막 위치 인덱스 번호 설정-->
<!--                    if (end == -1)-->
<!--                        end = document.cookie.length;-->
<!--                    console.log("end위치  : " + end);-->

<!--                    return unescape(document.cookie.substring(offset, end));-->
<!--                }-->
<!--            }-->
<!--            return "";-->
<!--        }-->

<!--        function saveid() {-->
<!--            var expdate = new Date();-->
<!--            if ($("#saveId").is(":checked")){-->
<!--                expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30);-->
<!--                setCookie("saveid", $("#logId").val(), expdate);-->
<!--                }else{-->
<!--               expdate.setTime(expdate.getTime() - 1000 * 3600 * 24 * 30);-->
<!--                setCookie("saveid", $("#logId").val(), expdate);-->
<!--            }-->
<!--        }-->
