<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax" %>
<%@ page import = "ru.vat78.homeMoney.controller.ControlTerms, ru.vat78.homeMoney.model.Defenitions.FIELDS" %>

<s:url value="{api}{operation}?{param}={type}" var="data_url">
    <s:param name="type" value="${tableDef.name}" />
    <s:param name="api" value="${apiGroup}" />
    <s:param name="operation" value="${ControlTerms.API_TABLE_DATA}" />
    <s:param name="param" value="${ControlTerms.OBJECT_TYPE}" />
</s:url>

<s:url value="{api}{operation}" var="save_url">
    <s:param name="api" value="${apiGroup}" />
    <s:param name="operation" value="${ControlTerms.SAVE}" />
</s:url>

<s:url value="{api}{operation}" var="delete_url">
    <s:param name="api" value="${apiGroup}" />
    <s:param name="operation" value="${ControlTerms.DELETE}" />
</s:url>

<s:url value="${requestScope['javax.servlet.forward.request_uri']}" var="page_url" />

<c:set var = "fieldGroup" value="<%= FIELDS.GROUP %>" />
<c:set var = "fieldType" value="<%= FIELDS.TYPE %>" />
<c:set var = "fieldId" value="<%= FIELDS.ID %>" />

<!-- Modal -->
<div class="modal fade" id="formModal" tabindex="-1" role="dialog" aria-labelledby="formModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel"> ${tableDef.parameters['caption']} </h4>
            </div>
            <div class="modal-body">
                <form role="form" name="editForm" id="editForm" action="">
                    <fieldset>
                        <input class="form-control" type="hidden" id="${fieldGroup}" name="${fieldGroup}" value="" />
                        <input class="form-control" type="hidden" id="${fieldType}" name="${fieldType}" value="${tableDef.name}" />
                        <input class="form-control" type="hidden" id="${fieldId}" name="${fieldId}" value="0" />

                        <c:forEach var="column" items="${columns}">

                            <c:if test="${column.parameters['editable'] == 'true' && column.name != fieldId}">
                                <div class="form-group" id="${column.name}ControlGroup">
                                    <label class="control-label"> <c:out value="${column.parameters['caption']}" /> </label>

                                    <c:if test="${column.parameters['control'] == 'text'}">
                                        <div class="controls">
                                            <input class="form-control" id="${column.name}" name="${column.name}" type="text" /><br>
                                        </div>
                                    </c:if>

                                    <c:if test="${column.parameters['control'] == 'checkbox'}">
                                        <div class="controls">
                                            <input class="form-control" id="${column.name}" name="${column.name}" type="checkbox" /><br>
                                        </div>
                                    </c:if>

                                    <c:if test="${column.parameters['control'] == 'date'}">
                                        <div class="controls">
                                            <input class="form-control" data-provide="datepicker" id="${column.name}" name="${column.name}" type="text" data-date-autoclose="true" /><br>
                                        </div>
                                    </c:if>

                                    <c:if test="${column.parameters['control'] == 'select'}">
                                        <div class="controls">
                                            <select class="form-control selectpicker" title="Choose one of the following..." data-size="5" id="${column.name}" name="${column.name}">
                                            </select>

                                            <s:url value="{api}{select}?{type}={dictionary}" var="selectUrl" >
                                                <s:param name="api" value="<%= ControlTerms.API_DICTIONARIES%>" />
                                                <s:param name="select" value="<%= ControlTerms.API_SELECT_DATA%>" />
                                                <s:param name="type" value="<%= ControlTerms.OBJECT_TYPE%>" />
                                                <s:param name="dictionary" value="${column.parameters['dataSource']}" />
                                            </s:url>

                                            <script>
                                                $(document).ready(function () {
                                                    var request = $.ajax({
                                                        type: 'GET',
                                                        url: '${selectUrl}',
                                                    });
                                                    request.done(function(data){
                                                        var my_select = $('#${column.name}');
                                                        my_select.empty();
                                                        $.each(data, function(index, item) {
                                                            my_select.append(new Option(item, item));
                                                        });
                                                    })
                                                })

                                            </script>
                                        </div>
                                    </c:if>

                                    <span class="help-block" name="${column.name}"></span>

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
            if ($inputs[i].name != '${fieldGroup}'){
                if ($inputs[i].name != '${fieldType}') {
                    if ($inputs[i].name != '${fieldId}') {
                        $inputs[i].focus();
                        break;
                    }
                }
            }
        }
    });

    $('#formModal').on('hide.bs.modal', function () {

    });

</script>

<ajax:formValidate formName="#editForm" urlJsonValidate="${save_url}" pageUrl="${page_url}" />
