'use strict';

angular.module('xapp')
    .controller('SignoutController', function ($rootScope, $scope, authService, $location, $state) {

		//button clicked
	    $scope.signout = function(){

		   authService.signout(
			   function(res){
				   console.log(res.data.msg);
				   $state.go('home');
				    authService.authenticate(
                                   function(res){
                                       $rootScope.USER={
                                           username:res.data.username,
                                           ime:res.data.ime,
                                           prezime:res.data.prezime,
                                           role:res.data.role
                                       };
                                       console.log($rootScope.user);
                                   },
                                   function(res){
                                       $rootScope.USER={
                                           username:"",
                                           ime:"",
                                           prezime:"",
                                           role:""
                                       };
                                   }
                               );
                           $state.go("home");
			   }
			   ,function(response){
					console.log(response.data.msg);
			   });
  		};



    });
