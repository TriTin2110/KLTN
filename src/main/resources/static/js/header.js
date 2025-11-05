window.addEventListener('pageshow', () =>{
	fetch('/user/notification', {
		method:'GET'
	}).then(res => res.json()).then(data =>{
		console.log(data)
	})
})