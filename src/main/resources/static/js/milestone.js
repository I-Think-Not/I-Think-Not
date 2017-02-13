var openIssueList = document.querySelector("#openIssueList");
var closeIssueList = document.querySelector("#closeIssueList");


function addClickEvent(){
	openIssueList.addEventListener("click",function(){
		var url="/milestone/api/"+{{id}}+"/openIssueList";
		ajax(url);
	});
	closeIssueList.addEventListener("click",function(){
		var url="/milestone/api/{{id}}/closeIssueList";
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
