
<%@ attribute name="formName" required="true" rtexprvalue="true" %>
<%@ attribute name="urlJsonValidate" required="true" rtexprvalue="true" %>
<%@ attribute name="pageUrl" required="true" rtexprvalue="true" %>

<script type="text/javascript">
    function collectFormData(fields) {
        var data = {};
        for (var i = 0; i < fields.length; i++) {
            var $item = $(fields[i]);
            if ($item.attr('type') == 'checkbox') {
                data[$item.attr('name')] = $item.is(':checked');
            } else {
                data[$item.attr('name')] = $item.val();
            }
        }
        return data;
    }

    $(document).ready(function() {
        var $form = $('${formName}');
        $form.bind('submit', function(e) {
            // Ajax validation
            var $inputs = $form.find('input, select');
            var data = collectFormData($inputs);

            $.post('${urlJsonValidate}', data, function(response) {
                $form.find('.form-group').removeClass('has-error');
                $form.find('.help-block').empty();
                $form.find('.alert').remove();

                if (response.status == 'FAIL') {
                    for (var i = 0; i < response.result.length; i++) {
                        var item = response.result[i];
                        var $controlGroup = $('#' + item.fieldName + 'ControlGroup');
                        $controlGroup.addClass('has-error');
                        $controlGroup.find('.help-block').html(item.message);
                    }
                } else {
                    document.location = "${pageUrl}";
                }
            }, 'json');

            e.preventDefault();
            return false;
        });
    });
</script>
