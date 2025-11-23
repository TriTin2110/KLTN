let chatButton = document.getElementById('chat-box-button')
let chatFrame = document.getElementById('chat-box-frame')
let inputField = document.getElementById('input-panel')
let content = document.getElementById('content')
let observer = new MutationObserver(() => scrollToLastMessage()); //MutationObserver dùng để theo dõi sự thay đổi trong DOM
observer.observe(content, { childList: true });//Chỉ theo dõi content và theo dõi thay đổi của các thẻ con trong content
inputField.addEventListener('keyup', (e) => {
	if (e.code == 'Enter')
		showCustomerText()
})



document.addEventListener('DOMContentLoaded', () => {
	scrollToLastMessage()
})
if (role != 'user') {
	ws.onmessage = function(res) { //Lấy phản hồi từ server gửi lên
		const data = JSON.parse(res.data)
		console.log(data)
		const chatRoomId = data.chatRoomId
		const currentChatRoomId = data.currentChatRoomId
		const message = data.message
		const userId = data.userId
		if(currentChatRoomId && chatRoomId == currentChatRoomId){
			let div = document.createElement('div')
			div.className = "message message-employee bg-secondary"
			div.textContent = message
			content.append(div)
		}
		const chatList = document.querySelector('.chat-list-panel')
		const chatItem = chatList.querySelector(`.chat-list[data-id='${chatRoomId}']`)
		let incomeMessage = chatItem.querySelector('.income-message')
		
		if(chatItem)
		{
			
			incomeMessage.textContent = userId+": "+ message
			chatList.prepend(chatItem)
		}
		
		let circle = chatItem.querySelector('.circle')
		if(currentChatRoomId != chatRoomId)
			circle.style.display = 'inline'
	}
}

async function loadContentForUser(id) {
	let res = await fetch(`/chat/${id}`)
	let messages = await res.json()
	content.innerHTML = messages.map(m => {

		if (m.roleId == role) {
			return `
			<div class="message message-customer bg-primary">
				${m.content}
			</div>`
		} else {
			return `
			<div class="message message-employee bg-secondary">
				${m.content}
			</div>`
		}
	}).join('')
}

async function loadContentForEmployee(id) {
	ws.send(JSON.stringify({
		type: "join-room",
		chatId: id
	}))
	let chatPanel = document.getElementById('chat-panel')
	chatPanel.style.display = 'block'
	let res = await fetch(`/chat/${id}`)
	let messages = await res.json()
	content.innerHTML = messages.map(m => {
		if (m.roleId == role) {
			return `
			<div class="message message-customer bg-primary">
				${m.content}
			</div>`
		} else {
			return `
			<div class="message message-employee bg-secondary">
				${m.content}
			</div>`
		}
	}).join('')
	const chatList = document.querySelector('.chat-list-panel')
		const chatItem = chatList.querySelector(`.chat-list[data-id='${id}']`)
		let circle = chatItem.querySelector('.circle')
		circle.style.display= 'none'
}

function scrollToLastMessage() {
	content.scrollTo({
		top: content.scrollHeight, //cuộn đến chiều cao của scroll có nghĩa là cuộn đến vị trí cuối cùng
		behavior: "smooth"
	})
}

function showCustomerText() {
	let inputValue = inputField.value
	const reg = /^\s+$/ //Kiểm tra chuỗi có chứa toàn khoảng trắng
	if (!inputValue || inputValue.length == 0 || reg.test(inputValue)) return
	let div = document.createElement('div')
	div.className = 'message message-customer bg-primary'
	div.textContent = inputValue
	content.append(div)
	inputField.value = ''
	ws.send(inputValue)
}

function toggleChatFrame(idRoom, userId) {
	if (chatFrame.classList.contains('active')) //Tắt khung chat
	{
		ws.close()
		chatFrame.classList.remove('active')
		chatButton.classList.remove('close')//Hiển thị widget
	}
	else {
		openConnection(`ws://localhost:8080/chat?role=user&userId=${userId}`, idRoom)
		chatFrame.classList.add('active')
		chatButton.classList.add('close')//Ẩn widget
	}
}

function changeConversation(e)
{
	ws.close()
	content.innerHTML = ''
	let button = e
	let currentConversation = button.dataset.conversation
	let userId = button.dataset.username
	let idRoom = button.dataset.id
	let conversationStatement
	if('employee' === currentConversation)//Chuyển sang chat bot
	{
		button.innerHTML = "Chuyển sang <b>Chat trực tiếp</b>"
		conversationStatement = 'chat bot'
		//khi kết nối với chat bot thì sẽ không truyền id (do cuộc trò chuyện với chat bot sẽ không được lưu trên DB)
		openConnection(`ws://localhost:8080/chat-bot?role=user&userId=${userId}`)
	}
	else{//Chuyển sang chat với nhân viên
		button.innerHTML = "Chuyển sang <b>Chat bot</b>"
		conversationStatement = 'employee'
		openConnection(`ws://localhost:8080/chat?role=user&userId=${userId}`, idRoom)
	}
	button.dataset.conversation = conversationStatement
}

function openConnection(connection, idRoom)
{
	ws = new WebSocket(connection) //Tạo websocket
		ws.onopen = function() { //Sau khi kết nối thành công
			if (role === 'user' && idRoom)
				loadContentForUser(idRoom)
		}
		ws.onmessage = function(res) { //Lấy phản hồi từ server gửi lên
			let data = JSON.parse(res.data)
			console.log(data)
			let div = document.createElement('div')
			div.className = "message message-employee bg-secondary"
			div.innerHTML = data.message
			content.append(div)
		}
}