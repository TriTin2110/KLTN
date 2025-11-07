function addComment(id) {
	let rating = document.querySelector('input[name="rating"]:checked').value;
	let content = document.querySelector('textarea[name="content"]').value;

	fetch(`/comment/${id}`, {
		headers: { 'Content-Type': 'application/json' },
		method: 'POST',
		body: JSON.stringify({ content: content, rating: rating })
	}).then(res => res.json()).then(data => {
		if (data.success) {
			const comment = data.comment;
			const userNameComment = data.userNameComment;
			const [year, month, day, hour, minute, second, nano] = comment.datePost;
			let date = new Date(year, month, day, hour, minute, second, nano / 1000000);
			let li = document.createElement('li')
			li.innerHTML = `<div class="d-flex align-items-start gap-2">
						<div
							class="avatar rounded-circle bg-secondary text-white d-flex justify-content-center align-items-center"
							style="width: 40px; height: 40px;">
							<span
								>${userNameComment.charAt(0).toUpperCase()}</span>
						</div>
						<div style="flex: 1;">
							<strong>${userNameComment}</strong>
							<small class="text-muted ms-2">${formatDate(date)}</small>

							<!-- Hiển thị sao -->
							<div>
								${formatRating(rating).innerHTML}
							</div>

							<p class="mt-1 mb-0">${comment.content}</p>
						</div>
					</div>`
			li.classList = 'border-bottom py-2'
			let ul = document.getElementById('comment-list')
			ul.append(li)
		}
	})
}

function formatDate(date) {
	const hours = String(date.getHours()).padStart(2, '0');//Thêm vào đầu chuỗi kí tự '0' nếu độ dài chuỗi < 2
	const minutes = String(date.getMinutes()).padStart(2, '0');
	const d = String(date.getDate()).padStart(2, '0');
	const m = String(date.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
	const y = date.getFullYear();
	return `${d}/${m}/${y} ${hours}:${minutes}`;
}

function formatRating(rating) {
	let container = document.createElement('span')

	for (let i = 1; i <= 5; i++) {
		const star = document.createElement("i");
		if (i <= rating) {
			star.className = "fas fa-star text-warning"; // Sao vàng
		} else {
			star.className = "far fa-star text-secondary"; // Sao xám
		}
		container.appendChild(star);
	}
	return container
}