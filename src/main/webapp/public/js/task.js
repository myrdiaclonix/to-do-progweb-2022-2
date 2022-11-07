/*
	Menu Tasks' Events
*/

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
	
	$("#select-list-task").load(CONTEXT_PATH + `/tasks #select-list-task >*`);

	if (type == 0) {

		$(this).find(".modal-title span").text("Editar tarefa");

	} else {

		$(this).find(".modal-title span").text("Adicionar tarefa");
	}

});

/*
	Form and Submit Events Modals
*/

// Form Modal Add Tasks
$("#form-modal-add-tasks").on("submit", function(e) {
	e.preventDefault();
	$.ajax({
		url: CONTEXT_PATH + "/tasks",
		type: 'POST',
		data: $(this).serialize() + "&action=addTask",
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
			
			alert(json.msg);
		} else {
			console.log(msg);
		}
	})
	.fail(function(jqXHR, textStatus, msg) {
		alert(msg);
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

function refreshListsTaskActual() {
	
	let params = getParametersURL();
	let search = params['s'] != undefined && (params['s']).length > 0 ? params['s'] : "";
	let lista = params['l'] != undefined && params['l'] >= 0 ? params['l'] : 0;
	let url = `/tasks?s=${search}&l=${lista}`;

	refreshListsTask(url);
}