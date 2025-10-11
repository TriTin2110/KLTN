function formatToVND(){
	let prices = document.getElementsByClassName("pro-price")
	for(let price of prices)
	{
		let priceValue = parseInt(price.textContent)
		price.textContent = priceValue.toLocaleString('vi-VN', {style: 'currency', currency: "VND"})
	}
}

function formatElementToVND(element)
{
	let priceValue = parseInt(element.textContent)
	element.textContent = priceValue.toLocaleString('vi-VN', {style: 'currency', currency: "VND"})
}