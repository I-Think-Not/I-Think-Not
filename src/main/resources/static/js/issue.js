$(document).on("click",".label-tag-deleBtn", deleteLabel);
$(document).on("click",".assignee-tag-deleBtn", deleteAssignee);
$(document).on("click",".assignSelect", authAssignee);

$(document).ready(function(){
    $("a.issue-delBtn").click(function(){
        document.getElementById("issues-menu-lower-right").submit();
    }); 
});

$(function(){
	$('#issueSubmit').click(function(event){
		if($("#subject").val().length > 40){
			alert("제목의 글자수가 40자를 넘었습니다.");
			event.preventDefault();
		}
	});
});

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
	});
	
	myLib.milestoneBtn().click(function(e){
		colorMilestoneList();
	});
}

function deleteLabel(e){
	e.preventDefault();
	var deleteBtn = $(this);
	var selectData = deleteBtn.parent().data();
	var data = {
			"issueId":selectData.issueId, 
			"labalId":selectData.labelId
	};
	
	$.ajax({
		type: 'delete',
		url: '/api/issue/'+selectData.issueId+'/label/'+selectData.labelId,
		data : data,
		success: function(result){
			if(result == true){
				deleteBtn.parent().remove();
			}
		}
	})
}


function authAssignee(e){
	e.preventDefault();
	var selectorVar = $(this);
	var idData = selectorVar.data();
	$.ajax({
		type: 'get',
		url: '/api/issue/'+idData.issueId+'/setAssignee',
		data: {"userId": idData.userId},
		success: function(result){
		        var template = $("#AssigneeTagTemplate").html();
		  		var AssigneeTagTemplate = template.format(result.name, result.id, idData.issueId);
		  		$(".assignee-tag").append(AssigneeTagTemplate);
		},error: function(){
			alert("아니됩니다");
		}
	})
	}

function deleteAssignee(e){
	e.preventDefault()
	var deleteBtn = $(this);
	var selectData = deleteBtn.parent().data();
	var data = {
			"issueId":selectData.issueId, 
			"assigneeId":selectData.assigneeId
	};
	$.ajax({
		type: 'delete',
		url: '/api/issue/'+selectData.issueId+'/delassignee/'+selectData.assigneeId,
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
	         }   ,error: function(){
	 			alert("아니됩니다");
	 		}
	      });
	      colorLabelList();
	   });
}

function colorMilestoneList(){
	var elem = myLib.milestoneList().find("li");
	 
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
	
	$.ajax({
		type: 'post',
		url : url,
		data : data,
		success : function(result){
			var elem = myLib.milestoneList().find("li");
			var updatedMilestoneId = result.id;
			
			for(var i = 0;i<elem.length;i++){
				  var item = $(elem.get(i));
				  item.data("selectedMilestone",updatedMilestoneId);
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
