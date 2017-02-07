/**
 * 
 */
//class element를 select(jquery)
$(".addCommentBtn").click(addComment);
$(".deleteCommentBtn").click(deleteComment);

function addComment(e){
	e.preventDefault();
	var url = $(".addComment").attr("action");	//attr는 action에 있는 값을 가져올때 사용
	var idData = $("#contents").data();
	var contents = $("#contents").val();
	var data = {
			issueId : idData.issueId,	
			id :  idData.commentId,
			contents : contents 
	}
	
	console.log("idData:" + idData);	
	console.log("data:" + data);
	
	$.ajax({
		type: 'post',
		url: url,
		data: data,
		success: function(result){
			console.log(result);
			
			var template = $("#commentTemplate").html();
			var commentTempleteHTML = template.format(result.writer.userId, result.contents, idData.issueId, result.id, result.formattedCreationDate);
			$(".commentSpace").prepend(commentTempleteHTML);
			$("#contents").val("");
		},
		error: function(){
			console.log("error");
		}
		
	})
}



function deleteComment(e){
	e.preventDefault();
	
	var deleteBtn = $(this);
	var dataId = deleteBtn.data();
	
	var qurl = deleteBtn.attr("href");
	var data = {"issueId":dataId.issueId, 
				"commentId":dataId.commentId};
	
	$.ajax({
		type: 'delete',
		url: '/api/issue/'+dataId.issueId+'/comment/'+dataId.commentId,
		dataType: 'json',
		data: data,
		success: function(data){
			console.log(data);
			$('#'+dataId.commentId).remove();
		},
		error: function(){
			console.log("!!!delete error!!!");
		}
		
	})
}




String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};