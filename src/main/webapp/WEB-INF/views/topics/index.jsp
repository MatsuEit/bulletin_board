<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="models.Topic" %>

<c:set var="actTop" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>トピック 一覧</h2>
        <table id="topic_list">
            <tbody>
                <tr>
                    <th class="topic_name">名前</th>
                    <th class="topic_date">日付</th>
                    <th class="topic_title">タイトル</th>
                </tr>
                <c:forEach var="topic" items="${topics}" varStatus="status">
                    <fmt:parseDate value="${topicsCreatedAtDesc}" pattern="yyyy-MM-dd HH:mm:ss" var="topicDateTime" type="date" />

                    <tr class="row${status.count % 2}">
                        <td class="topic_name"><c:out value="${topic.post.name}" /></td>
                        <td class="topic_date"><fmt:formatDate value='${topicDateTime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
                        <td class="topic_title">${topic.title}</td>
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
                        <a href="<c:url value='?action=${actTop}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <form method="post" action="<c:url value='?action=${actTop}&command=${commNew}' />">
            <input type="text" name="title"> <input type="submit" value="新規トピックの投稿">
        </form>
    </c:param>
</c:import>