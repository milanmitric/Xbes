'use strict';

angular.module('xapp')
    .controller('NavbarController', function ($scope, $state, authService, sednicaService) {



    sednicaService.getSednicaStatus(
    function(res){
    $scope.sednicaStatus=res.data;
    },
    function(res){
    $scope.sednicaStatus=false;
    }

    );



    });
