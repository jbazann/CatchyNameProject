"use strict";

export function sync(update) {
    fetch('/sheet/sync', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(update)
    })
    .then(response => {
        if(!response.ok) throw new Error(`${response.status}: ${response.statusText}`);
        return response.text();
    })
    .then(data =>{
        console.log(`Received: ${JSON.stringify(data)}`);
    })
}

