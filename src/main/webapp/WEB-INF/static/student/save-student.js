$(function() {
  $('#back').click(() => {
    window.location.href = '/enrolment-management/student';
  });

  const options = {
    format: 'dd/mm/yyyy',
    todayHighlight: true,
    autoclose: true
  }
  $('#dob').datepicker(options);

  $('form#save-student').submit(() => {
    const nameInput = $('input#name');
    if (_.isEmpty(nameInput.val())) {
      nameInput.addClass('is-invalid');
      return false;
    }
    nameInput.removeClass('is-invalid');

    const emailInput = $('input#email');
    if (_.isEmpty(emailInput.val())) {
      emailInput.addClass('is-invalid');
      return false;
    }
    emailInput.removeClass('is-invalid');
  });
});