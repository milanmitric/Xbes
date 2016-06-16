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

      $scope.download = function(id){
        //alert(id);
        var res = id.slice(0, id.length-4);
                aktService.download(
                        res,
                        function(response){
                              //console.log("Stigao odgovor sa servera!");

                        },
                        function(response){

                        }
                     );
         }





    });
