$(function()
{
    $(document).on('click', '.add-labels', function(e)
    {
        e.preventDefault();

        var chartForm = $('.chart form:first'),
            currentEntry = $(this).parents('.labels-group:first'),
            newEntry = $(currentEntry.clone()).insertBefore( "#dataset" );

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

    $(document).on('click', '.add-dataset', function(e)
    {
        e.preventDefault();

        var chartForm = $('.chart form:first'),
            currentEntry = $(this).parents('.dataset-group:first'),
            newEntry = $(currentEntry.clone()).insertBefore( "#submit-btn" );

        newEntry.find('input').val('');
        chartForm.find('.dataset-group:not(:last) .add-dataset')
            .removeClass('add-dataset').addClass('remove-dataset')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<span class="glyphicon glyphicon-minus"></span>');
    }).on('click', '.remove-dataset', function(e)
    {
        $(this).parents('.dataset-group:first').remove();

        e.preventDefault();
        return false;
    });
});