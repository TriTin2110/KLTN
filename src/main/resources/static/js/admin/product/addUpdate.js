const btn = document.getElementById('toggleFormBtn');
const formSection = document.querySelector('.form-section.collapsible');
let form = document.getElementById('productForm');
let fileImportButton = document.getElementById("file-import-button")
let fileImportInput = document.getElementById("file-import-input")

fileImportButton.addEventListener('click', function() {
	fileImportInput.click()
})
fileImportInput.addEventListener('change', function() {
	if (fileImportInput.files.length > 0) {
		fileImportInput.form.submit()
	}
})
// Toggle collapsible form
btn.addEventListener('click', () => {
	formSection.classList.toggle('active');
	btn.querySelector('i').classList.toggle('fa-chevron-down');
	btn.querySelector('i').classList.toggle('fa-chevron-up');
});

// Nút Return: quay lại Thêm sản phẩm
function returnToAdd() {
	form.reset();
	console.log(form)
	form.setAttribute('action', '/product/admin/insert');
	formSection.querySelector('h2').innerText = 'Thêm sản phẩm mới';
	const returnBtn = formSection.querySelector('.return-btn');
	if (returnBtn) returnBtn.style.display = 'none';
}
//Thêm dòng size price vào khỏi bảng
function insertSizePriceRow() {
	let tbody = document.getElementById('size-price-tbody')
	let row = document.createElement('tr')
	let sizeCell = document.createElement('td')
	let priceCell = document.createElement('td')
	let actionCell = document.createElement('td')
	let sizeInput = `<input type="text" list="sizes" class="sizes" placeholder="S, M, L" required/>
								<datalist id="sizes">
									<option>S</option>
									<option>M</option>
									<option>L</option>
								</datalist>`
	let priceInput = `<input type="text" class="prices" required>`
	let actionInput = `<button class="remove-size-price-btn" type="button" onclick="removeSizePrice(this)"><i class="fa fa-times" aria-hidden="true"></i></button>`
	sizeCell.innerHTML = sizeInput
	priceCell.innerHTML = priceInput
	actionCell.innerHTML = actionInput
	actionCell.style.fontSize = "28px"
	row.appendChild(sizeCell)
	row.appendChild(priceCell)
	row.appendChild(actionCell)
	tbody.appendChild(row)
}    

//Xóa dòng size price ra khỏi bảng
function removeSizePrice(e)
{
	let tbody = document.getElementById('size-price-tbody')
	let td = e.parentElement
	let tr = td.parentElement
	if(tbody.childElementCount > 1){
		tbody.removeChild(tr)
	}else{
		alert('Không thể xóa tất cả size-price')
	}
}

function submitForm(){
	let sizes = document.querySelectorAll('.sizes')
	let prices = document.querySelectorAll('.prices')
	let sizesInputValues = Array.from(sizes).map(input => input.value)
	let pricesInputValues = Array.from(prices).map(input => input.value)
	let sizesJoining = sizesInputValues.join(', ')
	let pricesJoining = pricesInputValues.join(', ')
	let sizeResult = document.getElementById('size-result')
	let priceResult = document.getElementById('price-result')
	
	let categorySelect = document.getElementById('category-select').value
	let categoryError = document.getElementById('category-error')
	categoryError.style.display = 'none'
	if(categorySelect == 'default') //Khi chưa chọn danh mục
	{
		categoryError.style.display = 'block'
		return
	}
	sizeResult.value = sizesJoining
	priceResult.value = pricesJoining
	let productForm = document.getElementById('productForm')
	productForm.submit()
	
}