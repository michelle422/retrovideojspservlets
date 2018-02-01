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
	<a href='/retrovideo/klanten.htm'>Klant</a>
	<br>
	<h1>Mandje</h1>
	<br>
	<c:if test="${not empty filmsInMandje}">
		<form method="post">
		<table id="mandje">
			<thead>
				<tr>
					<th>Film</th>
					<th>Prijs</th>
					<th>
						<input type='submit' value='Verwijderen' >
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var='film' items='${filmsInMandje}'>
					<tr>
						<c:set var="teller" value="${teller + 1}"/>
						<td>${film.titel}</td>
						<td>${film.prijs}</td>
						<td>
							<input type='checkbox' name='id' value='${teller-1}'>
						</td>
						<c:set var="totaal" value="${totaal + film.prijs}"/>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td>Totaal</td>
					<td>${totaal}</td>
				</tr>
			</tfoot>
		</table>
		</form>
	</c:if>
</body>
</html>