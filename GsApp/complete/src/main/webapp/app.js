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
                           //controller: 'WelcomeController'
                       }
                   }
               });
        //$locationProvider.html5Mode(true);

    });
