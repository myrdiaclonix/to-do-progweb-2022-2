/*
	Menu Tasks' Events
*/

$("#btn-logout").on("click", function(e) {
	e.preventDefault();
	$.ajax({
		url: CONTEXT_PATH + "/",
		type: 'POST',
		data: $(this).serialize() + "&action=logout",
		
	})
	.done(function(msg) {
		if (isJson(msg)) {
			let json = JSON.parse(msg);
			if (json.status == 1) {
				window.location.href = CONTEXT_PATH + "/";
			} 
			
		} 
	})
	.fail(function(jqXHR, textStatus, msg) {
		alertSweet(msg);
	});
});

$(document).on("change", ".check-confirm-task", function (event) {

	// Previne a ação padrão
	event.preventDefault(); 
	
	let newStatus;
	if($(this).is(':checked')) {
		newStatus = 1;
	} else {
		newStatus = 0;
	}
	
	let task = $(this).attr("value");
	task = task != undefined && task > 0 ? task : 0;
	
	$.ajax({
		url: CONTEXT_PATH + "/tasks",
		type: 'POST',
		data: $(this).serialize() + `&action=changeStatus&id=${task}&status=${newStatus}`,
		beforeSend: function() {
			console.log("Enviando...");
		}
	})
	.done(function(msg) {
		if (isJson(msg)) {
			let json = JSON.parse(msg);
			if (json.status == 1) {
				refreshListsTaskActual("/tasks", 1);
			} 
		} 
	})
	.fail(function(jqXHR, textStatus, msg) {
		alertSweet(msg);
	});
	
});

/*
    Events Remove
*/
$(document).on("click", ".btn-remove-task", function (event) {

	// Previne a ação padrão
	event.preventDefault(); 
	
	let conf = confirm("Confirma a remoção dessa tarefa?");
	
	if(conf) {
		let task = $(this).attr("data-task");
		task = task != undefined && task > 0 ? task : 0;
		
		let action = "removeTask";
		
		$.ajax({
			url: CONTEXT_PATH + "/tasks",
			type: 'POST',
			data: $(this).serialize() + `&action=${action}&id=${task}`,
			beforeSend: function() {
				console.log("Enviando...");
			}
		})
		.done(function(msg) {
			
			if (isJson(msg)) {
				let json = JSON.parse(msg);
				if (json.status == 1) {
					refreshListsTaskActual("/tasks", 1);
				} 
				alertSweet(json.msg, json.status);
			} 
		})
		.fail(function(jqXHR, textStatus, msg) {
			alertSweet(msg);
		});
	}
	
});

/*
	Element Modals' Events
*/

// Event onclick for theme-buttons
$(".theme-buttons").on("click", function(e) {
	let color = $(this).attr("data-color");
	$(".color-switcher button span").css("color", color);
	$(".color-switcher input").val(color);
});

/*
	Initialization Events Modals
*/

// Modal Add Tasks
$("#modal-add-tasks").on("show.bs.modal", function(e) {

	let btn = $(e.relatedTarget);
	let type = btn.attr("data-type") != undefined ? 1 : 0;
	let form = $(this).find("#form-modal-add-tasks");

	// Limpa os valores dos campos do formulario do modal
	$("#form-modal-add-tasks")[0].reset();
	
	if (type == 0) {
		$(this).find(".modal-title span").text("Editar tarefa");
		$(this).find("#task-submit-button").text("Atualizar");

		let btn = $(e.relatedTarget);
		let task = btn.attr("data-task") != undefined ? btn.attr("data-task") : 0;
		form.attr("data-task", task);
		
		$.ajax({
			url: CONTEXT_PATH + "/tasks",
			type: "POST",
			data: `action=getTask&id=${task}`,
			beforeSend: () => { console.log("Buscando..."); }
		})
		.done((msg) => {
			if (isJson(msg)) {
				let json = JSON.parse(msg);
				if (json.status == 1) {
					
					$("#select-list-task").load(CONTEXT_PATH + `/tasks #select-list-task >*`, function(e) {
						$("#input-title-task").val(json.res[0]);
						$("#input-date-limit").val(json.res[1]);
						$("#textarea-description").val(json.res[2]);
						if (json.res.length > 3) {
							$(`#select-list-task option[value="${json.res[3]}"]`).prop("selected", true);
						} else {
							$(`#select-list-task option:first`).prop("selected", true);
						}	
					});
					
				}
			}
		})
		.fail(function(jqXHR, textStatus, msg) {
			console.log(msg);
		});
	}
	else {
		
		$("#select-list-task").load(CONTEXT_PATH + `/tasks #select-list-task >*`, function() {
			$(`#select-list-task option:first`).prop("selected", true);
		});
		form.removeAttr("data-task");
		$(this).find(".modal-title span").text("Adicionar tarefa");
		$(this).find("#task-submit-button").text("Adicionar");
	}
});

