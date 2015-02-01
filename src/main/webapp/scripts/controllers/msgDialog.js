'use strict';

angular.module('MyApp')
  .controller('MsgDialogCtrl', function ($scope, $modalInstance, messageType, message) {
	  
	  $scope.messageType = messageType;
	  $scope.message = message;
  
	  $scope.ok = function () {
		  $modalInstance.close();
		
	  };

	  $scope.cancel = function () {
		  $modalInstance.dismiss();
	  };
   
  });