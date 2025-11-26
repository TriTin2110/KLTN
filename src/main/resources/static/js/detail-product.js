let quantity = document.getElementById("quantity")

if(quantity)
	var quantityValue = quantity.value
let priceText = document.getElementById("price")
function plusQuantity(){
	if(quantityValue)
	{
	quantityValue = parseInt(quantityValue)
	quantityValue +=1
	quantity.value = quantityValue
	}
}

function minusQuantity(){
	if(quantityValue){
		quantityValue = parseInt(quantityValue)
		if(quantityValue > 1){
			quantityValue -=1
			quantity.value = quantityValue
		}
	}
}

function setPrice(){
	let price = document.getElementById("size-selection").value
	priceText.innerText = price
	formatElementToVND(priceText)
}