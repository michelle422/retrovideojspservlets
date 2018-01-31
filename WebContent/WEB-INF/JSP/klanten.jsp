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
	<br>
	<h1>Klant</h1>
	<br>
	<form method="get" action="<c:url value="/klanten.htm" />">
		<label>Familienaam bevat:<span>${fouten}</span>
			<input name='familienaam' value='${param.familienaam}' type="text" autofocus required>
		</label>
		<input type='submit' value='Zoeken' >
	</form>
	<c:if test="${not empty klanten}">
		<table id="klanten">
			<thead>
				<tr>
					<th>Naam</th>
					<th>Straat - Huisnummer</th>
					<th>Postcode</th>
					<th>Gemeente</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="klant" items="${klanten}">
					<tr>
						<td>
							<c:url value='/bevestigen.htm' var='bevestigURL'>
								<c:param name='id' value='${klant.id}'/>
							</c:url>
							<a href="<c:out value='${bevestigURL}'/>">${klant.voornaam.concat(klant.familienaam)}</a>
						</td>
						<td>${klant.straatNummer}</td>
						<td>${klant.postcode}</td>
						<td>${klant.gemeente}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>