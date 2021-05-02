$(function() {
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
    });
});