<%@ tag description='menu' pageEncoding="UTF-8"%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<header>
<nav><ul>
<li><a href="<c:url value='/'/>">Welkom</a></li>
<c:forEach var='genre' items='${genres}'>
	<li><a href="<c:url value='/${genre.naam}.htm'/>" id='${genre.id}'>${genre.naam}</a></li>
</c:forEach>
</ul></nav>
</header>
