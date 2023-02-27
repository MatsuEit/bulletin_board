<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actPos" value="${ForwardConst.ACT_POS.getValue()}" />
<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commOut" value="${ForwardConst.CMD_LOGOUT.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title><c:out value="掲示板" /></title>
<link rel="stylesheet" href="<c:url value='/css/reset.css' />">
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <div id="header_menu">
                <h1>
                    <a href="<c:url value='/?action=${actTop}&command=${commIdx}' />">掲示板</a>
                </h1>
                &nbsp;&nbsp;&nbsp;
            </div>
            <c:choose>
                <c:when test="${sessionScope.login_post != null}">
                    <div id="post_name">
                        <c:out value="${sessionScope.login_post.name}" />
                        &nbsp;さん&nbsp;&nbsp;&nbsp; <a href="<c:url value='?action=${actAuth}&command=${commOut}' />">ログアウト</a>
                    </div>
                </c:when>
                <c:when test="${sessionScope.login_post == null}">
                    <div id="post_new">
                        <a href="<c:url value='?action=${actPos}&command=${commNew}' />">アカウント作成</a>
                    </div>
                </c:when>
            </c:choose>
        </div>
        <div id="content">${param.content}</div>
        <div id="footer">by Eichi Matsui.</div>
    </div>
</body>
</html>