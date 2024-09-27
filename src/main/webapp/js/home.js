function searchArticle(){
    var startDate = document.getElementById("start-date").value
    var endDate = document.getElementById("end-date").value
    var title = document.getElementById("searchTitle").value
    var content = document.getElementById("articleContents").value
    var branch = document.getElementById("searchBranch").value
    var data = {
        startDate: startDate,
        endDate: endDate,
        title: title,
        content: content,
        branch: branch
    };

    fetch('/searchArticle', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.text())
        .then(data => {
            alert(data); // 서버 응답을 팝업창으로 표시
        })
        .catch(error => {
            alert('파일 업로드 중 오류가 발생했습니다.');
            console.error('Error:', error);
        }).finally(()=>{
    });
}

function closeModal(){
    var modal = document.getElementById("myModal");

    modal.style.display = "none";

}
document.addEventListener('DOMContentLoaded', (event) => {
    var today = new Date();
    // 날짜 형식을 YYYY-MM-DD로 맞추기
    var yyyy = today.getFullYear();
    var mm = String(today.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더함
    var dd = String(today.getDate()).padStart(2, '0');
    var formattedToday = yyyy + '-' + mm + '-' + dd;

    document.getElementById('end-date').value = formattedToday;
    document.getElementById('start-date').value = formattedToday;

    // Get the modal
    var modal = document.getElementById("myModal");

    // Get the button that opens the modal
    var btn = document.querySelector(".register-button");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal
    btn.onclick = function() {
        modal.style.display = "block";
    }

    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }

    // Tab functionality
    var tabs = document.getElementsByClassName("tablinks");
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].addEventListener("click", function(evt) {
            openTab(evt, this.getAttribute("data-tab"));
        });
    }
});

function openTab(evt, tabName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}
var isSubmitting = false;

function articleSubmit() {
    if (isSubmitting) {
        return false;
    }

    var form = document.getElementById('registrationForm');
    var formData = new FormData(form)

    // 유효성 검사
    var noticeTitle = formData.get('noticeTitle');
    var noticeDetail = formData.get('noticeDetail');
    var noticeState = formData.get('noticeState');
    var noticeType = formData.get('noticeType');

    noticeDetail = encodeSpecialChars(noticeDetail);
    noticeDetail = noticeDetail.replace(/'/g, "\\'");

    console.log(noticeDetail);
    // noticeDetail = noticeDetail.replace(/'/g, "\'");
    // console.log(noticeDetail);

    if (!noticeType || !noticeTitle ||! noticeDetail||!noticeState) {
        alert('빈칸을 채워주세요');
        return false;
    }
    if (noticeDetail.length > 499){
        var len = noticeDetail.length
        alert(`500자를 초과 하였습니다.` + len+`자 입력되었습니다.`);

        return false;
    }

    isSubmitting = true;


    fetch('/uploadArticle', {
        method: 'POST',
        body: formData
    })
        .then(response => response.text())
        .then(data => {
            alert(data); // 서버 응답을 팝업창으로 표시
        })
        .catch(error => {
            alert('파일 업로드 중 오류가 발생했습니다.');
            console.error('Error:', error);
        }).finally(()=>{
        isSubmitting = false;
    });
    return false;

}
function articleDetail(element) {
    var snValue = element.querySelector('.sn').value;
    var url = "/getArticle?sn=" + encodeURIComponent(snValue);
    window.location.href = url;
}


function encodeSpecialChars(str) {
    return str.split('').map(char => {
        // 한글이나 영문자, 숫자는 그대로 유지
        if (/[\w가-힣]/.test(char)) {
            return char;
        }
        // 나머지 특수 문자는 인코딩
        return encodeURIComponent(char);
    }).join('');
}