/*var openIssueList = document.querySelector("#openIssueList");
var closeIssueList = document.querySelector("#closeIssueList");*/

var myLib=(function(){
	var openIssueList = function(){
		return $("#openIssueList");
	};
	var closeIssueList = function(){
		return $("#closeIssueList");
	};
	
	return {
		openIssueList : openIssueList,
		closeIssueList : closeIssueList
	};
})();

function addClickEvent(){
	myLib.openIssueList().click(function(e){
		var event = $(e.target);
		var url="/milestone/api/"+event.data("issueId")+"/openIssues";
		ajax(url);
	});
	myLib.closeIssueList().click(function(e){
		var event = $(e.target);
		var url="/milestone/api/"+event.data("issueId")+"/closeIssues";
		ajax(url);
	});
}

function ajax(url){
	function reqListener () {
		  console.log(this.responseText); //JSON.parse(this.responseText)
		  var abc=JSON.parse(this.responseText);
	}  
	var oReq = new XMLHttpRequest();
	oReq.addEventListener("load", reqListener);
	oReq.open("post", url);
	oReq.send();
}

document.addEventListener("DOMContentLoaded",function(){
	addClickEvent();
});
