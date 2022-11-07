/*
    Menu Lista' Events
*/

// Event onclick for menu-task
$(document).on("click", ".menu-task", function (event) {

	// Previne a ação padrão
	event.preventDefault(); 
	
	// Adicionar button de editar
    $(".menu-task").removeClass("actual");
    $(".menu-task .btn-list-edit").removeClass("actual");
    $(this).addClass("actual");
    $(this).find(".btn-list-edit").addClass("actual");
    
    // Atualiza Lista de tarefas
	let lista = $(this).attr("href");
	let format = "#lista-";
	
	// Verifica formato do texto do href, pega somente o texto depois de "#lista-" e converte para inteiro
	lista = lista.indexOf(format) >= 0 ? parseInt(lista.substring((format).length)) : 0;
	replaceTaskLista(lista);
	
});

/*
    Initialization Events Modals
*/

// Modal Add Lists
$("#modal-add-lists").on("show.bs.modal", function (e) {

    let btn = $(e.relatedTarget);
	let form = $(this).find("#form-modal-add-lists");
    let type = btn.attr("data-type") != undefined ? btn.attr("data-type") : 0;
    let lista = btn.attr("data-lista") != undefined ? btn.attr("data-lista") : 0;

	form.attr("data-type", type);
	form.attr("data-lista", lista);

	// Limpa os valores dos campos do formulario do modal
	$("#form-modal-add-lists")[0].reset();

    if (type == 0) {

        $(this).find(".modal-title span").text("Editar lista");
        $("#form-modal-add-lists button[type='submit']").text("Atualizar");

		let data = `action=getLista&code=${lista}`;

		// Faz a busca da tarefa selecionada
		$.ajax({
			url: CONTEXT_PATH + "/lists",
			type: 'POST',
			data: data,
			beforeSend: function() {
				console.log("Buscando...");
			}
		})
		.done(function(msg) {
			
			if (isJson(msg)) {
				let json = JSON.parse(msg);
				
				if(json.status == 1) {
					// Altera os valores dos campos com os valores do array de retorno
					$("#input-title-list").val(json.res[0]);
					$("#textarea-description-list").val(json.res[1]);
				}
				
			} 
		})
		.fail(function(jqXHR, textStatus, msg) {
			console.log(msg);
		});

    } else {
        $(this).find(".modal-title span").text("Adicionar lista");
        $("#form-modal-add-lists button[type='submit']").text("Adicionar");
    }

});

/*
    Events Remove
*/
$(document).on("click", ".btn-remove-lista", function (event) {

	// Previne a ação padrão
	event.preventDefault(); 
	
	let conf = confirm("Confirma a remoção dessa lista?");
	
	if(conf) {
		let lista = $(this).attr("data-lista");
	
		lista = lista != undefined && lista > 0 ? lista : 0;
		
		let data = `action=delLista&code=${lista}`;
		
		$.ajax({
			url: CONTEXT_PATH + "/lists",
			type: 'POST',
			data: data,
			beforeSend: function() {
				console.log("Removendo...");
			}
		})
		.done(function(msg) {
			
			if (isJson(msg)) {
				let json = JSON.parse(msg);
				if (json.status == 1) {
					
					let params = getParametersURL();
					
					if(params['l'] != undefined && params['l'] == lista) {
						let url = "/tasks";
	
						refreshListsTask(url);
						replaceSearchURL(url);	
					} else {
						refreshListsTaskActual();
					}
				} 
				
				alert(json.msg);
			} else {
				console.log(msg);
			}
		})
		.fail(function(jqXHR, textStatus, msg) {
			alert(msg);
		});
	}
	
});

/*
    Form and Submit Events Modals
*/

// Form Modal Add Lists
$("#form-modal-add-lists").on("submit", function(e) {

    e.preventDefault();
	
	let type = $(this).attr("data-type");
	let lista = $(this).attr("data-lista");
	
	type = type != undefined && type >= 0 && type <= 1 ? type : 0;
	lista = lista != undefined && lista > 0 ? lista : 0;
	
	let action = type == 1 ? "addLista" : "editLista";
	let data = $(this).serialize() + `&action=${action}&code=${lista}`;
	
	$.ajax({
		url: CONTEXT_PATH + "/lists",
		type: 'POST',
		data: data,
		beforeSend: function() {
			console.log("Enviando...");
		}
	})
	.done(function(msg) {
		
		if (isJson(msg)) {
			let json = JSON.parse(msg);
			if (json.status == 1) {
				refreshListsTaskActual(); 
			} 
			
			alert(json.msg);
		} else {
			console.log(msg);
		}
	})
	.fail(function(jqXHR, textStatus, msg) {
		alert(msg);
	});
	/*
    let json = {
        title : $("#input-title-list").val()
    };

    if((json.title).length > 0 ) {
        $("#menu-list-tasks").append(`
        <a href="#list-${json.title}" class="list-group-item list-group-item-action border-0 list-item-tasks menu-task fs-5">
            <span>
                <i class="far fa-bars icons-menu-tasks"></i>
                ${json.title}
            </span>
            <span class="btn-list-edit float-end" data-bs-toggle="modal" data-bs-target="#modal-add-lists">
                <i class="fad fa-edit icons-menu-tasks"></i>
            </span>
        </a>
        `);
        
        alert("Lista foi adicionada com sucesso");

    }*/

});

function replaceTaskLista(lista = 0) {
	
	let params = getParametersURL();
	let search = params['s'] != undefined ? params['s'] : "";
	let url = `/tasks?s=${search}&l=${lista}`;
	
	refreshListsTask(url);
	replaceSearchURL(url);
}