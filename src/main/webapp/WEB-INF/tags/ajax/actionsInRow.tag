<%@ attribute name="table" required="true" rtexprvalue="true" %>
<%@ attribute name="editUrl" required="true" rtexprvalue="true" %>
<%@ attribute name="deleteUrl" required="true" rtexprvalue="true" %>

<script type="text/javascript">
    window.operateEvents = {
        'click .edit': function (e, value, row) {
            var $form = $('#editForm');
            var $inputs = $form.find('input');
            var $columns = row.originalProperties;
            for (var i = 0; i < $inputs.length; i++) {
                var $item = $inputs[i];
                if ($columns == $item.attr('name')){
                    $item.attr('name').value(row.attr('name').value());
                }
            }
            alert('You click like action, row: ' + JSON.stringify(row));
        },
        'click .remove': function (e, value, row) {
            var $conf = 'Are you want to delete "' + row['name'] + '"?';
            var $data = {table: '${table}', id: row['id']};
            if (confirm($conf))
            {
                $.post('${deleteUrl}', $data, function(response) {

                    if (response.status == 'FAIL') {
                        alert('Couldn\'t delete this record.\n' + response.result);
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
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>',
            '</div>'
        ].join('');
    }
</script>