'use strict';

angular.module('MyApp')
  .controller('CreateStudentCtrl', function ($scope, $modalInstance, $http, child, mode) {
  
    $scope.child = child;
    
    $scope.disable = mode === 'view' ? 'true' : 'false';
    
    var originChild = JSON.parse(JSON.stringify(child));
    
    var childStructure = [
                          {
                        	  category: 'basicInfo',
                        	  fields: ['name', 'grade', 'className', 'gender', 'nation', 'birthday',
                        	           'idCardNo', 'huKou', 'huKouAddr', 'migration', 'onlyChild', 'minLiving',
                        	           'imburse', 'orphan', 'pathography', 'specialPerformance', 'otherAnnouncement']
                          },
                          {
                        	  category: 'contactInfo',
                        	  fields: ['motherName', 'motherCompany', 'motherContact', 'motherIdCard',
                        	           'fatherName', 'fatherCompany', 'fatherContact', 'fatherIdCard',
                        	           'livingAddr', 'otherContact']
                          },
                          {
                        	  category: 'bodyInfo',
                        	  fields: ['doffDon', 'eating', 'toileting', 'sleeping', 'sleepingInfo', 'eatingSpeed', 'appetite', 'pickyEating',
                        	           'pickyEatingInfo', 'eatingAbility', 'foodAllergy', 'foodAllergyInfo', 'healthdStatus']
                          }];
    
    var getChangedData = function() {
    	var changedChild = {};
    	var currentChild = $scope.child;
    	for (var i=0; i<childStructure.length; i++)
    	{
    		var category = childStructure[i].category;
    		var fields = childStructure[i].fields;
    		
    		for (var j=0; j<fields.length; j++)
    		{
    			var field = fields[j];
    			if (currentChild[category][field] != originChild[category][field])
    			{
    				if (changedChild[category] == undefined)
    					changedChild[category] = {};
    				changedChild[category][field] = currentChild[category][field];
    			}
    				
    		}
    	}
    	return changedChild;
    }
	
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
    	getChangedData();
    	$scope.child = originChild;
    	$modalInstance.dismiss('cancel');
    };
    
	$scope.format = 'yyyy-MM-dd';
	$scope.open = function($event) {
		$event.preventDefault();
		$event.stopPropagation();

		$scope.opened = true;
    };
	
  });