<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.10.377/pdf.min.js"></script>
  <link rel="stylesheet" href="../css/home.css">
  <script src="../js/home.js"></script>

  <title>Document</title>
</head>
<body>
<div class="container">
  <header>
    <h1>공지사항</h1>
    <p>전체/지사별 공지사항을 관리합니다.</p>
    <button id="downloadButton">Download</button>
    <div id="overlay"></div>
    <div id="loadingIndicator"></div>
    <div id="progressText"></div>

  </header>

  <section class="actions">
    <div class="filters">
      <label for="start-date">조회:</label>
      <input type="date" id="start-date" value="2024-06-26">
      <input type="date" id="end-date" value="2024-06-26">
      <input type="text" id="searchTitle" placeholder="제목">
      <input type="text" id="searchContent" placeholder="내용">
      <select id="searchBranch">
        <option value="all">지사 전체</option>
        <c:forEach var="branch" items="${branchs}">
          <option value="${branch}">${branch}</option>
        </c:forEach>
      </select>
      <button class="search-button" onclick="searchArticle()">조회</button>
      <div class="spacer"></div>
      <button class="register-button">등록</button>
    </div>
  </section>
  <table>
    <thead>
    <tr>
      <th>제목</th>
      <th>내용</th>
      <th>첨부 이미지</th>
      <th>미리 보기</th>
      <th>게시 여부</th>
      <th>적용 지사</th>
      <th>구분</th>
      <th>등록일</th>
      <th>수정</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="article" items="${articles}">
      <tr>
        <td>${article.noticeTitle}</td>
        <td>${article.noticeDetail}</td>
        <td>
          <c:choose>
            <c:when test="${empty article.noticeImg}">
              <img src="${pageContext.request.contextPath}/img/default.jpg" alt="등록 이미지 없음" style="height: 100px;" /><br />
            </c:when>
            <c:otherwise>
              <img src="${pageContext.request.contextPath}/img/${article.noticeImg}" alt="${article.noticeImg}" style="height: 100px;" /><br />
            </c:otherwise>
          </c:choose>
        </td>
        <td>
          <button onclick="articleDetail(this)">미리보기
            <input type="hidden"  class="sn" value="${article.sn}">

          </button>

        </td>
        <td>
          <c:choose>
            <c:when test="${article.noticeState == '0'}">
              대기
            </c:when>
            <c:when test="${article.noticeState == '1'}">
              진행
            </c:when>
            <c:otherwise>
              알 수 없음
            </c:otherwise>
          </c:choose>
        </td>
        <td>
          <c:forEach var="tag" items="${article.noticePsint}">
            ${tag} <br />
          </c:forEach>
        </td>
        <td>
          <c:choose>
            <c:when test="${article.noticeType == '0'}">
              중요
            </c:when>
            <c:when test="${article.noticeType == '1'}">
              일반
            </c:when>
            <c:otherwise>
              알 수 없음
            </c:otherwise>
          </c:choose>
        </td>
        <td>${article.creatDt}</td>
        <td><button>수정</button></td>
      </tr>
    </c:forEach>

    </tbody>
  </table>

  <!-- Modal -->
  <div id="myModal" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>
      <h2>공지사항 등록</h2>
      <div id="basic" class="tabcontent">
        <form id="registrationForm" action="/uploadArticle" method="POST" enctype="multipart/form-data" accept-charset="UTF-8">
          <div>
            <label for="articleTitle">제목</label>
            <input type="text" id="articleTitle" name="noticeTitle">
          </div>
          <div>
            <label for="articleContents" >내용</label>
            <textarea id="articleContents" name="noticeDetail" placeholder="최대 500 글자까지 가능합니다."></textarea>
          </div>
          <div>
            <label>이미지</label>
            <input style="margin-left: 20px" type="file" name="noticeImg" id="noticeImg" />
          </div>
          <div class="custom-radio-group">
            <label>게시 여부</label>
            <label class="custom-radio-btn">
              <input type="radio" name="noticeState" value="1">
              <span style="margin-left: 20px" class="custom-radio-box">Y</span>
            </label>
            <label class="custom-radio-btn">
              <input type="radio" name="noticeState" value="0">
              <span class="custom-radio-box">N</span>
            </label>
          </div>
          <div class="custom-radio-group">
            <label>구분</label>
            <label class="custom-radio-btn">
              <input type="radio" name="noticeType" value="1">
              <span style="margin-left: 20px" class="custom-radio-box">일반</span>
            </label>
            <label class="custom-radio-btn">
              <input type="radio" name="noticeType" value="0">
              <span class="custom-radio-box">중요</span>
            </label>
          </div>
          <div>
            <label>적용 지사</label>
            <label style="margin-left: 20px">
              <select name="noticePsint">
                <option value="all">지사 전체</option>
                <c:forEach var="branch" items="${branchs}">
                  <option value="${branch}">${branch}</option>
                </c:forEach>
              </select>
            </label>
          </div>
          <div>
            <div id="saveAndCancelButtons">
              <button type="button" onclick="articleSubmit(this)" class="save-button">저장</button>
              <button type="button" class="cancel-button" onclick="closeModal()">취소</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

</body>
