'use strict';

angular.module('xapp', ['ngResource', 'ui.router', 'angularModalService','ngSanitize'])
    .config(function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/home');
         $stateProvider
           .state('home', {
                   url: '/home',
                   views: {
                       'content': {
                           templateUrl: 'home/home.html',
                           controller: 'HomeController'
                       },
                       'navbar':{
                           templateUrl: 'navbar/navbar.html',
                           controller: 'NavbarController'
                       }
                   }
               });
        //$locationProvider.html5Mode(true);


    }).run(function($rootScope,  $state, authService) {

          $rootScope.$on("$locationChangeStart", function(event, next, current) {
             // handle route changes
             // console.log(event);
             //$state.go('signup');
          });

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

        });
