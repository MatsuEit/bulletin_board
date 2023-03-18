<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="action" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOPI.getValue()}" />
<c:set var="commSho" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${commentError}">
            <div id="flush_error">コメント内容を入力してください。</div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <h2>
            <c:out value="${topic.title}" />
        </h2>

        <c:forEach var="comment" items="${comments}" varStatus="status">
            <div class="row${status.count % 2}">
                <div class="meta">
                    <fmt:parseDate value="${comment.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <span class="comment_date"><fmt:formatDate value='${createDay}' pattern='yyyy-MM-dd HH:mm:ss' /></span> <span class="comment_name"><c:out value="投稿者：${comment.post.name}" /></span>
                </div>
                <div class="message">
                    <span class="comment_title"> <c:out value="${comment.title}" />
                    </span> <br />
                    <br />
                </div>
            </div>
        </c:forEach>

        <br />
        <div id="pagination">
            （全 ${comments_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((comments_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actTop}&command=${commSho}&id=${topic.id}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <br />
        <br />
        <form method="POST" action="<c:url value='?action=${actTop}&command=${commCrt}&id=${topic.id}' />">
            <c:import url="../comments/_form.jsp" />
        </form>
        <p><a href="<c:url value='?action=${action}&command=${commIdx}' />">トップページに戻る</a></p>
    </c:param>
</c:import>