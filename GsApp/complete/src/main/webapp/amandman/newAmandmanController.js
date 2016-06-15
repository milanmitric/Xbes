'use strict';

angular.module('xapp')
    .controller('NewAmandmanController', function ($scope, $state, authService,amandmanService) {

    $scope.create = function(){
        var str = $scope.document;
        var akt = "";
        var pos = str.indexOf("<Sadrzaj>");
        var posKraj = str.indexOf("</Sadrzaj>");
        var previousPos = 0;
        console.log("pos + " + pos);
        console.log("posKraj + " + posKraj);

        while(pos!=-1){
            console.log("pos + " + pos);
            console.log("posKraj + " + posKraj);
            var substring1 = str.substring(previousPos ,pos+9);
            var zaEskejp = str.substring(pos+9,posKraj);
            akt = akt + substring1;
            zaEskejp = zaEskejp.replace(/</g,"&lt;");
            zaEskejp = zaEskejp.replace(/>/g,"&gt;");
            akt = akt + zaEskejp;

            previousPos = posKraj;
            var pos = str.indexOf("<Sadrzaj>", posKraj+10);
            var posKraj = str.indexOf("</Sadrzaj>", posKraj+10);
        }
        var endstring = str.substring(previousPos ,str.length);
        akt = akt + endstring;

        console.log("AKT + " + akt);

        amandmanService.saveAmandman(akt,function(response){
                 alertify.success("Uspijesno ste dodali amandman!");
        },function(response){
                 alertify.error("Dodavanje nije uspjelo!");
        });
    }
    });
