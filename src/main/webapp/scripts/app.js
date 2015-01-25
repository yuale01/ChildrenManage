var app = angular.module('MyApp', ['ngTouch', 'ui.grid', 'ui.grid.paging', 'ui.grid.exporter', 'ui.grid.selection', 'ui.bootstrap', 'ui.grid.resizeColumns']);
 
app.controller('MainCtrl', ['$scope', '$interval', '$q', '$modal', '$http', 'uiGridConstants', function ($scope, $interval, $q, $modal, $http, uiGridConstants) {
 
  $scope.students = [{
	"basicInfo": {
		"id": 1422075036178,
		"name": "test",
		"grade": "grade1",
		"className": "class1",
		"gender": 0,
		"nation": "",
		"birthday": "1987-08-22",
		"idCardNo": "130303198708222110",
		"huKou": "",
		"huKouAddr": "",
		"migration": true,
		"onlyChild": true,
		"minLiving": false,
		"imburse": false,
		"orphan": false,
		"pathography": false,
		"specialPerformance": "this is special",
		"otherAnnouncement": "",
		"timeStamp": 1421909589000
	},
	"contactInfo": {
		"id": 1422075036178,
		"motherName": "wang",
		"motherCompany": "comp",
		"motherContact": "afdsfdf",
		"motherIdCard": "mother_id_card",
		"fatherName": "fang",
		"fatherCompany": "comp",
		"fatherContact": "afdsfdf",
		"fatherIdCard": "father_id_card",
		"livingAddr": "",
		"otherContact": "",
		"timeStamp": 1421909589000
	},
	"bodyInfo": {
		"id": 1422075036178,
		"doffDon": 0,
		"eating": 1,
		"toileting": 0,
		"sleeping": 2,
		"sleepingInfo": "sleeping info",
		"eatingSpeed": 0,
		"appetite": 0,
		"pickyEating": 0,
		"pickyEatingInfo": "",
		"eatingAbility": 0,
		"foodAllergy": 0,
		"foodAllergyInfo": "",
		"healthStatus": 0,
		"timeStamp": 1421909589000
	},
	"id": 1421748057732
}];
  
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
	  { field: 'id',
        displayName: 'ID',
        visible: false
	  }
    ]
  };
  $scope.gridOptions.data = 'data';
  
  var loadData = function() {
	  $http.get("/ChildrenManage/webapi/Children").
	  	success(function(data) {
	  		for (var i=0;i<data.length;i++)
	  			{
	  				data[i].basicInfo.translatedGender = data[i].basicInfo.gender == 0 ? 'male' : 'female';
	  			}
	  		$scope.data = data;
	  	}).
	  	error(function(data) {
	  		
	  	});
  }; 
  
  var deferFun = function(timeout) {
    var deferred = $q.defer();
	$interval( function() {
		deferred.resolve();
	}, timeout, 1);
	return deferred.promise;
  }
  
  var promise = deferFun(500);
  promise.then(function() {
    loadData();
  });
  
  $scope.create = function() {
    var modalInstance = $modal.open({
	  templateUrl: 'template/createStudent.html',
	  controller: 'CreateStudentCtrl',
	  //backdrop: 'static',
	  size: 'lg'
	});
	modalInstance.result.then(function () {
      //$log.info('Modal OK at: ' + new Date());
	  loadData();
    }, function () {
      //$log.info('Modal dismissed at: ' + new Date());
    });
  };
  
}]);