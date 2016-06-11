'use strict';

angular.module('xapp')
    .config(function ($stateProvider) {
        $stateProvider
        .state('sednicahome', {
                url: '/sednicahome',
                views: {
                    'content': {
                        templateUrl: 'sednica/sednicaHome.html',
                        controller: 'SednicaHomeController'
                    },
                    'navbar':{
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    }
                }
        });


        //$locationProvider.html5Mode(true);

    });
