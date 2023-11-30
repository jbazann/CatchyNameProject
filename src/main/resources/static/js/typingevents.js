"use strict";

import {textarea} from './sheet.js';
import * as format from './format.js';

export const ALLOW_DEFAULT = 69;
const CAUGHT_KEYS = ['Enter','Backspace']; 
let expectedKeyUps = [];

export function catchKeyUp(event) {
    //
    // Check if the event is relevant and update expected_keyups accordingly.
    //
    // firstFoundIndex is only meaningful when a match is found by .some() 
    let firstFoundIndex = -1;
    if( !expectedKeyUps.some(
        (v,i) => {
            firstFoundIndex = i; 
            return event.key == v.key;
        }) 
    ) return ALLOW_DEFAULT;

    const data = expectedKeyUps[firstFoundIndex];
    expectedKeyUps = expectedKeyUps.splice(firstFoundIndex,1);

    //
    // Do the thing that should be done for the event.
    //
    switch(event.key) {
        case 'Backspace':
            // delete from selectionEnd to selectoinEnd because if the key is held it may be possible to insert a 
            // new selection during the last repeat delay. Otherwise it's equivalent to selectionStart anwyay.
            return format.partialReplace('',textarea.selectionEnd,data.selectionEnd);
        default:
            console.log(`Untracked KeyUpEvent: ${event.key} with data: ${data}`); 
            return ALLOW_DEFAULT;
    }
}

export function catchKeyDown(event) {
    if( !CAUGHT_KEYS.includes(event.key) ) return ALLOW_DEFAULT;
    switch(event.key) {
        case 'Enter': // this implementation is probably wrong
            textarea.value = insert(deleteSelection(),textarea.selectionEnd,'\n');
            if(textarea.value.length == textarea.selectionStart) return format.append('\n')
            return format.insert('\n',textarea.selectionStart);
        case 'Backspace':
            expectedKeyUps.push(new KeyUpData(
                event.key,
                textarea.selectionStart,
                textarea.selectionEnd,
                textarea.value
                ));
            return ALLOW_DEFAULT;
        default:
            return ALLOW_DEFAULT;
    }
}

export function catchInput(event) {
    switch(event.inputType) {
        case 'insertText':
            if(!event.data) return format.noOp();
            if(textarea.selectionStart != textarea.selectionEnd) 
                return format.partialReplace(event.data,textarea.selectionStart,textarea.selectionEnd);
            if(textarea.selectionEnd == textarea.value.length) return format.append(event.data);
            return format.insert(event.data,textarea.selectionStart);
        default:
            console.log(`Untracked InputEvent: ${event.inputType} with data: ${event.data}`);
            return ALLOW_DEFAULT;
    }
}

function deleteSelection() {
    return textarea.value.slice(0,textarea.selectionStart) + textarea.value.slice(textarea.selectionEnd);
}

function insert(str,at,chars) {
    return str.slice(0,at)+chars+str.slice(at);
}

class KeyUpData {
    constructor(key,selectionStart,selectionEnd,fulltext) {
        this.key = key;
        this.selectionStart = selectionStart;
        this.selectionEnd = selectionEnd;
        this.fulltext = fulltext;
    }
}