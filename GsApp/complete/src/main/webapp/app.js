'use strict';

angular.module('xapp', ['ngResource', 'ui.router'])
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


    }).run(function($rootScope,  $state) {
          $rootScope.$on("$locationChangeStart", function(event, next, current) {
              // handle route changes
             // console.log(event);

              //$state.go('signup');



          });
          });
