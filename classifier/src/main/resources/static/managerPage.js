function getParameterByName(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

// rs 값을 URL 파라미터에서 가져옴
var message = getParameterByName('rs');
console.log('메시지:', message); // 메시지 로그
if (message) { // message가 존재할 경우
    alert(message); // 알림 표시
}

function cabuttons() {


    var formData = new FormData();
    formData.append("name", document.getElementById("caInput").value);

    fetch('/admin/add', {
        method: 'POST',
        body: formData
    })
        .then(rs=>{
            if(rs.redirected){
                window.location.href=rs.url;
            }
        })

        .catch(error => {
            console.error('Error:', error);
        });
}