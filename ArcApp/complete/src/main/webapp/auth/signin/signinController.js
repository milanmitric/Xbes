'use strict';

angular.module('xapp')
    .controller('SigninController', function ($rootScope, $scope, authService, $location, $state) {

		//button clicked
	    $scope.signin = function(){

		   authService.signin(
               $scope.user,
			   function(res){
				   console.log(res);
				   if(res.data.success=='true'){
				   alertify.success("SUCCESS");
				   console.log(res.data);
				    $rootScope.USER={
                                           username:res.data.username,
                                           ime:res.data.ime,
                                           prezime:res.data.prezime,
                                           role:res.data.role
                                       };
				   }else{
				   alertify.error("FAIL");
				   }
				   $scope.user={};
				   $state.go("home");
			   }
			   ,function(response){
					alertify.error("ERROR");
			   });
  		};



    });
