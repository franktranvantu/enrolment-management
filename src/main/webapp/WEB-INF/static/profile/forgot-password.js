$(function() {
    $('#back').click(() => {
        window.location.href = '/';
    });

    $('form#forgot-password').submit(() => {
        const emailInput = $('input#email');
        if (_.isEmpty(emailInput.val())) {
            emailInput.addClass('is-invalid');
            return false;
        }
        emailInput.removeClass('is-invalid');
    });
});