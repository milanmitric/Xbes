'use strict';

angular.module('xapp')
    .controller('ActSearchByTagResultsController', function ($scope, $state, $stateParams,aktService) {





        aktService.searchActsByTag($stateParams.tag,$stateParams.pretraga,function (response){

                            console.log("STIGAO ODGOVOR SA SERVERA!!!!");
                            console.log(response.data);

                            $scope.collectionPredlozeni = response.data.predlozeni;
                            $scope.collectionUsvojeni = response.data.usvojeni;
                },function(response){

                });


    });
