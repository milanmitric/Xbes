'use strict';

angular.module('xapp')
    .controller('AmandmansForAktController', function ($stateParams, $scope, $state, authService, amandmanService, sednicaService, aktService) {

    amandmanService.getAmandmantsForAKt(
    $stateParams.id,
    function(res){
        console.log("AMANDMANI: ");
        console.log(res.data);
        $scope.amandmands=res.data;
    },
    function(res){

    }
    );



    });
