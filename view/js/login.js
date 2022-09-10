// Função: alterar as paginas do login (entre Login e Cadastro)

$('.return-btn').on("click", function(e) {

    let el = $(this).attr("href");
    let page = $(el);
    let allPages = $(".sing-page");

    allPages.addClass("fade");

    setTimeout(function() {
        allPages.removeClass("show fade");
        page.addClass("show");
    }, 200);

});

$('.btn-alter-sing-page').on("click", function(e) {

    let el = $(this).attr("href");
    let page = $(el);
    let allPages = $(".sing-page");

    allPages.addClass("fade");

    setTimeout(function() {
        allPages.removeClass("show fade");
        page.addClass("show");
    }, 200);

});

// Função: validar o formulário de login
$('#form-login').on("submit", function(e) {
    
    e.preventDefault();

    /* Inserir todo o código de tratamento */
    
    alert("Fiz Login!");

});

// Função: validar o formulário de cadastro
$('#form-cadastro').on("submit", function(e) {

    e.preventDefault();

    /* Inserir todo o código de tratamento */

    alert("Fiz Cadastro!");

});
