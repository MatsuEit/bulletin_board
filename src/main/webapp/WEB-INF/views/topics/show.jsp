<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOPI.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
            <c:if test="${commentError}">
            <div id="flush_error">
                コメント内容を入力してください。
            </div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <h2><c:out value="${topic.title}" /></h2>

        <table id="comment_list">
            <tbody>
                <tr>
                    <th class="comment_id">ID</th>
                    <th class="comment_title">コメント内容</th>
                    <th class="comment_name">投稿者</th>
                    <th class="comment_date">投稿日時</th>
                </tr>
                <c:forEach var="comment" items="${comments}"
                    varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="comment_id"><c:out
                                value="${comment.id}" /></td>
                        <td class="comment_title"><c:out
                                value="${comment.title}" /></td>
                        <td class="comment_name"><c:out
                                value="${comment.post.name}" /></td>
                        <fmt:parseDate value="${comment.createdAt}"
                            pattern="yyyy-MM-dd'T'HH:mm:ss"
                            var="createDay" type="date" />
                        <td class="comment_date"><fmt:formatDate
                                value='${createDay}'
                                pattern='yyyy-MM-dd HH:mm:ss' /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br />
        <br />
        <form method="POST"
            action="<c:url value='?action=${actTop}&command=${commCrt}&id=${topic.id}' />">
            <c:import url="../comments/_form.jsp" />
        </form>
        <p>
            <a
                href="<c:url value='?action=${action}&command=${commIdx}' />">トップページに戻る</a>
        </p>
    </c:param>
</c:import>