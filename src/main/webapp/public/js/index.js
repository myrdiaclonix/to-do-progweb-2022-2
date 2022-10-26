/*
let input = document.querySelector("#email");

input.addEventListener("click", function(e) {
	console.log("Esse é o e-mail");
	this.classList.add("desactive");
});

$("#email").on("click", function(e) {
	console.log("Esse é o input email 2");
	$("input").addClass("active");
});
*/

$("#form-cadastro").on("submit", function(event) {
	
	event.preventDefault(); // previne a ação padrão
	
	$.ajax({
	     url : CONTEXT_PATH + "/cadastro",
	     type : 'POST',
	     data : $(this).serialize(),
	     beforeSend : function(){
	          console.log("enviando...");
	     }
	})
	.done(function(msg){
	     
	   if(isJson(msg)) {
		
			let json = JSON.parse(msg);
			
			if(json.status == 1) {
				alert(json.msg);
				window.location.replace(CONTEXT_PATH + "/cadastro");
			} else {
				console.log(json.msg);
			}
			
		} else {
			console.log(msg);
		}
	})
	.fail(function(jqXHR, textStatus, msg){
	     alert(msg);
	});
	
});