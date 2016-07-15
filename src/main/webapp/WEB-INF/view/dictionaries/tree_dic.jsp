<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<link rel="stylesheet" type="text/css" href="/resources/lumino/css/bootstrap-gtreetable.min.css" />


<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <c:out value="${tableDef.caption}" />
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
                    url: '/dictionaries/tree.json',
                    data: { 'id': id, 'table': "${tableDef.name}" },
                    dataType: 'json',
                    error: function(XMLHttpRequest) {
                        alert(XMLHttpRequest.status+': '+XMLHttpRequest.responseText);
                    }
                }
            },
            'onSave':function (oNode) {
                return {
                    type: 'POST',
                    url: '/dictionaries/tsave',
                    data: {
                        table: '${tableDef.name}',
                        id: oNode.getId(),
                        parent: oNode.getParent(),
                        name: oNode.getName()
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
                    url: '/dictionaries/tdelete?table=${tableDef.name}&id=' + oNode.getId(),
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