angular.module('xapp')
.service('aktService', function($http){
	return{
		getAllActs: function(onSuccess, onError){
             var req = {
                            method: 'GET',
                            url: '/api/getallacts',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            }
                        }

             			$http(req).then(onSuccess, onError);
		},
		getMyAllActs: function(onSuccess, onError){
                     var req = {
                                    method: 'GET',
                                    url: '/api/getmyallacts',
                                    headers: {
                                        'Content-Type': 'application/x-www-form-urlencoded'
                                    }
                                }

                     			$http(req).then(onSuccess, onError);
        },

		searchActs: function(parametar,onSuccess, onError){
                     var req = {
                                    method: 'GET',
                                    url: '/api/searchacts/'+parametar,
                                    headers: {
                                        'Content-Type': 'application/x-www-form-urlencoded'
                                    }
                                }

                     			$http(req).then(onSuccess, onError);
        },searchActsByTag: function(tagParam,parametarParam,onSuccess, onError){
                              var req = {
                                             method: 'GET',
                                             url: '/api/tagsearch',
                                             params : {tag:tagParam , parametar:parametarParam},
                                             headers: {
                                                 'Content-Type': 'application/x-www-form-urlencoded'
                                             }
                                         }

                              			$http(req).then(onSuccess, onError);
                 },
		saveAct: function(act,onSuccess, onError){
                     var req = {
                     					method: 'POST',
                     					url: '/api/akt',
                     					headers: {
                                        		        'Content-Type': 'application/xml'
                                        },
                     					data: act
                     				}
                     				$http(req).then(onSuccess, onError);
        },
		delete: function(onSuccess,onError){

		},
		getProposed:function(onSuccess, onError){

		var req = {
                                    method: 'GET',
                                    url: '/api/getproposed',
                                    headers: {
                                        'Content-Type': 'application/x-www-form-urlencoded'
                                    }
                                }

                     			$http(req).then(onSuccess, onError);

		},
		download:function(id, onSuccess, onError){

            $http.post('/api/download/'+id,{}, {responseType:'arraybuffer'})
                .then(onSuccess,onError);

		},
		getActById:function(docID,onSuccess, onError){

        		var req = {
                                            method: 'POST',
                                            url: '/api/getactbyid',
                                            headers: {
                                                'Content-Type': 'text/html'
                                            },
                                            transformResponse: [function (data) {
                                                                        return data;
                                                                    }]
                                            ,data:docID
                                        }

                             			$http(req).then(onSuccess, onError);

        		} ,
	    rejectAct:function(docID,onSuccess, onError){

                		var req = {
                                                    method: 'POST',
                                                    url: '/api/opozoviakt',
                                                    headers: {
                                                        'Content-Type': 'application/x-www-form-urlencoded'
                                                    },
                                                    data:docID
                                                }

                                     			$http(req).then(onSuccess, onError);

                		}
        	}
});