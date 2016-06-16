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
        }) .state('amandmantsforakt', {
                          url: '/amandmantsforakt/{id}',
                          views: {
                              'content': {
                                  templateUrl: 'sednica/amandmansForAkt.html',
                                  controller: 'AmandmansForAktController'
                              },
                              'navbar':{
                                  templateUrl: 'navbar/navbar.html',
                                  controller: 'NavbarController'
                              }
                          }
                  });


        //$locationProvider.html5Mode(true);

    });
