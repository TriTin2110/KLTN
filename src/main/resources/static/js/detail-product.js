let quantity = document.getElementById("quantity")
let quantityValue = document.getElementById("quantity").value
let priceText = document.getElementById("price")
function plusQuantity(){
	quantityValue = parseInt(quantityValue)
	quantityValue +=1
	quantity.value = quantityValue
}

function minusQuantity(){
	quantityValue = parseInt(quantityValue)
	if(quantityValue > 1){
		quantityValue -=1
		quantity.value = quantityValue
	}
}

function setPrice(){
	let price = document.getElementById("size-selection").value
	priceText.innerText = price
	formatElementToVND(priceText)
}