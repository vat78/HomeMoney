
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="caption" required="true" rtexprvalue="true" %>
<%@ attribute name="table" required="true" rtexprvalue="true" %>
<%@ attribute name="columns" required="true" type="java.util.Collection" %>
<%@ attribute name="currencies" required="true" type="java.util.Collection" %>


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

                        <div class="form-group" id="nameControlGroup">
                            <label class="control-label"> Name </label>
                            <div class="controls">
                                <input class="form-control" id="name" name="name" type="text" /><br>
                                <span class="help-block" name="name"></span>
                            </div>
                        </div>

                        <div class="form-group" id="openingDateControlGroup" >
                            <label class="control-label"> Open date </label>
                            <div class="controls">
                                <input class="form-control" data-provide="datepicker" id="openingDate" name="openingDate" type="text" data-date-format="dd.mm.yyyy" data-date-autoclose="true" /><br>
                                <span class="help-block" name="openingDate"></span>
                            </div>
                        </div>

                        <div class="form-group" id="currencyControlGroup">
                            <label class="control-label"> Currency </label>
                            <div class="controls">
                                <select class="custom-select" id="currency" name="currency" >
                                    <option selected>Select currency</option>
                                    <c:forEach var="cur" items="${currencies}">
                                        <option value="${cur.id}">${cur.name}</option>
                                    </c:forEach>
                                </select><br>
                                <span class="help-block" name="currency"></span>
                            </div>
                        </div>

                        <div class="form-group" id="activeControlGroup">
                            <label class="control-label"> Active </label>
                            <div class="controls">
                                <input class="form-control" id="active" name="active" type="checkbox" align="left" /><br>
                                <span class="help-block" name="active"></span>
                            </div>
                        </div>

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


</script>