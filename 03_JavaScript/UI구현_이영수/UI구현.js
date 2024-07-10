//document.querySelector("[name=team]:checked").value

const finish = document.getElementById('finish');
const f_style = finish.style;

const btn = document.querySelector("#complete");
btn.addEventListener("click", function () {

// 너비
const bWidth = document.getElementById('b_width').value;
f_style.width = `${bWidth}px`;

// 높이
const bHeight = document.getElementById('b_height').value;
f_style.height = `${bHeight}px`;

// 글자 크기
const bFontSize = document.getElementById('b_fontSize').value;
f_style.fontSize = `${bFontSize}px`;

// 글자 굵기
const bFontWeight = document.querySelector("[name=fWeight]:checked").value;
f_style.fontWeight = `${bFontWeight}`;

// 글자색
const bFontColor = document.getElementById('fColor').value;
f_style.color = `${bFontColor}`;

// 배경색
const bBGC = document.getElementById('BGC').value;
f_style.backgroundColor = `${bBGC}`;

// 가로 정렬
const bJustify = document.querySelector("[name=f_justify]:checked").value;
f_style.justifyContent = `${bJustify}`;

// 세로 정렬
const bAlign = document.querySelector("[name=f_align]:checked").value;
f_style.alignItems = `${bAlign}`;

// 출력 문자열
const bString = document.getElementById('input_string').value;
finish.innerText = `${bString}`;


console.log(bWidth, bHeight, bFontSize, bFontWeight,bFontColor, bBGC, bJustify, bAlign, bString);
});
