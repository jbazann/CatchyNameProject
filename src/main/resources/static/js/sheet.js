import { sync } from './sheet-api.js';
import * as evs from './typingevents.js';
import { getOperation } from './format.js';

export const textarea = document.getElementById('writebox-main-textarea');

let timeoutId = setTimeout(() => {});
const threshold = 1500; 

let operations = [];

function inputHandler(event) {
    clearTimeout(timeoutId);
    const taggedStr = evs.catchInput(event);
    if( taggedStr !== evs.ALLOW_DEFAULT ) {
        const operation = getOperation(taggedStr);
        operations.push(operation);
    }
    timeoutId = setTimeout(sendBufferedData,threshold);
}

function keyDownHandler(event) {
    clearTimeout(timeoutId);
    const taggedStr = evs.catchKeyDown(event);
    if( taggedStr !== evs.ALLOW_DEFAULT ) {
        event.preventDefault();
        event.stopPropagation();
        const operation = getOperation(taggedStr);
        operations.push(operation);
    }
    timeoutId = setTimeout(sendBufferedData,threshold);
}

function keyUpHanlder(event) {
    clearTimeout(timeoutId);
    const taggedStr = evs.catchKeyUp(event);
    if( taggedStr !== evs.ALLOW_DEFAULT ) {
        event.preventDefault();
        event.stopPropagation();
        const operation = getOperation(taggedStr);
        operations.push(operation);
    }
    timeoutId = setTimeout(sendBufferedData,threshold);
}

// maybe some day I'll learn some asian language and I'll know what to do here
function compositionHandler(event) {
    clearTimeout(timeoutId);

    timeoutId = setTimeout(sendBufferedData,threshold);
}

function sendBufferedData() {
    sync(operations[0]);
    operations = [];
}

(function addEventHandlers() {
    if(textarea) {
        textarea.addEventListener('input',inputHandler);
        textarea.addEventListener('compositionend',compositionHandler);
        textarea.addEventListener('compositionupdate',compositionHandler);
        textarea.addEventListener('compositionstart',compositionHandler);
        textarea.addEventListener('keydown',keyDownHandler);
        textarea.addEventListener('keyup',keyUpHanlder);
    }
})();
