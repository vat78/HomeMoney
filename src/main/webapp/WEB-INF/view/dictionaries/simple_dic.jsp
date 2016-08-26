<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax" %>

<s:url value="/dictionaries/data.json?table={tableName}" var="data_url">
    <s:param name="tableName" value="${tableDef.name}" />
</s:url>
<s:url value="/dictionaries/view/{tableName}" var="page_url">
    <s:param name="tableName" value="${tableDef.name}" />
</s:url>

<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <c:out value="${tableDef.caption}" />
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
                   data-pagination="true"
                   data-side-pagination="server"
                   data-sort-name="${tableDef.sortColumn}"
                   data-sort-order="${tableDef.sortOrder}"
                   data-reorderable-columns="true"
                   data-show-export="true"
            >

                <thead>
                    <tr>
                    <th data-field="action" data-formatter="operateFormatter" data-events="operateEvents" class="td2icon">&nbsp;</th>
                    <c:forEach var="column" items="${columns}">
                        <c:if test="${column.shown == 'true'}">
                            <th data-field="${column.name}" data-sortable="true" data-visible = ${column.visible}><c:out value="${column.caption}" /> </th>
                        </c:if>
                    </c:forEach>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<script>
    $('.menu').find('[name = <c:out value="${tableDef.name}" />]').addClass("active");
</script>

<ajax:actionsInRow table = "${tableDef.name}" editUrl="/dictionaries/save" deleteUrl="/dictionaries/delete" />

<html:editDictionaryForm caption="Adding new ${tableDef.caption}" table="${tableDef.name}" columns="${columns}" />

<ajax:formValidate formName="#editForm" urlJsonValidate="/dictionaries/save" pageUrl="${page_url}" />
