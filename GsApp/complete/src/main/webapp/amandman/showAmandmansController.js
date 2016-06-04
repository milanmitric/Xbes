'use strict';

angular.module('xapp')
    .controller('ShowAmandmansController', function ($scope, $state, aktService) {

    aktService.getAllActs(function(response){
                $scope.collection = [];
                //                    $scope.result = response.data
                },function(response){

    });
    });
