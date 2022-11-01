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
    let type = btn.attr("data-type") != undefined ? 1 : 0;

    if (type == 0) {

        $(this).find(".modal-title span").text("Editar lista");

    } else {

        $(this).find(".modal-title span").text("Adicionar lista");
    }

});

/*
    Form and Submit Events Modals
*/

// Form Modal Add Lists
$("#form-modal-add-lists").on("submit", function(e) {

    e.preventDefault();

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

    }

});

function replaceTaskLista(lista = 0) {
	
	let params = getParametersURL();
	let search = params['s'] != undefined ? params['s'] : "";
	let url = `/tasks?s=${search}&l=${lista}`;
	
	$("#list-all-tasks").load( CONTEXT_PATH + `${url} #list-all-tasks >*`);
	replaceSearchURL(url);
	
}