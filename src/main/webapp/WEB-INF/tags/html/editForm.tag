<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="caption" required="true" rtexprvalue="true" %>
<%@ attribute name="table" required="true" rtexprvalue="true" %>
<%@ attribute name="useShortName" required="true" rtexprvalue="true" %>

<!-- Modal -->
<div class="modal fade" id="formModal" tabindex="-1" role="dialog" aria-labelledby="formModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel"> ${caption} </h4>
            </div>
            <div class="modal-body">
                <form role="form" name="edit" id="edit" action="">
                    <fieldset>
                        <input class="form-control" type="hidden" id="table" name="table" value="${table}" />
                        <input class="form-control" type="hidden" id="id" name="id" value="0" />

                        <div class="form-group" id="nameControlGroup">
                            <label class="control-label"> Name </label>
                            <div class="controls">
                                <input class="form-control" id="name" name="name" autofocus /><br>
                                <span class="help-block" name="name"></span>
                            </div>
                        </div>

                        <c:if test="${useShortName}">
                            <div class="form-group" id="symbolControlGroup">
                                <label class="control-label"> Short name </label>
                                <div class="controls">
                                    <input class="form-control" id="symbol" name="symbol"/><br>
                                    <span class="help-block"><form:errors path="symbol"/></span>
                                </div>
                            </div>
                        </c:if>
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
