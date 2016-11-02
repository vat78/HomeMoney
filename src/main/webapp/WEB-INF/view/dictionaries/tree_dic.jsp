<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ page import = "ru.vat78.homeMoney.controller.ControlTerms" %>
<%@ page import="ru.vat78.homeMoney.model.Defenitions" %>

<link rel="stylesheet" type="text/css" href="/resources/lumino/css/bootstrap-gtreetable.min.css" />

<c:set var="fieldId" value="<%= Defenitions.FIELDS.ID %>" />
<c:set var="fieldType" value="<%= Defenitions.FIELDS.TYPE %>" />

<s:url value="{api}{operation}?{param}={type}" var="data_url">
    <s:param name="type" value="${tableDef.name}" />
    <s:param name="api" value="<%= ControlTerms.API_DICTIONARIES %>" />
    <s:param name="operation" value="<%= ControlTerms.API_TREE_DATA %>" />
    <s:param name="param" value="<%= ControlTerms.OBJECT_TYPE %>" />
</s:url>
<s:url value="{api}{operation}" var="save_url">
    <s:param name="api" value="<%= ControlTerms.API_DICTIONARIES %>" />
    <s:param name="operation" value="<%= ControlTerms.SAVE_TREE_NODE %>" />
</s:url>

<s:url value="{api}{operation}" var="delete_url">
    <s:param name="api" value="<%= ControlTerms.API_DICTIONARIES %>" />
    <s:param name="operation" value="<%= ControlTerms.DELETE %>" />
</s:url>


<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <c:out value="${tableDef.parameters['caption']}" />
        </div>
        <div class="panel-body">

            <table class="table gtreetable" id="gtreetable" ><thead><tr><th>Name</th></tr></thead></table>

        </div>
    </div>
</div>


<script type="text/javascript" src="/resources/lumino/js/bootstrap-gtreetable.js"></script>

<script>
    $('.menu').find('[name = <c:out value="${tableDef.name}" />]').addClass("active");
</script>

<script type="text/javascript">
    jQuery(function($) {
        jQuery('#gtreetable').gtreetable({
            'source': function (id) {
                return {
                    type: 'GET',
                    url: '${data_url}',
                    data: { '${fieldId}': id, '${fieldType}': "${tableDef.name}" },
                    dataType: 'json',
                    error: function(XMLHttpRequest) {
                        alert(XMLHttpRequest.status+': '+XMLHttpRequest.responseText);
                    }
                }
            },
            'onSave':function (oNode) {
                return {
                    type: 'POST',
                    url: '${save_url}',
                    data: {
                        <%= Defenitions.FIELDS.TYPE %>: '${tableDef.name}',
                        <%= Defenitions.FIELDS.ID %>: oNode.getId(),
                        <%= Defenitions.FIELDS.PARENT_ID %>: oNode.getParent(),
                        <%= Defenitions.FIELDS.NAME %>: oNode.getName()
                    },
                    dataType: 'json',
                    error: function(XMLHttpRequest) {
                        alert(XMLHttpRequest.status+': '+XMLHttpRequest.responseText);
                    }
                };
            },
            'onDelete':function (oNode) {
                return {
                    type: 'POST',
                    url: '${delete_url}',
                    data: {
                        <%= Defenitions.FIELDS.TYPE %>: '${tableDef.name}',
                        <%= Defenitions.FIELDS.ID %>: oNode.getId()
                    },
                    dataType: 'json',
                    error: function(XMLHttpRequest) {
                        alert(XMLHttpRequest.status+': '+XMLHttpRequest.responseText);
                    }
                };
            },
            nodesWrapper : 'result',
            manyroots : 'true',
            defaultActions : null,
            actions: [
                {
                    name: 'Add new',
                    event: function (oNode, oManager) {
                        oNode.add('before', 'default');
                    }
                },
                {
                    name: 'Add child',
                    event: function (oNode, oManager) {
                        oNode.add('firstChild', 'default');
                    }
                },
                {
                    divider: true
                },
                {
                    name: 'Update',
                    event: function (oNode, oManager) {
                        oNode.makeEditable();
                    }
                },
                {
                    name: 'Change parent',
                    event: function (oNode, oManager) {
                        oNode.makeEditable();
                    }
                },
                {
                    name: 'Delete',
                    event: function (oNode, oManager) {
                        if (confirm(oManager.language.messages.onDelete)) {
                            oNode.remove();
                        }
                    }
                }
            ],
            'types': { default: 'glyphicon glyphicon-folder-open'}
        });
    })

</script>