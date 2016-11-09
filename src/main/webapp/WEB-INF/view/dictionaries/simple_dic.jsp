<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax" %>
<%@ page import = "ru.vat78.homeMoney.controller.ControlTerms" %>
<%@ page import="ru.vat78.homeMoney.model.Defenitions" %>

<c:set var="fieldId" value="<%= Defenitions.FIELDS.ID %>" />
<c:set var="fieldType" value="<%= Defenitions.FIELDS.TYPE %>" />
<c:set var="apiGroup" value="<%= ControlTerms.API_DICTIONARIES %>" />

<s:url value="{api}{operation}?{param}={type}" var="data_url">
    <s:param name="type" value="${tableDef.name}" />
    <s:param name="api" value="<%= ControlTerms.API_DICTIONARIES %>" />
    <s:param name="operation" value="<%= ControlTerms.API_TABLE_DATA %>" />
    <s:param name="param" value="<%= ControlTerms.OBJECT_TYPE %>" />
</s:url>

<s:url value="{api}{operation}" var="get_url">
    <s:param name="api" value="<%= ControlTerms.API_DICTIONARIES %>" />
    <s:param name="operation" value="<%= ControlTerms.API_ONE_ELEMENT %>" />
</s:url>

<s:url value="{api}{operation}" var="save_url">
    <s:param name="api" value="<%= ControlTerms.API_DICTIONARIES %>" />
    <s:param name="operation" value="<%= ControlTerms.SAVE %>" />
</s:url>

<s:url value="{api}{operation}" var="delete_url">
    <s:param name="api" value="<%= ControlTerms.API_DICTIONARIES %>" />
    <s:param name="operation" value="<%= ControlTerms.DELETE %>" />
</s:url>

<s:url value="{section}/view/{tableName}" var="page_url">
    <s:param name="tableName" value="${tableDef.name}" />
    <s:param name="section" value="<%= ControlTerms.DICTIONARIES %>" />
</s:url>

<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <c:out value="${tableDef.parameters['caption']}" />
        </div>
        <div class="panel-body">

            <div id="toolbar">
                <div class="btn-group">
                    <button class="btn btn-default btn-add" type="button" name="add" title="Add">
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
                   data-sort-name="${tableDef.parameters['sortColumn']}"
                   data-sort-order="${tableDef.parameters['sortOrder']}"
                   data-reorderable-columns="true"
                   data-show-export="true"
            >

                <thead>
                    <tr>
                    <th data-field="action" data-formatter="operateFormatter" data-events="operateEvents" class="td2icon">&nbsp;</th>
                    <c:forEach var="column" items="${columns}">
                        <c:if test="${column.parameters['shown'] == 'true'}">
                            <th data-field="${column.name}" data-sortable="true" data-visible = ${column.parameters['visible']}><c:out value="${column.parameters['caption']}" /> </th>
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
    $('.btn-add').click(function(){
        prepareAndOpenForm('${get_url}0');
    });
</script>

<ajax:actionsInRow table = "${tableDef.name}" getUrl="${get_url}" deleteUrl="${delete_url}" fieldId="${fieldId}" fieldType="${fieldType}" />

