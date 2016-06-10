'use strict';

angular.module('xapp')
    .controller('ActSearchResultsController', function ($scope, $state, $stateParams,aktService) {


//        $scope.collectionPredlozeni = [];
//        $scope.collectionUsvojeni = [];

                aktService.searchActs($stateParams.pretraga,function (response){
                 console.log("ODGOVOR SA SERVERA SA AKTIMA!");
                 console.log("ODGOVOR SA SERVERA SA AKTIMA!");
                 console.log(response.data);
                 $scope.collectionPredlozeni = response.data.predlozeni;



                 $scope.collectionUsvojeni = response.data.usvojeni;
                },function(response){

                });



    });
