<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="nav menu" onload="">

    <c:forEach var="type" items="${accountTypes}">
        <li name = "${type}"><a href="/accounts?type=${type}"><svg class="glyph stroked tag"><use xlink:href="#stroked-tag"/></svg></use></svg> ${type}</a></li>
    </c:forEach>
    <li name = "Closed"><a href="/accounts?type=closed"><svg class="glyph stroked tag"><use xlink:href="#stroked-tag"/></svg></use></svg> Closed</a></li>
    <li role="presentation" class="divider"></li>

</ul>
