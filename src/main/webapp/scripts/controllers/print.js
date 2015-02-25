'use strict';

angular.module('print', [])
  .controller('PrintCtrl', ['$scope', function ($scope) {
    
	$scope.child = {"basicInfo": {
		"name": 'test',
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
	}};
	
	
  }]);