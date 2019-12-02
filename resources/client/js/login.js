function pageLoad() {

    let now = new Date();



    document.getElementById("logo").innerHTML = '<div style="text-align:center;">'
        //+ '<h1>Welcome to my API powered website!</h1>'
        + '<img src="/client/img/logo.png"  alt="Logo"/>';
        /*+ '<div style="font-style: italic;">'
        + 'Generated at ' + now.toLocaleTimeString()
        + '</div>'
        + '</div>';*/

    document.getElementById("field").innerHTML = '<div style="text-align: center;">'
        + '<div>'
        + '<br1> Username: <input type = "text" id="Username" value ="Type Here">'
        + '<br2> Password: <input type = "text" id="Password" value ="Type Here">'
        + '</div>'

}

function login() {


    const Username = getValue("Username");
    const Password = getValue("Password");
    //const formData = new FormData(Password);

    alert(Username);
    alert(Password);
    let apiPath = "User/login";

       fetch(apiPath, {method: 'post', body: Username})
           .then(response => response.json())
           .then(responseData => {

           if (responseData.hasOwnProperty('error')) {
               alert("Test1");
               alert(responseData.error);
           } else {
               alert("test2");
           }
       });
}

function getValue(id) {
    return document.getElementById(id);

}