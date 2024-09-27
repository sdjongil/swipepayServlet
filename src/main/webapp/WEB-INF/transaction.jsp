<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2024-08-01
  Time: 오전 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/transaction.css">

    <title>신용카드 PG 매출 거래내역 확인서</title>
</head>
<body>
<c:forEach var="detail" items="${transactionDto.details}" varStatus="status">
    <c:set var="totalNum" value="${totalNum + detail.transactionCount}" />
    <c:set var="totalAmount" value="${totalAmount + detail.transactionAmount}" />
</c:forEach>
<%
    // 현재 날짜 및 시간 가져오기
    Date now = new Date();
    // SimpleDateFormat을 사용하여 날짜 포맷 설정
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmm");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy년 M월 d일");

    // 포맷된 날짜 문자열
    String formattedDateTime = sdf1.format(now);
    String formattedDate = sdf2.format(now);
%>
<div class="container">
    <header>
        <p style="text-align: center">
            서울시 구로구 디지털로31길 53 이앤씨벤쳐드림타워5차 709호 TEL: 1666-6315 FAX: 02-2108-1224
        </p>
        <hr>

        <table style="width: 100%">
            <tbody style="width: 100%">
                <tr>
                    <th>문서번호 :</th>
                    <td style="display: flex; justify-content: space-between; ">
                        <span style="text-align: left;"><%= formattedDateTime %></span>
                        <span style="text-align: right;"><%= formattedDate %></span>
                    </td>
                </tr>
                <tr>
                    <th>수      신 :</th>
                    <td>${transactionDto.name}</td>
                </tr>
                <tr>
                    <th>발      신 :</th>
                    <td>㈜스와이프페이</td>
                </tr>
                <tr>
                    <th>참      조 :</th>
                    <td>“신용카드 PG 거래내역 확인서” 발급요청 관련부서</td>
                </tr>
                <tr>
                    <th>제      목 :</th>
                    <td>“신용카드 PG 거래내역 ” 발급요청“ 件</td>
                </tr>
            </tbody>
        </table>
        <hr>
    </header>

    <main>
        <section class="introduction">
            <p>1. 귀하의 무궁한 발전을 기원합니다.</p>
            <h5 style="margin: 2px;text-align: center">제목 : 신용카드 PG 매출 거래내역 확인서</h5>
        </section>

        <section class="details">
            <table>
                <tr>
                    <th>이름</th>
                    <th>사업자번호</th>
                    <th>거래건수</th>
                    <th>거래금액 합계</th>
                </tr>
                <tr>
                    <td>${transactionDto.name}</td>
                    <td>${transactionDto.businessNumber}</td>
                    <td>${totalNum} 건</td>
                    <td style="text-align: right">
                        <fmt:formatNumber value="${totalAmount}" pattern="#,###"/> 원
                    </td>
                </tr>
            </table>
        </section>

        <section class="summary">
            <table>
                <thead>
                    <tr>
                        <th>기관 명</th>
                        <th>거래내역(月)</th>
                        <th>거래건수</th>
                        <th>거래금액</th>
                    </tr>
                </thead>
                <c:forEach var="detail" items="${transactionDto.details}" varStatus="status">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${status.first}">
                                    신용카드
                                </c:when>
                                <c:otherwise>
                                    &nbsp;
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${detail.date}</td>
                        <td>${detail.transactionCount}건</td>
                        <td style="text-align: right">
                            <fmt:formatNumber value="${detail.transactionAmount}" pattern="#,###"/>원
                        </td>
                    </tr>
                </c:forEach>
                <tfoot>
                    <tr>
                        <td>매출  총합계</td>
                        <td>취소 건 제외</td>
                        <td>${totalNum} 건</td>
                        <td style="text-align: right">
                            <fmt:formatNumber value="${totalAmount}" pattern="#,###"/> 원
                        </td>
                    </tr>
                </tfoot>
            </table>
        </section>
    </main>

    <footer>
        <p style="text-align: center">상기 자료는 ㈜스와이프페이의 서비스 시스템을 이용하여 거래 된 내역이며, <br>
            국세청 전산에 결제대행신고로 제출된 자료입니다.(KSNET 결제대행 동일)</p>
        <p style="text-align: right">귀사의 발전과 번영을 기원합니다. 이상 끝.</p>
        <div style="height: 80px;  text-align: center; line-height: 80px;">
            <h2 style="display: inline-block; line-height: 20px; vertical-align: middle;">
                (주)스와이프페이 대표이사 전규남
                <span>
                    <img style="height: 55px; vertical-align: middle;" src="/img/auth.png" alt="인장"/>
                </span>
            </h2>
        </div>
    </footer>
</div>
</body>
</html>
