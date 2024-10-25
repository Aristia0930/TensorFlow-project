function getParameterByName(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

// rs 값을 URL 파라미터에서 가져옴
var message = getParameterByName('rs');
console.log('메시지:', message); // 메시지 로그
if (message==="false"){
    message="카데고리 추가 실패"
}
if (message==="success"){
    message="카데고리 추가 성공"
}

if (message) { // message가 존재할 경우
    alert(message); // 알림 표시
}

function cabuttons() {


    var formData = new FormData();
    formData.append("name", document.getElementById("caInput").value);

    fetch('/admin/add', {
        method: 'POST',
        body: formData
    })  .then(response => response.text())
        .then(text => {
            if (text === "success") {
                alert("카테고리 추가 성공")
                // Reload the current page
                window.location.reload();
            } else {
                alert("카테고리 추가 실패")
                console.error('Category creation failed:', text);
            }
        })

        .catch(error => {
            console.error('Error:', error);
        });
}

function showEditInput(id) {
    console.log(id)
    // 수정 버튼 숨기고, 입력 필드와 저장 버튼 표시
    document.getElementById(`name-${id}`).style.display = 'none';
    document.getElementById(`input-${id}`).style.display = 'inline';
    document.getElementById(`edit-btn-${id}`).style.display = 'none';
    document.getElementById(`save-btn-${id}`).style.display = 'inline';
}


function deleteCategory(myname,) {

    var formData = new FormData();
    formData.append("name", myname);

    fetch('/admin/delete', {
        method: 'DELETE',
        body: formData
    })
        .then(response => response.text())
        .then(result => {
            alert("삭제완료")
            window.location.href = "http://localhost:8080/admin"; // 특정 주소로 리다이렉션
        })
        .catch(error => console.error('Error:', error));
}


function saveEdit(myname,myid) {
    const newName = document.getElementById(`input-${myid}`).value;
    console.log(myname)
    // PATCH 요청을 통해 새로운 이름 전송
    var formData = new FormData();
    formData.append("name", myname);
    formData.append("newName", newName);

    fetch('/admin/edit', {
        method: 'PATCH',
        body: formData
    })
        .then(response => response.text())
        .then(result => {
            if (result==="success"){
                alert("카테고리 이름 수정 완료")
                window.location.reload(); // 페이지 새로고침
            }
            else{
                alert("카테고리 이름 수정 실패")
                window.location.reload(); // 페이지 새로고침
            }

        })
        .catch(error => console.error('Error:', error));
}
