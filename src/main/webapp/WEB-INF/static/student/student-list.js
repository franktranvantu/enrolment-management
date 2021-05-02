$(function() {
  const options = {
    format: 'dd/mm/yyyy',
    todayHighlight: true,
    autoclose: true
  }
  $('#dob').datepicker(options);

  $('#student').DataTable({
    scrollY: 450,
    scroller: true
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
    const $form = $('<form action="/enrolment-management/student/delete-student" method="POST"></form>');
    $form.append(`<input type="hidden" name="id" value="${id}">`);
    $(document.body).append($form);
    $($form).submit();
  });
});