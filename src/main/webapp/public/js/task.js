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
$("#modal-add-tasks").on("show.bs.modal", function (e) {

    let btn = $(e.relatedTarget);
    let type = btn.attr("data-type") != undefined ? 1 : 0;

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
		.done(function(msg)
		{
			if (isJson(msg))
			{
				let json = JSON.parse(msg);
				if (json.status == 1)
				{
					alert(json.msg);
					window.location.replace(CONTEXT_PATH + "/tasks");
				}
				else
				{
					console.log(json.msg);
				}
			}
			else
			{
				console.log(msg);
			}
		})
		.fail(function(jqXHR, textStatus, msg)
		{
		     alert(msg);
		});

	let json = {
		title: $("#input-title-task").val(),
		date: $("#input-date-limit").val(),
	};

	if ((json.title).length > 0 && (json.date).length > 0) {

		let limit = new Date(json.date);
		let data = limit.toLocaleDateString("pt-BR", { day: 'numeric', month: 'long', year: 'numeric' });
		let hora = limit.toLocaleTimeString("pt-BR", { hour: 'numeric', minute: 'numeric' });
		let num = $("#list-my-tasks").children("div").length + 1;

		$("#list-my-tasks").prepend(`
            <div class="list-group-item list-group-item-action border rounded-4 fs-4 fundo-task">
                <div class="row">
                    <div class="col-5">
                        <div class="form-check">
                            <label class="form-check-label" for="input-check-task${num}">${json.title}</label>
                            <input class="form-check-input" type="checkbox" id="input-check-task${num}" name="input-check-task">
                        </div>
                    </div>
                    <div class="col-7 d-flex justify-content-end align-items-center gap-5">
                        <span class="fs-6">${hora} - ${data}</span>
                        <a href="#modal-add-tasks" class="link-dark fs-5" data-bs-toggle="modal" data-bs-target="#modal-add-tasks">
                            <i class="far fa-pen"></i>
                        </a>
                    </div>
                </div>
            </div>
        `);

		//alert("Tarefa foi adicionada com sucesso.");

	}

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
	$("#list-my-tasks").load( CONTEXT_PATH + `${url} #list-my-tasks >*`);
	$("#list-my-tasks-complete").load( CONTEXT_PATH + `${url} #list-my-tasks-complete >*`);
	
	replaceSearchURL(url);
	
});