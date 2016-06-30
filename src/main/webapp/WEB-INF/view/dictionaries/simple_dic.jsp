<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:url value="/dictionaries/ajax?table={tableName}" var="data_url">
    <s:param name="tableName" value="${tableName}" />
</s:url>
<s:url value="/dictionaries/check?table={tableName}" var="check_url">
    <s:param name="tableName" value="${tableName}" />
</s:url>
<s:url value="/dictionaries/{tableName}" var="page_url">
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

            <table data-toggle="table" data-toolbar="#toolbar" data-url="${data_url}"  data-show-refresh="true" data-show-toggle="false" data-show-columns="true" data-search="true" data-select-item-name="toolbar1" data-pagination="true" data-side-pagination="server" data-sort-name="name" data-sort-order="desc" data-reorderable-columns="true">
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
                <h4 class="modal-title" id="myModalLabel">Add <c:out value="${dictionaryName}" /></h4>
            </div>
            <div class="modal-body">
                <form role="form" name="edit" id="edit" action="">
                    <fieldset>
                        <input class="form-control" type="hidden" id="table" name="table" value="${tableName}" />
                        <input class="form-control" type="hidden" id="id" name="id" value="" />

                        <c:forEach var="column" items="${form}">
                            <div class="form-group">
                                <input class="form-control" placeholder="${column.caption}" id="${column.name}" name="${column.name}" type='text' />
                            </div>
                        </c:forEach>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button form="edit" type="submit" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>

<script src="/resources/lumino/js/jquery.validate.js"></script>
<script>
    $(document).ready(function(){

        $("#edit").validate({
            focusCleanup: true,
            rules: {
                name: {
                    required: true,
                    remote: {
                        url: "${check_url}",
                        type: "post"
                    }
                }
            },
            messages: {
                name: {
                    required: "This field is required",
                    remote: "This element already exists"
                }
            },
            success: function ajax() { //Ajax sending form
                var msg = $("#edit").serialize();
                $.ajax({
                    type: "POST",
                    url: "/dictionaries/save",
                    data: msg,
                    success: function(data) {
                        document.location = "${page_url}";
                    },
                    error:  function(xhr, str){
                        alert("Something went wrong!");
                    }
                });
            }
        });

    });

</script>