const avatarContainer = document.querySelector('.avatar-container');
const avatarInput = document.getElementById('avatarInput');
const userAvatar = document.getElementById('userAvatar');

avatarContainer.addEventListener('click', () => {
	avatarInput.click(); // mở file dialog
});

avatarInput.addEventListener('change', () => {
	const file = avatarInput.files[0];
	if (file) {
		// hiển thị preview ngay lập tức
		const reader = new FileReader();
		reader.onload = e => userAvatar.src = e.target.result;
		reader.readAsDataURL(file);

		// gửi file lên server
		const formData = new FormData();
		formData.append('avatar', file);

		fetch('/user/upload-avatar', {
			method: 'POST',
			body: formData
		}).then(res => res.json())
			.then(data => {
				if (data.success) {
					console.log('Upload thành công');
				} else {
					alert('Upload thất bại');
				}
			});
	}
});
function showUpdateButton(element) {
	if (element instanceof HTMLElement) {
		let button = element.children[1]
		button.style.opacity = 1
		button.style.pointerEvents = 'all';
	}
}
function hideUpdateButton(element) {
	if (element instanceof HTMLElement) {
		let button = element.children[1]
		button.style.opacity = 0
	}
}

function allowUpdate(element) {
	if (element instanceof HTMLElement) {
		let input = element.parentElement.previousSibling
		input.disabled = false
		input.focus()
	}
}

function showSaveButton() {
	let submitButton = document.getElementById('submit-button')
	submitButton.style.display = 'block'
}

function submitChange() {
	const modal = new bootstrap.Modal(document.querySelector('.modal'), {
		backdrop: 'static',   // Không đóng khi click ngoài
		keyboard: false       // Không đóng khi nhấn ESC
	});
	modal.show();
	let fullName = document.getElementById('fullName').value
	let address = document.getElementById('address').value
	let phoneNumber = document.getElementById('phoneNumber').value

	fetch('/user/profile-update', {
		headers: { 'Content-Type': "application/json" },
		method: 'POST',
		body: JSON.stringify({ fullName: fullName, address: address, phoneNumber: phoneNumber })
	}).then(res => {
		if (!res.ok) {
			console.log('Đã có lỗi!')
		}
		return res.json()
	}).then(data => {
		if (data.success) {
			console.log(data.text)
		}
	})
	let submitButton = document.getElementById('submit-button')
	submitButton.style.display = 'none'
	modal.hide();
}