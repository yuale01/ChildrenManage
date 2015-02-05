'use strict';

angular.module('app.children.controllers')
  .controller('MsgDialogCtrl', ['$scope', '$modalInstance', 'messageType', 'message', function ($scope, $modalInstance, messageType, message) {
	  
	  $scope.messageType = messageType;
	  $scope.message = message;
  
	  $scope.ok = function () {
		  $modalInstance.close();
		
	  };

	  $scope.cancel = function () {
		  $modalInstance.dismiss();
	  };
   
  }]);