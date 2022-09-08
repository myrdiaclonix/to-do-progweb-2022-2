



var addTarefa = document.querySelector(".adicionar-tarefa");

console.log(addTarefa);

addTarefa.addEventListener("click", function(){
    event.preventDefault();

    var maeDeTodasAsDivs = document.querySelector(".list-group");
    console.log("oi",maeDeTodasAsDivs);
    var form = document.querySelector("#add-Tarefa");
    var titulo = form.titulo.value;
    var dataLimite = form.dataLimite.value;
    var descricao = form.descricao.value;
    var categoria = form.opcao.value;
    //console.log(titulo, dataLimite, descricao, categoria);

    var nova = document.createElement("div");
});


function aparece(){

    var troca = document.querySelector(".add-task2");
    console.log(troca);
    troca.classList.add("add-task3");    

}