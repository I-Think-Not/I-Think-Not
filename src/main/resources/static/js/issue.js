var milestoneList = $("#milestoneList"); 
//var milestoneList = document.querySelector(".milestoneList");
var milestoneBtn = $("#milestone-menu");  //마일스톤 버튼
var issueId; //선택된 이슈의 아이디
var milestoneId; //선택된 마일스톤의 아이디
var labelList = $("#labelList"); //라벨 리스트
var labelBtn = $("#label-menu"); //라벨 버튼
var labelId;  //선택된 라벨의 아이디

var myLib=(function(){
	var milestoneList = $("#milestoneList"); 
	var milestoneBtn = $("#milestone-menu");  //마일스톤 버튼
	var issueId; //선택된 이슈의 아이디
	var milestoneId; //선택된 마일스톤의 아이디
	var labelList = $("#labelList"); //라벨 리스트
	var labelBtn = $("#label-menu"); //라벨 버튼
	var labelId;  //선택된 라벨의 아이디
	
	return {
	   
	}
})();


function addMilestoneClickEvent(){
/*	milestoneList.addEventListener("click",function(evt){
		var url = "/api/issue/"+evt.target.dataset.issueId+"/setMilestone/"+evt.target.dataset.milestoneId;
		issueId = evt.target.dataset.issueId;
		milestoneId = evt.target.dataset.milestoneId;
		milestoneAjax(url);
	});*/
	milestoneList.click(function(e){
		//var clickedIssue = $(this).data();
		//console.log(clickedIssue);
		var event = $(e.target);
		var currentIssueId = event.data("issueId"); //현재 선택된 마일스톤에 연결된 issueid
		var currentMilestoneId = event.data("milestoneId");  //선택된 마일스톤아이디
		var url = "/api/issue/"+currentIssueId+"/setMilestone/"+currentMilestoneId;
		milestoneAjax(url,currentIssueId,currentMilestoneId);
		console.log();
	});
	
	milestoneBtn.click(function(e){
		colorMilestoneList();
	});
/*	milestoneBtn.addEventListener("click",function(){
		colorMilestoneList();
	});*/
}

function addLabelClickEvent(){
	/*labelList.addEventListener("click",function(evt){
		console.log(evt.target);
		var url = "/api/issue/"+evt.target.dataset.issueId+"/setLabel/"+evt.target.dataset.labelId;
		issueId = evt.target.dataset.issueId;
		labelId = evt.target.dataset.labelId;
		console.log("issueId:"+issueId);
		console.log("labelId:"+labelId);
		labelAjax(url);
	});*/
/*	labelBtn.addEventListener("click",function(evt){
		//colorLabelList();
	});*/
	labelBtn.click(function(evt){
		colorLabelList();
	});
	labelList.click(function(e){ 
	      e.preventDefault();
	      var event = $(e.target);
	      console.log(event);
	      var currentIssueId = event.data("issueId");
	      var currentLabelId =event.data("labelId");
	      var url="/api/issue/"+currentIssueId+"/setLabel/"+currentLabelId;
	      console.log("issueId:"+currentIssueId);
	      console.log("labelId:"+currentLabelId);
	      
	      var data={"issueId": issueId, "labelId" : labelId};
	      
	      $.ajax({
	         type:'post',
	         url: url,
	         data: data,
	         success: function(result){
	            var elem = labelList.find("li");
	            var   updatedLabelId = result.id;
	            
	            console.log(result);
	            
	            for(var i = 0;i<elem.length;i++){
	               var item = $(elem.get(i));
	               
	               item.data("labelId",updatedLabelId);
	            }
	         }   
	      });
	      colorLabelList();
	   });
}

function colorMilestoneList(){
/*	var elem=milestoneList.getElementsByTagName("li");
	var milestoneId;
	var issuedMilestone;
	
	for(var i = 0;i<elem.length;i++){
		milestoneId = elem[i].dataset.milestoneId;
		issuedMilestone = elem[i].dataset.selectedMilestone;
		
		if(issuedMilestone==milestoneId)
			 elem[i].style.background="blue";
		else
			elem[i].style.background="white";
	}*/
	var elem = milestoneList.find("li");
	 console.log("coloring");
	 
	for(var i = 0;i<elem.length;i++){
		var item = $(elem.get(i));
		var milestoneId = item.data('milestoneId');
		issuedMilestone = item.data('selectedMilestone');
		
		if(issuedMilestone==milestoneId)
			item.css("background-color","blue");
		else
			item.css("background-color","white");
	}
}

function colorLabelList(){
	
}

function milestoneAjax(url,issueId,milestoneId){
	/*function reqListener () {
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
	
	colorMilestoneList();*/
	var data = {"issueId": issueId, "milestoneId" : milestoneId};
	console.log(data);
	
	$.ajax({
		type: 'post',
		url : url,
		data : data,
		success : function(result){
			var elem = milestoneList.find("li");
			var updatedMilestoneId = result.id;
			
			console.log(result);
			
			for(var i = 0;i<elem.length;i++){
				  var item = $(elem.get(i));
				  //item.attr("data-selected-milestone",updatedMilestoneId);
				  item.data("selectedMilestone",updatedMilestoneId);
				  console.log("selectedMiestone"+item.data("selectedMilestone"));
				  console.log(item.attr("data-selected-milestone"));
			}
			  //dataset dom으로 업데이트 하기.
		}
	
	});
	colorMilestoneList();

}

function labelAjax(url){
	console.log("hello label Ajax");
	var oReq = new XMLHttpRequest();
	oReq.addEventListener("load", function(){
		 console.log(this.responseText);
	});
	oReq.open("post", url);
	var data = {"issueId": issueId, "labelId" : labelId};
	oReq.send(data);
}


document.addEventListener("DOMContentLoaded",function(){
	addMilestoneClickEvent();
	addLabelClickEvent();
});
