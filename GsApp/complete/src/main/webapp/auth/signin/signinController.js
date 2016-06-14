'use strict';

angular.module('xapp')
    .controller('SigninController', function ($rootScope, $scope, authService, $location, $state) {

		//button clicked
	    $scope.signin = function(){

		   authService.signin(
               $scope.user,
			   function(response){
				   console.log(response);
				   if(response.data.success=='true'){
				   alertify.success("SUCCESS");
				    $rootScope.USER={
                                           username:res.data.username,
                                           ime:res.data.ime,
                                           prezime:res.data.prezime,
                                           role:res.data.role
                                       };
				   }else{
				   alertify.error("FAIL");
				   }
			   }
			   ,function(response){
					alertify.error("ERROR");
			   });
  		};



    });
