'use strict';

angular.module('xapp')
    .controller('ActDetailController', function ($scope, $state, $stateParams,aktService,$sce) {


    $scope.showPdf = false;
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
                        console.log("ODGOVOR SA PDF-OM");
                        console.log(response);
                        var file = new Blob([response.data], {type: 'application/pdf'});
                        var fileURL = URL.createObjectURL(file);
                        $scope.showPdf = true;
                        $scope.content = $sce.trustAsResourceUrl(fileURL);
                    },
                    function(response){

                    }
                 );
     }



 });
