/*
    Menu Tasks' Events
*/

// Event onclick for menu-task
// $(document).on("click", ".menu-task", function (e) { TESTANDO

$(".menu-task").on("click", function(e) {
    $(".menu-task").removeClass("actual");
    $(".menu-task .btn-list-edit").removeClass("active");
    $(this).addClass("actual");
    $(this).find(".btn-list-edit").addClass("active");
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
    Initialization Modals' Events
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
    Form and Submit Modals' Events
*/

// Form Modal Add Tasks
$("#form-modal-add-tasks").on("submit", function(e) {

    e.preventDefault();

    let json = {
        title : $("#input-title-task").val(),
        date : $("#input-date-limit").val(),
    };
    
    if((json.title).length > 0 && (json.date).length > 0 ) {
        
        let limit = new Date(json.date);
        let data = limit.toLocaleDateString("pt-BR", { day: 'numeric', month: 'long', year: 'numeric' });
        let hora = limit.toLocaleTimeString("pt-BR", { hour: 'numeric', minute: 'numeric'});
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
        
        alert("Tarefa foi adicionada com sucesso");

    }

});

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