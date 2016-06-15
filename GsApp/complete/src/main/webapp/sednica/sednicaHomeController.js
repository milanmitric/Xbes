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
        aktService.getProposed(
                    function(response){

                        $scope.akts = response.data;
                        $scope.btns=[];
                        for(var i=0; i<$scope.akts.length; i++){
                            $scope.btns.push(false);
                        }

                    },function(response){

                    }
        );
    }


   //prihvati btn
   $scope.prihvatiUNacelu=function(aktID, idx){
    //alert(aktID);
    $scope.btns[idx]=true;
    var list=[];
    list.push(aktID);
     sednicaService.prihvati(
                         list,
                         function(res){


                         },
                         function(res){


                         }
            );



   }

   //odbij btn
   $scope.odbij=function(aktID){
       //alert("odbij");
       var list=[];
       list.push('AKTSEODBIJA');
       list.push(aktID);
            sednicaService.prihvati(
                                list,
                                function(res){


                                },
                                function(res){


                                }
                   );

   }













    });
