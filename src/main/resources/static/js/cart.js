var userName = document.querySelector("#user-name")

window.addEventListener('pageshow', () => {
	if (userName) {
		fetch('/cart/header', {
			method: 'GET',
			header: { 'Content-Type': 'application/json' },
		}).then(res => res.json())
			.then(data => {
				loadAllProduct(data)
			})
	}
})

function addItem() {
	const sizeSelectedIndex = document.getElementById("size-selection").selectedIndex;
	let sizeOption = document.getElementById("size-selection").options[sizeSelectedIndex]
	let formData = new FormData(document.getElementById("add-item-form"))
	let productId = formData.get('product-name')
	let productImage = formData.get('product-image')
	let quantity = formData.get('quantity')
	let price = sizeOption.value
	let size = sizeOption.textContent
	let product = { productId: productId, productImage: productImage, quantity: quantity, price: price, size: size }
	fetch("/cart/add", {
		headers: { 'Content-Type': 'application/json' },
		method: 'POST',
		body: JSON.stringify(product),
		keepalive: true //Đảm bảo request có thể gửi đến server ngay cả khi người dùng thoát trang
	}).then(res => res.json())
		.then(data => {
			if (data.success = true) {
				loadAllProduct(data)
			}
		})
}


function removeItem(e, tableId) {
	if (e instanceof HTMLElement) {
		let span = e.parentElement
		let td = span.parentElement
		let tr = td.parentElement
		let productName = e.dataset.productName
		let size = e.dataset.size
		fetch('/cart/remove', {
			headers: { 'Content-Type': 'application/json' },
			method: 'POST',
			body: JSON.stringify({ productName: productName, size: size })
		})
			.then(res => res.json())
			.then(data => {
				if (data.success) {
					let tbody = document.getElementById(tableId).querySelector('tbody')
					let totalPrice = data.totalPrice
					tbody.removeChild(tr)
					updateTotalPrice(totalPrice)
					if(tableId === 'cart-item-list-desktop' || tableId === 'cart-item-list-mobile')
					{
						loadAllProduct(data)
					}
					if(totalPrice <= 0)
					{
						document.getElementById('total-price').style.display = 'none'
					}
				}
			})
	}
}


function updateTotalPrice(totalPrice) {
	//Cập nhật totalPrice cho header
	let totalPriceView = document.getElementById('total-price-view')
	totalPriceView.textContent = parseInt(totalPrice)
	formatElementToVND(totalPriceView)
	
	//Cập nhật totalPrice cho trang cart (nếu có)
	let orderTotalPriceView = document.getElementById('order-total-price-view')
	if(orderTotalPriceView)
	{
		orderTotalPriceView.textContent = parseInt(totalPrice)
		formatElementToVND(orderTotalPriceView)
	}
}

function loadAllProduct(data)
{
	let tbody = document.getElementById('cart-view').querySelector('tbody')
	tbody.innerHTML = ''
	data.cartItem.forEach(product =>{
					renderCart(data, product)	
				})
}

function renderCart(data, product) {
	let totalPrice = data.totalPrice
	let tbody = document.getElementById('cart-view').querySelector('tbody')
	let tr = document.createElement('tr')
	let formatPrice = formatValueToVND(product.price)
	tr.className = 'item_1'
	tr.innerHTML = `
					<td class="img">
						<a href="" title="${product.productId}">
						<img src="https://s3.cloudfly.vn/kltn/images/${product.productImage}" alt="${product.productImage}"></a>
					</td>
					<td>
						<a class="pro-title-view" style="color: #272727" href="/san-pham/${product.productId}" title="${product.productId}">${product.productId + ' (<span class="header-product-quantity">' + product.quantity + '</span>)'}</a>
							<span>${'Size: ' + product.size}</span>
							<span class="remove_link remove-cart"><button class="btn-remove-cart-item" onclick="removeItem(this, 'cart-view')"
							data-product-name="${product.productId}"
							data-size="${product.size}"
							><i
									 class="fas fa-times"></i></button></span>
							<div style="text-align: left;"
								class="item-price pro-price">${formatPrice}</div>	
					</td>`
	tbody.appendChild(tr)
	//Cập nhật
	updateTotalPrice(totalPrice)
}

function totalPriceProcess()
{
	document.getElementById('total-price').style.display = 'block'
	document.getElementById('btn-show-cart').style.display = 'block'
	let quantities = document.querySelectorAll('.quantity')
	let headerQuantity = document.querySelectorAll('.header-product-quantity')
	let totalPrice = 0
	for(let i = 0; i < quantities.length; i++)
	{
		headerQuantity[i].textContent = quantities[i].value
		let price = quantities[i].dataset.price
		totalPrice += price * quantities[i].value
	}
	if(totalPrice <= 0)
	{
		document.getElementById('total-price').style.display = 'none'
		document.getElementById('btn-show-cart').style.display = 'none'
	}else{
		// Thay đổi totalprice của trang cart và header
		let orderTotalPriceView = document.getElementById('order-total-price-view')
		orderTotalPriceView.textContent = totalPrice
		let totalPriceView = document.getElementById('total-price-view')
		totalPriceView.textContent = totalPrice
		formatElementToVND(orderTotalPriceView)
		formatElementToVND(totalPriceView)
	}
}
function checkout(){
	let quantities = []
	document.querySelectorAll('.quantity').forEach(q =>{
			quantities.push({
				itemId: q.dataset.id,
				quantity: q.value
			})
	})
	
	fetch('/cart/check-cart-item', {
		headers: {'Content-Type': 'application/json'},
		method: 'POST',
		body: JSON.stringify(quantities)
	}).then(res => res.text())
	.then(url => {
		window.location.href =  url
	})
}


