<%@ tag body-content="empty" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="value" type="java.lang.String" required="true"%>
<%
	value = value.replace("&", "&amp;");
	value = value.replace("<", "&lt;");
	value = value.replace(" ", "&nbsp;");
	value = value.replace("\n", "\n<br>");
%>
<%= value %>
