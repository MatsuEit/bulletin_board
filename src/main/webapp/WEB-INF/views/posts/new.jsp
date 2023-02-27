<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="action" value="${ForwardConst.ACT_POS.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>新規登録ページ</h2>

        <form method="POST" action="<c:url value='?action=${action}&command=${commCrt}' />">
            <c:import url="_form.jsp" />
        </form>
        <p><a href="<c:url value='?action=${actTop}&command=${commIdx}' />">ログイン画面に戻る</a></p>
    </c:param>
</c:import>