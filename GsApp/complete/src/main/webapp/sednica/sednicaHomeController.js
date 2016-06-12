'use strict';

angular.module('xapp')
    .controller('SednicaHomeController', function ($scope, $state, authService, amandmanService, sednicaService, aktService, ModalService) {


     //load sednica status
     sednicaService.getSednicaStatus(
                function(res){
                        $scope.sednicaState=res.data;
                        if($scope.sednicaState){
                            $scope.buttonColor="danger";
                            $scope.buttonTxt="[SEDNICA U TOKU...] ZAVRSI SEDNICU";
                            getProposedAkts();
                        }else{
                            $scope.buttonColor="success";
                            $scope.buttonTxt="ZAPOCNI SEDNICU";
                            $scope.akts=[];
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
            getProposedAkts();
        }else{
            $scope.buttonColor="success";
            $scope.buttonTxt="ZAPOCNI SEDNICU";
            $scope.akts=[];
        }
        $scope.sednicaState=!$scope.sednicaState;
        sednicaService.setSednicaStatus(
            $scope.sednicaState,
            function(res){
            },
            function(res){
        });
    }

     //func for getting acts(proposed ones)
     var getProposedAkts=function(){
        aktService.getProposed(function(response){
                    $scope.akts = response.data;
                    },function(response){
        });
    }


   //prihvati btn
   $scope.prihvati=function(){
    alert("prihvati");
   }

   //odbij btn
   $scope.odbij=function(){
       alert("odbij");
   }













    });
