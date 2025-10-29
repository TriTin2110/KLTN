let newsEditor = document.getElementById("news-editor")
	let newsListTable = document.getElementById("news-list-table")
	let newsBtn = document.getElementById("news-btn") 
	let mainForm = document.getElementById("main-form")
	let mainImageView = document.getElementById('main-image-view')
		function openEditor()
		{
			newsEditor.style.display = "block"; 
			newsListTable.style.display = "none";
			newsBtn.innerText = "Danh sách tin tức"
			newsBtn.className = "btn btn-primary"
			newsBtn.onclick= openNewsListTable
		}
	
		function openEditor()
		{
			newsEditor.style.display = "block"; 
			newsListTable.style.display = "none";
			newsBtn.innerText = "Danh sách tin tức"
			newsBtn.className = "btn btn-primary"
			newsBtn.onclick= openNewsListTable
			mainForm.action = "/news/insert"
			mainForm.reset()
			mainImageView.src =''
			window.editor.setData('')
		}

		function openNewsListTable(){
			newsEditor.style.display = "none"; 
			newsListTable.style.display = "block";
			newsBtn.innerText = "Thêm tin tức"
			newsBtn.className = "btn btn-success"
			newsBtn.onclick= openEditor
		}
		
function editNews(button)
{
	let name = button.dataset.name
	const news = newsList.find(n => n.name === name) //Kiểm tra news có tồn tại trong danh sách news bên list không 
	if(!news) return	
	openEditor()
	mainForm.action = "/news/update"
	let nameInput = mainForm.querySelector('#name')
	let shortDescription = mainForm.querySelector('#shortDescription')
	nameInput.value = news.name
	nameInput.disabled = true
	shortDescription.value = news.shortDescription
	window.editor.setData(news.content)
	mainImageView.src = "https://s3.cloudfly.vn/kltn/images/news/main-image/"+news.image
}

function changeMainImageView(input)
{
		const file = input.files[0] //Lấy file đầu tiên
		if(file)
		{
			const reader = new FileReader() //Đọc file từ local
			reader.onload = (e) =>{
				mainImageView.src = e.target.result //hiển thị ảnh
			}
			reader.readAsDataURL(file)
		}
}