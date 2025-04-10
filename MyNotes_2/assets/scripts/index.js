const $button = document.body.querySelector(':scope > .submit');

const xhr = new XMLHttpRequest();

xhr.onreadystatechange = (e) => {
    if (xhr.readyState !== XMLHttpRequest.DONE) {
        return;
    }
    console.log(xhr.responseText);
    if (xhr.status < 200 || xhr.status >= 400) {
        alert('응답 요청에 실패하였습니다.');
        return;
    }
}

$button.onclick = () => {
    xhr.open('GET', 'https://www.daegufood.go.kr/kor/api/Alley.html?mode=json', true);
    xhr.setRequestHeader("", "");
    xhr.send();
}