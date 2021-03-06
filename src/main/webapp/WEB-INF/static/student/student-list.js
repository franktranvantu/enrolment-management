$(function() {
  $('#dobRange').daterangepicker({
    opens: 'center',
    autoUpdateInput: false,
    locale: {
      format: 'DD/MM/YYYY',
      cancelLabel: 'Clear'
    }
  });

  $('#dobRange').on('apply.daterangepicker', function(ev, picker) {
    $(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
  });

  $('#dobRange').on('cancel.daterangepicker', function(ev, picker) {
    $('#dobRange').val('');
  });

  $('#student').DataTable({
    searching: false,
    scrollY: 450,
    scroller: true
  });

  $('#export-excel, #export-pdf').click(e => {
    e.preventDefault();
    const id = $(e.target).attr('id');
    const {name, email, dobRange} = getFilterInfo();
    let action;
    switch (id) {
      case 'export-excel':
        action = '/export-excel';
        break;
      case 'export-pdf':
        action = '/export-pdf';
        break;
    }
    const $form = $(`<form action="/admin/student${action}"></form>`);
    $form.append(`<input type="hidden" name="name" value="${name}">`);
    $form.append(`<input type="hidden" name="email" value="${email}">`);
    $form.append(`<input type="hidden" name="dobRange" value="${dobRange}">`);
    $(document.body).append($form);
    $($form).submit();
  });

  $('.table tbody').click(e => {
    const target = $(e.target);
    if (target.hasClass('delete-student-button') || target.hasClass('fa-trash')) {
      e.preventDefault();
      const id = target.closest('button').data('id');
      const modal = $('#delete-student-modal');
      modal.data('id', id);
      modal.modal('show');
    }
  });

  $('#confirm-delete-student').click(() => {
    const modal = $('#delete-student-modal');
    const id = modal.data('id');
    modal.modal('hide');
    const $form = $('<form action="/student/delete-student" method="POST"></form>');
    $form.append(`<input type="hidden" name="id" value="${id}">`);
    $(document.body).append($form);
    $($form).submit();
  });

  function getFilterInfo() {
    const name = $('input#name').val();
    const email = $('input#email').val();
    const dobRange = $('input#dobRange').val();
    return {
      name,
      email,
      dobRange
    }
  }
});