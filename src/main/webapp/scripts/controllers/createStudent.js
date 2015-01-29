'use strict';

angular.module('MyApp')
  .controller('CreateStudentCtrl', function ($scope, $modalInstance, $http, child, mode) {
  
    $scope.child = child;
    
    $scope.disable = mode === 'view' ? 'true' : 'false';
    
    var originData = child;
	
	$scope.ok = function () {
		//mask();
		if (mode === 'create')
		{
			$http.post('/ChildrenManage/webapi/Children', $scope.child).
				success(function(data, status, headers, config) {
					//unmask();
					//add succes  info;
					$modalInstance.close();
				}).
				error(function(data, status, headers, config) {
					//unmask
					//add error info
					//$modalInstance.close();
				});
		}
		else if (mode === 'update')
		{
			$http.put('/ChildrenManage/webapi/Children/'+$scope.child.id, $scope.child).
				success(function(data, status, headers, config) {
					$modalInstance.close();
				}).
				error(function(data, status, headers, config) {
					
				})
		}
		else if (mode === 'view')
		{
			$modalInstance.dismiss('cancel');
		}
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