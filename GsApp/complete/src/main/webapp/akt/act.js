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

        }).state('searchacts', {
                          url: '/searchacts',
                          views: {
                              'content': {
                                  templateUrl: 'akt/searchActs.html',
                                  controller: 'SearchAktController'
                              },
                              'navbar':{
                                  templateUrl: 'navbar/navbar.html',
                                  controller: 'NavbarController'
                              }
                          }
        }).state('showacts', {
                                                        url: '/showacts',
                                                        views: {
                                                            'content': {
                                                                templateUrl: 'akt/showActs.html',
                                                                controller: 'ShowActsController'
                                                            },
                                                            'navbar':{
                                                                templateUrl: 'navbar/navbar.html',
                                                                controller: 'NavbarController'
                                                            }
                                                        }
                                                });


        //$locationProvider.html5Mode(true);

    });
