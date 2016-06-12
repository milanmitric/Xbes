'use strict';

angular.module('xapp')
    .controller('SearchAktController', function ($scope, $state, aktService) {



      $scope.options =["Naslov","Glava","Odeljak","Clan","Stav","Sadrzaj","Tacka","ZavrsniDeo","Organ","Broj","Datum","Potpisnik","Ime","Prezime"];

    $scope.search=function(param){
       // alert(param);
        $state.go("actsearchresults",{"akt":param});

    }


    });
