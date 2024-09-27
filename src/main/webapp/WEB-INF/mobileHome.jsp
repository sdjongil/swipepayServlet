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
    <link rel="stylesheet" href="../css/mobileHome.css">
    <script src="../js/mobileHome.js"></script>
    <title>Document</title>
    <style>

    </style>
</head>
<body>
    <div class="container">
        <header class="header">
        <h1 style="color: white">공지사항</h1>
        </header>
        <section class="notices">
            <c:forEach var="article" items="${articles}">
                <div class="notice" onclick="articleDetail(this)">
                    <input type="hidden"  class="sn" value="${article.sn}">
                    <p class="title">${article.noticeTitle}</p>
                    <p class="date" data-date="${article.creatDt}"></p>
                </div>
            </c:forEach>
        </section>
    </div>
</body>
