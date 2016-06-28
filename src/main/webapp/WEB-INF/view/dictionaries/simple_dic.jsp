<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:url value="/ajax/{tableName}" var="data_url">
    <s:param name="tableName" value="${tableName}" />
</s:url>

<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <c:out value="${dictionaryName}" />
        </div>
        <div class="panel-body">
            <table data-toggle="table" data-url="${data_url}"  data-show-refresh="true" data-show-toggle="true" data-show-columns="true" data-search="true" data-select-item-name="toolbar1" data-pagination="true" data-sort-name="name" data-sort-order="desc">
                <thead>
                    <tr>
                    <th data-field="state" data-checkbox="true" >Item ID</th>
                    <c:forEach var="column" items="${columns}">
                            <th data-field="${column.columnName}" data-sortable="true" data-visible = ${column.visible}><c:out value="${column.columnName}" /></th>
                    </c:forEach>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<script>
    $('.menu').find('[name = <c:out value="${tableName}" />]').addClass("active");
</script>
