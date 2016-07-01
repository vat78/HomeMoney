<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="true" %>

<div class="control-group" id="${name}ControlGroup">
    <label class="control-label">${label}</label>
    <div class="controls">
        <form:input path="${name}"/>
        <span class="help-inline"><form:errors path="${name}"/></span>
    </div>
</div>