/**
 * 
 */
$(".addCommentBtn").click(addComment);
$(".deleteCommentBtn").click(deleteComment);
$(".modifyCommentBtn").click(replaceCommentHTML);

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
	$.ajax({
		type: 'post',
		url: url,
		data: data,
		success: function(result){
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
	var data = {
				"issueId":dataId.issueId, 
				"commentId":dataId.commentId
	};
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

function replaceCommentHTML(e){
	e.preventDefault();
	
	var modifyBtn = $(this);
	var dataCommentId = modifyBtn.data();
	var commentContents = modifyBtn.prev().find('.comment_contents').text();
	var parent = modifyBtn.parents('.comment');
	var updateSelector = parent.next();
	
	var template = $("#commentModifyTemplate").html();
	var commentModifyTemplateHTML = template.format(commentContents, dataCommentId.commentId);
	parent.replaceWith(commentModifyTemplateHTML);

	$(".updateCommentBtn").click(modifyComment);
}

function modifyComment(e){
	e.preventDefault();
	var parent = $(this).parents('.new-comment');
	var url = $(".updateComment").attr("action");
	console.log(url);
	var idData = $("#updateContents").data();
	var contents = $("#updateContents").val();
	var data = {
			issueId : idData.issueId,	
			id :  idData.commentId,
			contents : contents
	}
	console.log(data);
	$.ajax({
		type: 'put',
		url: url,
		data: data,
		success: function(result){
			var template = $("#commentTemplate").html();
			var commentTempleteHTML = template.format(result.writer.userId, result.contents, idData.issueId, result.id, result.formattedCreationDate);
			parent.replaceWith(commentTempleteHTML);
		},
		error: function(){
			console.log("error");
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