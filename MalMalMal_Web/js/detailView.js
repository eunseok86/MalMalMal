function detailView(jdata,issueId){
	var root = $('#detailView');
	var nrCols=5;
	var maxRows=23;
	var nrRows=maxRows+1;
	root.empty();



	var newdiv, divIdName;
	//console.log(jdata);
	var tidArr = new Array();
	var tab=document.createElement('table');
			tab.className="timelineTable";
			tab.setAttribute('id',"timelineTable");
			tab.setAttribute('name',"timelineTable");
	var thead = document.createElement('thead');

	for(i=0; i < jdata.length;i++){
		if(i%3==0||i==0){//4개씩 잘라서 테이블 만듬, 처음이랑..
			var row,cell;
			row=document.createElement('tr');
			row.setAttribute('id',"row"+i);
			cell=document.createElement('th');
			
			tidArr.push(i);
			row.appendChild(cell);
			
			cell=document.createElement('th');
			cell.appendChild(document.createTextNode(jdata[(i)].cpKorName));
			cell.setAttribute('id',jdata[(i)].cpId);
			
		}else{
			cell=document.createElement('th');
			cell.appendChild(document.createTextNode(jdata[(i)].cpKorName));
			cell.setAttribute('id',jdata[(i)].cpId);
		}
		
		row.appendChild(cell);

		if((i+1)%3==0||(i+1) == jdata.length){//테이블 닫기
			thead.appendChild(row);
		}
	}
	
	
	var tbody = document.createElement('tbody');
			
			
			row=document.createElement('tr');
			cell = document.createElement('td');
			cell.setAttribute('colspan','4');
			cell.setAttribute('id','targetCell');
			cell.setAttribute('height','500');
			row.appendChild(cell);
			tbody.appendChild(row);
			
	tab.appendChild(thead);
	tab.appendChild(tbody);
	root.append(tab);
	
	for(i=0;i<tidArr.length;i++ ){
		(function() {   // 새로운 스코프 선언
        var idx = tidArr[i]; // 클로저가 접근할 변수 선언
       	$('#row'+idx+'').on('click',function(e){
       		
		
       		var targetRow =	$('#row'+idx+'');
		
					var tdId1 = targetRow.find('th:eq(1)').attr('id');
					
					var tdId2 = targetRow.find('th:eq(2)').attr('id');
					
					var tdId3 = targetRow.find('th:eq(3)').attr('id');
					
					if(tdId2==null)tdId3="-1";
					if(tdId3==null)tdId3="-1";
					
					$('.timelineTable thead tr').removeClass('focus');
    			targetRow.addClass('focus');		
    			
    			
       	   
    			
        
        

    			
					$.ajax({         
				       type:'POST',
							 url:SERVER_IP+'WebService.asmx/TL0112',               //데이터를 요청할 페이지
							 dataType: 'json',                   //데이터 유형
				  	   data:{'in_idx':issueId,'StartDate':'','EndDate':'', 'cpId1':tdId1, 'cpId2':tdId2, 'cpId3':tdId3},  //요청할 페이지에 전송할 파라메터
				       error:function(xhr,status,e){       //에러 발생시 처리함수
								  console.log(e);
					   	 },
					  	 success: function(jdata2,data){           //성공시 처리 함수, 인수는 위에서 data를 사용한 경우
									callDetailData(jdata2,tdId1,tdId2,tdId3);
									$("body").removeClass("loading");
									 console.log(jdata2);
						 	 }
						 	 
					});
					
       		
       		/*
					$('tbody[id^="tbody"]').slideUp( "slow", function() {
							$('#tbody'+idx+'').slideDown("slow");
					});
					*/
			});
    })();
		
	}
	
	
	//callDetailData(jdata2);
	
	$('#row0').trigger('click');
		
	$('.tabs').fadeOut("slow");
	$(".topDatePicker").fadeOut("slow",function(){
		$(".cloudCallBtn").fadeIn("slow");
	});
	$( ".timelineLayout" ).slideUp( "slow");
	$( ".issuesCloudLayout" ).slideUp( "slow", function() {
				$( ".detailViewLayout" ).slideDown("slow");	
	 });
	
	
}


