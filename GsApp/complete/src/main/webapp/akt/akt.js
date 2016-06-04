'use strict';

angular.module('xapp')
    .config(function ($stateProvider) {
        $stateProvider
        .state('newakt', {
                url: '/newakt',
                views: {
                    'content': {
                        templateUrl: 'akt/newAkt.html',
                        controller: 'NewAktController'
                    },
                    'navbar':{
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    }
                }
        })
        ;


        //$locationProvider.html5Mode(true);

    });
