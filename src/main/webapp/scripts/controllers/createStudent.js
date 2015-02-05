'use strict';

angular.module('app.children.controllers', ['ui.bootstrap'])
  .controller('CreateStudentCtrl', ['$scope', '$modalInstance', '$modal', '$http', 'child', 'mode', function ($scope, $modalInstance, $modal, $http, child, mode) {
  
    $scope.child = JSON.parse(JSON.stringify(child));
    
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
    
    $scope.alert = {
    		  'type': '',
    		  'msg': '',
    		  'show': false
      };
      
      var showAlert = function(type, msg) {
    	    $scope.alert.type = type;
    	    $scope.alert.msg = msg;
    	    $scope.alert.show = true;
      };

      $scope.closeAlert = function() {
    	    $scope.alert.type = '';
    	    $scope.alert.msg = '';
    	    $scope.alert.show = false;
      };
    
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
    				{
    					changedChild[category] = {};
    					changedChild[category].timeStamp = originChild[category].timeStamp;
    				}
    					
    				changedChild[category][field] = currentChild[category][field];
    			}
    				
    		}
    	}
    	return changedChild;
    };
	
    var openMessageDialog = function (messageType, message, okCallBack, cancleCallBack){
    	
    	/*bootbox.confirm("Are you sure?", function(result) {
    		  if (result == true)
    			  okCallBack();
    		  else
    			  cancleCallBack();
    		});*/
    	
    	var modalInstance = $modal.open({
    		windowTemplateUrl: 'template/modalDialogWindow.html',
			templateUrl: 'template/msgDialog.html',
			controller: 'MsgDialogCtrl',
			backdrop: 'static',
			//size: 'lg',
			resolve: {
				messageType: function() {
					return messageType;
				},
		        message: function() {
		        	return message;  
		        }
			}
		});
		modalInstance.result.then(function () {
		      //$log.info('Modal OK at: ' + new Date());
			if (okCallBack != undefined && okCallBack != null)	
				okCallBack();
			}, function () {
		      //$log.info('Modal dismissed at: ' + new Date());
			if (cancleCallBack != undefined && cancleCallBack != null)	
				cancleCallBack();
		});
    };
    
	$scope.ok = function () {
		//mask();
		$scope.closeAlert();
		if (mode === 'create')
		{
			$("#create-student-dialog").mask({spinner: { lines: 10, length: 6, width: 3, radius: 5}, delay: 0, label: 'Creating...'});
			$http.post('/ChildrenManage/webapi/Children', $scope.child).
				success(function(data, status, headers, config) {
					$("#create-student-dialog").unmask();
					//add succes  info;
					$modalInstance.close();
				}).
				error(function(data, status, headers, config) {
					$("#create-student-dialog").unmask();
					showAlert('danger', data.message);
				});
		}
		else if (mode === 'update')
		{
			var result = getChangedData();
			if (JSON.stringify(result) == "{}")
			{
				$modalInstance.dismiss('cancel');
				return;
			}
			
			$("#create-student-dialog").mask({spinner: { lines: 10, length: 6, width: 3, radius: 5}, delay: 0, label: 'Updating...'});
				
			$http.put('/ChildrenManage/webapi/Children/'+$scope.child.id, result).
				success(function(data, status, headers, config) {
					$("#create-student-dialog").unmask();
					$modalInstance.close();
				}).
				error(function(data, status, headers, config) {
					$("#create-student-dialog").unmask();
					showAlert('danger', data.message);
				});
		}
		else if (mode === 'view')
		{
			$modalInstance.dismiss('cancel');
		}
    };

    $scope.cancel = function () {
    	if (mode == 'view')
    	{
    		$modalInstance.dismiss('cancel');
    	}
    	else if (mode == 'create')
    	{
    		var okCallBack = function() {
    			$modalInstance.dismiss('cancel');
    		};
    		var cancleCallBack = function() {
    			//Do nothing.
    		};
    		var messageType = 'Warning';
    		var message = 'Are you sure to drop creating page?';
    		openMessageDialog(messageType, message, okCallBack, cancleCallBack);
    	}
    	else if (mode == 'update')
    	{
    		var result = getChangedData();
        	if (JSON.stringify(result) == "{}")
        	{
            	$modalInstance.dismiss('cancel');
        	}
        	else
        	{
        		var okCallBack = function() {
        			$modalInstance.dismiss('cancel');
        		};
        		var cancleCallBack = function() {
        			//Do nothing.
        		};
        		var messageType = 'Warning';
        		var message = 'Children has been changed, are you sure to drop this?';
        		openMessageDialog(messageType, message, okCallBack, cancleCallBack);
        	}
    	}
    	
    };
    
	$scope.format = 'yyyy-MM-dd';
	$scope.open = function($event) {
		$event.preventDefault();
		$event.stopPropagation();

		$scope.opened = true;
    };
	
  }]);