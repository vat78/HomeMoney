<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="nav menu" onload="">

    <li name = "add_transfer"><a href="/transactions/transfer?account=${account}">
        <svg class="glyph stroked plus sign"><use xlink:href="#stroked-plus-sign"/></svg>
        Add transfer
    </a></li>

    <li name = "add_bill"><a href="/transactions/bill?account=${account}">
        <svg class="glyph stroked plus sign"><use xlink:href="#stroked-plus-sign"/></svg>
        Add bill
    </a></li>

    <li role="presentation" class="divider"></li>

    <c:forEach var="type" items="${accountTypes}">
        <li name = "${type}"><a href="/accounts?type=${type}"><svg class="glyph stroked tag"><use xlink:href="#stroked-tag"/></svg> ${type}</a></li>
    </c:forEach>


</ul>