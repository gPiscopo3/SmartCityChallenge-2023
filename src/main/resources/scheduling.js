function ottieniSchedulingTotale(){
    var target = "http://localhost:8080/rec/rec/forecasting/2023-07-14";
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if(this.status == 200){
                response = xhttp.responseText;
                sessionStorage.setItem("Scheduling",response);
                window.location.href ="Application.html";


            }
            else
                alert(xhttp.responseText);
        }

    };

    xhttp.open("GET",target,false);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
    xhttp.setRequestHeader("Access-Control-Allow-Headers", "*");
    xhttp.setRequestHeader("Access-Control-Allow-Methods", "*");
    xhttp.send();




}


function vediSchedulingTotale(){

    //var scheduling = JSON.parse(sessionStorage.getItem("Scheduling"));
    var scheduling = '{\n' +
        '  "_id": {\n' +
        '    "$oid": "64afc2542b032358d851aeb7"\n' +
        '  },\n' +
        '  "consumatori": [\n' +
        '    {\n' +
        '      "allocazione": [\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false\n' +
        '      ],\n' +
        '      "giorno": {\n' +
        '        "$date": "2023-07-13T00:00:00.000Z"\n' +
        '      },\n' +
        '      "smartMeter": "a"\n' +
        '    },\n' +
        '    {\n' +
        '      "allocazione": [\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false\n' +
        '      ],\n' +
        '      "giorno": {\n' +
        '        "$date": "2023-07-13T00:00:00.000Z"\n' +
        '      },\n' +
        '      "smartMeter": "b"\n' +
        '    },\n' +
        '    {\n' +
        '      "allocazione": [\n' +
        '        false,\n' +
        '        true,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false\n' +
        '      ],\n' +
        '      "giorno": {\n' +
        '        "$date": "2023-07-13T00:00:00.000Z"\n' +
        '      },\n' +
        '      "smartMeter": "c"\n' +
        '    },\n' +
        '    {\n' +
        '      "allocazione": [\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false\n' +
        '      ],\n' +
        '      "giorno": {\n' +
        '        "$date": "2023-07-13T00:00:00.000Z"\n' +
        '      },\n' +
        '      "smartMeter": "d"\n' +
        '    },\n' +
        '    {\n' +
        '      "allocazione": [\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false\n' +
        '      ],\n' +
        '      "giorno": {\n' +
        '        "$date": "2023-07-13T00:00:00.000Z"\n' +
        '      },\n' +
        '      "smartMeter": "e"\n' +
        '    }\n' +
        '  ],\n' +
        '  "giorno": {\n' +
        '    "$date": "2023-07-13T00:00:00.000Z"\n' +
        '  }\n' +
        '}'

    var row1="";
    var sm ="";
    var boh="";

    //prima riga
    row1 = row1 + "<span></span>";
    //row1 = row1 +"<ul></ul>";
    for (let i=1; i<49; i++) {
        row1 = row1 + "<ul>" + i + "</ul>";
    }
    document.getElementById("row1").innerHTML = row1;

    //linea separatrice
    for(let i=1; i<49; i++){
        boh = boh + "<span></span>";
    }
    document.getElementById("boh").innerHTML = boh;

    //righe tabella con cose dentro
    for (let i=1; i<3; i++){
        sm = sm +"<div class=\"chart-row\">\n" +
            "          <div class=\"chart-row-item\">"+i+"</div>\n" +
            "          <ul class=\"chart-row-bars\">\n" +
            "              <li class=\"chart-li-four\"></li></li>\n" +
            "          </ul>\n" +
            "      </div>";

    }

    document.getElementById("sm").innerHTML =sm;


}

function isMTU(){

     var schedulingj = '{\n' +
        '  "_id": {\n' +
        '    "$oid": "64afc2542b032358d851aeb7"\n' +
        '  },\n' +
        '  "consumatori": [\n' +
        '    {\n' +
        '      "allocazione": [\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false\n' +
        '      ],\n' +
        '      "giorno": {\n' +
        '        "$date": "2023-07-13T00:00:00.000Z"\n' +
        '      },\n' +
        '      "smartMeter": "a"\n' +
        '    },\n' +
        '    {\n' +
        '      "allocazione": [\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false\n' +
        '      ],\n' +
        '      "giorno": {\n' +
        '        "$date": "2023-07-13T00:00:00.000Z"\n' +
        '      },\n' +
        '      "smartMeter": "b"\n' +
        '    },\n' +
        '    {\n' +
        '      "allocazione": [\n' +
        '        false,\n' +
        '        true,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false\n' +
        '      ],\n' +
        '      "giorno": {\n' +
        '        "$date": "2023-07-13T00:00:00.000Z"\n' +
        '      },\n' +
        '      "smartMeter": "c"\n' +
        '    },\n' +
        '    {\n' +
        '      "allocazione": [\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false\n' +
        '      ],\n' +
        '      "giorno": {\n' +
        '        "$date": "2023-07-13T00:00:00.000Z"\n' +
        '      },\n' +
        '      "smartMeter": "d"\n' +
        '    },\n' +
        '    {\n' +
        '      "allocazione": [\n' +
        '        false,\n' +
        '        false,\n' +
        '        false,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        true,\n' +
        '        false,\n' +
        '        false,\n' +
        '        false\n' +
        '      ],\n' +
        '      "giorno": {\n' +
        '        "$date": "2023-07-13T00:00:00.000Z"\n' +
        '      },\n' +
        '      "smartMeter": "e"\n' +
        '    }\n' +
        '  ],\n' +
        '  "giorno": {\n' +
        '    "$date": "2023-07-13T00:00:00.000Z"\n' +
        '  }\n' +
        '}'

    var scheduling = JSON.parse(schedulingj);

     for (let i = 0; i<scheduling.consumatori.length; i++) {

         alert(scheduling.consumatori[i]);
     }

    var sm ='';

    for(let x in scheduling.consumatori){
        sm = sm +"<div class=\"chart-row\">\n" +
            "          <div class=\"chart-row-item\">"+scheduling.consumatori[x]+"</div>\n";
            for( let i in scheduling.consumatori[x].allocazione){
                if(scheduling.consumatori[x].allocazione[i] == true){

                }
            }

    }

}