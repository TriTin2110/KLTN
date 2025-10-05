let error = document.getElementById('error')
function sendMail() {
	let sendMailButton = document.getElementById('send-mail-button');
	sendMailButton.disabled = true
	let email = document.getElementById('email')
	let regex = /^\S+@\S+\.\S+$/
	if(regex.exec(email.value) === null)
	{
		error.style.display = 'block'
		error.innerText = 'Email không hợp lệ!'
		sendMailButton.disabled = false
		return
	}
	fetch("/user/verify-code", {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ email: email.value })
	}).then(res => {
		if (!res.ok) {
			console.log('Đã có lỗi')
		}
		return res.json()
	}).then(data => {
		if (data.success) {
			document.getElementById('verify-code').style.display = 'block'
			document.getElementById('vefify-password').style.display = 'block'
			let sendMaiButton = document.getElementById('send-mail-button')
			sendMaiButton.setAttribute('onclick', 'changePassword()')
			sendMailButton.disabled = false
			error.style.display = 'none'
			email.disabled=true
		}
	})
}

function changePassword() {
	let sendMailButton = document.getElementById('send-mail-button');
	sendMailButton.disabled = true
	let email = document.getElementById('email').value;
	let password = document.getElementById('new-password').value;
	let verifyCode = document.getElementById('verify-code-input').value;
	if (!verifyCode || !checkPassword(password)) {
		error.innerText = 'Thông tin không hợp lệ!'
		error.style.display = 'block'
		sendMailButton.disabled = false
		return
	}
	fetch('/user/reset-password', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ email: email, password: password, verifyCode: verifyCode })
	}).then(res => {
		if (!res.ok) {
			console.log('Đã có lỗi')
		}
		return res.json()
	}).then(data => {
		if (!data.success) {

			error.style.display = 'block'
			error.innerText = data.text
		} else {
			window.location.href = '/user/sign-in'
		}
	})
	sendMailButton.disabled = false
}

function checkPassword(password) {
	if (!password || password.length < 4) {
		return false;
	}
	return true;
}