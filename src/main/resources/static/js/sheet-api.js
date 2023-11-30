"use strict";

export function sync(op) {
    fetch('/sheet/sync', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(op)
    })
    .then(response => {
        if(!response.ok) {
            throw new Error('Invalid network response.');
        }
        return response.text();
    })
    .then(data =>{
        console.log(`Received: ${JSON.stringify(data)}`);
    })
}

