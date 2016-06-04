'use strict';

angular.module('xapp')
    .controller('SignupController', function ($scope, authService, $location) {

 		//button clicked
	    $scope.signup = function(){
            console.log($scope.user);
		   authService.signup(
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
