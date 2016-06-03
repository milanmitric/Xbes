'use strict';

angular.module('xapp')
    .controller('SignupController', function ($scope, authService, $location) {

 		//button clicked
	    $scope.signup = function(){
            console.log($scope.user);
		   authService.signup(
			   $scope.user,
			   function(response){
				   console.log(response.data);
			   }
			   ,function(response){
				    console.log(response.data);
			   });
  		};


    });
