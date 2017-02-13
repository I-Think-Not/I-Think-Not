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
					},
					success : function(result){
						console.log(result);
						if(result == "ok"){
							$("#result_id_msg").html("사용 가능한 아이디 입니다.");
						} else {
							$("#result_id_msg").html("사용 불가능한 아이디 입니다.");
						}
					}
				});
			}
	});
});