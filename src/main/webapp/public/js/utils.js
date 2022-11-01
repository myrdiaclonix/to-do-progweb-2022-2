function isJson(text) {
	try {
		let json = JSON.parse(text);
		return true;
	} catch(error) {
		return false;
	}
}

function getParametersURL() {
	
	// Acha parametros da barra de pesquisa do navegador
	let query = location.search.slice(1);
	let partes = query.split('&');
	let params = {};

	// Associa todos os parametros com seu nome em um array
	partes.forEach(function (parte) {
	    let chaveValor = parte.split('=');
	    let chave = chaveValor[0];
	    let valor = chaveValor[1];
	    params[chave] = valor;
	});
	
	return params; 
	
}

function replaceSearchURL(url) {
	
	window.history.replaceState("", "",  CONTEXT_PATH + url);
	
}