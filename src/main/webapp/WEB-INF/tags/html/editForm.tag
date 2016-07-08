
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="caption" required="true" rtexprvalue="true" %>
<%@ attribute name="table" required="true" rtexprvalue="true" %>
<%@ attribute name="columns" required="true" type="java.util.Collection" %>

<!-- Modal -->
<div class="modal fade" id="formModal" tabindex="-1" role="dialog" aria-labelledby="formModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel"> ${caption} </h4>
            </div>
            <div class="modal-body">
                <form role="form" name="editForm" id="editForm" action="">
                    <fieldset>
                        <input class="form-control" type="hidden" id="table" name="table" value="${table}" />
                        <input class="form-control" type="hidden" id="id" name="id" value="0" />

                        <c:forEach var="column" items="${columns}">

                            <c:if test="${column.editable == 'true' && column.name != 'id'}">
                                <div class="form-group" id="${column.name}ControlGroup">
                                    <label class="control-label"> <c:out value="${column.caption}" /> </label>
                                    <div class="controls">
                                        <input class="form-control" id="${column.name}" name="${column.name}" /><br>
                                        <span class="help-block" name="${column.name}"></span>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>

                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button form="editForm" type="submit" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>

<script>
    $('#formModal').on('shown.bs.modal', function () {
        var $form = $('#editForm');
        var $inputs = $form.find('input');

        for (var i = 0; i < $inputs.length; i++){
           if ($inputs[i].name != 'table'){
               if ($inputs[i].name != 'id') {
                   $inputs[i].focus();
                   break;
               }
           }
        }
    });

    $('#formModal').on('hide.bs.modal', function () {
        var $form = $('#editForm');
        var $inputs = $form.find('input');
        for (var i = 0; i < $inputs.length; i++) {
            if ($inputs[i].name != 'table') {
                $inputs[i].value = '';
            }
        }
    });

</script>