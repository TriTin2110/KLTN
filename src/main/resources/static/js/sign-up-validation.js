document.addEventListener("DOMContentLoaded", function () {
  const password = document.getElementById("password");
  const confirm = document.getElementById("confirmPassword");
  const form = password.closest("form");
  const confirmError = confirm.parentElement.querySelector(".invalid-field");

  function validatePasswordMatch() {
    if (confirm.value.trim() === "") {
      confirmError.style.display = "none";
      confirm.style.borderColor = "#ccc";
      return;
    }

    if (password.value !== confirm.value) {
      confirmError.style.display = "block";
      confirm.style.borderColor = "red";
    } else {
      confirmError.style.display = "none";
      confirm.style.borderColor = "green";
    }
  }

  // Gọi khi gõ
  password.addEventListener("input", validatePasswordMatch);
  confirm.addEventListener("input", validatePasswordMatch);

  // Ngăn submit nếu mật khẩu sai
  form.addEventListener("submit", function (e) {
    if (password.value !== confirm.value) {
      e.preventDefault(); // ❗ Dừng gửi form
      confirmError.style.display = "block";
      confirm.style.borderColor = "red";
      alert("Mật khẩu xác nhận không trùng khớp!");
    }
  });
});











function checkLength() {
	let username = document.getElementById('username').value
	let password = document.getElementById('password').value
	let invalidUsername = document.getElementById('username').previousElementSibling
	let invalidPassword = document.getElementById('password').previousElementSibling
	invalidUsername.style.display = 'none'
	invalidPassword.style.display = 'none'
	if (!username || username.length < 4) {
		invalidUsername.style.display = 'block'
		return false;
	}
	if (!password || password.length < 4) {
		invalidPassword.style.display = 'block'
		return false;
	}
	return true;
}

function checkPhoneNumber() {
	let phoneNumber = document.getElementById('number').value
	let reg = new RegExp("^(032|033|034|035|036|037|038|039|096|097|098|086|083|084|085|081|082|088|091|094|070|079|077|076|078|090|093|089|056|058|092|059|099)[0-9]{7}$");
	if(!phoneNumber || phoneNumber.length !== 10 || reg.exec(phoneNumber) == null)
	{
		let invalidPhoneNumber = document.getElementById('number').previousElementSibling
		invalidPhoneNumber.style.display = 'block'
		return false;
	}
	return true;
}

function checkValidation()
{
	return checkLength() && checkPhoneNumber();
}
