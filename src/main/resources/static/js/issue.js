function $(ele){
	return document.querySelector(ele);
}
var ul=document.querySelector("#milestoneList");
var milestoneList=document.querySelector(".milestoneList");
var issueId;
var milestoneId;

function addClickEvent(){
	
	ul.addEventListener("click",function(evt){
		console.log("ul item : "+evt.target.dataset.milestoneId);
		var url="/api/issue/"+evt.target.dataset.issueId+"/setMilestone/"+evt.target.dataset.milestoneId;
		issueId=evt.target.dataset.issueId;
		milestoneId=evt.target.dataset.milestoneId;
		ajax(url);
	});
	
/*	milestoneList.addEventListener("click",function(evt){
		//milestoneList = document.querySelector(".milestoneList");
		milestoneList=this;
		console.log(this);
		listdata=milestoneList.dataset.issueId;
		var url="/api/issue/"+milestoneList.dataset.issueId+"/setMilestone/"+milestoneList.dataset.milestoneId;
		ajax(url);
	});*/
}

function ajax(url){
	function reqListener () {
		  console.log(this.responseText); //JSON.parse(this.responseText)
		  var abc=JSON.parse(this.responseText);
	}  
	
	
	var oReq = new XMLHttpRequest();
	oReq.addEventListener("load", reqListener);
	oReq.open("post", url);
	//var data={"issueId": milestoneList.dataset.issueId,"milestoneId":milestoneList.dataset.milestoneId};
	var data={"issueId": issueId,"milestoneId":milestoneId};
	console.log(data);
	oReq.send(data);
}

document.addEventListener("DOMContentLoaded",function(){
	addClickEvent();
});
