var events = events
let mainForm = document.getElementById("main-form")
let eventEditor = document.getElementById("coupon-editor")
	let eventListTable = document.getElementById("coupon-list-table")
	let eventBtn = document.getElementById("coupon-btn") 
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
		
function checkingDataBeforeSubmit()
{
	return checkDate()
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


function setupDate(select)
{
	let startDate = document.getElementById('startDateInput')
	let endDate = document.getElementById('endDateInput')
	let selectOption = select.value
	let event = events.find(e => e.name === selectOption)
	let score = document.getElementById('score')//đóng mở score tùy theo event
	if(event)
	{
		startDate.value = event.startDate
		document.querySelector("input[name='startAt']").value //2025-11-28

		endDate.value = event.endDate
		console.log(startDate.value)
		startDate.readOnly = true
		endDate.readOnly = true
		score.required = false
		score.disabled = true
	}else{
		startDate.readOnly = false
		endDate.readOnly = false
		score.required = true
		score.disabled = false
	}
	
}