function addItem()
{
	const sizeSelectedIndex = document.getElementById("size-selection").selectedIndex;
	let sizeOption =  document.getElementById("size-selection").options[sizeSelectedIndex]
	let formData = new FormData(document.getElementById("add-item-form"))
	let productName = formData.get('product-name')
	let productQuantity = formData.get('quantity')
	let price = sizeOption.value
	let size = sizeOption.textContent
		
	fetch("/cart/add", {
		headers:{'Content-Type':'application/json'},
		method: 'POST',
		body: JSON.stringify({productName: productName, productQuantity:productQuantity, price:price, size:size})
	})
}

function insertItemToCart()
{
	let tbody = document.getElementById("cart-view").querySelector("tbody")
}