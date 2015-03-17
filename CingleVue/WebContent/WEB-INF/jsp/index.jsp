<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="eng" ng-app="myApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>CingleVue</title>

<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container" ng-controller="AnnounceController" ng-init="init()">
		<div class="header">
			<h2>CignleVue - Announcement</h2>
			<p>View List of Announcements, View Detail of an Announcement
				that you chose, Register Announcement, Update/Delete Announcement</p>
		</div>

		<div class="container">
			<section>
				<article>
					<p>
						searched announcements :<span ng-bind="announcementsData.length"></span>
						articles
					</p>
					<div id="listError"></div>
				</article>
			</section>
			<div class="table-responsive">
				<a ng-click="addAnnouncement()" class="btn btn-primary active">Add
					Announcement</a>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>title</th>
							<th>start Date</th>
							<th>Expiry Date</th>
							<th>View</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="announcement in announcementsData">
							<td>{{announcement.title}}</td>
							<td>{{announcement.startDate}}</td>
							<td>{{announcement.expireyDate}}</td>
							<td>&nbsp;<a href="" ng-click="$parent.viewDetail(announcement)">View Detail</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>		
			
			<div id="viewDetailDiv" ng-show="viewDetailFlag" class="col-md-12">			
				<div class="col-md-12"> 
				<h4>View Article Detail</h4>
				<a href="" ng-click="modify('update')" class="btn btn-default" ng-show="mForm.$dirty && mForm.$valid">update</a>
				<a href="" ng-click="modify('delete')" class="btn btn-default" >delete</a>
				</div>	
				<div id="callout-tooltip-multiline">
			    <form name="mForm" novalidate>
			    <h4><input type="text" class="form-control" ng-model="activeAnnouncement.title" required/></h4>
			    <p><textarea  class="form-control" ng-model="activeAnnouncement.body" rows="14" required></textarea></p>
			    </form>
			  </div>
			</div>
			
			<div id="addArticle" class="modal fade bs-example-modal-lg"
				role="dialog" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<h4>New Announcement</h4>
						<form role="form" name="nForm" novalidate>
							<div class="form-group">
								<label for="title">Title</label> <input type="text"
									class="form-control" id="title"
									placeholder="Enter Title" ng-model="announcementForm.title" required >
							</div>
							<div class="form-group">
								<label for="body">Contents</label> <textarea rows="14"
									class="form-control" id="body"
									placeholder="Enter Contents" ng-model="announcementForm.body" required ></textarea>
							</div>
							<div class="form-group">
								<input type="button" ng-click="registerAnnouncement()" value="Create Announcement" ng-show="nForm.$valid && nForm.$dirty">
							</div>
						</form>
					</div>
				</div>
			</div>
			
		</div>
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="http://www.underscorejs.org/underscore.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="https://code.jquery.com/jquery-1.11.2.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<!-- AngularJS -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-route.js"></script>
	<script>
		$().ready(function() {
		});	
		
		var myApp = angular.module("myApp", []).factory('responseMsgParser', function(){
			var msg = "";
			return function(input,elementId){
				if(_.isArray(input)) {
					msg= input.join(",").replace(/,/g,"\n");
				} else {
					msg=input;
				}
				$("#"+elementId).innerHtml(msg);
			};
		})
		.config(['$provide',function($provide){
			$provide.factory('restApi',function(){
				return function(param) {
					return {				
					"getList": {method:'GET',uri:'<%=root%>/announcements',Accept:'application/json',},
					"getDetail": {method:'GET',uri:'<%=root%>/announcements/'+param,Accept:'application/json',},
					"add": {method:'POST',uri:'<%=root%>/announcements',Accept:'application/json','Content-Type':'application/json'},
					"update": {method:'POST',uri:'<%=root%>/announcement/'+param+'/update',Accept:'application/json','content-type':'application/json'},
					"delete": {method:'POST',uri:'<%=root%>/announcement/'+param+'/delete',Accept:'application/json','content-type':'application/json'}
					
					};
				};
			});
			$provide.factory('httpRequest', function(){
				return function(restApiObj,data) {
					var req={};
					req["method"] = restApiObj.method;
					req["url"] = restApiObj.uri;
					req["header"]={};
					if(restApiObj["Content-Type"]){
						req.header["Content-Type"]=restApiObj["Content-Type"];
					}
					if(restApiObj["Accept"]){
						req.header["Accept"]=restApiObj["Accept"];
					}
					req["data"] = data;
					return req;
				};
			});
		}]);
		
		
		myApp.controller("AnnounceController",['$http','$scope','$filter','responseMsgParser','restApi','httpRequest',
		                                       function($http,$scope,$filter,responseMsgParser,restApi,httpRequest){
			
			$scope.viewName="Announcements Managmenet";
			$scope.announcementsData = [];

			$scope.init = function(){
				//console.log(restApi().getList);
				//console.log(httpRequest(restApi().getList));
				$http(httpRequest(restApi().getList))
				.success(function(data, headersGetter, status){
					$scope.announcementsData = data.body;
				})
				.error(function(data, headersGetter, status){
					responseMsgParser(data.errorMsg,"listError");
				});
			};
				
			$scope.announcementForm = {title:"",body:""};
			
			$scope.registerAnnouncement=function(){				

				var jsonStr= angular.toJson($scope.announcementForm);
				$http(httpRequest(restApi().add,jsonStr))
				.success(function(data, headersGetter, status){
					$scope.announcementsData.push(data.body);
					$("#addArticle").modal('hide');
					$scope.announcementForm = {title:"",body:""};
				})
				.error(function(data, headersGetter, status){
					responseMsgParser(data.errorMsg,"listError");
				});
			};
			$scope.addAnnouncement = function() {
				$("#addArticle").modal('show');
			};
			
			$scope.activeAnnouncement = {};
			$scope.viewDetailFlag=false;
			$scope.viewDetail = function(announce) {
				$scope.viewDetailFlag=true;
				$scope.activeAnnouncement=announce;
				window.scrollTo(0, $("#viewDetailDiv").position().top+10);
			};
			
			$scope.modify = function(mode){
				
				var jsonStr= angular.toJson($scope.activeAnnouncement);
				var req = "";				
				if(mode=="update") {
					req = httpRequest(restApi($scope.activeAnnouncement.id)["update"],jsonStr);
				} else {
					req = httpRequest(restApi($scope.activeAnnouncement.id)["delete"],jsonStr);
				}				
				$http(req)
				.success(function(data, headersGetter, status){
					if(mode=="delete") {
						$scope.init();
					}
					$scope.viewDetailFlag=false;
					$scope.activeAnnouncement=null;
				});
				
			};
			
			
		}]);
		
	</script>

</body>
</html>