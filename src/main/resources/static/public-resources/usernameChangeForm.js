function main_test() {
    let input = inputs[0];
    let type = 3;
    let errors = check(input.id, type);
    let message = "";
    let valid = true;
    if (!errors[0]) {
        message += "Нужно использовать как минимум 3 символа. \n";
        valid = false;
    }
    if (!errors[1]) {
        message += "Используйте только буквы и цифры (без спец. символов). \n";
        valid = false;
    }
    console.log("?")
    if (used_logins.includes(input.value)) {
        message += "Этот логин уже занят. \n";
        valid = false;
    }
    console.log("!")
    input.setCustomValidity(message);
    if (valid) {
        input.classList.remove("invalid");
        input.classList.add("valid");
        input.removeEventListener('input', main_test);
    } else {
        input.classList.remove("valid");
        input.classList.add("invalid");
        input.removeEventListener('input', main_test);
        input.addEventListener('input', main_test);
    }
}

submit.addEventListener('click', main_test);

for (let i = 0; i < uls.length; i++) {
    let ul = uls[i];
    let lis = ul.children;
    for (let j = 0; j < lis.length; j++) {
        lis[j].classList.add("invalid");
    }
}