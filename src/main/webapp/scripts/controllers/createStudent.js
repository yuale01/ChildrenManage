'use strict';

angular.module('app.children.controllers', ['ui.bootstrap', 'focusOn'])
  .controller('CreateStudentCtrl', ['$scope', '$modalInstance', '$modal', '$http', '$translate', 'child', 'mode', '$location', '$anchorScroll', 'focus', '$window', function ($scope, $modalInstance, $modal, $http, $translate, child, mode, $location, $anchorScroll, focus, $window) {
    
	$scope.child = JSON.parse(JSON.stringify(child));
	
	//$scope.child.basicInfo.name = "test";
    
    $scope.viewType = mode === 'view' ? true : false;
    
    $scope.editBoxChecked = false;
    
    $scope.disable = mode === 'view' ? true : false;
    
    $scope.title = mode === 'view' ? $translate.instant('VIEW_EDIT_CHILD') : $translate.instant('CREATE_NEW_CHILD');
    
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
    	if ($scope.child.basicInfo.name == "" || $scope.child.basicInfo.name == undefined) {
    		focus('name');
    		return $translate.instant('NAME_IS_EMPTY');
    	}
    	else if ($scope.child.basicInfo.birthday == "" || $scope.child.basicInfo.birthday == undefined)
    		return $translate.instant('BIRTHDAY_IS_EMPTY');
    	else if ($scope.child.contactInfo.fatherName == "" || $scope.child.contactInfo.fatherName == undefined) {
    	    focus('fatherName');
    		return $translate.instant('FATHER_NAME_EMPTY');
        }
    	else if ($scope.child.contactInfo.fatherContact == "" || $scope.child.contactInfo.fatherContact == undefined) {
    		focus('fatherTel');
    		return $translate.instant('FATHER_PHONE_EMPTY');
    	}
    	else if ($scope.child.contactInfo.motherName == "" || $scope.child.contactInfo.motherName == undefined) {
    		focus('motherName');
    		return $translate.instant('MOTHER_NAME_EMPTY');
    	}	
    	else if ($scope.child.contactInfo.motherContact == "" || $scope.child.contactInfo.motherContact == undefined) {
    		focus('motherTel');
    		return $translate.instant('MOTHER_PHONE_EMPTY');
    	}
    	else if ($scope.child.contactInfo.livingAddr == "" || $scope.child.contactInfo.livingAddr == undefined) {
    		focus('livingAddr');
    		return $translate.instant('LIVING_ADDR_EMPTY');
    	}
    	else
    		return "";
    };
    
    $scope.print = function() {
    	$scope.closeAlert();
    	
//    	var printContents = document.getElementById("modal-body").innerHTML;
//        var originalContents = document.body.innerHTML;
//
//        document.body.innerHTML = printContents;
//
//        window.print();
//
//        document.body.innerHTML = originalContents;
    	
    	var hdHTML = "<!doctype html><head>" +
					    	"<link rel=\"stylesheet\" href=\"bower_components/bootstrap/dist/css/bootstrap.min.css\" type=\"text/css\"/>"+
					    	"<link rel=\"stylesheet\" href=\"styles/ng-grid.css\" type=\"text/css\">"+
					    	"<script src=\"bower_components/jquery/dist/jquery.min.js\"></script>"+
					    	"<script src=\"bower_components/bootstrap/dist/js/bootstrap.min.js\"></script>"+
					    	"<script src=\"bower_components/angular/angular.min.js\"></script>"+
					        "<script src=\"bower_components/bower-angular-i18n-master/angular-locale_zh-cn.js\"></script>"+
					    	"<script src=\"bower_components/angular-animate/angular-animate.min.js\"></script>"+
					        "<script src=\"bower_components/angular-cookies/angular-cookies.min.js\"></script>"+
					        "<script src=\"bower_components/angular-resource/angular-resource.min.js\"></script>"+
					        "<script src=\"bower_components/angular-route/angular-route.min.js\"></script>"+
					        "<script src=\"bower_components/angular-sanitize/angular-sanitize.min.js\"></script>"+
					        "<script src=\"bower_components/angular-touch/angular-touch.min.js\"></script>"+
						"</head>";
    	var jsHTML = "<script>angular.module('print', []).controller('PrintCtrl', ['$scope', function ($scope) {$scope.child ="+JSON.stringify($scope.child)+" }]);</script>";
		var bodyHTML = "<body>"+jsHTML+"<div ng-controller=\"PrintCtrl\" style=\"width: 800px;\">" + window.document.getElementById("fieldset").innerHTML + "</div></body>";
		var printWindow = window.open();
		var html = "<html ng-app=\"print\">"+hdHTML+bodyHTML+"</html>";
		printWindow.document.write("<html ng-app=\"print\">"+hdHTML+bodyHTML+"</html>");
		printWindow.document.close();
    	
    	//var childStr = "$scope.child = "+JSON.stringify(child);
    	//<script>"+childStr+";window.print();</script>
		
//    	var hdHTML = "<head>"+window.document.getElementsByTagName('head')[0].innerHTML+"</head>";
//    	var bodyHTML = "<body><div style=\"width: 800px;\">" + window.document.getElementById("modal-body").innerHTML + "</div></body>";
//    	var printWindow = $window.open();
//    	printWindow.document.write(hdHTML+bodyHTML);
//    	printWindow.document.close();
    	
    	/*var innerHTML = window.document.getElementById("modal-body").innerHTML;
    	
    	for (var i=0; i<childStructure.length; i++)
    	{
    		var category = childStructure[i].category;
    		var fields = childStructure[i].fields;
    		
    		for (var j=0; j<fields.length; j++)
    		{
    			var field = fields[j];
    			innerHTML = innerHTML.replace("ng-model=\"child."+category+"."+field+"\"","ng-init=\"child."+category+"."+field+"='"+$scope.child[category][field]+"'\""+" ng-model=\"child."+category+"."+field+"\"");
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
    	};
    	var hdHTML = "<head>"+window.document.getElementsByTagName('head')[0].innerHTML+"</head>";
    	var bodyHTML = "<body><div style=\"width: 800px;\">" + innerHTML + "</div></body>";
    	var printWindow = $window.open();
    	printWindow.document.write(hdHTML+bodyHTML);
    	printWindow.document.close();*/
    	
    };
    
	$scope.ok = function () {
		
		var required = valideInput();
		
		if (required != "")
		{
			showAlert('danger', required);
			//scroll(0, -1000);
			$location.hash('create-student-dialog');
		    $anchorScroll();
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
		else if (mode === 'view')
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
		}
		
    };

    $scope.cancel = function () {
    	
    	if (mode == 'create')
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
    	else if (mode === 'view')
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