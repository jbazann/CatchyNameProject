"use strict";

import { sync } from './sheet-api.js';

export const textarea = document.getElementById('sheet-textarea');

let timeoutId = setTimeout(() => {});
const threshold = 1500; 

if(textarea) {
    textarea.addEventListener('input',inputHandler);
}

function inputHandler(event) {
    clearTimeout(timeoutId);
    timeoutId = setTimeout(sendData,threshold);
}

function sendData() {
    sync(new UpdateWrapper("19443e46-058e-4b7b-b0e7-fbdb5b1d2c66","fulltext",textarea.value));
}

class UpdateWrapper {
    constructor(user,tag,content) {
        this.user = user;
        this.tag = tag;
        this.content = content;
    }
}