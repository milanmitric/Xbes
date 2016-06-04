'use strict';

angular.module('xapp')
    .controller('SearchAktController', function ($scope, $state, aktService) {

    $scope.search = function(){

        console.log("Sadrzaj polja:")
        console.log($scope.searchField);
    }
    });
