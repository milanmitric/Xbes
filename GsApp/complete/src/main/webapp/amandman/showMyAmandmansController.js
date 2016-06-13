'use strict';

angular.module('xapp')
    .controller('ShowMyAmandmansController', function ($scope, $state, $stateParams,aktService) {

     $scope.collection = [{naslov : "Moj Amandman 1", id : "IdOdAmandmana"}];
//    aktService.getMyAllActs(function(response){
////                console.log("ODGOVOR SA SERVERA!!!");
////                console.log(response.data);
//
////                $scope.collectionOdobreni = response.data.approved;
//                },function(response){
//
//    });

    });
