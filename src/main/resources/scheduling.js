function ottieniScheduling(){
	 var sm = document.getElementById("smartmeter").value;
	
	if(sm == "" || sm == null)
		ottieniSchedulingTotale();
	else
		ottieniSchedulingSM();
		
}


function ottieniSchedulingTotale(){
    var data = document.getElementById("date").value;
    var sm = document.getElementById("smartmeter").value;
    var target = "http://localhost:8080/rec/rec/scheduling";
    var url="";
	
	
    if (sm != null) {
        url = target + "/" + data + "?smartMeter=" + sm;
    }else url = target +"/"+data;



    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if(this.status == 200){
                response = xhttp.responseText;
                sessionStorage.setItem("Scheduling",response);
                //   window.location.href ="Sc.html";


            }
            else{
                alert("Non è presente uno scheduling per la data specificata");
            }

        }

    };

    xhttp.open("GET",url,false);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
    xhttp.send();

    vediSchedulingTotale();
}

function ottieniSchedulingSM(){
    var data = document.getElementById("date").value;
    var sm = document.getElementById("smartmeter").value;
    var target = "http://localhost:8080/rec/rec/scheduling";
    var url="";



    console.log(sm.value);


    if (data != null) {
        url = target + "/" + data + "?smartMeter=" + sm;
    }else if(sm != null ){
        url = target +"?smartMeter="+sm;
    }else window.location.href = "Sc.html";

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            if(this.status == 200){
                response = xhttp.responseText;
                sessionStorage.setItem("Scheduling",response);
                //   window.location.href ="Sc.html";


            }
            else {
                alert("L'appliance specificato non è stato schedulato");
           
            }
        }

    };

    xhttp.open("GET",url,false);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
    xhttp.send();

    vediSchedulingSM();
}

function vediSchedulingSM(){
    var scheduling = JSON.parse(sessionStorage.getItem("Scheduling"));

    var result = "";
    var count = 0;
    result = result + '<table align ="center">';
    result = result + '<tr>';
    result = result + '<th></th>';
    for(let i=0; i<24; i++){

        result = result + '<th>'+i+'</th>';
    }
    result = result + '</tr>';

    result = result + '<th>' + scheduling.smartMeter + '</th>';
    for (i in scheduling.allocazione) {
        var all = scheduling.allocazione[i];

        for (j in all) {

            if (all[j] == true) {
                result = result + '<th bgcolor="#000099"></th>';
            } else {
                result = result + '<th></th>';
            }

        }
        result = result + '</tr>';
    }
    result = result + '</table>';
    document.getElementById("result").innerHTML = result;

}

function vediSchedulingTotale(){

    var scheduling = JSON.parse(sessionStorage.getItem("Scheduling"));


    var result = "";
    var count = 0;
    result = result + '<table align ="center">';
    result = result + '<tr>';
    result = result + '<th></th>';
    for(let i=0; i<24; i++){

        result = result + '<th>'+i+'</th>';
    }
    result = result + '</tr>';



    for (x in scheduling.consumatori) {
        count = count+1;
        if (count % 2 ==0){
            result = result + '<tr bgcolor="#E6E6E6">';
        }
        else result = result + '<tr bgcolor="#ffffff">';


        result = result + '<th>' + scheduling.consumatori[x].smartMeter + '</th>';
        for (i in scheduling.consumatori[x].allocazione) {
            var all = scheduling.consumatori[x].allocazione[i];

            for (j in all) {

                if (all[j] == true) {
                    result = result + '<th bgcolor="#000099"></th>';
                } else {
                    result = result + '<th></th>';
                }

            }
            result = result + '</tr>';
        }


    }


    result = result + '</table>';


    document.getElementById("result").innerHTML = result;
}
