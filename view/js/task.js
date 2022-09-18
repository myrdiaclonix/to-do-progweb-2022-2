var addTarefa = document.querySelector(".adicionar-tarefa");

// console.log(addTarefa);

addTarefa.addEventListener("click", function () {
    event.preventDefault();

    var maeDeTodasAsDivs = document.querySelector(".list-group");
    console.log("oi", maeDeTodasAsDivs);
    var form = document.querySelector("#add-Tarefa");
    var titulo = form.titulo.value;
    var dataLimite = form.dataLimite.value;
    var descricao = form.descricao.value;
    var categoria = form.opcao.value;
    var nova = document.createElement("div");
});


function aparece() {

    var troca = document.querySelector(".add-task2");
    console.log(troca);
    troca.classList.add("add-task3");

}

$("#modal-add-lists").on("show.bs.modal", function (e) {

    console.log("teste");

    let btn = $(e.target);
    let type = btn.attr("data-type") != undefined ? 1 : 0;

    if (type == 1) {

        $("#modal-add-lists .modal-title").text("Editar lista");

    } else {

        $("#modal-add-lists .modal-title").text("Adicionar lista");
    }


});