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

//Submit 버튼이 동작가능하도록 만드는 변수
var priorCheckSubmit = false;

$(function() {
	$("#idCheck").click(function(){
			if($("#userId").val().length > 0){
				var userId = $("#userId").val();
				console.log(userId);
				$.ajax({
					type:'POST',
					url: '/api/user/idCheck' ,
					data:
					{
						  "id":userId,
					},
					success: function(result){
						console.log(result);
						//이제 Submit 버튼이 동작가능
						priorCheckSubmit = true;
						if(result == false){
							$("#result_id_msg").html("아이디가 이미 존재합니다.");
						}else{
							$("#result_id_msg").html("아이디 사용이 가능합니다.");
						}
					}
				});
			}
	});
});

$(function(){
	$('#joinButton').click(function(event){
		if(priorCheckSubmit == false && ($("#userId").val().length > 0)){
			alert("ID중복체크를 하십시오");
			event.preventDefault();
		}
	});
});