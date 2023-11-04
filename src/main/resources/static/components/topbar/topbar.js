
function render() {
    const element = document.getElementById('topbar');

    fetch('components/topbar/topbar.html')
        .then(response => response.text())
        .then(html => {
             // Extract head and body content
             const parser = new DOMParser();
             const doc = parser.parseFromString(html, 'text/html');
             const headContent = doc.head.innerHTML;
             const bodyContent = doc.body.innerHTML;
 
             // Append head content to index.html's head
             document.head.innerHTML += headContent;
 
             // Append body content to element
             element.innerHTML = bodyContent;
        });
}

render();