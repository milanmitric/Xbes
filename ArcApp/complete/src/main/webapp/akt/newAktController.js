'use strict';

angular.module('xapp')
    .controller('NewAktController', function ($scope, $state, authService,aktService) {



    $scope.create = function(){
            console.log("Sadrzaj dokumenta:")
            console.log($scope.document);
            aktService.saveAct($scope.document,function(response){
                             alertify.success("Uspijesno ste dodali akt!");
                    },function(response){
                             alertify.error("Dodavanje akta nije uspjelo!");
                    });
    }

    });
