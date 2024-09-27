<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2024-08-09
  Time: 오전 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="../js/excel.js"></script>
    <link rel="stylesheet" href="../css/excel.css">

    <title>Title</title>
</head>
<body>
    <p>엑셀 다운로드 청크</p>
    <label for="startDt">시작 날짜:</label>
    <input type="date" id="startDt" name="start-date" value="2021-01-01">

    <label for="endDt">종료 날짜:</label>
    <input type="date" id="endDt" name="end-date" value="2021-03-31">
    <button id="downloadButton">Download</button>

    <div id="overlay"></div>
    <div id="loadingIndicator"></div>
    <div id="progressText"></div>
</body>
</html>
