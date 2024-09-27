<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2024-08-02
  Time: 오전 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
    <script src="../js/predict.js"></script>
    <link rel="stylesheet" href="../css/predict.css">

    <title>Title</title>
    <style>

    </style>
</head>
<body>

<h2>미리보기</h2>

<!-- 버튼 클릭 시 모달 열림 -->
<label for="startDt">시작 날짜:</label>
<input type="date" id="startDt" name="start-date" value="2022-01-01">

<label for="endDt">종료 날짜:</label>
<input type="date" id="endDt" name="end-date" value="2022-12-31">

<button id="previewBtn">미리보기</button>

<!-- 모달 구조 -->
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <span class="download">PDF 다운로드</span>
        <div id="modalContent">Loading...</div>
    </div>
</div>



</body>
</html>