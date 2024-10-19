document.getElementById("review").style.display = "none";
if(/*[[${rs}]]*/ ''){
    alert(/*[[${rs}]]*/ '')
}
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
function uploadImage() {


    var formData = new FormData();
    formData.append("file", document.getElementById("fileInput").files[0]);

    fetch('/classify', {
        method: 'POST',
        body: formData
    })
        .then(response => response.text())
        .then(result => {
            document.getElementById("result").innerHTML = "Result: " + result;
            if (result){
                document.getElementById("review").style.display = "block"; // review div를 보이도록 설정
            } else {
                document.getElementById("review").style.display = "none"; // review div를 숨김
            }
        })


        .catch(error => {
            console.error('Error:', error);
        });
}

function editImage() {


    var formData = new FormData();
    formData.append("file", document.getElementById("fileInput").files[0]);
    formData.append("text", document.getElementById("textError").value);

    fetch('/edite', {
        method: 'POST',
        body: formData
    })
        .then(rs=>{
            if(rs.redirected){
                alert("오류수정 전송완료")
                window.location.href=rs.url;
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}



function UploadButton(event){
    const img=event.target.files[0];
    if(img){
        const reader = new FileReader(); // FileReader 객체 생성

        reader.onload = function(e) {
            const imgElement = document.getElementById("img_preview");
            imgElement.src = e.target.result; // 미리보기 이미지에 src 설정
            imgElement.style.display = "block"; // 이미지 보이도록 설정
        }

        reader.readAsDataURL(img); // 파일을 데이터 URL로 읽기
    }


    const fileInput = document.getElementById("fileInput");
    const uploadButton = document.getElementById("uploadButton");

    // 파일이 선택되면 버튼 활성화, 아니면 비활성화
    uploadButton.disabled = !fileInput.files.length;
}

function editButton(){
    const text = document.getElementById("textError");
    const submitError = document.getElementById("submitError");

    submitError.disabled=! text.value
}