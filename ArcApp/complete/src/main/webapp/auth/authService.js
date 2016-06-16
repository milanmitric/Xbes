angular.module('xapp')
.service('authService', function($http){
	return{
		signin: function(user, onSuccess, onError){
		var req = {
		    method: 'POST',
		    url: '/api/signin',
		    headers: {
		        'Content-Type': 'application/json',
		    },
		    data: user
		}
		$http(req).then(onSuccess, onError);
		},
		signup: function(user, onSuccess, onError){
		var req = {
		    method: 'POST',
		    url: '/api/signup',
		    headers: {
		        'Content-Type': 'application/json'
		    },
		    data: user
		}
		$http(req).then(onSuccess, onError);
		},
        signout: function(onSuccess, onError){
		var req = {
		    method: 'POST',
		    url: '/api/signout'
		}
		$http(req).then(onSuccess, onError);

        },
        authenticate: function(onSuccess, onError){
        var req = {
		    method: 'POST',
		    url: '/api/authenticate'
		}	

		$http(req).then(onSuccess, onError);


        }
	}
});