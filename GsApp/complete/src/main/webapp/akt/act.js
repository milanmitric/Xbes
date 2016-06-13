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
        }).state('actsearchresults', {
                                              url: '/actsearchresults/{pretraga}',
                                              params: {akt: null},
                                              views: {
                                                  'content': {
                                                      templateUrl: 'akt/searchResults.html',
                                                      controller: 'ActSearchResultsController'
                                                  },
                                                  'navbar':{
                                                      templateUrl: 'navbar/navbar.html',
                                                      controller: 'NavbarController'
                                                  }
                                              }
          }).state('actsearchbytagresults', {
                                                          url: '/actsearchbytagresults/:pretraga?tag',
                                                          views: {
                                                              'content': {
                                                                  templateUrl: 'akt/searchResults.html',
                                                                  controller: 'ActSearchByTagResultsController'
                                                              },
                                                              'navbar':{
                                                                  templateUrl: 'navbar/navbar.html',
                                                                  controller: 'NavbarController'
                                                              }
                                                          }
          }).state('aktdetail', {
                                                                      url: '/aktdetail/{id}',
                                                                      views: {
                                                                          'content': {
                                                                              templateUrl: 'akt/aktdetail.html',
                                                                              controller: 'ActDetailController'
                                                                          },
                                                                          'navbar':{
                                                                              templateUrl: 'navbar/navbar.html',
                                                                              controller: 'NavbarController'
                                                                          }
                                                                      }
          }).state('myacts', {
                                                                                  url: '/myacts',
                                                                                  views: {
                                                                                      'content': {
                                                                                          templateUrl: 'akt/showMyActs.html',
                                                                                          controller: 'ShowMyActsController'
                                                                                      },
                                                                                      'navbar':{
                                                                                          templateUrl: 'navbar/navbar.html',
                                                                                          controller: 'NavbarController'
                                                                                      }
                                                                                  }
          });


        //$locationProvider.html5Mode(true);

    });
