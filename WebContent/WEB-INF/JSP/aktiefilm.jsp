<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='nl'>
<head>
	<vdab:head title="Reservaties"/>
</head>
<body>
	<h1>Reservaties</h1>
	<vdab:menu/>
	<c:forEach var='aktiefilm' items='${aktiefilms}'>
		<c:url value="/images/${aktiefilm.id}.jpg" var="fotoURL" />
		<a href="${fotoURL}"></a>
	</c:forEach>
</body>
</html>