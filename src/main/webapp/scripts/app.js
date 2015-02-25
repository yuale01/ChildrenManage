var app = angular.module('app', ['app.children.controllers', 'pascalprecht.translate', 'utils.autofocus', 'ngTouch', 'ui.grid', 'ui.grid.paging', 'ui.grid.exporter', 'ui.grid.selection', 'ui.bootstrap', 'ui.grid.resizeColumns']);

app.config(['$translateProvider', function ($translateProvider){
	
  
  
  var getLocale = function () {
		 var nav    = window.navigator;	    
		 var tokens = (nav.language || nav.browserLanguage || nav.systemLanguage || nav.userLanguage || '').split('-');
			
		 if (tokens.length > 1)
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

app.controller('MainCtrl', ['$rootScope', '$scope', '$interval', '$q', '$modal', '$http', '$translate', 'uiGridConstants', 'i18nService', function ($rootScope, $scope, $interval, $q, $modal, $http, $translate, uiGridConstants, i18nService) {

  $scope.alert = {
		'type': '',
		'msg': '',
		'show': false
  };
  
  $scope.lang = 'zh-cn';
  
  $scope.filterCollapsed = true;
  
  var fullData = []; 
  
  //$scope.data = [];
  
  $scope.filter= {
		'name': '',
		'grade': -1,
		'className': -1,
		'gender': -1,
		'birthdayFrom': '',
		'birthdayTo': '',
		'id': ''
  };
  
  var isEmpty = function(data) {
	  if (data == null || data == undefined || data == '' )
		  return true;
	  else 
		  return false;
  };
  
  var filterData = function(data) {
	  var result = [];
	  for (var i = 0; i< data.length; i++)
	  {
		  if (!isEmpty($scope.filter.name) && data[i].basicInfo.name.indexOf($scope.filter.name)==-1)
			  continue;
		  if ($scope.filter.grade != -1 && data[i].basicInfo.grade != $scope.filter.grade )
			  continue;
		  if ($scope.filter.className != -1 && data[i].basicInfo.className != $scope.filter.className)
			  continue;
		  if ($scope.filter.gender != -1 && data[i].basicInfo.gender != $scope.filter.gender )
			  continue;
		  if (!isEmpty($scope.filter.id) && data[i].basicInfo.idCardNo != $scope.filter.id )
			  continue;
		  if (!isEmpty($scope.filter.birthdayFrom) && new Date(data[i].basicInfo.birthday).getTime() < $scope.filter.birthdayFrom.getTime() )
			  continue;
		  if (!isEmpty($scope.filter.birthdayTo) && new Date(data[i].basicInfo.birthday).getTime() > $scope.filter.birthdayTo )
			  continue;
		  result.push(data[i]);
	  }
	  return result;
  };
  
  $scope.doFilter = function() {
	  $scope.gridOptions.data = filterData(fullData);
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
  
  $scope.gridOptions = {
	enableColumnResizing: true,
	enableSorting: true,
    enableFiltering: false,
    exporterMenuCsv: false,
    exporterMenuPdf: false,
    enableGridMenu: true,
    gridMenuTitleFilter: $translate,
	pagingPageSizes: [25, 50, 75],
	pagingPageSize: 25,
	columnDefs: [
      { field: 'basicInfo.name',
    	headerCellFilter: 'translate',
	    displayName: $translate.instant('NAME')
	    /*filters: [{condition: uiGridConstants.filter.CONTAINS}],
	    sort: {
	    	direction: uiGridConstants.ASC,
	    	priority: 1
	    },
	    suppressRemoveSort: true*/
	  },
	  { field: 'basicInfo.idCardNo',
		headerCellFilter: 'translate',
		displayName: $translate.instant('ID_CARD_NO.')
	    //filters: [{condition: uiGridConstants.filter.CONTAINS}],
	    //suppressRemoveSort: true
	  },
	  { field: 'basicInfo.translatedGrade',
		headerCellFilter: 'translate',
		displayName: $translate.instant('GRADE')
		//filters: [{condition: uiGridConstants.filter.CONTAINS}],
		//suppressRemoveSort: true
	  },
      { field: 'basicInfo.translatedClassName',
		headerCellFilter: 'translate',
	    displayName: $translate.instant('CLASS')
	    //filters: [{condition: uiGridConstants.filter.CONTAINS}],
	    //suppressRemoveSort: true
	  },
      { field: 'basicInfo.translatedGender',
		headerCellFilter: 'translate',
	    displayName: $translate.instant('GENDER')
	    /*filters: [{condition: uiGridConstants.filter.CONTAINS}],
	    sort: {
	    	direction: uiGridConstants.ASC,
	    	priority: 0
	    },
	    suppressRemoveSort: true*/
	  },
      { field: 'basicInfo.nation',
		headerCellFilter: 'translate',
	    displayName: $translate.instant('NATION')
	    /*filters: [{condition: uiGridConstants.filter.CONTAINS}],
	    suppressRemoveSort: true*/
	  },
      { field: 'basicInfo.birthday',
		headerCellFilter: 'translate',
		displayName: $translate.instant('BIRTHDAY')
		/*filters: [
		          {
		            condition: function(searchTerm, cellValue) {
		            	var strippedValue = searchTerm.replace(/\\/g, '');
		            	if (strippedValue.localeCompare(cellValue) < 1)
		            		return true;
		            }
		            //placeholder: CONDITION_PLACE_HOLDER_GREATER_THAN
		          },
		          {
		            condition: function(searchTerm, cellValue) {
		            	var strippedSearchValue = searchTerm.replace(/\\/g, '');
		            	var subCellValue = cellValue.substring(0, strippedSearchValue.length);
		            	if (strippedSearchValue.localeCompare(subCellValue) > -1)
		            		return true;
		            }
		            //placeholder: CONDITION_PLACE_HOLDER_LESS_THAN
		          }
		        ],
	    suppressRemoveSort: true*/
	  },
	  { field: 'contactInfo.motherName',
		headerCellFilter: 'translate',
		displayName: $translate.instant('MOTHER_NAME')
		//enableFiltering: false,
		//suppressRemoveSort: true
	  },
      { field: 'contactInfo.motherContact',
		headerCellFilter: 'translate',
	    displayName: $translate.instant('MOTHER_TEL')
	    //enableFiltering: false,
	    //suppressRemoveSort: true
	  },
	  { field: 'contactInfo.fatherName',
		headerCellFilter: 'translate',
		displayName: $translate.instant('FATHER_NAME')
		//enableFiltering: false,
		//suppressRemoveSort: true
	  },
	  { field: 'contactInfo.fatherContact',
		headerCellFilter: 'translate',
	    displayName: $translate.instant('FATHER_TEL')
	    //enableFiltering: false,
	    //suppressRemoveSort: true
	  },
	  { field: 'contactInfo.livingAddr',
		headerCellFilter: 'translate',
		displayName: $translate.instant('LIVING_ADDR'),
		//enableFiltering: false,
		visible: false
		//suppressRemoveSort: true
	  }
    ]
  };
  
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
  
  var translateData = function(data) {
	  for (var i=0;i<data.length;i++)
	  {
			data[i].basicInfo.translatedGender = data[i].basicInfo.gender == 0 ? $translate.instant('GENDER_MALE') : $translate.instant('GENDER_FEMALE');
			
			if (data[i].basicInfo.grade == 0)
				data[i].basicInfo.translatedGrade = $translate.instant('GRADE_ONE');
			else if (data[i].basicInfo.grade == 1)
				data[i].basicInfo.translatedGrade = $translate.instant('GRADE_TWO');
			else if (data[i].basicInfo.grade == 2)
				data[i].basicInfo.translatedGrade = $translate.instant('GRADE_THREE');
			
			if (data[i].basicInfo.className == 0)
				data[i].basicInfo.translatedClassName = $translate.instant('CLASS_ONE');
			else if (data[i].basicInfo.className == 1)
				data[i].basicInfo.translatedClassName = $translate.instant('CLASS_TWO');
			else if (data[i].basicInfo.className == 2)
				data[i].basicInfo.translatedClassName = $translate.instant('CLASS_THREE');
	  }
	  return data;
  };
  
  var loadData = function() {
	  $scope.closeAlert();
	  $("#grid").mask({spinner: { lines: 10, length: 6, width: 3, radius: 5}, delay: 0, label: $translate.instant('LOADING')});
	  $http.get("/ChildrenManage/webapi/Children").
	  	success(function(data) {
	  		$("#grid").unmask()
	  		fullData = translateData(data);
	  		$scope.gridOptions.data = filterData(fullData);
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
						"grade": 0,
						"className": 0,
						"gender": 0,
						"birthday": new Date(),
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
			  $("#grid").mask({spinner: { lines: 10, length: 6, width: 3, radius: 5}, delay: 0, label: $translate.instant('DELETING')});
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
  
  $scope.view = function() {
	  $scope.closeAlert();
	  var rows = $scope.gridApi.selection.getSelectedRows();
	  if (rows.length !== 1)
	  {
		  showAlert('danger', $translate.instant('MULTI_ITEMS_SELECTED'));
		  return;
	  }
	  var modalInstance = $modal.open({
		  templateUrl: 'template/createStudent.html',
		  controller: 'CreateStudentCtrl',
		  backdrop: 'static',
		  size: 'lg',
		  resolve: {
			  child: function() {
				  return rows[0];
			  },
	          mode: function() {
	        	return 'view';  
	          }
		  }
		});
		modalInstance.result.then(function () {
			loadData();
	    }, function () {
	    });
  };
  
  /*$scope.edit = function() {
	  $scope.closeAlert();
	  var rows = $scope.gridApi.selection.getSelectedRows();
	  if (rows.length !== 1)
	  {
		  showAlert('danger', $translate.instant('MULTI_ITEMS_SELECTED'));
		  return;
	  }
	  var modalInstance = $modal.open({
		  templateUrl: 'template/createStudent.html',
		  controller: 'CreateStudentCtrl',
		  backdrop: 'static',
		  size: 'lg',
		  resolve: {
			  child: function() {
				  return rows[0];
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
  };*/
  
  $scope.import = function() {
	  
  };
  
  $scope.export = function() {
	  $scope.closeAlert();
	  $http.get("/ChildrenManage/webapi/Children/export").
	  	success(function(data) {
	  		//$("#grid").unmask();
	  	}).
	  	error(function(data) {
	  		//$("#grid").unmask();
	  		showAlert('danger', $translate.instant('FAIL_EXPORT_DATA', {msg: data.message}));
	  	});
	  
  };
  
  /*angular.forEach($scope.gridOptions.columnDefs, function(value){
	  $translate(value.displayName).then(function(data){value.displayName = data;});
	  });*/
  
  /*$translate(['CONDITION_PLACE_HOLDER_GREATER_THAN', 'CONDITION_PLACE_HOLDER_LESS_THAN']).then(function (translations) {
	  $scope.gridOptions.columnDefs[6].filters[0].placeholder = translations.CONDITION_PLACE_HOLDER_GREATER_THAN;
	  $scope.gridOptions.columnDefs[6].filters[1].placeholder = translations.CONDITION_PLACE_HOLDER_LESS_THAN;
  });*/
  
  $rootScope.$on('$translateLoadingEnd', function(promise) {
	  //$translate.refresh('CREATE');
  });
  
  $scope.datepicker = {
			format: 'yyyy-MM-dd',
			maxDate: new Date(),
			minDate: '2001-01-01',
			openFrom: function($event) {
			    $event.preventDefault();
			    $event.stopPropagation();

			    $scope.datepicker.fromOpened = true;
			  },
			openTo: function($event) {
				$event.preventDefault();
				$event.stopPropagation();

				$scope.datepicker.toOpened = true;
			}
	};
  
  
}]);