/*
UM0101 ����ڰ���-���������-ȸ������-�ߺ�����Ȯ��(email����)

UM0102 ����ڰ���-���������-ȸ���������
email:	password:	nickname:	gender:	birthday_year:	


UM0103  ����ڰ���-���������-ȸ������-ȸ����������
u_idx:	password:	nickname:	gender:	birthday_year:	hashcode


UM0104 ����ڰ���-���������-ȸ������-�α���Ȯ��
email:	password:	BrowserInformation:
*/
/*
$(function(){
	var loginMenu = $("#loginDiv"); //�α��� ��ư�� ���Ե� �޴�
	var loginbtn = $("#loginBtn");
	var accountRegBtn = $("#accountRegBtn");
	
	var logoffMenu = $("#logoffDiv"); //�α׿��� ��ư�� ���Ե� �޴�
	var logoffbtn = $("#logoffBtn");
	var accountEdit = $("#accountEdit");
	
	loginbtn.on('click',function(e){
		
	});
	
	accountRegBtn.on('click',function(e){
		var temp = "<p><label for='emailInput'>�̸���:</label><input id='emailInput' name='emailInput'/><label for='emailInput' id='emailchkBtn' class='menuOver' style='margin-left:8px;'>Ȯ��</label></p>";
		temp += "<p><label for='nicknameInput'>�г���:</label><input id='nicknameInput' name='nicknameInput'/><label for='nicknameInput' id='nicknamechkBtn' class='menuOver' style='margin-left:8px;'>Ȯ��</label></p>";
		temp += "<p><label for='genderInput' style='margin-left:2px;'>�� ��&nbsp; :</label><input id='genderInput' name='genderInput'/></p>";
		temp +="<p><label for='birthday_yearInput' style='margin-left:2px;'>�� ��&nbsp; :</label><input id='birthday_yearInput' name='birthday_yearInput'/></p>";
		temp +="<p><button class='btn' id='cancelAc_modal' href='#'>�����ϱ�</button></p>";
		//MESSI_POPUP.setContent(temp);
		//MESSI_POPUP.show();
		
		//$(".messi-title").empty();
		//$(".messi-title").append("ȸ������");viewport: {top: '760px', left: '0px'}
		//$(".messi").css("top",e.clientY+10);
		//$(".messi").css("left",e.clientX-300);

		
		var accountModal = new Messi(temp, {title: 'ȸ������',modal: true, center: false, viewport:{top:e.clientY+10+'px',left:e.clientX-300+'px'}});
		
		//Messi.ask(temp, function(e){console.log(e);},{title:'ȸ������',center: false, viewport:{top:e.clientY+10+'px',left:e.clientX-300+'px'}});
		
		
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
				alert("�̸��� ��Ŀ� �°� �ۼ����ּ���.");
	}else{
					$.ajax({         
		        type:'POST', url:SERVER_IP+'WebService.asmx/UM0101', dataType: 'json',  data:{'email':email}, 
		        error:function(xhr,status,e){    console.log(e);	},
		        success: function(jdata,data){  
		        	if(jdata.result!="N"){
		        		$("#emailInput").val(null); 
		        		
						 		alert("����� �Ұ����մϴ�..");
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