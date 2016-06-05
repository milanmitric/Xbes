'use strict';

angular.module('xapp')
    .controller('NewAmandmanController', function ($scope, $state, authService,amandmanService) {

    $scope.create = function(){
        amandmanService.saveAmandman($scope.document,function(response){
                 alertify.success("Uspijesno ste dodali amandman!");
        },function(response){
                 alertify.error("Dodavanje nije uspjelo!");
        });
    }
    });
