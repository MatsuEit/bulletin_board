<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="action" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOPI.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${topicError}">
            <div id="flush_error">トピック内容を入力してください。</div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>トピック 一覧</h2>

                <c:forEach var="topic" items="${topics}" varStatus="status">
            <div class="row${status.count % 2}">
                <div class="meta">
                    <fmt:parseDate value="${topic.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <span class="topic_date"><fmt:formatDate value='${createDay}' pattern='yyyy-MM-dd HH:mm:ss' /></span> <span class="topic_name"><c:out value="投稿者：${topic.post.name}" /></span>
                </div>
                <div class="title_bg">
                    <span class="topic_title"> <a href="<c:url value='?action=${actTop}&command=${commShow}&id=${topic.id}' />">${topic.title}</a>
                    </span> <br />
                    <br />
                </div>
            </div>
        </c:forEach>
<img src="./img/bg-header.jpg" alt="サンプル写真">
        <br />
        <div id="pagination">
            （全 ${topics_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((topics_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${action}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <form method="POST" action="<c:url value='?action=${action}&command=${commCrt}' />">
            <c:import url="../topics/_form.jsp" />
        </form>
    </c:param>
</c:import>