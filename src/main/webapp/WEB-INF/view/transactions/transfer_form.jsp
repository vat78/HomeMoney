<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <c:if test="${edit == 0}"> Add transfer </c:if>
            <c:if test="${edit == 1}"> Edit transfer </c:if>
        </div>
        <div class="panel-body">

            <form role="form" name="editForm" id="editForm" action="">
                <fieldset>
                    <input class="form-control" type="hidden" id="id" name="id" value="${entry.id}" />
                    <input class="form-control" type="hidden" id="account_type" name="operation_type" value="transfers" />

                    <div class="form-group" id="dateControlGroup">
                        <label class="control-label"> Date </label>
                        <div class="controls">
                            <input class="form-control" data-provide="datepicker" id="date" name="date" type="text" /><br>
                            <span class="help-block" name="date"></span>
                        </div>
                    </div>

                    <div class="form-group" id="accountControlGroup">
                        <label class="control-label"> From account </label>
                        <div class="controls">
                            <select class="form-control" data-width="auto" id="account" name="account" />
                                <option value="0">Select account</option>
                                <c:forEach var="cur" items="${accounts}">
                                    <option value="${cur.id}">${cur.name}</option>
                                </c:forEach>
                                </select><br>
                            <span class="help-block" name="account"></span>
                        </div>
                    </div>

                    <div class="form-group" id="corrAccountControlGroup">
                        <label class="control-label"> To account </label>
                        <div class="controls">
                            <select class="form-control" data-width="auto" id="corrAccount" name="corrAccount" />
                            <option value="0">Select account</option>
                            <c:forEach var="cur" items="${accounts}">
                                <option value="${cur.id}">${cur.name}</option>
                            </c:forEach>
                            </select><br>
                            <span class="help-block" name="corrAccount"></span>
                        </div>
                    </div>

                    <div class="form-group" id="sumControlGroup">
                        <label class="control-label"> Sum </label>
                        <div class="controls">
                            <input class="form-control" id="sum" name="sum" type="text" value="${entry.sum}" /><br>
                            <span class="help-block" name="sum"></span>
                        </div>
                    </div>

                    <div class="form-group invisible" id="conversionControlGroup">
                        <label class="control-label"> Conversion </label>
                        <div class="controls">
                            <input class="form-control" id="conversion" name="conversion" type="text" value="${entry.conversion}" /><br>
                            <span class="help-block" name="conversion"></span>
                        </div>
                    </div>

                </fieldset>
            </form>
        </div>
        <div class="modal-footer">
            <a class="btn" href="/transactions?account=${account}">
            <button type="submit" class="btn btn-default">Close</button></a>
            <button form="editForm" type="submit" class="btn btn-primary">Save</button>
        </div>

    </div>
</div>

<script>
    $( document ).ready(function() {
        $("#account option[value='${entry.account.id}']").prop('selected', true);
        $("#corrAccount option[value='${entry.corrAccount.id}']").prop('selected', true);
        $("#date").prop('value', '<fmt:formatDate value="${entry.date}" pattern="${dateFormat}" />');
    });
    $("#account").change(onChangeAccounts);
    $("#corrAccount").change(onChangeAccounts);

    function onChangeAccounts(){
        $("#conversion").prop('value', '1');
    }
</script>

<ajax:formValidate formName="#editForm" urlJsonValidate="/transactions/save" pageUrl="/transactions?account=${account}" />