$(function() {
    $('form#login').submit(() => {
        const usernameInput = $('input#username');
        if (_.isEmpty(usernameInput.val())) {
            usernameInput.addClass('is-invalid');
            return false;
        }
        usernameInput.removeClass('is-invalid');

        const passwordInput = $('input#password');
        if (_.isEmpty(passwordInput.val())) {
            passwordInput.addClass('is-invalid');
            return false;
        }
        passwordInput.removeClass('is-invalid');
    });
});