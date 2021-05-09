$(function() {
    $('#back').click(() => {
        window.location.href = '/';
    });

    $('form#change-password').submit(() => {
        const passwordInput = $('input#password');
        if (_.isEmpty(passwordInput.val())) {
            passwordInput.addClass('is-invalid');
            return false;
        }
        passwordInput.removeClass('is-invalid');

        const newPasswordInput = $('input#new-password');
        if (_.isEmpty(newPasswordInput.val())) {
            newPasswordInput.addClass('is-invalid');
            return false;
        }
        newPasswordInput.removeClass('is-invalid');

        const confirmNewPasswordInput = $('input#confirm-new-password');
        const mandatory = $('.mandatory');
        const mismatch = $('.mismatch');
        if (_.isEmpty(confirmNewPasswordInput.val())) {
            confirmNewPasswordInput.addClass('is-invalid');
            mandatory.show();
            mismatch.hide();
            return false;
        } else if (newPasswordInput.val() !== confirmNewPasswordInput.val()) {
            confirmNewPasswordInput.addClass('is-invalid');
            mandatory.hide();
            mismatch.show();
            return false;
        }
        confirmNewPasswordInput.removeClass('is-invalid');
    });
});