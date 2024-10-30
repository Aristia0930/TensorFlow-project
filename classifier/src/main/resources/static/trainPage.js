function acceptButton(id,path,naem) {

    const category = document.getElementById(`select-${id}`).value;// PATCH 요청을 통해 새로운 이름 전송
    console.log(category)
    var formData = new FormData();
    formData.append("id", id);
    formData.append("path", path);
    formData.append("name", naem);
    formData.append("categoryName", category);

    fetch('/admin/train/add', {
        method: 'PATCH',
        body: formData
    })
        .then(response => response.text())
        .then(result => {
            if (result==="success"){
                alert("학습 파일 업데이트 성공")
                window.location.reload(); // 페이지 새로고침
            }
            else{
                alert("학습 파일 업데이트 실패")
                window.location.reload(); // 페이지 새로고침
            }

        })
        .catch(error => console.error('Error:', error));
}

function refuseButton(id) {



    var formData = new FormData();
    formData.append("id", id);

    fetch('/admin/train/refuse', {
        method: 'PATCH',
        body: formData
    })
        .then(response => response.text())
        .then(result => {
            if (result==="success"){
                alert("삭제완료")
                window.location.reload(); // 페이지 새로고침
            }
            else{
                alert("삭제실패")
                window.location.reload(); // 페이지 새로고침
            }

        })
        .catch(error => console.error('Error:', error));
}