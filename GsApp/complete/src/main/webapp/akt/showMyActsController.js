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

        aktService.rejectAct(aktID,function(response){
                     var indexForDelete= -1;
                                                    for(var i = 0;i<$scope.collection.length;i++){
                                                        if($scope.collection[i].documentId===aktID){
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
