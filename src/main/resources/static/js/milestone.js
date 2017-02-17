
var myLib=(function(){
	var openIssueList = function(){
		return $("#openIssueList");
	};
	var closeIssueList = function(){
		return $("#closeIssueList");
	};
	var issueList = function(){
		return $("#issueList");
	};
	var milestoneFooter = function(){
		return $("#milestone_footer");
	};
	return {
		openIssueList : openIssueList,
		closeIssueList : closeIssueList,
		issueList : issueList,
		milestoneFooter : milestoneFooter
	};
})();

function addClickEvent(){
	myLib.openIssueList().click(function(e){
		var event = $(e.target);
		console.log(e.target);
		var url="/api/milestone/"+event.data("milestoneId")+"/openIssues";
		milestoneAjax(url,event.data("milestoneId"));
	});
	myLib.closeIssueList().click(function(e){
		var event = $(e.target);
		var url="/api/milestone/"+event.data("milestoneId")+"/closeIssues";
		milestoneAjax(url,event.data("milestoneId"));
	});
}

function milestoneAjax(url,milestoneId){
/*	function reqListener () {
		  console.log(this.responseText); //JSON.parse(this.responseText)
		  var abc=JSON.parse(this.responseText);
	}  
	var oReq = new XMLHttpRequest();
	oReq.addEventListener("load", reqListener);
	oReq.open("post", url);
	oReq.send();*/
	var data={"id":milestoneId};
	 $.ajax({
         type:'post',
         url: url,
         data: data,
         success: function(result){
        	 var template = myLib.milestoneFooter().html();
        	 var templateResult="";
        	for(var i = 0;i<result.length;i++){
           	 templateResult += template.format(result[i].id,result[i].subject,new Date(result[i].updateDate).toISOString(),
        			 result[i].writer.userId,result[i].commentCounter,result[i].state,result[i].writer.profile);
        	}

        	myLib.issueList().html(templateResult);
         },
         error:function(err){
        	 console.log("error");
         }
      });
	 
}

document.addEventListener("DOMContentLoaded",function(){
	addClickEvent();
});

String.prototype.format = function(){
	  var args = arguments;
	  return this.replace(/{(\d+)}/g, function(match, number) {
	    return typeof args[number] != 'undefined'
	        ? args[number]
	        : match
	        ;
	  });
};