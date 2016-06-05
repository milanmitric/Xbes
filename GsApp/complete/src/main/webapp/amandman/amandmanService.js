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
		saveAmandman: function(amandman,onSuccess, onError){
                             var req = {
                             					method: 'POST',
                             					url: '/api/amandman',
                             					headers: {
                                                		        'Content-Type': 'application/xml'
                                                },
                             					data: amandman
                             				}
                             				$http(req).then(onSuccess, onError);
        },
		delete: function(onSuccess,onError){

		},
		get:function(onSuccess, onError){

		},
		update:function(onSuccess, onError){

		}
	}
});