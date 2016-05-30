'use strict';

angular.module('xapp')
    .controller('SignoutController', function ($scope, authService, $location, $state) {

		//button clicked
	    $scope.signout = function(){

		   authService.signout(
			   function(response){
				   console.log(response.data.msg);
				   	$state.go('home');
			   }
			   ,function(response){
					console.log(response.data.msg);
			   });
  		};



    });
