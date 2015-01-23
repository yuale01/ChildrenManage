'use strict';

angular.module('angularApp')
  .controller('ListCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
	$scope.students = [{name: 'Yuan', class: 'Class 12', phone: '13030310124', id: '001'},
					   {name: 'Le', class: 'Class 2', phone: '13430310124', id: '002'}];
					   
	$scope.gridOptions = {
    enableFiltering: true,
    columnDefs: [
      // default
      { field: 'name' },
      // pre-populated search field
      { field: 'class' },
      // custom condition function
      {
        field: 'phone',
        filter: {
          condition: function(searchTerm, cellValue) {
            var strippedValue = (cellValue + '').replace(/[^\d]/g, '');
            return strippedValue.indexOf(searchTerm) >= 0;
          }
        }
      },
      // multiple filters
      { field: 'id'}
    ]
  };
  
  $scope.gridOptions.data = students;
});