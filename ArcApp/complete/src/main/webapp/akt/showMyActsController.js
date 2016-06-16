'use strict';

angular.module('xapp')
    .controller('ShowMyActsController', function ($scope, $state, $stateParams,aktService) {

    $scope.collection = [{naslov : "Moj Akt 1", id : "IdOdAkta"}];
//    aktService.getMyAllActs(function(response){
////                console.log("ODGOVOR SA SERVERA!!!");
////                console.log(response.data);
//
////                $scope.collectionOdobreni = response.data.approved;
//                },function(response){
//
//    });

    });