function callDetailData(jdata2,tdId1,tdId2,tdId3){
	
									
	var tableId = tdId1+tdId2+tdId3;
	var targetBody = $('#targetCell');
	targetBody.empty();
	
	targetBody.append("<table id=tableView"+tableId+" class='timelineTable'><tbody id="+tableId+">");
	var targetBody = $('#'+tableId+'');
	
	for(var i=0;i<jdata2.length;i++){
		var timeH = jdata2[i].yyyymmddhh;
		timeH=timeH.substring(0,8);//월
		var cpid = jdata2[i].cpid;
		//var targetTable =	$('#'+cpid+'').closest('table');
		var Id1 = tdId1 + timeH;
		var Id2 = tdId2 + timeH;
		var Id3 = tdId3 + timeH;
		
		
		if($('#row'+timeH+'').length==0){//해당 row가 생성이 안되어 있으면...
			targetBody.append("<tr id=row"+timeH+"></tr>");
			
			
			var targetRow = $('#row'+timeH+'');
			targetRow.append("<td id="+timeH+">"+timeH+"</td>");
			targetRow.append("<td id="+Id1+" ></td>");
			targetRow.append("<td id="+Id2+"></td>");
			targetRow.append("<td id="+Id3+"></td>");
		}
		
	
	
		
		var targetCell = $('#'+cpid+''+timeH+'');
		
		for(var x=0;x<jdata2[i].tln.length;x++){//뉴스들
			var data = jdata2[i].tln[x];
			var secondComment=false;
			// ; 뉴스타이틀
			var addData = $("<div class='newsbox'><a href='"+data.outlinkUrl+"' target='_blank'>"+data.title+"  "+data.n_idx+"</a></div>");
			targetCell.append(addData);
			var commenterPrev;
			
			for(var y=0;y<data.tlp.length;y++){//발언부분 추출
				var comment = data.tlp[y].o;
				var commenter =  data.tlp[y].s;
				var commentId = commenter.replace(/ /gi, '');
				commentId= commentId+data.n_idx;
				var commentParser = 	parseSpString(commentId);
					var divObj;
				if(addData.find('#'+commentParser+'').length != 1){
					addData.append("<div class='commentfooter' id='"+commentId+"'>"+commenter+"</div>");
					divObj = $("<div class='commentbox' id='"+commentId+"ContentBtn'> <a href='"+jdata2[i].outlinkUrl+"' target='_blank'>"+comment+"</a></div>");
					addData.append(divObj);
					
					
	
				//	addData
					
					secondComment = false;				
				}else if(!secondComment){
					if($("#"+commentId+"MoreBtn").length==0){
					addData.find('#'+commentParser+'').append("<div class='menuOver rightAlign' id='"+commentId+"MoreBtn'>More...</div>");
					addData.find('#'+commentParser+'').append("<div class='menuOver rightAlign' id='"+commentId+"HideBtn' style='display:none;'>Hide...</div>");
					}
					divObj = $("<div class='commentbox' id='"+commentId+"More' style='display:none;'> <a href='"+jdata2[i].outlinkUrl+"' target='_blank'>"+comment+"</a></div>");
					addData.append(divObj);
					
					$(function() {   // 새로운 스코프 선언
        			var paserID = commentParser; // 클로저가 접근할 변수 선언
        			var Tdate = jdata2[i].yyyymmddhh;
        			var Tcid = cpid;
							$("#"+paserID+"MoreBtn").on('click',function(e){
								$( "#"+paserID+"More" ).slideDown( "fast");
								$("#"+paserID+"MoreBtn").hide();
								$("#"+paserID+"HideBtn").show();
							});
							
							$("#"+paserID+"HideBtn").on('click',function(e){
								$( "#"+paserID+"More" ).slideUp( "fast");
								$("#"+paserID+"MoreBtn").show();
								$("#"+paserID+"HideBtn").hide();
							});
					});
					
					
					secondComment = true;
				}else{
					divObj = $("<div class='commentbox' id='"+commentId+"More' style='display:none;'> "+comment+"</div>");
					addData.append(divObj);
				}
				
				
				
				
				
				$(function() {
						var nIdx = data.n_idx;
						var thisData = data.tlp[y];
						 
						var paserID = commentParser; // 클로저가 접근할 변수 선언
					//	var targetDiv = $("#"+paserID+"ContentBtn");
						var targetDiv = divObj;
						targetDiv.on('mouseenter',function(e){
								var heightM = targetDiv.outerHeight()+4;
								var overLayDiv = $("<div class='commentboxB' style='position:relative;margin-top:-"+heightM+"px;height:"+targetDiv.height()+"px;width:"+targetDiv.width()+"px;'></div>");
								var likeDiv =$("<div class='commentboxLike' style='height:"+targetDiv.height()+"px; line-height: "+targetDiv.height()+"px;background-color:#6C8C37;'>좋아요/"+thisData.likescnt+"</div>");
								var unLikeDiv =$("<div class='commentboxUnlike' style='height:"+targetDiv.height()+"px;line-height: "+targetDiv.height()+"px;background-color:#FF7979;'>싫어요/"+thisData.unlikescnt+"</div>");
								var replyDiv =$("<div class='commentboxReply' style='height:"+targetDiv.height()+"px;line-height: "+targetDiv.height()+"px;background-color:#EEE;'>&nbsp;&nbsp;댓글수/0&nbsp;</div>");
								if(thisData.likeflag=="-"){
									likeDiv.on("mouseenter",function(e){
										likeDiv.animate({
									    opacity: 1,
									   
									  }, 300, function() {
									    // Animation complete.
									  });
									});
									likeDiv.on("click",function(e){
										var ldiv =likeDiv;
										var udiv =unLikeDiv;
										console.log(ldiv);
										likeAndUnlkieSeq(nIdx,thisData,"Y",ldiv,udiv);
									});
									
									unLikeDiv.on("mouseenter",function(e){
										unLikeDiv.animate({
									    opacity: 1,
									   
									  }, 300, function() {
									    // Animation complete.
									  });
									});
									unLikeDiv.on("click",function(e){
										var ldiv =likeDiv;
										var udiv =unLikeDiv;
										likeAndUnlkieSeq(nIdx,thisData,"N",ldiv,udiv);
									});
									
									likeDiv.on('mouseleave',function(e){
											likeDiv.animate({
									    opacity: 0.5,
									   
									  }, 300, function() {
									    // Animation complete.
									  });
										//	targetDiv.find(".commentboxB").remove();
									});
									unLikeDiv.on('mouseleave',function(e){
												unLikeDiv.animate({
									    opacity: 0.5,
									   
									  }, 300, function() {
									    // Animation complete.
									  });
									});
								}
								overLayDiv.append(likeDiv);
								
								overLayDiv.append(replyDiv);
								overLayDiv.append(unLikeDiv);
								
								targetDiv.after(overLayDiv);
						});
						
						
					});
				
			}
			
		}
	}
	targetBody.append("</tbody></table>");
	/**좋아요 싫어요 삭제 **/
	$(".newsbox").on('mouseleave',function(e){
		$(".commentboxB").fadeOut("slow",function(){
			$(".commentboxB").remove();
		});
		
	});
	
	//최상단 아이들 호출

	
	$('#targetCell').slideUp( "slow", function() {
		
		
		$('#targetCell').slideDown( "slow", function() {
			
			});
	});
	
}

function likeAndUnlkieSeq(nIdx,thisData,flag,ldiv,udiv){
	$("body").addClass("loading"); 

	$.ajax({         
				       type:'POST',
							 url:SERVER_IP+'WebService.asmx/TL0212',               //데이터를 요청할 페이지
							 dataType: 'json',                   //데이터 유형
				  	   data:{'n_idx':nIdx,'p_idx':thisData.p_idx,'lsp_idx':thisData.lsp_idx, 'seq':thisData.seq, 'LikeFlag':flag},  //요청할 페이지에 전송할 파라메터
				       error:function(xhr,status,e){       //에러 발생시 처리함수
								  console.log(e);
					   	 },
					  	 success: function(jdata2,data){           //성공시 처리 함수, 인수는 위에서 data를 사용한 경우
									if(jdata2[0].result=="ERROR"){
										alert(jdata2[0].message);
									}else{
										console.log("여 들어옴");
										
									}
									console.log(ldiv);
									ldiv.off();
										udiv.off();
										ldiv.unbind();
										udiv.unbind();
										ldiv.text("수정됨");
									$("body").removeClass("loading"); 
									console.log(jdata2);
						 	 }
						 	 
					});
	
}