const ws = new WebSocket('ws://localhost:8080/chat') //Tạo websocket

let chatButton = document.getElementById('chat-box-button')
let chatFrame = document.getElementById('chat-box-frame')
let inputField = document.getElementById('input-panel')
let content = document.getElementById('content')
let observer = new MutationObserver(() => scrollToLastMessage()); //MutationObserver dùng để theo dõi sự thay đổi trong DOM
observer.observe(content, { childList: true });//Chỉ theo dõi content và theo dõi thay đổi của các thẻ con trong content
inputField.addEventListener('keyup', (e) =>{
	if(e.code == 'Enter')
		showCustomerText()
})

document.addEventListener('DOMContentLoaded', () =>{
	scrollToLastMessage()
})

ws.onopen = function(){ //Sau khi kết nối thành công
	console.log('Bắt đầu')
}

ws.onmessage = function (res){ //Lấy phản hồi từ server gửi lên
	console.log(res.data)
}

function scrollToLastMessage()
{
	content.scrollTo({
	top: content.scrollHeight, //cuộn đến chiều cao của scroll có nghĩa là cuộn đến vị trí cuối cùng
	behavior: "smooth"
})
}

function showCustomerText()
{
	let inputValue = inputField.value
	const reg = /^\s+$/ //Kiểm tra chuỗi có chứa toàn khoảng trắng
	if(!inputValue || inputValue.length == 0 || reg.test(inputValue)) return
	let div = document.createElement('div')
	div.className = 'message message-customer bg-primary'
	div.textContent = inputValue
	content.append(div)
	inputField.value = ''
	ws.send(inputValue)
}

function toggleChatFrame(){
	if(chatFrame.classList.contains('active'))
	{
		chatFrame.classList.remove('active')
		chatButton.classList.remove('close')
	}
	else{
		chatFrame.classList.add('active')
		chatButton.classList.add('close')
	}
}