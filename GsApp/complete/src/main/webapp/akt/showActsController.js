'use strict';

angular.module('xapp')
    .controller('ShowActsController', function ($scope, $state, aktService) {

    aktService.getAllActs(function(response){
                $scope.collection = response.data;

                },function(response){

    });
    });
