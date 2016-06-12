'use strict';

angular.module('xapp')
    .controller('AmandmansForAktController', function ($stateParams, $scope, $state, authService, amandmanService, sednicaService, aktService) {

    /*GET AMANDMANST*/
    amandmanService.getAmandmantsForAKt(
            $stateParams.id,
            function(res){
                console.log("AMANDMANI: ");
                console.log(res.data);
                $scope.amandmands=res.data;
            },
            function(res){

            }
    );

    /*CLICKING LOGIC :D*/
    $scope.amandmantsToAdd=[];
    $scope.dodaj=function(amID){

        if($scope.amandmantsToAdd.indexOf(amID)==-1){
            $scope.amandmantsToAdd.push(amID);
            alertify.success('Dodato!');
        }else{
            $scope.amandmantsToAdd.splice($scope.amandmantsToAdd.indexOf(amID), 1);
             alertify.warning('Uklonjeno!');
        }

    }


    /*
    MAIN LOGIC HERE
    */
    
    /*DELIMICNO*/
    $scope.prihvatiDelimicno=function(){

    }

    /*SVE*/
    $scope.prihvatiSve=function(){

    }

    /*SVE*/
    $scope.odbij=function(){

    }

















    });