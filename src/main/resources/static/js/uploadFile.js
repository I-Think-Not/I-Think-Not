document.onload = function(){
	var uploadForm = $("#uploadForm");
	
	$("#submit").click(function(event){
		event.preventdefault();
		$.ajax({
			url:"/api/file/",
			method:"POST",
			contentType:multipart/form-data,
			data:uploadForm.serialize()
		});
	});
};