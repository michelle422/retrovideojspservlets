<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='nl'>
<head>
	<vdab:head title="Reservaties"/>
</head>
<body>
	<a href='/retrovideo'>Reserveren</a>
	<br>
	<h1>${film.titel}</h1>
	<img src='<c:url value="/images/${film.id}.jpg"/>' alt='${film.titel}'>
	<c:set var="beschikbaar" value="${film.voorraad - film.gereserveerd}"/>
	<dl>
		<dt>Prijs</dt><dd>${film.prijs}</dd>
		<dt>Voorraad</dt><dd>${film.voorraad}</dd>
		<dt>Gereserveerd</dt><dd>${film.gereserveerd}</dd>
		<dt>Beschikbaar</dt><dd>${beschikbaar}</dd>
	</dl>
	<c:if test='${beschikbaar > 0}'>
		<form action="inmandje.htm" id="naarmandje">
			<input type='submit' value='In mandje' id='inmandje'>
		</form>
	</c:if>
</body>
</html>