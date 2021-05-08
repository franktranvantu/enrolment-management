$(function() {
  $('#back').click(() => {
    window.location.href = '/';
  });

  $('form#save-enrolment').submit(() => {
    const courseSelect = $('select#course');
    if (_.isEmpty(courseSelect.val())) {
      courseSelect.addClass('is-invalid');
      return false;
    }
    courseSelect.removeClass('is-invalid');

    const studentSelect = $('select#student');
    if (_.isEmpty(studentSelect.val())) {
      studentSelect.addClass('is-invalid');
      return false;
    }
    studentSelect.removeClass('is-invalid');

    const semesterInput = $('input#semester');
    if (_.isEmpty(semesterInput.val())) {
      semesterInput.addClass('is-invalid');
      return false;
    }
    semesterInput.removeClass('is-invalid');
  });
});