function formSubmit() {
    if (typeof gapi !== 'undefined') {
        googleSingOut();
    } else {
        document.getElementById("logoutForm").submit();
    }
}

function googleSingOut() {
    var auth2 = gapi.auth2;
    if (typeof auth2 !== 'undefined') {
        authSingOut();
    } else {
        gapi.load('auth2', function () {
            gapi.auth2.init().then(function () {
                authSingOut();
            });

        });
    }
}

function authSingOut() {
    var auth2 = gapi.auth2;
    if (typeof auth2 !== 'undefined') {
        var auth2In = auth2.getAuthInstance();
        auth2In.signOut().then(function () {
            console.log('User signed out.');
            document.getElementById("logoutForm").submit();
        });
    }
}

function onSignIn(googleUser) {
    var id_token = googleUser.getAuthResponse().id_token;
    var root = window.location.protocol + '//' + window.location.host;
    var endpointAddress = root + '/logingoogle';
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", endpointAddress);
    var params = {idtoken: id_token};
    for (var key in params) {
        if (params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);
            form.appendChild(hiddenField);
        }
    }
    document.body.appendChild(form);
    form.submit();
}