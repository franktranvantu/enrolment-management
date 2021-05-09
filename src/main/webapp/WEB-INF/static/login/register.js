$(function() {
    $('#back').click(() => {
        window.location.href = '/';
    });

    $('form#register').submit(() => {
        const usernameInput = $('input#username');
        if (_.isEmpty(usernameInput.val())) {
            usernameInput.addClass('is-invalid');
            return false;
        }
        usernameInput.removeClass('is-invalid');

        const emailInput = $('input#email');
        if (_.isEmpty(emailInput.val())) {
            emailInput.addClass('is-invalid');
            return false;
        }
        emailInput.removeClass('is-invalid');

        const passwordInput = $('input#password');
        if (_.isEmpty(passwordInput.val())) {
            passwordInput.addClass('is-invalid');
            return false;
        }
        passwordInput.removeClass('is-invalid');

        const confirmPasswordInput = $('input#confirm-password');
        const mandatory = $('.mandatory');
        const mismatch = $('.mismatch');
        if (_.isEmpty(confirmPasswordInput.val())) {
            confirmPasswordInput.addClass('is-invalid');
            mandatory.show();
            mismatch.hide();
            return false;
        } else if (passwordInput.val() !== confirmPasswordInput.val()) {
            confirmPasswordInput.addClass('is-invalid');
            mandatory.hide();
            mismatch.show();
            return false;
        }
        confirmPasswordInput.removeClass('is-invalid');
    });
});