$(function()
{
    $(document).on('click', '.add-labels', function(e)
    {
        e.preventDefault();

        var chartForm = $('.chart form:first'),
            currentEntry = $(this).parents('.labels-group:first'),
            newEntry = $(currentEntry.clone()).insertBefore( "#submit-btn" );

        newEntry.find('input').val('');
        chartForm.find('.labels-group:not(:last) .add-labels')
            .removeClass('add-labels').addClass('remove-labels')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<span class="glyphicon glyphicon-minus"></span>');
    }).on('click', '.remove-labels', function(e)
    {
        $(this).parents('.labels-group:first').remove();

        e.preventDefault();
        return false;
    });

});