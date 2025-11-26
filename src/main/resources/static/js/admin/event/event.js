var categories = categories
let mainForm = document.getElementById("main-form")
let eventEditor = document.getElementById("event-editor")
	let eventListTable = document.getElementById("event-list-table")
	let eventBtn = document.getElementById("event-btn") 
	let mainImageView = document.getElementById('main-image-view')
	let productTableEditor = document.getElementById('product-table-editor')
	
		function openEditor()
		{
			eventEditor.style.display = "block"; 
			eventListTable.style.display = "none";
			eventBtn.innerText = "Danh sách tin tức"
			eventBtn.className = "btn btn-primary"
			eventBtn.onclick= openNewsListTable //Hiển thị danh sách sản phẩm
		}

		function openNewsListTable(){
			eventEditor.style.display = "none"; 
			eventListTable.style.display = "block";
			eventBtn.innerText = "Thêm tin tức"
			eventBtn.className = "btn btn-success"
			eventBtn.onclick= openEditor
		}
		
/*function editNews(button)
{
	let name = button.dataset.name
	const news = newsList.find(n => n.name === name) //Kiểm tra news có tồn tại trong danh sách news bên list không 
	if(!news) return	
	openEditor()
	mainForm.action = "/news/update"
	let nameInput = mainForm.querySelector('#name')
	let shortDescription = mainForm.querySelector('#shortDescription')
	let mainImage = mainForm.querySelector("#main-image")
	
	//Đặt giá trị cho các thẻ input (sử dụng setAttribute do xung đột với th:object trên form edit)
	nameInput.setAttribute('value', news.name)
	shortDescription.setAttribute('value', news.shortDescription)
	shortDescription.value = news.shortDescription
	mainImage.setAttribute('value', news.image)
	
	window.editor.setData(news.content)
	mainImageView.src = "https://s3.cloudfly.vn/kltn/images/news/main-image/"+news.image
	nameInput.readOnly = true
}
*/
function changeMainImageView(input)
{
		const file = input.files[0] //Lấy file đầu tiên
		if(file)
		{
			const reader = new FileReader() //Đọc file từ local
			reader.onload = (e) =>{
				mainImageView.src = e.target.result //hiển thị ảnh
			}
			let mainImage = mainForm.querySelector("#main-image")
			mainImage.setAttribute('value', "success")
			reader.readAsDataURL(file)
		}
}

function checkingDataBeforeSubmit()
{
	return checkTable() && checkMainImage() && checkDate()
}

function checkDate()
{
	let startDateInput = document.getElementById('startDateInput').value
	let endDateInput = document.getElementById('endDateInput').value
	const today = new Date().toISOString().slice(0, 10) //lấy "YYYY-MM-DD" hiện tại 
	console.log(startDateInput >= today)
	if(startDateInput && endDateInput && startDateInput < endDateInput && startDateInput >= today && endDateInput > today)
		return true
	alert('Vui lòng chọn ngày bắt đầu và ngày kết thúc hợp lệ!')
	return false;
}

function checkTable()
{
	let tbody = productTableEditor.querySelector('tbody')
	if(tbody.rows.length > 0)
			return true;
	alert('Vui lòng chọn ít nhất một danh mục để giảm giá!')
	return false;
}

function checkMainImage()
{
	let mainImage = mainForm.querySelector("#main-image").value
	console.log(mainImage)
	if(!mainImage || mainImage.length == 0)
	{
		alert('Vui lòng chọn ảnh bìa')
		return false;
	}
	return true;
}

function addCategory()
{
	let tbody = productTableEditor.querySelector('tbody')
	let tr = document.createElement('tr')
	let td1 = document.createElement('td')
	let td2 = document.createElement('td')
	let td3 = document.createElement('td')
	let td4 = document.createElement('td')
	const selectedCategories = getSelectedCategory()
	let number = document.createElement('span')
	let select = document.createElement('select')
	let inputDiscount = document.createElement('input')
	let buttonItem = document.createElement('button')
	
	number.innerText = tbody.rows.length + 1 //lấy số lượng hàng trong tbody
	inputDiscount.type = 'number'
	inputDiscount.max = 90
	inputDiscount.min = 1
	inputDiscount.required = true	
	inputDiscount.name = `items[${tbody.rows.length}].discount` //Đặt tên cho field để có thể gửi lên Server. Spring sẽ tự map thành CategoryItemDTO 
	
	buttonItem.className = "btn btn-danger"
	buttonItem.innerText= 'Xóa'
	
	buttonItem.onclick = () => removeCategoryItem(tr) //xóa item này khi người dùng click vào button
	
	select.name = `items[${tbody.rows.length}].categoryName`//Đặt tên cho field để có thể gửi lên Server
	select.innerHTML = '<option value="default">Chọn danh mục</option>'
	for(let category of categories)
	{
		let option = document.createElement('option')
		option.value = category.name
		option.innerText = category.name
		if(selectedCategories.includes(option.innerText))//Nếu item này đã được chọn trước đó thì sẽ disable
			option.disabled = true;
			
		select.appendChild(option)
	}
	
	select.onchange = updateAllCategoryItems
	select.classList = 'category-items'
	td1.append(number)
	td2.append(select)
	td3.append(inputDiscount)
	td4.append(buttonItem)
	
	tr.appendChild(td1)
	tr.appendChild(td2)
	tr.appendChild(td3)
	tr.appendChild(td4)
	
	tbody.append(tr)
}

function removeCategoryItem(tr)
{
	let tbody = productTableEditor.querySelector('tbody')
	tbody.removeChild(tr)
	updateAllCategoryItems()
}

function getSelectedCategory() //lấy những item đã được chọn trước đó
{
	return Array.from(productTableEditor.querySelectorAll('.category-items')).map(c => c.value).filter(v => v !== '')
}

function updateAllCategoryItems()
{
	console.log('da thay doi')
	const selectedCategories = getSelectedCategory()
	productTableEditor.tBodies[0].querySelectorAll('select').forEach(select => {
		Array.from(select.options).forEach(option => {
			console.log(select.options)
			console.log(option.value)
			if (option.value == "") return
			option.disabled = selectedCategories.includes(option.value) && select.value != option.value
		})
	})
}