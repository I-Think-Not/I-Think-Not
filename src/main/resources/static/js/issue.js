$(document).on("click",".label-tag-deleBtn", deleteLabel);

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

function deleteLabel(e){
	e.preventDefault();
	var deleteBtn = $(this);
	var selectData = deleteBtn.parent().data();
	console.log(selectData);
	var data = {
			"issueId":selectData.issueId, 
			"labalId":selectData.labelId
	};
	
	$.ajax({
		type: 'delete',
		url: '/api/issue/'+selectData.issueId+'/delLabel/'+selectData.labelId,
		data : data,
		success: function(result){
			if(result == true){
				deleteBtn.parent().remove();
			}
		}
	})
	
}

function addLabelClickEvent(){
	myLib.labelBtn().click(function(evt){
		colorLabelList();
	});
	myLib.labelList().click(function(e){ 
	      e.preventDefault();
	      var event = $(e.target);
	      var currentIssueId = event.data("issueId");
	      var currentLabelId =event.data("labelId");
	      console.log(currentLabelId);
	      var url="/api/issue/"+currentIssueId+"/setLabel/"+currentLabelId;
	      var data={"issueId": currentIssueId, "labelId" : currentLabelId};
	      
	      $.ajax({
	         type:'post',
	         url: url,
	         data: data,
	         success: function(result){
	            var template = $("#labelTagTemplate").html();
		      		var labelTagTemplateHTML = template.format(result.name, result.id, currentIssueId);
	            $(".label-tag").append(labelTagTemplateHTML);
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
			item.css("background-color","blue");
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
