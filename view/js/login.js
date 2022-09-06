const e = document.getElementsByTagName("a")[0];
e.addEventListener("click", changeView);

function changeView() {
    let a = document.getElementById("signIn");
    let b = document.getElementById("signUp");
    a.style.opacity = "0";
    setTimeout(function() {
        a.style.display = "none";
        b.style.display = "block";
        setTimeout(function() {
            b.style.opacity = "1";
        }, 50);
    }, 100);
}