<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="models.Topic"%>
<%@ page import="constants.AttributeConst"%>


    <input type="text" name="${AttributeConst.TOP_TITLE.getValue()}" id="${AttributeConst.TOP_TITLE.getValue()}" value="${topic.title}" />
    <input type="hidden" name="${AttributeConst.TOP_ID.getValue()}" value="${topic.id}" />
    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
    <button type="submit">新規トピックの投稿</button>
