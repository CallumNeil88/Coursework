function pageLoad() {

    let now = new Date();

    document.getElementById("logo").innerHTML = '<div style="text-align:center;">'
        + '<h1>Welcome to my API powered website!</h1>'
        + '<img src="/client/img/logo.png"  alt="Logo"/>'
        + '<div style="font-style: italic">'
        + 'Generated at ' + now.toLocaleTimeString()
        + '</div>'
        + '</div>';

    document.getElementById("field").innerHTML = '<div style="text-align: center;">'

    document.getElementById('loginForm').addEventListener('submit', login);


    //+ '<div>'
        //+ '<br1> Username: <input type = "text" name = "username" id="Username" value ="Type Here">'
        // + '<br2> Password: <input type = "text" name = "password" id="Password" value ="Type Here">'
        //+ '</div>'

    //if (window.location.search === '?logout') {
    //    document.getElementById('content').innerHTML = '<h1>Logging out, please wait...</h1>';
    //    logout();
    //} else {
    //    document.getElementById("loginButton").addEventListener("click", login);
    //}
}


function login(event) {

    event.preventDefault();

    const form = document.getElementById("loginForm");
    const formData = new FormData(form);

    fetch("/users/login", {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            Cookies.set("username", responseData.username);
            Cookies.set("token", responseData.token);

            window.location.href = '/client/index.html';
        }
    });
}

function gotoSignup() {
    
}

/*function logout() {

    fetch("/user/logout", {method: 'post'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {

            alert(responseData.error);

        } else {

            Cookies.remove("username");
            Cookies.remove("token");

            window.location.href = '/client/index.html';

        }
    });

}

































/*    let now = new Date();



document.getElementById("logo").innerHTML = '<div style="text-align:center;">'
    //+ '<h1>Welcome to my API powered website!</h1>'
    + '<img src="/client/img/logo.png"  alt="Logo"/>';
    + '<div style="font-style: italic;">'
    + 'Generated at ' + now.toLocaleTimeString()
    + '</div>'
    + '</div>';

     document.getElementById("field").innerHTML = '<div style="text-align: center;">'
      + '<div>'
      + '<br1> Username: <input type = "text" name = "username" id="Username" value ="Type Here">'
      + '<br2> Password: <input type = "text" name = "password" id="Password" value ="Type Here">'
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

*/