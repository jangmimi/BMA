$(document).ready(function(){

    let items=[]
    let positions=[]

    $.ajax({
    type:"GET",
    url:"/kim_ajaxr",
    dataType:"json",
    //서버 요청 전 호출되는 함수 return false; 일 경우 요청을 중단한다
    beforeSend : function(){
    console.log("ajax호출")
    },
    success:function(data){
          console.log("ajax호출성공")
          items = data.response.body.items.item
          console.log(items);
          let positions = [];
          for(let i=0;i<items.length;i++){
                positions[i] = {
                    content : "<div>"+items[i].MAIN_TITLE+"</div>",
                    latlng: {LAT : items[i].LAT, LNG : items[i].LNG}
                }
          }

        console.log(positions[0].latlng.LAT,positions[0].latlng.LNG)

        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {

        center: new kakao.maps.LatLng(positions[0].latlng.LAT,positions[0].latlng.LNG), // 지도의 중심좌표

        level: 3 // 지도의 확대 레벨

        };

        console.log(mapOption.center);
        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        for (var i = 0; i < positions.length; i ++) {
        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: new kakao.maps.LatLng(positions[i].latlng.LAT,positions[i].latlng.LNG) // 마커의 위치
        });

        // 마커에 표시할 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
        content: positions[i].content // 인포윈도우에 표시할 내용
        });

    // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
    // 이벤트 리스너로는 클로저를 만들어 등록합니다
    // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
    kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
    kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
}
    },
    error : function(jqXHR){
          console.log("실패입니다");
    }
    });
});


// 마커를 표시할 위치와 내용을 가지고 있는 객체 배열입니다



// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
    return function() {
        infowindow.close();
    };
}





