

$(function(){
	$(".sendEmail").click(function(){
		if($("#email").val().length > 0){
			var userEmail = $("#email").val();
			console.log(userEmail);
			$.ajax({
				type: 'POST',
				url: '/api/user/findPw',
				data:{
					"toEmail":userEmail
				},
				success: function(result){
					console.log(result);
					if(result == false){
						$("#resultEmail").html("이메일이 잘못되었습니다.");
					}else{
						$("#resultEmail").html("변경된 비밀번호가 전송되었습니다.");
					}
				}
			});
		}
	});
});

$(function() {
	$("#id_check").click(function(){
			if($("#userId").val().length > 0){
				var userId = $("#userId").val();
				console.log(userId);
				$.ajax({
					type:'POST',
					url: '/api/user/id_check' ,
					data:
					{
						  "id":userId,
					}
				});
			}
	});
});