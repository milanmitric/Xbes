angular.module('xapp')
.service('amandmanService', function($http){
	return{
		getAllAmandmans: function(onSuccess, onError){
             var req = {
                            method: 'GET',
                            url: '/api/getallamandmans',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            }
                        }

             			$http(req).then(onSuccess, onError);
		},
		 getMyAllAmandmans: function(onSuccess, onError){
                                     var req = {
                                                    method: 'GET',
                                                    url: '/api/getmyallamandmans',
                                                    headers: {
                                                        'Content-Type': 'application/x-www-form-urlencoded'
                                                    }
                                                }

                                     			$http(req).then(onSuccess, onError);
         },
		saveAmandman: function(amandman,onSuccess, onError){
                             var req = {
                             					method: 'POST',
                             					url: '/api/amandman',
                             					headers: {
                                                		        'Content-Type': 'text/plain'
                                                },
                             					data: amandman
                             				}
                             				$http(req).then(onSuccess, onError);
        },
		getAmandmantsForAKt: function(aktID, onSuccess,onError){

                var req = {
                    method: 'POST',
                    url: '/api/getamandmantsforakt',
                    headers: {
                                    'Content-Type': 'application/xml'
                    },
                    params : {docID:aktID},

                }
                $http(req).then(onSuccess, onError);



		},
		get:function(onSuccess, onError){

		},
		update:function(onSuccess, onError){

		},
		rejectAmandman:function(docID,onSuccess, onError){

                        		var req = {
                                                            method: 'POST',
                                                            url: '/api/opozoviamandman',
                                                            headers: {
                                                                'Content-Type': 'application/x-www-form-urlencoded'
                                                            },
                                                            data:docID
                                                        }

                                             			$http(req).then(onSuccess, onError);


                	    }
	}
});