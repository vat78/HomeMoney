<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:url value="/dictionaries/ajax?table={tableName}" var="data_url">
    <s:param name="tableName" value="${tableName}" />
</s:url>

<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <c:out value="${dictionaryName}" />
        </div>
        <div class="panel-body">

            <div id="toolbar">
                <div class="btn-group">
                    <button class="btn btn-default" type="button" name="add" title="Add" data-toggle="modal" data-target="#addFormModal">
                        <i class="glyphicon glyphicon-plus icon-plus"></i>
                    </button>
                </div>
            </div>

            <table data-toggle="table" data-toolbar="#toolbar" data-url="${data_url}"  data-show-refresh="true" data-show-toggle="false" data-show-columns="true" data-search="true" data-select-item-name="toolbar1" data-pagination="true" data-sort-name="name" data-sort-order="desc" data-reorderable-columns="true">
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

<!-- Modal -->
<div class="modal fade" id="addFormModal" tabindex="-1" role="dialog" aria-labelledby="formModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>