$(function() {
  $('#enrolment').DataTable({
    scrollY: 450,
    scroller: true
  });

  $('.table tbody').click(e => {
    const target = $(e.target);
    if (target.hasClass('delete-enrolment-button') || target.hasClass('fa-trash')) {
      e.preventDefault();
      const id = target.closest('a').data('id');
      const modal = $('#delete-enrolment-modal');
      modal.data('id', id);
      modal.modal('show');
    }
  });

  $('#confirm-delete-enrolment').click(() => {
    const modal = $('#delete-enrolment-modal');
    const id = modal.data('id');
    modal.modal('hide');
    const $form = $('<form action="/enrolment-management/delete-enrolment" method="POST"></form>');
    $form.append(`<input type="hidden" name="id" value="${id}">`);
    $(document.body).append($form);
    $($form).submit();
  });
});