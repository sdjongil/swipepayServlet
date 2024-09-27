<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.10.377/pdf.min.js"></script>
    <link rel="stylesheet" href="../css/articleDetail.css">
    <script src="../js/articleDetail.js"></script>
    <title>Document</title>
</head>
<body>
<div class="container">
    <header class="header">
        <button class="back-button" style="color: white" onclick="backArticle()">&larr;</button>
        <h1 style="color: white">공지사항</h1>
    </header>

    <section class="notices">
            <div class="notice">
                <h3 class="title">${article.noticeTitle}</h3>
                <p class="date" data-date="${article.creatDt}"></p>
            </div>
    </section>

    <p>${article.noticeDetail}</p>
    <img id="" src="${pageContext.request.contextPath}/img/${article.noticeImg}" alt="${article.noticeImg}" style="width: 80%;" />

</div>
</body>
