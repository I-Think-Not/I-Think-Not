$(document).on("click",".assignSelect", authAssignee);

function authAssignee(e){
e.preventDefault();
var selectedUserId = $(".assignSelect").val();
var deleteData = $("#deleteCommentBtn").data();
console.log(deleteData);
$.ajax({
	type: 'get',
	url: 'issue/'+deleteData.issueId+'/setAssignee',
	data: selectedUserId,
	success: function(result){
		
	},
	error: function(){
		console.err();
	}
})
}



//$(document).on("click",".issueShow", issueShow);
//
//function issueShow(e){
//	e.preventDefault();
//	var issueData = $(".issueShow").data();
//	var issueId = issueData.issueId;
//	var url = issueData.url;
//	
//	$.ajax({
//		type: 'get',
//		url: url,
//		data: issueId,
//		success: function(result){
//			
//		},
//		error: function(){
//			console.err();
//		}
//	})
//}
//
//
//String.prototype.format = function() {
//	  var args = arguments;
//	  return this.replace(/{(\d+)}/g, function(match, number) {
//	    return typeof args[number] != 'undefined'
//	        ? args[number]
//	        : match
//	        ;
//	  });
//	};