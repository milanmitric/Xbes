'use strict';

angular.module('xapp')
    .controller('SearchAmandmansController', function ($scope, $state, amandmanService) {

        $scope.search = function(){

                console.log("Sadrzaj polja:")
                console.log($scope.searchField);
            }

    });
