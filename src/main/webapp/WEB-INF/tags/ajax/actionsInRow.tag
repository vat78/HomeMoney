<%@ tag import="ru.vat78.homeMoney.model.Defenitions" %>
<%@ attribute name="table" required="true" rtexprvalue="true" %>
<%@ attribute name="getUrl" required="true" rtexprvalue="true" %>
<%@ attribute name="deleteUrl" required="true" rtexprvalue="true" %>
<%@ attribute name="fieldId" required="true" rtexprvalue="true" %>

<script type="text/javascript">
    window.operateEvents = {
        'click .edit': function (e, value, row) {

            var full_url = '${getUrl}' + row['${fieldId}'];
            prepareAndOpenForm(full_url);
        },
        'click .remove': function (e, value, row) {
            var $conf = 'Are you want to delete "' + row['name'] + '"?';
            var $data = {<%= Defenitions.FIELDS.TYPE %>: '${table}', <%= Defenitions.FIELDS.ID %>: row['id']};
            if (confirm($conf))
            {
                $.post('${deleteUrl}', $data, function(response) {

                    if (response.status == 'FAIL') {
                        alert('Couldn\'t delete this record.\n' + response.result[0].message);
                    } else {
                        document.location = "${pageUrl}";
                    }
                }, 'json');
            }
        }
    };

    function operateFormatter(value, row, index) {
        return [
            '<div class="pull-left">',
            '<a class="edit" href="javascript:void(0)" title="Edit">',
            '<i class="glyphicon glyphicon-edit"></i>',
            '</a>  ',
            '<a class="remove" href="javascript:void(0)" title="Delete">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>',
            '</div>'
        ].join('');
    }


    function prepareAndOpenForm(url) {
        $.get(url, function(data) {
            var $form = $('#editForm');
            var $inputs = $form.find('input, select');
            for (var i = 0; i < $inputs.length; i++) {
                var $item = $inputs[i];
                if (data[$item.name] != null){
                    if ($item.type == 'checkbox') {
                        $item.checked = data[$item.name];
                    } else {
                        $item.value = data[$item.name];
                    }
                }
            }

            $("#formModal").modal('show');
        });
    }
</script>