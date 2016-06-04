'use strict';

angular.module('xapp')
    .controller('SigninController', function ($scope, authService, $location, $state) {

		//button clicked
	    $scope.signin = function(){

		   authService.signin(
               $scope.user,
			   function(response){
				   console.log(response);
				   if(response.data.success=='true'){
				   alertify.success("SUCCESS");
				   }else{
				   alertify.error("FAIL");
				   }
			   }
			   ,function(response){
					alertify.error("ERROR");
			   });
  		};



    });
