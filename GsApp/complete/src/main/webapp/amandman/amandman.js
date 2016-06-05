'use strict';

angular.module('xapp')
    .config(function ($stateProvider) {
        $stateProvider
        .state('newamandman', {
                url: '/newamandman',
                views: {
                    'content': {
                        templateUrl: 'amandman/newAmandman.html',
                        controller: 'NewAmandmanController'
                    },
                    'navbar':{
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    }
                }
        }).state('searchamandmans', {
                url: '/searchamandmans',
                views: {
                    'content': {
                        templateUrl: 'amandman/searchAmandmans.html',
                        controller: 'SearchAmandmansController'
                    },
                    'navbar':{
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    }
                }
        }).state('showamandmans', {
                url: '/showamandmans',
                views: {
                    'content': {
                        templateUrl: 'amandman/showAmandmans.html',
                        controller: 'ShowAmandmansController'
                    },
                    'navbar':{
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    }
                }
              });


        //$locationProvider.html5Mode(true);

    });
