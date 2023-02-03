const entry = document.getElementById('userkey');
const loginbtn = document.getElementById('loginbtn');
 
loginbtn.onclick = handleSubmit;

//Using plain HTTP GET reveals our sensitive data;
//->Workaround: use HTTP POST; emmited data hidden
function handleSubmit(event)
{
    event.preventDefault();

    var form = document.createElement('form');
    document.body.appendChild(form);
    form.method = 'post';
    form.action = window.location.origin + "/landing";    //modify on switch between testing and deployment
    
    var root = document.createElement('input'), pass = document.createElement('input');
    root.type = 'hidden';
    root.name = 'username';
    root.value = 'admin';
    pass.type = 'hidden';
    pass.name = 'password';
    pass.value = entry.value;

    form.appendChild(root);
    form.appendChild(pass);
    
    form.submit();
    document.body.removeChild(form);
}