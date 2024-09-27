document.addEventListener("DOMContentLoaded", function () {
    var modal = document.getElementById("myModal");
    var btn = document.getElementById("previewBtn");
    var spanClose = document.getElementsByClassName("close")[0];
    var spanDownload = document.getElementsByClassName("download")[0];
    const { jsPDF } = window.jspdf;  // jsPDF 모듈 가져오기

    btn.onclick = function () {
        var endDt = document.getElementById("endDt").value;
        var startDt = document.getElementById("startDt").value;
        // AJAX 요청 생성
        var xhr = new XMLHttpRequest();
        var url = '/getPreview?startDt=' + encodeURIComponent(formatDateToYYYYMMDD(startDt)) + '&endDt=' + encodeURIComponent(formatDateToYYYYMMDD(endDt));
        console.log(url)
        xhr.open('GET', url, true);  // 서블릿 URL로 요청
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // 서버로부터 받은 JSP 콘텐츠를 모달에 표시
                document.getElementById("modalContent").innerHTML = xhr.responseText;
                modal.style.display = "block";
            }
        };
        xhr.send();
    };

    spanClose.onclick = function () {
        modal.style.display = "none";
    };

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };

    spanDownload.onclick = function () {
        var content = document.getElementById("modalContent");
        html2canvas(content, {
            scale: 2, // 해상도를 높여 품질 개선
            useCORS: true, // 외부 리소스 로드 허용
            logging: true // 디버깅 활성화
        }).then(canvas => {
            const imgData = canvas.toDataURL('image/png');
            const doc = new jsPDF('p', 'mm', 'a4');

            var margin = 10; // 출력 페이지 여백설정
            var imgWidth = 195;
            var pageHeight = 295;

            var imgHeight = canvas.height * imgWidth / canvas.width;
            var heightLeft = imgHeight;
            var position = margin;

            doc.addImage(imgData, 'JPEG', margin, position, imgWidth, imgHeight);
            heightLeft -= pageHeight;

            while (heightLeft >= 0) {
                position = heightLeft - imgHeight;
                doc.addPage();
                doc.addImage(imgData, 'JPEG', margin, position + 10, imgWidth, imgHeight);
                heightLeft -= pageHeight;
            }


            doc.save('download.pdf');
        });
    };
});
function formatDateToYYYYMMDD(dateString) {
    var date = new Date(dateString);
    var year = date.getFullYear();
    var month = ('0' + (date.getMonth() + 1)).slice(-2);
    var day = ('0' + date.getDate()).slice(-2);
    return year + month + day;
}