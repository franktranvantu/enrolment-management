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

  $('#search, #export-excel, #export-pdf').click(e => {
    const id = $(e.target).attr('id');
    const {name, email, dobRange} = getFilterInfo();
    let action;
    let method;
    switch (id) {
      case 'export-excel':
        action = '/export-excel';
        method = 'POST';
        break;
      case 'export-pdf':
        action = '/export-pdf';
        method = 'POST';
        break;
      default:
        action = '';
        method = 'GET';
    }
    const $form = $(`<form action="/student${action}" method="${method}"></form>`);
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
      const id = target.closest('a').data('id');
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