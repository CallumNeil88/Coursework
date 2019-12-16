function pageLoad() {
    /*-------------------------- v Test Data v -----------------------------------------------------------------------*/
    let now = new Date();

    let myHTML = '<div style="text-align:center;">'
        + '<h1>Sign Up</h1>'
        + '<img src="/client/img/logo.png"  alt="Logo"/>'
        + '<div style="font-style: italic;">'
        + 'Generated at ' + now.toLocaleTimeString()
        + '</div>'
        + '</div>';

    document.getElementById("testDiv").innerHTML = myHTML;
    /*----------------------------------------------------------------------------------------------------------------*/
    document.getElementById('signupForm').addEventListener('submit', signup);

}

function signup(event) {

    event.preventDefault();

    const form = document.getElementById("signupForm");
    const formData = new FormData(form);
    alert("testing 1");
    fetch("/users/new", {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {
        alert("testing 2");
        if (responseData.hasOwnProperty('error')) {
            alert("testing 3");
            alert(responseData.error);
        } else {
            alert("You have Signed Up");

            window.location.href = '/client/login.html';
        }

    });
}