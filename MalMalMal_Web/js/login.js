/*
UM0101 사용자관리-사용자정보-회원가입-중복가입확인(email기준)

UM0102 사용자관리-사용자정보-회원정보등록
email:	password:	nickname:	gender:	birthday_year:	


UM0103  사용자관리-사용자정보-회원가입-회원정보변경
u_idx:	password:	nickname:	gender:	birthday_year:	hashcode


UM0104 사용자관리-사용자정보-회원가입-로그인확인
email:	password:	BrowserInformation:
*/
/*
$(function(){
	var loginMenu = $("#loginDiv"); //로그인 버튼이 포함된 메뉴
	var loginbtn = $("#loginBtn");
	var accountRegBtn = $("#accountRegBtn");
	
	var logoffMenu = $("#logoffDiv"); //로그오프 버튼이 포함된 메뉴
	var logoffbtn = $("#logoffBtn");
	var accountEdit = $("#accountEdit");
	
	loginbtn.on('click',function(e){
		
	});
	
	accountRegBtn.on('click',function(e){
		var temp = "<p><label for='emailInput'>이메일:</label><input id='emailInput' name='emailInput'/><label for='emailInput' id='emailchkBtn' class='menuOver' style='margin-left:8px;'>확인</label></p>";
		temp += "<p><label for='nicknameInput'>닉네임:</label><input id='nicknameInput' name='nicknameInput'/><label for='nicknameInput' id='nicknamechkBtn' class='menuOver' style='margin-left:8px;'>확인</label></p>";
		temp += "<p><label for='genderInput' style='margin-left:2px;'>성 별&nbsp; :</label><input id='genderInput' name='genderInput'/></p>";
		temp +="<p><label for='birthday_yearInput' style='margin-left:2px;'>생 년&nbsp; :</label><input id='birthday_yearInput' name='birthday_yearInput'/></p>";
		temp +="<p><button class='btn' id='cancelAc_modal' href='#'>가입하기</button></p>";
		//MESSI_POPUP.setContent(temp);
		//MESSI_POPUP.show();
		
		//$(".messi-title").empty();
		//$(".messi-title").append("회원가입");viewport: {top: '760px', left: '0px'}
		//$(".messi").css("top",e.clientY+10);
		//$(".messi").css("left",e.clientX-300);

		
		var accountModal = new Messi(temp, {title: '회원가입',modal: true, center: false, viewport:{top:e.clientY+10+'px',left:e.clientX-300+'px'}});
		
		//Messi.ask(temp, function(e){console.log(e);},{title:'회원가입',center: false, viewport:{top:e.clientY+10+'px',left:e.clientX-300+'px'}});
		
		
		$("#cancelAc_modal").on('click',function(e){
			console.log($("#emailInput").attr('readonly'));
			console.log($("#emailInput").val());
			accountModal.unload();
		});
		$("#emailchkBtn").on('click',function(e){
			checkEmail($("#emailInput").val());
		});
		$("#emailchkBtn").on('click',function(e){
			checkEmail($("#emailInput").val());
		});
		
	});
	
	logoffbtn.on('click',function(e){
		
	});
	accountEdit.on('click',function(e){
		
	});
	

});

function checkEmail(email){
	if(email.match(/^(\w+)@(\w+)[.](\w+)$/ig) == null && email.match(/^(\w+)@(\w+)[.](\w+)[.](\w+)$/ig) == null){
				alert("이메일 양식에 맞게 작성해주세요.");
	}else{
					$.ajax({         
		        type:'POST', url:SERVER_IP+'WebService.asmx/UM0101', dataType: 'json',  data:{'email':email}, 
		        error:function(xhr,status,e){    console.log(e);	},
		        success: function(jdata,data){  
		        	if(jdata.result!="N"){
		        		$("#emailInput").val(null); 
		        		
						 		alert("사용이 불가능합니다..");
		        	}else{
		        		$("#emailInput").attr("readonly",true);
		        		$("#emailInput").css({ 'background': '#33fc6f' });
		        	}
		        }
		      });
	}
}

function loginSubmit(){
	
	
}

function submitAcount(){
	
	
}*/