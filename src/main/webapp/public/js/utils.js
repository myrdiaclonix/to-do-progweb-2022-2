function isJson(text) {
	try {
		let json = JSON.parse(text);
		return true;
	} catch(error) {
		return false;
	}
}