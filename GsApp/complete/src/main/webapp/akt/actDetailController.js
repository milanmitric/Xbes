'use strict';

angular.module('xapp')
    .controller('ActDetailController', function ($scope, $state, $stateParams,aktService,$sce) {



    aktService.getActById(
        $stateParams.id,
        function(response){
              console.log("Stigao odgovor sa servera!");
              //console.log(response.data)
             $scope.thisCanBeusedInsideNgBindHtml = $sce.trustAsHtml(response.data);
        },
        function(response){

        }
     );


     $scope.download = function(){

            aktService.download(
                    $stateParams.id,
                    function(response){
                          //console.log("Stigao odgovor sa servera!");

                    },
                    function(response){

                    }
                 );
     }



 });
