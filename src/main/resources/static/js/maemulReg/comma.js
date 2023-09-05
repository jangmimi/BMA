function getNumber(obj){
        
         var num01;
         var num02;
         num01 = obj.value;
         num02 = num01.replace(/\D/g,""); //숫자가 아닌것을 제거, 
                                          //즉 [0-9]를 제외한 문자 제거; /[^0-9]/g 와 같은 표현
         num01 = setComma(num02); //콤마 찍기
         obj.value =  num01;

    }
    function setComma(n) {
         var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
         n += '';                          // 숫자를 문자열로 변환         
         while (reg.test(n)) {
            n = n.replace(reg, '$1' + ',' + '$2');
         }         
         return n;
    }

//적용시
//<input class="won" name="loanAmount" type="text" onchange="getNumber(this);" onkeyup="getNumber(this);" placeholder="0" onblur = "showPriceInfo();"> 원
//onchange="getNumber(this);" onkeyup="getNumber(this);"

