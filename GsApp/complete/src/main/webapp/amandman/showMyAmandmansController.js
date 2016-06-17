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

                            amandmanService.rejectAmandman(amID,function(response){

                                var indexForDelete= -1;
                                for(var i = 0;i<$scope.collection.length;i++){
                                    if($scope.collection[i].documentId===amID){
                                        indexForDelete=i;
                                        break;
                                    }

                                }
                                if (indexForDelete > -1) {
                                    $scope.collection.splice(indexForDelete, 1);
                                }



                            },function(response){


                            })


                 }







    });
