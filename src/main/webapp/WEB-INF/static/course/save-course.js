$(function() {
  $('#back').click(() => {
    window.location.href = '/enrolment-management/course';
  });

  $('form#save-course').submit(() => {
    const nameInput = $('input#name');
    if (_.isEmpty(nameInput.val())) {
      nameInput.addClass('is-invalid');
      return false;
    }
    nameInput.removeClass('is-invalid');
  });
});