"use strict";
import { textarea } from './admin-dashboard.js';

const eventSource = new EventSource('/admin/dashboard/watch-sheet');

export function connectSheetTracking() {
    eventSource.addEventListener('message',(event) => {
        let wrappedMessage = JSON.parse(event.data);
        console.log(`[${event.timeStamp}] Sheet tracking event of type: ${wrappedMessage.tag}`);
        textarea.value = wrappedMessage.content;
    });
}

