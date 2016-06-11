'use strict';

angular.module('xapp')
    .controller('SednicaHomeController', function ($scope, $state, authService, amandmanService, sednicaService) {

     //load sednica status
     sednicaService.getSednicaStatus(
                function(res){

                        $scope.sednicaState=res.data;
                        if($scope.sednicaState){
                            $scope.buttonColor="danger";
                            $scope.buttonTxt="[SEDNICA U TOKU...] ZAVRSI SEDNICU";
                        }else{
                            $scope.buttonColor="success";
                            $scope.buttonTxt="ZAPOCNI SEDNICU";
                        }

                },
                function(res){

                }
     );


    //btn
    $scope.startStopSednica=function(){

        if(!$scope.sednicaState){
            $scope.buttonColor="danger";
            $scope.buttonTxt="[SEDNICA U TOKU...] ZAVRSI SEDNICU";
        }else{
            $scope.buttonColor="success";
            $scope.buttonTxt="ZAPOCNI SEDNICU";
        }
        $scope.sednicaState=!$scope.sednicaState;
        sednicaService.setSednicaStatus(
            $scope.sednicaState,
            function(res){

            },
            function(res){

        });
    }




    });
