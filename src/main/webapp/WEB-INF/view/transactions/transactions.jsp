<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax" %>

<s:url value="/transactions/data.json?account={accountId}" var="data_url">
    <s:param name="accountId" value="${account}" />
</s:url>

<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            Operations
        </div>
        <div class="panel-body">


            <div id="toolbar">
                <div class="btn-group">
                        <button class="btn btn-default" type="button" name="add" title="Add" data-toggle="modal" data-target="#formModal">
                            <i class="glyphicon glyphicon-plus icon-plus"></i>
                        </button>
                </div>
            </div>

            <table data-toggle="table"
                   data-toolbar="#toolbar"
                   data-url="${data_url}"
                   data-show-refresh="true"
                   data-show-toggle="false"
                   data-show-columns="true"
                   data-search="true"
                   data-select-item-name="toolbar1"
                   data-pagination="false"
                   data-sort-name="name"
                   data-sort-order="asc"
                   data-reorderable-columns="false"
                   data-show-export="true"
            >

                <thead>
                <tr>
                    <th data-field="action" data-formatter="operateFormatter" data-events="operateEvents" class="td2icon">&nbsp;</th>
                    <c:forEach var="column" items="${columns}">
                        <c:if test="${column.shown == 'true' && column.name != 'name'}">
                            <th data-field="${column.name}" data-sortable="true" data-visible = ${column.visible}><c:out value="${column.caption}" /> </th>
                        </c:if>
                    </c:forEach>
                </tr>
                </thead>
            </table>

        </div>
    </div>
</div>

<ajax:actionsInRow table = "${tableDef.name}" editUrl="/accounts/save" deleteUrl="/accounts/delete" />
