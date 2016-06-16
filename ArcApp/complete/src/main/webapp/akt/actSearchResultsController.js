'use strict';

angular.module('xapp')
    .controller('ActSearchResultsController', function ($scope, $state, $stateParams,aktService) {


//        $scope.collectionPredlozeni = [];
//        $scope.collectionUsvojeni = [];
//$scope.poOvome=$stateParams.akt;
$scope.poOvome=$stateParams.pretraga;

                aktService.searchActs(
                    $stateParams.pretraga,
                    //$stateParams.akt,
                    function (response){
                     console.log("ODGOVOR SA SERVERA SA AKTIMA!");
                     console.log(response.data);
                     $scope.collectionPredlozeni = response.data.predlozeni;
                     $scope.collectionUsvojeni = response.data.usvojeni;
                    },
                    function(response){

                });




console.log("DOSLO JE :"+$stateParams.akt);









    });
