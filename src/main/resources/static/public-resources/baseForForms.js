let uls = document.getElementsByClassName("input-requirements");
const submit = document.getElementById("submit");
const inputs = document.getElementsByClassName("our_input");

function check(id, type) {
    let input = document.getElementById(id);
    let results = [];
    if (type === 1) {
        results[0] = (input.value.length >= 3);
        results[1] = (!input.value.match(/[^A-Za-z_ёЁА-Яа-я]/));
    } else if (type === 2) {
        results[0] = (input.value.match(/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/));
    } else if (type === 3) {
        results[0] = (input.value.length >= 3);
        results[1] = (!input.value.match(/[^A-Za-z0-9_ёЁА-Яа-я]/));
    } else if (type === 4) {
        results[0] = (input.value.length >= 8 && input.value.length <= 64);
        results[1] = (input.value.match(/[a-zа-яё]/));
        results[2] = (input.value.match(/[A-ZА-ЯЁ]/));
        results[3] = (input.value.match(/[0-9]/));
        results[4] = (input.value.match(/[^A-Za-z0-9_ёЁА-Яа-я]/));
    } else {
        results[0] = (input.value === document.getElementById("password").value);
    }
    return results;
}

function hide_all() {
    for (let i = 0; i < uls.length; i++) {
        let ul = uls[i];
        ul.style.display = "none";
    }
}

function show_name() {
    document.getElementById("for_name").style.display = "block";
}

function show_surname() {
    document.getElementById("for_surname").style.display = "block";
}

function show_login() {
    document.getElementById("for_login").style.display = "block";
}

function show_password() {
    document.getElementById("for_password").style.display = "block";
}

function show_new_password() {
    document.getElementById("for_new_password").style.display = "block";
}

function analise_results(results, requirements) {
    let validity = true;
    for (let i = 0; i < requirements.length; i++) {
        if (results[i]) {
            requirements[i].classList.remove("invalid");
            requirements[i].classList.add("valid");
        } else {
            requirements[i].classList.remove("valid");
            requirements[i].classList.add("invalid");
            validity = false;
        }
    }
    if (validity) {
        hide_all();
    }
    return validity;
}

function test_name() {
    show_name();
    let requirements = document.getElementById("for_name").children;
    let results = check("name", 1);
    let validity = analise_results(results, requirements);
    if (validity) {
        document.getElementById("name").classList.remove("invalid");
        document.getElementById("name").classList.add("valid");
    } else {
        document.getElementById("name").classList.remove("valid");
        document.getElementById("name").classList.add("invalid");
    }
}

function test_surname() {
    show_surname();
    let requirements = document.getElementById("for_surname").children;
    let results = check("surname", 1);
    let validity = analise_results(results, requirements);
    if (validity) {
        document.getElementById("surname").classList.remove("invalid");
        document.getElementById("surname").classList.add("valid");
    } else {
        document.getElementById("surname").classList.remove("valid");
        document.getElementById("surname").classList.add("invalid");
    }
}

function test_email() {
    let results = check("email", 2);
    if (results[0]) {
        document.getElementById("email").classList.remove("invalid");
        document.getElementById("email").classList.add("valid");
    } else {
        document.getElementById("email").classList.remove("valid");
        document.getElementById("email").classList.add("invalid");
    }
}

function test_login() {
    show_login();
    let requirements = document.getElementById("for_login").children;
    let results = check("login", 3);
    let validity = analise_results(results, requirements);
    if (validity) {
        document.getElementById("login").classList.remove("invalid");
        document.getElementById("login").classList.add("valid");
    } else {
        document.getElementById("login").classList.remove("valid");
        document.getElementById("login").classList.add("invalid");
    }
}

function test_password() {
    show_password();
    let requirements = document.getElementById("for_password").children;
    let results = check("password", 4);
    let validity = analise_results(results, requirements);
    if (validity) {
        document.getElementById("password").classList.remove("invalid");
        document.getElementById("password").classList.add("valid");
    } else {
        document.getElementById("password").classList.remove("valid");
        document.getElementById("password").classList.add("invalid");
    }
}

function test_new_password() {
    show_new_password();
    let requirements = document.getElementById("for_new_password").children;
    let results = check("new_password", 4);
    let validity = analise_results(results, requirements);
    if (validity) {
        document.getElementById("new_password").classList.remove("invalid");
        document.getElementById("new_password").classList.add("valid");
    } else {
        document.getElementById("new_password").classList.remove("valid");
        document.getElementById("new_password").classList.add("invalid");
    }
}

function test_password_repeat() {
    let results = check("password_again", 5);
    if (results[0]) {
        document.getElementById("password_again").classList.remove("invalid");
        document.getElementById("password_again").classList.add("valid");
    } else {
        document.getElementById("password_again").classList.remove("valid");
        document.getElementById("password_again").classList.add("invalid");
    }
}

for (let i = 0; i < uls.length; i++) {
    let ul = uls[i];
    let lis = ul.children;
    for (let j = 0; j < lis.length; j++) {
        lis[j].classList.add("invalid");
    }
}