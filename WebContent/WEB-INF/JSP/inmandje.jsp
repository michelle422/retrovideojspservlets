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
	<a href='/klanten.htm'>Klant</a>
	<br>
	<h1>Mandje</h1>
	<br>
	<c:if test="${not empty filmInMandje}">
		<table>
			<thead>
				<tr>
					<th>Film</th>
					<th>Prijs</th>
					<th class="verwijderen">Verwijderen</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${filmInMandje.titel}</td>
					<td>${filmInMandje.prijs}</td>
				</tr>
			</tbody>
		</table>
	</c:if>
</body>
</html>