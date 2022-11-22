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
	form[0].reset();

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

// Modal Share Lists
$("#modal-share-lists").on("show.bs.modal", function (e) {

    let btn = $(e.relatedTarget);
	let form = $(this).find("#form-modal-share-lists");

	// Limpa os valores dos campos do formulario do modal
	form[0].reset();
	
	$("#lists-modal-share").load(CONTEXT_PATH + `/tasks #lists-modal-share >*`);

});

// Modal Share Lists
$("#modal-share-list-config").on("show.bs.modal", function (e) {

    let btn = $(e.relatedTarget);
	let form = $(this).find("#form-modal-share-list-config");
	let lista = btn.attr("data-lista");
	
	lista = lista != undefined && lista > 0 ? lista : 0;
	// Limpa os valores dos campos do formulario do modal
	form[0].reset();
	
	$("#list-emails-shared").load(CONTEXT_PATH + `/tasks?l=${lista} #list-emails-shared >*`);

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
				
				alertSweet(json.msg, json.status);
			} else {
				console.log(msg);
			}
		})
		.fail(function(jqXHR, textStatus, msg) {
			alertSweet(msg);
		});
	}
	
});

/*
    Event Elements Modal
*/
$(document).on("change", "#btn-select-todes", function (event) {

	// Previne a ação padrão
	event.preventDefault(); 
	
	if($(this).is(":checked")) {
		$("input[name='input-check-share-list']").prop("checked", true);
	} else {
		$("input[name='input-check-share-list']").prop("checked", false);
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
			
			alertSweet(json.msg, json.status);
		} else {
			console.log(msg);
		}
	})
	.fail(function(jqXHR, textStatus, msg) {
		alertSweet(msg);
	});

});

// Form Modal Share Lists
$("#form-modal-share-lists").on("submit", function(e) {

    e.preventDefault();
	
	let inputs = $("input[name='input-check-share-list']:checked"); 
	let checks = "";
	
	if(inputs.length > 0) {
		
		for(let i = 0; i < inputs.length; i++) {
			checks += i < inputs.length-1 ? inputs[i].dataset.lista + ",": inputs[i].dataset.lista;
		}
		
	}
	
	let lista = $(this).attr("data-lista");
	
	lista = lista != undefined && lista > 0 ? lista : 0;
	
	let action = "shareLista";
	let data = $(this).serialize() + `&action=${action}&code=${lista}&checks=${checks}`;
	
	$.ajax({
		url: CONTEXT_PATH + "/lists",
		type: 'POST',
		data: data,
		beforeSend: function() {
			console.log("Enviando...");
		}
	})
	.done(function(msg) {
			console.log(msg);
		if (isJson(msg)) {
			let json = JSON.parse(msg);
			if (json.status == 1) {
				//refreshListsTaskActual(); 
			} 
			
			alertSweet(json.msg, json.status);
		} else {
			console.log(msg);
		}
	})
	.fail(function(jqXHR, textStatus, msg) {
		alertSweet(msg);
	});

});

function replaceTaskLista(lista = 0) {
	
	let params = getParametersURL();
	let search = params['s'] != undefined ? params['s'] : "";
	let url = `/tasks?s=${search}&l=${lista}`;
	
	refreshOnlyTasks(url);
	replaceSearchURL(url);
}