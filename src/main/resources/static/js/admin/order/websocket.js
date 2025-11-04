var statusConverter = new Map([
  ["PENDING", "Đang xử lý"],
  ["COMPLETED", "Đã hoàn tất"],
  ["CANCELLED", "Bị hủy"],
  ["WAITING", "Chờ xác nhận"],
  ["DELIVERY", "Đang giao hàng"]
])

ws.onmessage = function (res){
	let order = JSON.parse(res.data)
	let newOrderNotification = document.getElementById('new-order-notification')
	newOrderNotification.classList.add('active')
	setTimeout(function (){
		newOrderNotification.classList.remove('active')
	}, 5000)
	
	//Trường hợp người dùng đang trong Order Dashboard thì lấy bảng
	let tableOrder = document.getElementById('table-order')
	if(tableOrder)
	{
		console.log(order)
		orders.push(order)
		let tbody = tableOrder.querySelector('tbody')
		let tr = document.createElement('tr')
		tr.classList.add('order-row')
		let td1 = document.createElement('td')
		let td2= document.createElement('td')
		let td3 = document.createElement('td')
		let td4 = document.createElement('td')
		let td5 = document.createElement('td')
		let td6 = document.createElement('td')
		let td7 = document.createElement('td')
		let button = document.createElement('button')
		
		td1.style.padding = '20px 0px'
		td1.innerText = order.id
		td2.innerText = order.address
		td3.innerText = order.phoneNumber
		td4.innerText = new Date(order.createdDate).toISOString().split('T')[0] //Cắt bỏ phần giờ bằng cách tách chuỗi tại ký tự T
		td5.classList.add('order-status')
		td5.innerText = statusConverter.get(order.status)
		td6.classList.add('pro-price')
		td6.style.fontWeight = 'bold'
		td6.innerText = formatValueToVND(order.totalPrice)
		button.innerHTML = '<i class="fa-solid fa-pen-to-square"></i>'
		button.onclick=() => openEditPanel(order.id)
		button.classList.add('order-edit-btn')
		td7.append(button)
		
		tr.id=order.id
		tr.append(td1)
		tr.append(td2)
		tr.append(td3)
		tr.append(td4)
		tr.append(td5)
		tr.append(td6)
		tr.append(td7)
		tbody.append(tr)
	}
}
