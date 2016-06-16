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

                $scope.amandmandsAll=[];
                $scope.btns=[];
                $scope.btns2=[];
                for(var i=0; i<$scope.amandmands.length; i++){
                    $scope.amandmandsAll.push($scope.amandmands[i].documentId);
                    $scope.btns.push('danger');
                    $scope.btns2.push('remove');
                }


            },
            function(res){

            }
    );

    /*CLICKING LOGIC :D*/

    $scope.amandmantsToAdd=[];

    $scope.dodaj=function(amID, idx){

        //alert(idx);

        if($scope.amandmantsToAdd.indexOf(amID)==-1){
            $scope.amandmantsToAdd.push(amID);
            //alertify.success('Dodato!');
            $scope.btns[idx]='success';
            $scope.btns2[idx]='ok';
        }else{
            $scope.amandmantsToAdd.splice($scope.amandmantsToAdd.indexOf(amID), 1);
            // alertify.warning('Uklonjeno!');
              $scope.btns[idx]='danger';
              $scope.btns2[idx]='remove';
        }

    }


    /*
    //-----MAIN LOGIC HERE-------//
    */

    /*DELIMICNO*/
    $scope.prihvatiDelimicno=function(){

            var list=[];
            list.push($stateParams.id);
            list = list.concat($scope.amandmantsToAdd);

            sednicaService.prihvati(
                    list,
                    function(res){


                    },
                    function(res){


                    }
            );
            $state.go('sednicahome');

    }

    /*SVE*/
    $scope.prihvatiSve=function(){

        var list=[];
        list.push($stateParams.id);
        list = list.concat($scope.amandmandsAll);
        console.log(list);
        sednicaService.prihvati(
                     list,
                     function(res){
                        $state.go('sednicahome');
                     },
                     function(res){

                     }
        );

    }

    /*SVE*/
    $scope.odbij=function(){

        var list=[];
        list.push('ODBIJAJUSE');
        list.push($stateParams.id);
        list = list.concat($scope.amandmandsAll);
        console.log(list);


        sednicaService.prihvati(
                         list,
                         function(res){


                         },
                         function(res){


                         }
         );

         $state.go('sednicahome');
    }

















    });