/*
	Form and Submit Events Modals
*/

// Form Modal Add Tasks
$("#form-modal-add-tasks").on("submit", function(e) {
	
	e.preventDefault();
	
	let task = $(this).attr("data-task");
	task = task != undefined && task > 0 ? task : 0;
	
	let action = task > 0 ? "editTask" : "addTask";
	
	$.ajax({
		url: CONTEXT_PATH + "/tasks",
		type: 'POST',
		data: $(this).serialize() + `&action=${action}&id=${task}`,
		beforeSend: function() {
			console.log("Enviando...");
		}
	})
	.done(function(msg) {
		if (isJson(msg)) {
			let json = JSON.parse(msg);
			if (json.status == 1) {
				let params = getParametersURL();

				let search = params['s'] != undefined && (params['s']).length > 0 ? params['s'] : "";
				let lista = params['l'] != undefined && params['l'] >= 0 ? params['l'] : 0;

				let url = `/tasks?s=${search}&l=${lista}`;

				refreshListsTask(url);
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

/*
	Search Tasks
*/

// button para fazer a pesquisa das tarefas
$("#form-search-tasks").on("submit", function(event) {

	// Previne a ação padrão
	event.preventDefault();

	let params = getParametersURL();

	// Pega o valor do input de pesquisa das tarefas
	let search = $("#input-search-task").val();

	// Pega valor do parametro "l" informado na barra de pesquisa do navegador
	let lista = params['l'] != undefined && params['l'] > 0 ? params['l'] : 0;

	// Constroi a URL
	let url = `/tasks?s=${search}&l=${lista}`;

	// Atualiza a lista de tarefas pendentes e concluidas
	refreshListsTask(url);

	// Atualiza URL
	replaceSearchURL(url);

});

function refreshListsTask(url) {
	$("#menu-tasks").load(CONTEXT_PATH + `${url} #menu-tasks >*`);
	$("#list-all-tasks").load(CONTEXT_PATH + `${url} #list-all-tasks >*`);
	$("#list-my-tasks-complete").load(CONTEXT_PATH + `${url} #list-my-tasks-complete >*`);
}

function refreshOnlyTasks(url) {
	$("#list-all-tasks").load(CONTEXT_PATH + `${url} #list-all-tasks >*`);
	$("#list-my-tasks-complete").load(CONTEXT_PATH + `${url} #list-my-tasks-complete >*`);
}

function refreshOnlyTasksComplete(url) {
	$("#list-all-tasks").load(CONTEXT_PATH + `${url} #list-all-tasks >*`);
	$("#sub-list-tasks-complete").load(CONTEXT_PATH + `${url} #sub-list-tasks-complete >*`);
	$("#counter-tasks-complete").load(CONTEXT_PATH + `${url} #counter-tasks-complete >*`);
}

function refreshListsTaskActual(url = "/tasks", type = 0) {
	
	let params = getParametersURL();
	let search = params['s'] != undefined && (params['s']).length > 0 ? params['s'] : "";
	let lista = params['l'] != undefined && params['l'] >= 0 ? params['l'] : 0;
	url = `${url}?s=${search}&l=${lista}`;
	
	if(type != 0) {
		refreshOnlyTasksComplete(url);
	} else {
		refreshListsTask(url);
	}
	
}