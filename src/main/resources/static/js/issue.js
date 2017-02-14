var myLib=(function(){
	var milestoneList = function(){
	    return $("#milestoneList");	
	};
	var milestoneBtn = function(){
		return $("#milestone-menu");  //마일스톤 버튼
	};
	var labelList = function(){
		return $("#labelList"); //라벨 리스트
	};
	var labelBtn = function(){
		return $("#label-menu"); //라벨 버튼
	};
	return {
	   milestoneList : milestoneList,
	   milestoneBtn : milestoneBtn,
	   labelList : labelList,
	   labelBtn : labelBtn
	};
})();


function addMilestoneClickEvent(){
	myLib.milestoneList().click(function(e){
		var event = $(e.target);
		var currentIssueId = event.data("issueId"); //현재 선택된 마일스톤에 연결된 issueid
		var currentMilestoneId = event.data("milestoneId");  //선택된 마일스톤아이디
		var url = "/api/issue/"+currentIssueId+"/setMilestone/"+currentMilestoneId;
		milestoneAjax(url,currentIssueId,currentMilestoneId);
		console.log();
	});
	
	myLib.milestoneBtn().click(function(e){
		colorMilestoneList();
	});
}

function addLabelClickEvent(){
	myLib.labelBtn().click(function(evt){
		colorLabelList();
	});
	myLib.labelList().click(function(e){ 
	      e.preventDefault();
	      var event = $(e.target);
	      console.log(event);
	      var currentIssueId = event.data("issueId");
	      var currentLabelId =event.data("labelId");
	      var url="/api/issue/"+currentIssueId+"/setLabel/"+currentLabelId;
	      console.log("issueId:"+currentIssueId);
	      console.log("labelId:"+currentLabelId);
	      
	      var data={"issueId": currentIssueId, "labelId" : currentLabelId};
	      
	      $.ajax({
	         type:'post',
	         url: url,
	         data: data,
	         success: function(result){
	            var elem = myLib.labelList().find("li");
	            var updatedLabelId = result.id;
	            
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
	var elem = myLib.milestoneList().find("li");
	 console.log("coloring");
	 
	for(var i = 0;i<elem.length;i++){
		var item = $(elem.get(i));
		var milestoneId = item.data('milestoneId');
		var issuedMilestone = item.data('selectedMilestone');
		
		if(issuedMilestone==milestoneId)
			item.css("background-color","#424242");
		else
			item.css("background-color","white");
	}
}

function colorLabelList(){
	
}

function milestoneAjax(url,currentissueId,currentmilestoneId){
	var data = {"issueId": currentissueId, "milestoneId" : currentmilestoneId};
	console.log(data);
	
	$.ajax({
		type: 'post',
		url : url,
		data : data,
		success : function(result){
			var elem = myLib.milestoneList().find("li");
			var updatedMilestoneId = result.id;
			
			console.log(result);
			
			for(var i = 0;i<elem.length;i++){
				  var item = $(elem.get(i));
				  item.data("selectedMilestone",updatedMilestoneId);
				  console.log("selectedMiestone"+item.data("selectedMilestone"));
				  console.log(item.attr("data-selected-milestone"));
			}
			myLib.milestoneBtn().html("Milestone("+result.subject+")");
			  //dataset dom으로 업데이트 하기.
		}
	
	});
	colorMilestoneList();

}

document.addEventListener("DOMContentLoaded",function(){
	addMilestoneClickEvent();
	addLabelClickEvent();
});
