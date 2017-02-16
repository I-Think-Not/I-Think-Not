$(document).on("click",".comment-badge", popUpComment);

function popUpComment(e){
	e.preventDefault();
	var data = $(this).data();
	console.log(data);
	
	if(data.badge == 0){
		alert("댓글이 없습니다");
	}else{
	  var popupOption = 'directories=no, toolbar=no, location=no, menubar=no, status=no, scrollbars=no, resizable=no, left=400, top=200, width=440, height=550';
	    window.open("/issue/"+data.issueId+"/comment/popUpComment", name, popupOption);
	}
}
