const codeCount = ['A','B','C','D','E'];

// const code = document.getElementsByClassName(code);

document.querySelector("#create").addEventListener("click", function() {
  for(let i = 0; i<codeCount.length; i++){
    document.querySelectorAll('.code')[i].innerText = codeCount[i];
  }
});