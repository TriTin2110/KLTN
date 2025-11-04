
let orders = JSON.parse(orderJson)
let orderId = document.getElementById('order-id')
	let orderAddress = document.getElementById('order-address')
	let orderPhone = document.getElementById('order-phone')
	let orderDate = document.getElementById('order-date')
	let orderStatus = document.getElementById('order-status')
	let currentOrderStatus = document.getElementById('current-order-status')
	let totalPrice = document.getElementById('total-price')
	let listOrderItem = document.getElementById('list-order-item')
	
function openEditPanel(id) {
	let order = orders.find(o => o.id == id)
	let editor = document.getElementById('order-editor')
	let tablePanel = document.getElementById('table-panel')
	
	editor.style.display = 'block'
	tablePanel.style.display = 'none'
	
	let select = document.createElement('select')
	let date = new Date(order.createdDate)
	
	let opt1 = new Option('Chờ xác nhận', 'WAITING')
	let opt2 = new Option('Đang xử lý', 'PENDING')
	let opt3 = new Option('Đang giao hàng', 'DELIVERY')
	let opt4 = new Option('Đã hoàn tất', 'COMPLETED')
	let opt5 = new Option('Bị hủy', 'CANCELLED')

	select.add(opt1)
	select.add(opt2)
	select.add(opt3)
	select.add(opt4)
	select.add(opt5)
	select.value = order.status
	select.id = 'status'
	select.className = 'form-select'
	select.style.fontSize = '18px'

	orderId.innerText = order.id
	orderAddress.innerText = order.address
	orderPhone.innerText = order.phoneNumber
	totalPrice.innerText = formatValueToVND(order.totalPrice)
	orderDate.innerText = date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
	orderStatus.append(select)//Gán khung select option cho phép nhân viên có thể thay đổi trạng thái
	currentOrderStatus.innerText = order.status
	
	handlingStatus(order.status) //Kiểm tra trạng thái hiện tại
	const orderItem = order.orderItem
	for (item of orderItem) {
		let tr = document.createElement('tr')
		let tdImg = document.createElement('td')
		let tdName = document.createElement('td')
		let tdPrice = document.createElement('td')
		let tdQuantity = document.createElement('td')
		let tdSize = document.createElement('td')

		let img = document.createElement('img')
		img.src = 'https://s3.cloudfly.vn/kltn/images/' + item.productImage

		tdImg.append(img)
		tdName.textContent = item.productId
		tdPrice.textContent = item.price
		tdQuantity.textContent = item.quantity
		tdSize.textContent = item.size

		tr.append(tdImg)
		tr.append(tdName)
		tr.append(tdPrice)
		tr.append(tdQuantity)
		tr.append(tdSize)
		listOrderItem.append(tr)
	}
}

function updateStatus() {
	const orderId = document.getElementById('order-id').innerText
	const status = document.getElementById('status').value
	fetch('/admin/order/update/status', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ orderId: orderId, status: status })
	}).then(res => res.json()).then(data => {
		if (data.success) {
			handlingStatus(data)
		}
		let resultPanel = document.getElementById((data.success) ? 'success-panel' : 'failed-panel')
		resultPanel.classList.add('active')
		setTimeout(() => {
			resultPanel.classList.remove('active')
		}, 2000)
	})
	
}

function handlingStatus(data) {
	let statusText =  data.status
	let orderId = data.orderId
	let currentOrderStatus = document.getElementById('current-order-status')
	currentOrderStatus.textContent = statusText
	let status = document.getElementById('status')
	let btnUpdate = document.getElementById('btn-update')
	if (statusText == 'CANCELLED' || statusText == 'COMPLETED') {
		if (statusText == 'CANCELLED')
			currentOrderStatus.style.color = 'red'
		else
			currentOrderStatus.style.color = 'green'
		status.style.display = 'none'
		btnUpdate.style.display = 'none'
		orders.filter(o => o.id != orderId) //Xóa order ra khỏi danh sách orders nếu trạng thái là CANCELLED hoặc COMPLETED
		return
	}else{
		let order = orders.find(o => o.id = orderId)
		if(order){
			order.status = statusConverter.get(statusText)	//Cập nhật trạng thái cho order
			let orderRow = document.getElementById(orderId)
			let orderStatus = orderRow.querySelector('.order-status')
			orderStatus.textContent = statusConverter.get(statusText)
		}
		return
	}
}
function showTableOrder(){
	let editor = document.getElementById('order-editor')
	let tablePanel = document.getElementById('table-panel')	
	editor.style.display = 'none'
	tablePanel.style.display = 'block'
	let elements = [orderId, orderAddress, orderPhone, totalPrice, orderDate, orderStatus, currentOrderStatus, listOrderItem]
	elements.forEach(e => e.innerHTML = "")
}