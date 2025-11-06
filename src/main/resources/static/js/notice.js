let wsNotice = null
window.addEventListener('pageshow', () => {
	if (userNameNotification) {
		openWebSocket()
		if (!localStorage.getItem('notifications')) {
			fetch('/user/notification', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify({ userName: userNameNotification })
			}).then(res => res.json()).then(data => {
				let notifications = data.result
				localStorage.setItem('notifications', JSON.stringify(notifications)) //Giúp load notification đúng 1 lần khi user đăng nhập
				showNotification()

			})
		}
		else {
			showNotification()
		}
	}
})
function openWebSocket() {
	wsNotice = new WebSocket("ws://localhost:8080/notice?role=user&userId=" + userNameNotification)
	wsNotice.onmessage = function(res) {
		let data = JSON.parse(res.data)
		let notifications = JSON.parse(localStorage.getItem('notifications'))
		notifications.forEach(n =>{
			if(n.content == data.content)//Nếu là cập nhật
			{
				n.type = data.type
				n.localDateTime = data.localDateTime
				let container = document.getElementById(n.content)
				let status = container.querySelector('.notification-status')
				let dateHTML = container.querySelector('.notification-date') 
				status.innerHTML = 'Trạng thái: <b>'+data.type+"</b>"
				const [year, month, day, hour, minute, second, nano] = data.localDateTime;
				let date = new Date(year, month, day, hour, minute, second, nano/1000000);
				dateHTML.innerText = 'Ngày tạo: ' + formatDate(date) 
				updated = true;
				return
			}
		})
		if(!updated)//Nếu là thêm mới
		{
			const [year, month, day, hour, minute, second, nano] = data.localDateTime;
				let date = new Date(year, month, day, hour, minute, second, nano/1000000);
				data.localDateTime = date
			notifications.push(data)
		}
		localStorage.setItem('notifications', JSON.stringify(notifications))//Cập nhật lại danh sách
	}
}

function formatDate(date)
{
	const hours = String(date.getHours()).padStart(2, '0');//Thêm vào đầu chuỗi kí tự '0' nếu độ dài chuỗi < 2
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const d = String(date.getDate()).padStart(2, '0');
    const m = String(date.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
    const y = date.getFullYear();
    return `(${hours}:${minutes}) ${d}/${m}/${y}`;
}

function toggleNoticePanel() {
	let notificationPanel = document.getElementById('notification-panel')
	if (notificationPanel.classList.contains('active')) {
		notificationPanel.classList.remove('active')
	}
	else {
		notificationPanel.classList.add('active')
	}
}

function showNotification() {
	let notifications = JSON.parse(localStorage.getItem('notifications'))
	notifications.sort((a, b) => {
  // Chuyển sang Date object để so sánh
  return new Date(b.localDateTime) - new Date(a.localDateTime);
}).forEach(n => {
	const [year, month, day, hour, minute, second, nano] = n.localDateTime;
	n.localDateTime = new Date(year, month, day, hour, minute, second, nano/1000000);
	appendNotification(n)
	})
}

function appendNotification(n)
{
	let notificationList = document.getElementById('notification-list')
	let tr = document.createElement('tr')
		let td1 = document.createElement('td')
		let td2 = document.createElement('td')
		let image = document.createElement('img')
		let container = document.createElement('a')
		let title = document.createElement('div')
		let status = document.createElement('div')
		let date = document.createElement('div')

		image.src = 'https://s3.cloudfly.vn/kltn/images/' + n.image
		title.textContent = n.title
		status.innerHTML = 'Trạng thái: <b>' + n.type + '</b>'
		date.textContent = 'Ngày tạo: ' + formatDate(n.localDateTime)
		container.href = '/order/show-detail/' + n.content
	
		status.classList.add('notification-status')
		date.classList.add('notification-date')
		container.id = n.content
		
		td1.append(image)
		container.append(title)
		container.append(status)
		container.append(date)
		td2.append(container)

		tr.append(td1)
		tr.append(td2)
		notificationList.append(tr)
}

function dropLocalStorage() {
	localStorage.clear()
}