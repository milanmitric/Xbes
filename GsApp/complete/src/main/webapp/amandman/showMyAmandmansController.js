'use strict';

angular.module('xapp')
    .controller('ShowMyAmandmansController', function ($scope, $state, $stateParams,aktService, amandmanService) {


             amandmanService.getMyAllAmandmans(
                      function(response){
                                      console.log("my amandmans response:");
                                      console.log(response.data);
                                      $scope.collection = response.data.proposed;
                                      $scope.collectionOdobreni = response.data.approved;
                      },
                      function(response){

                      }
                 );


                 $scope.opozovi=function(amID){
                    //TODO
                    alert(amID);
                 }







    });
