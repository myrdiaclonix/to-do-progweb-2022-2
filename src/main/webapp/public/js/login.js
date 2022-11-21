// Função: alterar as paginas do login (entre Login e Cadastro)
$('.return-btn').on("click", function (e) {

    let el = $(this).attr("href");
    let page = $(el);
    let allPages = $(".sing-page");

    allPages.addClass("fade");

    setTimeout(function () {
        allPages.removeClass("show fade");
        page.addClass("show");
    }, 200);

});

// Função: altera entre a página de Cadastro e Login
$('.btn-alter-sing-page').on("click", function (e) {
	
	e.preventDefault();
	
    let el = $(this).attr("href");
    let page = $(el);
    let allPages = $(".sing-page");

    allPages.addClass("fade");

    setTimeout(function () {
        allPages.removeClass("show fade");
        page.addClass("show");
    }, 200);

});

// Função: validar o formulário de login
$('#form-login').on("submit", function (e) {

    e.preventDefault();

    $.ajax({
		url: CONTEXT_PATH + "/",
		type: 'POST',
		data: $(this).serialize() + "&action=login",
	})
	.done(function(msg) {
		if (isJson(msg)) {
			let json = JSON.parse(msg);
			if (json.status == 1) {
				window.location.href = CONTEXT_PATH + "/tasks";
			} else {
				alert(json.msg);
			}
			
		} else {
			alert(msg);
		}
	})
	.fail(function(jqXHR, textStatus, msg) {
		alert(msg);
	});

});

// Função: validar o formulário de cadastro
$('#form-cadastro').on("submit", function (e) {

    e.preventDefault();

    $.ajax({
		url: CONTEXT_PATH + "/",
		type: 'POST',
		data: $(this).serialize() + "&action=register",
	})
	.done(function(msg) {
		if (isJson(msg)) {
			let json = JSON.parse(msg);
			
			alert(json.msg);
			
			if (json.status == 1) {
				setTimeout(function(){
					window.location.href = CONTEXT_PATH + "/tasks";
				}, 2000);
			} 
			
		} else {
			alert(msg);
		}
	})
	.fail(function(jqXHR, textStatus, msg) {
		alert(msg);
	});

});
