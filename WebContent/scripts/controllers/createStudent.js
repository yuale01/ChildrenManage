'use strict';

angular.module('MyApp')
  .controller('CreateStudentCtrl', function ($scope, $modalInstance) {
	$scope.ok = function () {
      $modalInstance.close();
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
	$scope.open = function($event) {
      $event.preventDefault();
      $event.stopPropagation();

      $scope.opened = true;
    };
	
	$scope.format = 'yyyy/MM/dd';
	
  });