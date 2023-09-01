function ottieniforecasting(){


    var myDiv = document.getElementById('myDiv');
    myDiv.style.width = '800px'; // Imposta la larghezza del div a 400 pixel
    myDiv.style.height = '400px'; // Imposta l'altezza del div a 200 pixel

    const data = []

    var date = document.getElementById("date").value;

    var target = 'http://localhost:8080/rec/rec/forecasting/'+date;

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if(this.status == 200){
                response = xhttp.responseText;
                sessionStorage.setItem("Forecasting",response);

            }
            else {
                alert("Forecasting non presente per la data specificata");

            }
        }

    };

    xhttp.open("GET",target,false);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
    xhttp.send();

      /*
    fetch('http://localhost:8080/rec/rec/forecasting/'+date, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',

        },
    })
        .then(response => response.json())
        .then(data => {

            produzione = data.produzione.values;
            produzione = Object.values(produzione)

            const labels = produzione.map((_, index) => index + 1); // Esempio: array di etichette
            const values = produzione; // Esempio: array di valori

            console.log(produzione)

            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Forecasting Energia Generata',
                        data: values,
                        backgroundColor: 'rgba(0, 128, 255, 0.5)',
                        borderColor: 'rgba(0, 128, 255, 1)',
                        borderWidth: 1,
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: false,
                            title: {
                                display:true,
                                text: 'KWh'
                            }
                        },
                        x:{
                            title:{
                                diplay:true,
                                text:"MTU"
                            }
                        }
                    },
                }
            });

        })
        .catch(error => {
            // Gestisci gli error
            console.error('Si è verificato un errore:', error);
        });*/

}

function vediforecasting(){
    ottieniforecasting();




    var forecasting = JSON.parse(sessionStorage.getItem("Forecasting"));

    var produzione = forecasting.produzione.values;
    produzione = Object.values(produzione)

    const labels = produzione.map((_, index) => index + 1); // Esempio: array di etichette
    const values = produzione; // Esempio: array di valori

    console.log(produzione)
    var ctx = document.getElementById('myChart').getContext('2d');
    var myChart = new Chart(ctx, {

        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Forecasting Energia Generata',
                data: values,
                backgroundColor: 'rgba(0, 128, 255, 0.5)',
                borderColor: 'rgba(0, 128, 255, 1)',
                borderWidth: 1,
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: false,
                    title: {
                        display: true,
                        text: 'KWh'
                    }
                },
                x: {
                    title: {
                        diplay: true,
                        text: "MTU"
                    }
                }
            },
        }
    });

    sessionStorage.setItem("chart", myChart);
}

function updateForecasting(){
    var myChart = sessionStorage.getItem("chart");

    myChart.reset();
    vediforecasting();
}



