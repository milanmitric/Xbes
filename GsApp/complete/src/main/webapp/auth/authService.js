angular.module('xapp')
.service('authService', function($http){
	return{
		signin: function(username, password, onSuccess, onError){
		var req = {
		    method: 'POST',
		    url: '/api/signin',
		    headers: {
		        'Content-Type': 'application/json'
		    },
		    data: {
		    	username: username,
		    	password: password 
		    }
		}
		$http(req).then(onSuccess, onError);
		},
		signup: function(username, email, password, onSuccess, onError){
		var req = {
		    method: 'POST',
		    url: '/api/signup',
		    headers: {
		        'Content-Type': 'application/json'
		    },
		    data: {
		    	username: username,
		    	email: email,
		    	password: password 
		    }
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