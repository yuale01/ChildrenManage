'use strict';

angular.module('MyApp')
  .controller('CreateStudentCtrl', function ($scope, $modalInstance, $http) {
  
    $scope.child = {
		"basicInfo": {},
		"contactInfo": {},
		"bodyInfo": {}
	};
	
	$scope.ok = function () {
		//mask();
		//var aa = this.child;
		$http.post('/ChildrenManage/webapi/Children', this.child).
			success(function(data, status, headers, config) {
				//unmask();
				//add succes  info;
				$modalInstance.close();
			}).
			error(function(data, status, headers, config) {
				//unmask
				//add error info
				$modalInstance.close();
			});
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
	$scope.open = function($event) {
      $event.preventDefault();
      $event.stopPropagation();

      $scope.opened = true;
    };
	
	$scope.format = 'yyyy-MM-dd';
	
  });