var milestoneList=document.querySelector("#milestoneList"); 
//var milestoneList = document.querySelector(".milestoneList");
var milestoneBtn = document.querySelector("#milestone-menu");  //마일스톤 버튼
var issueId; //선택된 이유의 아이디
var milestoneId; //선택된 마일스톤의 아이디
var labelList=document.querySelector("#labelList"); //라벨 리스트
var labelBtn=document.querySelector("#label-menu"); //라벨 버튼
var labelId;  //선택된 라벨의 아이디

function addMilestoneClickEvent(){
	milestoneList.addEventListener("click",function(evt){
		var url = "/api/issue/"+evt.target.dataset.issueId+"/setMilestone/"+evt.target.dataset.milestoneId;
		issueId = evt.target.dataset.issueId;
		milestoneId = evt.target.dataset.milestoneId;
		milestoneAjax(url);
	});
	milestoneBtn.addEventListener("click",function(){
		colorMilestoneList();
	});
}

function addLabelClickEvent(){
	labelList.addEventListener("click",function(evt){
		console.log(evt.target);
		var url="/api/issue/"+evt.target.dataset.issueId+"/setLabel/"+evt.target.dataset.labelId;
		issueId = evt.target.dataset.issueId;
		labelId = evt.target.dataset.labelId;
		console.log("issueId:"+issueId);
		console.log("labelId:"+labelId);
		labelAjax(url);
	});
/*	labelBtn.addEventListener("click",function(evt){
		//colorLabelList();
	});*/
}

function colorMilestoneList(){
	var elem=milestoneList.getElementsByTagName("li");
	var milestoneId;
	var issuedMilestone;
	
	for(var i = 0;i<elem.length;i++){
		milestoneId = elem[i].dataset.milestoneId;
		issuedMilestone = elem[i].dataset.selectedMilestone;
		
		if(issuedMilestone==milestoneId)
			 elem[i].style.background="blue";
		else
			elem[i].style.background="white";
	}
}

function colorLabelList(){
	
}

function milestoneAjax(url){
	function reqListener () {
		  console.log(this.responseText); //JSON.parse(this.responseText)
		 var response=JSON.parse(this.responseText);
		 var elem=milestoneList.getElementsByTagName("li");
		 var changeMilestoneId = response.id;
		  
		  for(var i=0;i<elem.length;i++){
			  elem[i].dataset.selectedMilestone=changeMilestoneId;
			  console.log(elem[i].dataset.selectedMilestone);
		  }
		  //dataset dom으로 업데이트 하기.
	}  

	var oReq = new XMLHttpRequest();
	oReq.addEventListener("load", reqListener);
	oReq.open("post", url);
	var data={"issueId": issueId, "milestoneId" : milestoneId};
	oReq.send(data);
	colorMilestoneList();
}

function labelAjax(url){
	console.log("hello label Ajax");
	var oReq = new XMLHttpRequest();
	oReq.addEventListener("load", function(){
		 console.log(this.responseText);
	});
	oReq.open("post", url);
	var data={"issueId": issueId, "labelId" : labelId};
	oReq.send(data);
}


document.addEventListener("DOMContentLoaded",function(){
	addMilestoneClickEvent();
	addLabelClickEvent();
});
