function main_test() {
    for (let i = 0; i < inputs.length; i++) {
        let input = inputs[i];
        let type = 4;
        if(i === 1) {
            type = 5;
        }
        let errors = check(input.id, type);
        let message = "";
        let valid = true;
        if (type === 4) {
            if (!errors[0]) {
                message += "Нужно использовать от 8 до 20 символов. \n";
                valid = false;
            }
            if (!errors[1]) {
                message += "Используйте как минимум 1 строчную букву. \n";
                valid = false;
            }
            if (!errors[2]) {
                message += "Используйте как минимум 1 заглавную букву. \n";
                valid = false;
            }
            if (!errors[3]) {
                message += "Используйте как минимум 1 цифру. \n";
                valid = false;
            }
            if (!errors[4]) {
                message += "Используйте как минимум 1 спец. символ ($, @, ! и т.п.). \n";
                valid = false;
            }
        } else {
            if (!errors[0]) {
                message += "Пароль должен быть идентичен первому. \n";
                valid = false;
            }
        }
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
}

submit.addEventListener('click', main_test);

for (let i = 0; i < uls.length; i++) {
    let ul = uls[i];
    let lis = ul.children;
    for (let j = 0; j < lis.length; j++) {
        lis[j].classList.add("invalid");
    }
}