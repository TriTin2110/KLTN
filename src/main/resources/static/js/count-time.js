function count(timeout) { 
        let content = document.getElementById('content');
        setInterval(() => {
                content.innerText = timeout
                timeout -=1
        }, 1000);
        setTimeout(() => {
                window.location.href = '/';
        }, (timeout + 1) * 1000);
}