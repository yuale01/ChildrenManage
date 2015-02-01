var app = angular.module('MyApp', ['ngTouch', 'ui.grid', 'ui.grid.paging', 'ui.grid.exporter', 'ui.grid.selection', 'ui.bootstrap', 'ui.grid.resizeColumns']);
 
app.controller('MainCtrl', ['$scope', '$interval', '$q', '$modal', '$http', 'uiGridConstants', function ($scope, $interval, $q, $modal, $http, uiGridConstants) {
  
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
	    displayName: 'Name',
	    filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
	  { field: 'basicInfo.idCardNo',
		displayName: 'ID Card No.',
	    filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
	  { field: 'basicInfo.grade',
		displayName: 'Grade',
		filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
      { field: 'basicInfo.className',
	    displayName: 'Class',
	    filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
      { field: 'basicInfo.translatedGender',
	    displayName: 'Gender',
	    filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
      { field: 'basicInfo.nation',
	    displayName: 'Nation',
	    filters: [{condition: uiGridConstants.filter.CONTAINS}]
	  },
      { field: 'basicInfo.birthday',
		displayName: 'Birthday',
		filters: [
		          {
		            condition: uiGridConstants.filter.GREATER_THAN,
		            placeholder: 'greater than'
		          },
		          {
		            condition: uiGridConstants.filter.LESS_THAN,
		            placeholder: 'less than'
		          }
		        ]
	  },
	  { field: 'contactInfo.motherName',
		displayName: 'Mother Name',
		enableFiltering: false
	  },
      { field: 'contactInfo.motherContact',
	    displayName: 'Mother Phone',
	    enableFiltering: false
	  },
	  { field: 'contactInfo.fatherName',
		displayName: 'Father Name',
		enableFiltering: false,
	  },
	  { field: 'contactInfo.fatherContact',
	    displayName: 'Father Phone',
	    enableFiltering: false
	  },
	  { field: 'contactInfo.livingAddr',
		displayName: 'Living Address',
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
	  $("#grid").mask({spinner: { lines: 10, length: 6, width: 3, radius: 5}, delay: 0, label: 'Loading...'});
	  $http.get("/ChildrenManage/webapi/Children").
	  	success(function(data) {
	  		for (var i=0;i<data.length;i++)
	  			{
	  				data[i].basicInfo.translatedGender = data[i].basicInfo.gender == 0 ? 'male' : 'female';
	  			}
	  		$scope.data = data;
	  		$("#grid").unmask();
	  	}).
	  	error(function(data) {
	  		$("#grid").unmask();
	  		showAlert('danger','Failed to load data: '+data.message);
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
      scope: $scope,
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
      //$log.info('Modal OK at: ' + new Date());
	  loadData();
    }, function () {
      //$log.info('Modal dismissed at: ' + new Date());
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
		  showAlert('danger','No item(s) selected. Please select at least on item.');
		  return;
	  }
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
	  		showAlert('danger', 'Fail to delete item: '+data.message);
	  	});
  };
  
  $scope.edit = function() {
	  $scope.closeAlert();
	  var rows = $scope.gridApi.selection.getSelectedRows();
	  if (rows.length !== 1)
	  {
		  showAlert('danger', 'Please select only one item to edit.');
		  return;
	  }
	  var child = rows[0];
	  var modalInstance = $modal.open({
		  scope: $scope,
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
	      //$log.info('Modal OK at: ' + new Date());
		  loadData();
	    }, function () {
	      //$log.info('Modal dismissed at: ' + new Date());
	    });
  };
  
}]);