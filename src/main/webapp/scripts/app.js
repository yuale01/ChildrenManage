var app = angular.module('app', ['app.children.controllers', 'pascalprecht.translate', 'ngTouch', 'ui.grid', 'ui.grid.paging', 'ui.grid.exporter', 'ui.grid.selection', 'ui.bootstrap', 'ui.grid.resizeColumns']);

app.config(['$translateProvider', function ($translateProvider){
  
  var getLocale = function () {
		 var nav    = window.navigator;	    
		 var tokens = (nav.language || nav.browserLanguage || nav.systemLanguage || nav.userLanguage || '').split('-');
			
		 if (tokens > 1)
		 {
			tokens[1] = tokens[1].toUpperCase();
		 }
			
		 return tokens.join('_');
  };
 
  $translateProvider.useStaticFilesLoader({
	    prefix: 'i18n/locale-',
	    suffix: '.json'
  });
 
  $translateProvider.preferredLanguage(getLocale());
  $translateProvider.fallbackLanguage('en');
	
}]);

app.controller('MainCtrl', ['$rootScope', '$scope', '$interval', '$q', '$modal', '$http', '$translate', 'uiGridConstants', function ($rootScope, $scope, $interval, $q, $modal, $http, $translate, uiGridConstants) {

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
  
  $scope.data = []; 
  
  $scope.gridOptions = {
	enableColumnResizing: true,
    enableFiltering: true,
    exporterMenuCsv: false,
    enableGridMenu: true,
	pagingPageSizes: [25, 50, 75],
	pagingPageSize: 25,
	columnDefs: [
      { field: 'basicInfo.name',
    	headerCellFilter: 'translate',
	    displayName: $translate.instant('NAME'),
	    filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
	  { field: 'basicInfo.idCardNo',
		headerCellFilter: 'translate',
		displayName: $translate.instant('ID_CARD_NO.'),
	    filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
	  { field: 'basicInfo.grade',
		headerCellFilter: 'translate',
		displayName: $translate.instant('GRADE'),
		filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
      { field: 'basicInfo.className',
		headerCellFilter: 'translate',
	    displayName: $translate.instant('CLASS'),
	    filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
      { field: 'basicInfo.translatedGender',
		headerCellFilter: 'translate',
	    displayName: $translate.instant('GENDER'),
	    filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
      { field: 'basicInfo.nation',
		headerCellFilter: 'translate',
	    displayName: $translate.instant('NATION'),
	    filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
      { field: 'basicInfo.birthday',
		headerCellFilter: 'translate',
		displayName: $translate.instant('BIRTHDAY'),
		filters: [
		          {
		            condition: uiGridConstants.filter.GREATER_THAN,
		            //placeholder: 'aa'
		          },
		          {
		            condition: uiGridConstants.filter.LESS_THAN,
		            //placeholder: {{'CONDITION_PLACE_HOLDER_LESS_THAN' | translate}}
		          }
		        ]
	  },
	  { field: 'contactInfo.motherName',
		headerCellFilter: 'translate',
		displayName: $translate.instant('MOTHER_NAME'),
		enableFiltering: false
	  },
      { field: 'contactInfo.motherContact',
		headerCellFilter: 'translate',
	    displayName: $translate.instant('MOTHER_PHONE'),
	    enableFiltering: false
	  },
	  { field: 'contactInfo.fatherName',
		headerCellFilter: 'translate',
		displayName: $translate.instant('FATHER_NAME'),
		enableFiltering: false,
	  },
	  { field: 'contactInfo.fatherContact',
		headerCellFilter: 'translate',
	    displayName: $translate.instant('FATHER_PHONE'),
	    enableFiltering: false
	  },
	  { field: 'contactInfo.livingAddr',
		headerCellFilter: 'translate',
		displayName: $translate.instant('LIVING_ADDR'),
		enableFiltering: false,
		visible: false
	  }
    ]
  };
  
  $scope.gridOptions.data = 'data';
  
  $scope.gridOptions.onRegisterApi = function(gridApi){
      //set gridApi on scope
      $scope.gridApi = gridApi;
      /*gridApi.selection.on.rowSelectionChanged($scope,function(row){
        var msg = 'row selected ' + row.isSelected;
        $log.log(msg);
      });
 
      gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
        var msg = 'rows changed ' + rows.length;
        $log.log(msg);
      });*/
    };
  
  var loadData = function() {
	  $scope.closeAlert();
	  $("#grid").mask({spinner: { lines: 10, length: 6, width: 3, radius: 5}, delay: 0, label: $translate.instant('LOADING')});
	  $http.get("/ChildrenManage/webapi/Children").
	  	success(function(data) {
	  		for (var i=0;i<data.length;i++)
	  			{
	  				data[i].basicInfo.translatedGender = data[i].basicInfo.gender == 0 ? $translate.instant('GENDER_MALE') : $translate.instant('GENDER_FEMALE');
	  			}
	  		$scope.data = data;
	  		$("#grid").unmask();
	  	}).
	  	error(function(data) {
	  		$("#grid").unmask();
	  		showAlert('danger', $translate.instant('FAIL_LOAD_DATA', {msg: data.message}));
	  	});
  }; 
  
  var deferFun = function(timeout) {
    var deferred = $q.defer();
	$interval( function() {
		deferred.resolve();
	}, timeout, 1);
	return deferred.promise;
  };
  
  var promise = deferFun(500);
  promise.then(function() {
    loadData();
  });
  
  $scope.create = function() {
	$scope.closeAlert();
    var modalInstance = $modal.open({
      //scope: $scope,
      windowTemplateUrl: 'template/modalDialogWindow.html',
	  templateUrl: 'template/createStudent.html',
	  controller: 'CreateStudentCtrl',
	  backdrop: 'static',
	  size: 'lg',
	  resolve: {
		  child: function() {
			  return {
					"basicInfo": {
						"gender": 0,
						"birthday": "2015-01-01",
						"huKou": 0,
						"migration": 0,
						"onlyChild": 1,
						"minLiving": 0,
						"imburse": 0,
						"orphan": 0,
						"pathography": 0
					},
					"contactInfo": {},
					"bodyInfo": {
						"doffDon": 0,
						"eating": 0,
						"toileting": 0,
						"sleeping": 0,
						"eatingSpeed": 0,
						"appetite": 0,
						"pickyEating": 0,
						"eatingAbility": 0,
						"foodAllergy": 0,
						"healthStatus": 0
					}
				}; // used to init the create page
		  },
          mode: function() {
        	return 'create';  
          }
	  }
	});
	modalInstance.result.then(function () {
	  loadData();
    }, function () {
    });
  };
  
  $scope.refresh = function() {
	  loadData();
  };
  
  $scope.deleteChildren = function() {
	  $scope.closeAlert();
	  var rows = $scope.gridApi.selection.getSelectedRows();
	  if (rows.length == 0)
	  {
		  showAlert('danger', $translate.instant('NO_ITEM_SELECTED'));
		  return;
	  }
	  
	  var modalInstance = $modal.open({
  		    windowTemplateUrl: 'template/modalDialogWindow.html',
			templateUrl: 'template/msgDialog.html',
			controller: 'MsgDialogCtrl',
			backdrop: 'static',
			//size: 'lg',
			resolve: {
				messageType: function() {
					return $translate.instant('TYPE_WARNING');
				},
		        message: function() {
		        	return $translate.instant('CONFIRM_DELETE');  
		        }
			}
		});
		modalInstance.result.then(function () {
			  var ids = JSON.parse('[]');
			  for (var i=0; i<rows.length; i++)
				  ids.push(rows[i].id);
			  
			  $http({
				url: '/ChildrenManage/webapi/Children',
				method: 'DELETE',
				data: ids,
				headers: {'Content-Type': "application/json;charset=UTF-8"}
			  }).
			    success(function(data) {
			  		loadData();
			  	}).
			  	error(function(data) {
			  		showAlert('danger', $translate.instant('FAIl_DELETE_ITEMS', {msg: data.message}));
			  	});
			}, function () {
		});
	  
  };
  
  $scope.edit = function() {
	  $scope.closeAlert();
	  var rows = $scope.gridApi.selection.getSelectedRows();
	  if (rows.length !== 1)
	  {
		  showAlert('danger', $translate.instant('MULTI_ITEMS_SELECTED_EDIT'));
		  return;
	  }
	  var child = rows[0];
	  var modalInstance = $modal.open({
		  templateUrl: 'template/createStudent.html',
		  controller: 'CreateStudentCtrl',
		  backdrop: 'static',
		  size: 'lg',
		  resolve: {
			  child: function() {
				  return child;
			  },
	          mode: function() {
	        	return 'update';  
	          }
		  }
		});
		modalInstance.result.then(function () {
		  loadData();
	    }, function () {
	    });
  };
  
  /*angular.forEach($scope.gridOptions.columnDefs, function(value){
	  $translate(value.displayName).then(function(data){value.displayName = data;});
	  });*/
  
  $translate(['CONDITION_PLACE_HOLDER_GREATER_THAN', 'CONDITION_PLACE_HOLDER_LESS_THAN']).then(function (translations) {
	  $scope.gridOptions.columnDefs[6].filters[0].placeholder = translations.CONDITION_PLACE_HOLDER_GREATER_THAN;
	  $scope.gridOptions.columnDefs[6].filters[1].placeholder = translations.CONDITION_PLACE_HOLDER_LESS_THAN;
  });
  
}]);