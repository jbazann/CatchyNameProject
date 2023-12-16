let container = "be declared";// and initialized, i guess
let div = document.createElement("div");

// SHEET
fetch("sheet.html")
    .then(response => response.text())
    .then(htmlContent => {
        container = document.getElementById("sheet-container");
        div.innerHTML = htmlContent;
        div.querySelectorAll('link[rel="stylesheet"]').forEach((stylesheet) => 
            stylesheet.parentNode.removeChild(stylesheet));
        container.innerHTML += div.innerHTML;
    })
    .catch(error => console.error("sheet.html: ",error));
;

// TOPBAR
fetch("topbar.html")
    .then(response => response.text())
    .then(htmlContent => {
        container = document.getElementById("topbar-container");
        div.innerHTML = htmlContent;
        div.querySelectorAll('link[rel="stylesheet"]').forEach((stylesheet) => 
            stylesheet.parentNode.removeChild(stylesheet));
        container.innerHTML += div.innerHTML;
    })
    .catch(error => console.error("topbar.html: ",error));
;
    


