//function openModal(){
//        document.getElementById('aSaveBtn').addEventListener('click', function() {
//            // 여기서 로그인 여부 확인 및 처리
//            var isLoggedIn = true;  // 로그인 여부에 따라 true 또는 false 설정
//
//            // 동적으로 모달 내용 변경
//            var dynamicModalBody = document.getElementById('dynamicModalBody');
//
//            if (isLoggedIn) {
//                dynamicModalBody.innerHTML = '<p>로그인되었습니다.</p>';
//            } else {
//                dynamicModalBody.innerHTML = '<p>로그인이 필요합니다.</p>';
//            }
//        });
//
//}


// 데이터 배열 (크기에 따른 값을 포함)
var data = [
    { x: 1, y: 5 },
    { x: 2, y: 8 },
    { x: 3, y: 3 },
    { x: 4, y: 1 },
    { x: 5, y: 10 },
    { x: 6, y: 4 },
    { x: 7, y: 6 },
    { x: 8, y: 3 },
    { x: 9, y: 10 },
    { x: 10, y: 3 },
    // ... 더 많은 데이터 포인트
];

// SVG 요소 생성
var svg = d3.select("#realpricePercentChart")
    .append("svg")
    .attr("width", 300)
    .attr("height", 150);

// 스케일 설정 (x축, y축)
var xScale = d3.scaleLinear()
    .domain([1, data.length])
    .range([0, 200]);//표의 가로 길이 중 어디까지를 x선으로 사용할지인 듯

var graphHeight = 150; // 그래프의 높이를 조절해보세요

var yScale = d3.scaleLinear()
    .domain([0, d3.max(data, d => d.y)])
    .range([0, graphHeight]);


// 점 그리기
svg.selectAll("circle")
    .data(data)
    .enter()
    .append("circle")
    .attr("cx", d => xScale(d.x))
    .attr("cy", d => graphHeight - yScale(d.y)) // 수정된 부분
    .attr("r", 4) // 점의 반지름 조절
    .attr("fill", "#129EED"); // 점의 색상 조절

// 라인 그리기
var line = d3.line()
    .x(d => xScale(d.x))
    .y(d => graphHeight - yScale(d.y)); // 수정된 부분


svg.append("path")
    .datum(data)
    .attr("d", line)
    .attr("fill", "none")
    .attr("stroke", "#129EED")
    .attr("stroke-width", 2);//선 굵기

// x 축 생성 함수
var xAxis = d3.axisBottom(xScale);

// x 축 그리기
svg.append("g")
    .attr("class", "x-axis")
    .attr("transform", `translate(0, ${graphHeight})`)
    .call(xAxis)
    .selectAll("path, line")
    .attr("stroke-width", 2)  // x축 선 두께 조절
    .attr("stroke", "#B7D2E7");  // x축 선 색상 조절

// y 축 생성 함수
var yAxis = d3.axisLeft(yScale);

// y 축 그리기
svg.append("g")
    .attr("class", "y-axis")
    .call(yAxis)
    .selectAll("path, line")
    .attr("stroke-width", 2)  // y축 선 두께 조절
    .attr("stroke", "#B7D2E7");  // y축 선 색상 조절

// y 축 눈금 위치 지정
var yAxisTicks = [0, 2, 4, 6]; // 원하는 y축의 눈금 위치
var yAxisTickPositions = yAxisTicks.map(tick => graphHeight - yScale(tick));

// y 축 범위 표시 선 그리기
svg.selectAll(".y-axis-line")
   .data(yAxisTickPositions)
       .enter()
       .append("g")
       .attr("class", "y-axis-line")
       .each(function(d, i) {
           // 선 그리기
           d3.select(this)
               .append("line")
               .attr("x1", 0)
               .attr("x2", 300) // x축의 끝까지
               .attr("y1", d)
               .attr("y2", d)
               .attr("stroke", "#ccc")
               .attr("stroke-dasharray", "4");

           // 텍스트 추가
           d3.select(this)
               .append("text")
               .attr("x", -5) // 선 왼쪽에 위치
               .attr("y", d - 5) // 선 위쪽에 위치
               .attr("text-anchor", "end")
               .attr("dominant-baseline", "middle")
               .attr("font-size", "10px")
               .text(yAxisTicks[i] + "천만원"); // 텍스트 내용 d
       });