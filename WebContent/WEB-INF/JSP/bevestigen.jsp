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
	<a href='/retrovideo/inmandje.htm'>Mandje</a>
	<a href='/retrovideo/klanten.htm'>Klant</a>
	<br>
	<h1>Bevestigen</h1>
	<br>
	<form method="post" id="reservatie">
		<c:set var="voornaam" value="${klant.voornaam.concat(' ')}"/>
		<label>${aantalFilms} film(s) voor ${voornaam.concat(klant.familienaam)}</label>
		<input type='submit' value='Bevestigen' >
	</form>
</body>
</html>