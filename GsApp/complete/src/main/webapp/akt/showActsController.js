'use strict';

angular.module('xapp')
    .controller('ShowActsController', function ($scope, $state, aktService) {

    aktService.getAllActs(function(response){
                console.log("ODGOVOR SA SERVERA!!!");
                console.log(response.data);
                $scope.collection = response.data.proposed;
                $scope.collectionOdobreni = response.data.approved;
                },function(response){

    });
    });
