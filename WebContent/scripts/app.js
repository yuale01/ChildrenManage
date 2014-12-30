var app = angular.module('MyApp', ['ngTouch', 'ui.grid', 'ui.grid.paging', 'ui.grid.exporter', 'ui.grid.selection', 'ui.bootstrap']);
 
app.controller('MainCtrl', ['$scope', '$interval', '$q', '$modal', function ($scope, $interval, $q, $modal) {
 
  $scope.students = [{name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'},
					   {name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'}];
  
  var loadData = function() {
    var deferred = $q.defer();
	$interval( function() {
	  //$scope.gridOptions.data = $scope.students;
	  deferred.resolve($scope.students);
	}, 1000, 1);
	return deferred.promise;
  };  
  
  $scope.data = [] 
  $scope.gridOptions = {
    enableFiltering: true,
    exporterMenuCsv: false,
    enableGridMenu: true,
	pagingPageSizes: [25, 50, 75],
	pagingPageSize: 25,
	columnDefs: [
      { field: 'name',
	    displayName: 'Name'
	  },
      { field: 'class',
	    displayName: 'Class'
	  },
      { field: 'phone',
	    displayName: 'Phone',
	    filter: {
          condition: function(searchTerm, cellValue) {
            var strippedValue = (cellValue + '').replace(/[^\d]/g, '');
            return strippedValue.indexOf(searchTerm) >= 0;
          }
		}
	  },
	  { field: 'id',
        displayName: 'ID'	  
	  }
    ]
  };
  $scope.gridOptions.data = 'data';
  var promise=loadData();
  promise.then(function(data) {
    $scope.data = data;
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
    }, function () {
      //$log.info('Modal dismissed at: ' + new Date());
    });
  };
  
}]);