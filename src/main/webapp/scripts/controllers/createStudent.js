'use strict';

angular.module('app.children.controllers', ['ui.bootstrap'])
  .controller('CreateStudentCtrl', ['$scope', '$modalInstance', '$modal', '$http', '$translate', 'child', 'mode', function ($scope, $modalInstance, $modal, $http, $translate, child, mode) {
    
	$scope.child = JSON.parse(JSON.stringify(child));
    
    $scope.disable = mode === 'view' ? 'true' : 'false';
    
    $scope.okText = mode === 'view' ? $translate.instant('PRINT') : $translate.instant('OK');
    $scope.cancleText = mode === 'view' ? $translate.instant('CLOSE') : $translate.instant('CANCLE');
    
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
    
    var valideInput = function() {
    	if ($scope.child.basicInfo.name == "" || $scope.child.basicInfo.name == undefined)
    		return $translate.instant('NAME_IS_EMPTY');
    	else if ($scope.child.basicInfo.birthday == "" || $scope.child.basicInfo.birthday == undefined)
    		return $translate.instant('BIRTHDAY_IS_EMPTY');
    	else if ($scope.child.contactInfo.fatherName == "" || $scope.child.contactInfo.fatherName == undefined)
    		return $translate.instant('FATHER_NAME_EMPTY');
    	else if ($scope.child.contactInfo.fatherContact == "" || $scope.child.contactInfo.fatherContact == undefined)
    		return $translate.instant('FATHER_PHONE_EMPTY');
    	else if ($scope.child.contactInfo.motherName == "" || $scope.child.contactInfo.motherName == undefined)
    		return $translate.instant('MOTHER_NAME_EMPTY');
    	else if ($scope.child.contactInfo.motherContact == "" || $scope.child.contactInfo.motherContact == undefined)
    		return $translate.instant('MOTHER_PHONE_EMPTY');
    	
    	else
    		return "";
    };
    
	$scope.ok = function () {
		
		$scope.closeAlert();
		
		if (mode === 'view')
		{
			var hdHTML = "<!doctype html><head>" +
							"<link rel=\"stylesheet\" href=\"bower_components/bootstrap/dist/css/bootstrap.min.css\" type=\"text/css\"/>" +
							"<link rel=\"stylesheet\" href=\"styles/ng-grid.css\" type=\"text/css\">" +
						 "</head>";
			var bodyHTML = "<body><div style=\"width: 800px;\">" + window.document.getElementById("modal-body").innerHTML + "</div><script>window.print();</script></body>";
			var printWindow = window.open();
			printWindow.document.write(hdHTML+bodyHTML);
			printWindow.document.close();
			printWindow.focus();
			return;
		}
		
		var required = valideInput();
		
		if (required != "")
		{
			showAlert('danger', required);
			return;
		}

		if (mode === 'create')
		{
			$("#create-student-dialog").mask({spinner: { lines: 10, length: 6, width: 3, radius: 5}, delay: 0, label: $translate.instant('CREATING')});
			$http.post('/ChildrenManage/webapi/Children', $scope.child).
				success(function(data, status, headers, config) {
					$("#create-student-dialog").unmask();
					$modalInstance.close();
				}).
				error(function(data, status, headers, config) {
					$("#create-student-dialog").unmask();
					showAlert('danger', $translate.instant('FAIl_CREATE_ITEMS', {msg: data.message}));
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
			
			$("#create-student-dialog").mask({spinner: { lines: 10, length: 6, width: 3, radius: 5}, delay: 0, label: $translate.instant('UPDATING')});
				
			$http.put('/ChildrenManage/webapi/Children/'+$scope.child.id, result).
				success(function(data, status, headers, config) {
					$("#create-student-dialog").unmask();
					$modalInstance.close();
				}).
				error(function(data, status, headers, config) {
					$("#create-student-dialog").unmask();
					showAlert('danger', $translate.instant('FAIl_UPDATE_ITEMS', {msg: data.message}));
				});
		};
		
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
    		var messageType = $translate.instant('TYPE_WARNING');
    		var message = $translate.instant('CONFIRM_DROP_CREATE');
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
        		var messageType = $translate.instant('TYPE_WARNING');
        		var message = $translate.instant('CONFIRM_DROP_UPDATE');
        		openMessageDialog(messageType, message, okCallBack, cancleCallBack);
        	}
    	}
    	
    };
    
	$scope.datepicker = {
			format: 'yyyy-MM-dd',
			maxDate: new Date(),
			minDate: '2001-01-01',
			open: function($event) {
			    $event.preventDefault();
			    $event.stopPropagation();

			    $scope.datepicker.opened = true;
			  }
	};
	
  }]);