<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
            <c:if test="${topicError}">
            <div id="flush_error">
                トピック内容を入力してください。
            </div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>トピック 一覧</h2>
        <table id="topic_list">
            <tbody>
                <tr>
                    <th class="topic_title">タイトル</th>
                    <th class="topic_name">投稿者</th>
                    <th class="topic_date">日付</th>
                </tr>
                <c:forEach var="topic" items="${topics}"
                    varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="topic_title">${topic.title}</td>
                        <td class="topic_name"><c:out
                                value="${topic.post.name}" /></td>
                        <fmt:parseDate value="${topic.createdAt}"
                            pattern="yyyy-MM-dd'T'HH:mm:ss"
                            var="createDay" type="date" />
                        <td class="topic_date"><fmt:formatDate
                                value='${createDay}'
                                pattern='yyyy-MM-dd HH:mm:ss' /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
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