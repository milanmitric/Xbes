'use strict';

angular.module('xapp')
    .controller('ShowMyActsController', function ($scope, $state, $stateParams,aktService) {

      aktService.getMyAllActs(
          function(response){
                          console.log("my acts response:");
                          console.log(response.data);
                          $scope.collection = response.data.proposed;
                          $scope.collectionOdobreni = response.data.approved;
          },
          function(response){

          }
     );


     $scope.opozovi=function(aktID){
        //TODO
        alert(aktID);
     }









    });
