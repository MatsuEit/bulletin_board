<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />


<c:import url="../layout/app.jsp">
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
        <form method="POST" action="<c:url value='?action=${action}&command=${commCrt}' />">
            <c:import url="_form.jsp" />
        </form>
    </c:param>
</c:import>