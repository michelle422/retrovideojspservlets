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
	<c:forEach var='film' items='${films}'>
		<c:url value='/filmdetail.htm' var='detailURL'>
			<c:param name="id" value="${film.id}"/>
		</c:url>
		<c:set var="beschikbaar" value="${film.voorraad - film.gereserveerd}"/>
		<div class="tooltip">
			<a href="<c:out value='${detailURL}'/>">
				<img src='<c:url value="/images/${film.id}.jpg"/>' alt='${film.titel}'>
				<c:choose>
					<c:when test="${beschikbaar > 0}">
						<span class="tooltiptext">reservatie mogelijk</span>
					</c:when>
					<c:otherwise>
						<span class="tooltiptext">reservatie niet mogelijk</span>
					</c:otherwise>
				</c:choose>
			</a>
		</div>
	</c:forEach>
</body>
</html>