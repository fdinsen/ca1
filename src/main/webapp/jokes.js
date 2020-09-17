
document.getElementById("all").addEventListener("click", function (event) {
    event.preventDefault();

    getAndSetAll();
});

document.getElementById("single").addEventListener("click", function (event) {
    event.preventDefault();
    let url = "api/jokes/" + document.getElementById('id').value;

    fetch(url)
            .then(res => res.json()) //in flow1, just do it
            .then(joke => {
                console.log(joke)
                if (joke === 'Joke not found') {
                    alert(joke)
                } else {
                    tableBody = "<tr><td>" + joke.id + "</td>" + "<td>" + joke.joke + "</td>" + "<td>" + joke.type + "</td></tr>"
                    document.getElementById('tbody').innerHTML = tableBody;
                }

            });

});

document.getElementById("random").addEventListener("click", function (event) {
    event.preventDefault();
    let url = "api/jokes/random";

    fetch(url)
            .then(res => res.json()) //in flow1, just do it
            .then(joke => {
                tableBody = "<tr><td>" + joke.id + "</td>" + "<td>" + joke.joke + "</td>" + "<td>" + joke.type + "</td></tr>"
                document.getElementById('tbody').innerHTML = tableBody;

            });

});



document.getElementById("addJoke").addEventListener("click", function (event) {
    event.preventDefault();
    var text = document.getElementById('joke').value;
    console.log(text)
    text.replace(/\n\r?/g, '<br />');
    console.log(text)
    let url = "api/jokes/" + encodeURIComponent(text) + "/" + document.getElementById('type').value;

    fetch(url, {
        method: 'post'}
    )
            .then(() => {
                getAndSetAll();
            });
    document.getElementById('joke').value = '';
    document.getElementById('type').value = '';
});



function createTableBody(data) {
    return  data.map(joke => "<tr><td>" + joke.id + "</td>" + "<td>" + joke.joke + "</td>" + "<td>" + joke.type + "</td></tr>").join('');
}


function getAndSetAll(data) {
    let url = "api/jokes/all";

    fetch(url)
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                tableBody = createTableBody(data);
                document.getElementById('tbody').innerHTML = tableBody;
            });
}



