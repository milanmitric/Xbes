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
		delete: function(onSuccess,onError){

		},
		get:function(onSuccess, onError){

		},
		update:function(onSuccess, onError){

		}
	}
});