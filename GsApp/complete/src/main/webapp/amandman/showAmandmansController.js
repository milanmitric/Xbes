'use strict';

angular.module('xapp')
    .controller('ShowAmandmansController', function ($scope, $state, amandmanService) {

    amandmanService.getAllAmandmans(function(response){
                $scope.collection = response.data;

                },function(response){

    });
    });
