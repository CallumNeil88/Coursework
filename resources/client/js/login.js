function pageLoad() {

    let now = new Date();

    let myLogo = '<div style="text-align:center;">'
        //+ '<h1>Welcome to my API powered website!</h1>'
        + '<img src="/client/img/logo.png"  alt="Logo"/>'

    /*+ '<div style="font-style: italic;">'
    + 'Generated at ' + now.toLocaleTimeString()
    + '</div>'
    + '</div>';*/

    document.getElementById("logo").innerHTML = myLogo;

    let myFields = '<div style="text-align: center;">'
        + '<div>'
        + '<br1> Username: <input type = "text" id="myText" value ="Type Here">'
        + '<br2> Password: <input type = "text" id="myText" value ="Type Here">'
        + '</div>'

    document.getElementById("field").innerHTML = myFields

}