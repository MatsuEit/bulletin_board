<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="models.Topic"%>
<%@ page import="constants.AttributeConst"%>

<textarea name="${AttributeConst.COM_TITLE.getValue()}"
    id="${AttributeConst.COM_TITLE.getValue()}" rows="10" cols="50">${comment.title}</textarea>

<input type="hidden" name="${AttributeConst.COM_ID.getValue()}"
    value="${comment.id}" />
<input type="hidden" name="${AttributeConst.TOP_ID.getValue()}"
    value="${topic.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}"
    value="${_token}" />
<button type="submit">コメント投稿</button>
