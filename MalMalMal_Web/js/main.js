$(function(){
	SERVER_IP = '';
	NOW_COMMENTER_CLOUD = null;
	NOW_COMMENT_CLOUD = null;
	NOW_CANVAS = null;
	NOW_TAB = "tab1";
	NOW_TAB2 = "tab1";
	T1DATA_CALL_FLAG = true;
	T2DATA_CALL_FLAG = true;
	T3DATA_CALL_FLAG = true;
	T4DATA_CALL_FLAG = true;
	//back 제어..
	window.onbeforeunload = function (e) {
    var e = e || window.event;
    var msg = "Do you really want to leave this page?";
    // For IE and Firefox
    if (e) {
        e.returnValue = msg;
    }
    // For Safari / chrome
    return msg;
 };
 
	MESSI_POPUP = new Messi();
	MESSI_POPUP.hide();
	$('.messi-content').before('<div class="messi-titlebox"><span class="messi-title"></span></div>');
	
	//editModal datepicker 설정
	var nowYYYYMMDD = getDate(0);
	var prvYYYYMMDD = getDate(7*8);
	
	$('#eSdate').attr("value",prvYYYYMMDD);
	$('#eEdate').attr("value",nowYYYYMMDD);
	setDatePicker("eSdate","eEdate");

	issueCloudDataCall(function(){
		
	});
	
 //디테일 뷰 -> 클라우드 화면[1번 탭]
	$(".cloudCallBtn").on('click',function(e){
			$('#issuesTitle').empty();
			//document.body.style.cursor = 'pointer';
			$(".cloudCallBtn").fadeOut("slow",function(){
					$(".topDatePicker").fadeIn("slow");
						$("#issueGoBtn").fadeIn("slow");
					$('.tabs').fadeIn("slow");
			});
			
			$( ".detailViewLayout" ).slideUp( "slow", function() {
				$( ".issuesCloudLayout" ).slideDown( "slow");
				$( ".timelineLayout" ).slideDown("slow");
				$('#datePicker').fadeIn("slow");
					$("#issueGoBtn").fadeIn("slow");
			});
			NOW_TAB="tab1";
	});
	
	$("#tab1").on('click',function(e){
		if(T1DATA_CALL_FLAG){
				if(NOW_TAB=="tab2"){
					issueCloudDataCall(function(jdata){
					$( ".commenterTimelineLayout" ).slideUp( "fast", function() {
						
					});
					$( ".commenterCloudLayout" ).slideUp( "slow", function() {
							$( ".issuesCloudLayout" ).slideDown( "slow");
							$( ".timelineLayout" ).slideDown( "slow");
							if(jdata[0]!=null)mainCloudChartLoad(jdata); 
					});
				});
				}else if(NOW_TAB=="tab3"){
					issueCloudDataCall(function(jdata){
					$( ".commentTimelineLayout" ).slideUp( "fast", function() {
						
					});
					$( ".commentCloudLayout" ).slideUp( "slow", function() {
							$( ".issuesCloudLayout" ).slideDown( "slow");
							$( ".timelineLayout" ).slideDown( "slow");
							if(jdata[0]!=null)mainCloudChartLoad(jdata); 
						});
					});
				}else if(NOW_TAB=="tab4"){
					issueCloudDataCall(function(jdata){
						$( ".issueChartTimelineLayout" ).slideUp( "fast", function() {
						
					});
					$( ".issueChartLayout" ).slideUp( "slow", function() {
							$( ".issuesCloudLayout" ).slideDown( "slow");
							$( ".timelineLayout" ).slideDown( "slow");
							if(jdata[0]!=null)mainCloudChartLoad(jdata); 
						});
					});
				}
				
				T1DATA_CALL_FLAG = false;
			}else{
					if(NOW_TAB=="tab2"){
					$( ".commenterTimelineLayout" ).slideUp( "fast", function() {
						
					});
					$( ".commenterCloudLayout" ).slideUp( "slow", function() {
							$( ".issuesCloudLayout" ).slideDown( "slow");
							$( ".timelineLayout" ).slideDown( "slow");
					});
				}else if(NOW_TAB=="tab3"){
					$( ".commentTimelineLayout" ).slideUp( "fast", function() {
					});
					$( ".commentCloudLayout" ).slideUp( "slow", function() {
							$( ".issuesCloudLayout" ).slideDown( "slow");
							$( ".timelineLayout" ).slideDown( "slow");
						});
					
				}else if(NOW_TAB=="tab4"){
					$( ".issueChartTimelineLayout" ).slideUp( "fast", function() {
						
					});
					$( ".issueChartLayout" ).slideUp( "slow", function() {
							$( ".issuesCloudLayout" ).slideDown( "slow");
							$( ".timelineLayout" ).slideDown( "slow");
						});
					
				}
					
					
			}
		$('#issueChartSelector').fadeOut("slow");
		$('#issueSelectDiv').fadeOut("slow");
		NOW_TAB = "tab1";
			
	});
	$("#tab2").on('click',function(e){
		if(T2DATA_CALL_FLAG){
			if(NOW_TAB=="tab1"){
				commenterCloudDataCall(function(jdata){
					$( ".timelineLayout" ).slideUp("fast");
					$( ".issuesCloudLayout" ).slideUp( "slow", function() {
							$( ".commenterCloudLayout" ).slideDown( "slow");
						$( ".commenterTimelineLayout" ).slideDown( "slow");
						if(jdata[0]!=null)mainComenterCloudChartLoad(jdata);
					});
				});
				
				
			}else if(NOW_TAB=="tab3"){
				commenterCloudDataCall(function(jdata){
				$( ".commentTimelineLayout" ).slideUp("slow");
				
				$( ".commentCloudLayout" ).slideUp( "slow", function() {
							$( ".commenterCloudLayout" ).slideDown( "slow");
						$( ".commenterTimelineLayout" ).slideDown( "slow");
					if(jdata[0]!=null)mainComenterCloudChartLoad(jdata);
					});
				});
			}else if(NOW_TAB=="tab4"){
				commenterCloudDataCall(function(jdata){
						$( ".issueChartTimelineLayout" ).slideUp( "fast", function() {
						
					});
					$( ".issueChartLayout" ).slideUp( "slow", function() {
								$( ".commenterCloudLayout" ).slideDown( "slow");
						$( ".commenterTimelineLayout" ).slideDown( "slow");
						if(jdata[0]!=null)mainComenterCloudChartLoad(jdata);
						});
					});
				}
			
			T2DATA_CALL_FLAG = false;
		}else{
			if(NOW_TAB=="tab1"){
				$( ".timelineLayout" ).slideUp("fast");
				$( ".issuesCloudLayout" ).slideUp( "slow", function() {
						$( ".commenterCloudLayout" ).slideDown( "slow");
					$( ".commenterTimelineLayout" ).slideDown( "slow");
				
				});
			}else if(NOW_TAB=="tab3"){
			$( ".commentTimelineLayout" ).slideUp("slow");
			
			$( ".commentCloudLayout" ).slideUp( "slow", function() {
						$( ".commenterCloudLayout" ).slideDown( "slow");
					$( ".commenterTimelineLayout" ).slideDown( "slow");
				
				});
			}else if(NOW_TAB=="tab4"){
						$( ".issueChartTimelineLayout" ).slideUp( "fast", function() {
						
					});
					$( ".issueChartLayout" ).slideUp( "slow", function() {
								$( ".commenterCloudLayout" ).slideDown( "slow");
						$( ".commenterTimelineLayout" ).slideDown( "slow");
						});
				}
			
		}
		
		$('#issueChartSelector').fadeOut("slow");
		$('#issueSelectDiv').fadeOut("slow");
		NOW_TAB="tab2";
			
			
			
	});
	
	$("#tab3").on('click',function(e){
			if(T3DATA_CALL_FLAG){
					if(NOW_TAB=="tab1"){
						commentCloudDataCall(function(jdata){
						$( ".timelineLayout" ).slideUp( "slow");
							$( ".issuesCloudLayout" ).slideUp( "slow", function() {
									$( ".commentCloudLayout" ).slideDown( "slow");
								$( ".commentTimelineLayout" ).slideDown( "slow");
							if(jdata[0]!=null)mainComentCloudChartLoad(jdata);
							});
					});
						
					}else if(NOW_TAB=="tab2"){
						commentCloudDataCall(function(jdata){
						$( ".commenterTimelineLayout" ).slideUp( "slow");
						$( ".commenterCloudLayout" ).slideUp( "slow", function() {
								$( ".commentCloudLayout" ).slideDown( "slow");
								$( ".commentTimelineLayout" ).slideDown( "slow");
							if(jdata[0]!=null)mainComentCloudChartLoad(jdata);
						});
						});
					}else if(NOW_TAB=="tab4"){
						commentCloudDataCall(function(jdata){
						$( ".issueChartTimelineLayout" ).slideUp( "fast");
						$( ".issueChartLayout" ).slideUp( "slow", function() {
								$( ".commentCloudLayout" ).slideDown( "slow");
						$( ".commentTimelineLayout" ).slideDown( "slow");
							if(jdata[0]!=null)mainComentCloudChartLoad(jdata);
						});
						});
					}
				T3DATA_CALL_FLAG = false;
			}else{
				if(NOW_TAB=="tab1"){
					$( ".timelineLayout" ).slideUp( "slow");
							$( ".issuesCloudLayout" ).slideUp( "slow", function() {
									$( ".commentCloudLayout" ).slideDown( "slow");
								$( ".commentTimelineLayout" ).slideDown( "slow");
							
							});
				
				}else if(NOW_TAB=="tab2"){
					$( ".commenterTimelineLayout" ).slideUp( "slow");
						$( ".commenterCloudLayout" ).slideUp( "slow", function() {
								$( ".commentCloudLayout" ).slideDown( "slow");
								$( ".commentTimelineLayout" ).slideDown( "slow");
						});
				}else if(NOW_TAB=="tab4"){
						$( ".issueChartTimelineLayout" ).slideUp( "fast", function() {
						
						});
						$( ".issueChartLayout" ).slideUp( "slow", function() {
								$( ".commentCloudLayout" ).slideDown( "slow");
						$( ".commentTimelineLayout" ).slideDown( "slow");
						});
				}
			}
			$('#issueChartSelector').fadeOut("slow");
			$('#issueSelectDiv').fadeOut("slow");
			NOW_TAB="tab3";
			
	});

	$("#tab4").on('click',function(e){
			if(T4DATA_CALL_FLAG){
				
					if(NOW_TAB=="tab1"){
						//issueChartDataCall(function(){
						$( ".timelineLayout" ).slideUp( "slow");
							$( ".issuesCloudLayout" ).slideUp( "slow", function() {
								$( ".issueChartLayout" ).slideDown( "slow",function(){
									issueChartDataCall();
								});
								$( ".issueChartTimelineLayout" ).slideDown( "slow");
							});
					//});
						
					}else if(NOW_TAB=="tab2"){
					//	issueChartDataCall(function(){
						$( ".commenterTimelineLayout" ).slideUp( "slow");
						$( ".commenterCloudLayout" ).slideUp( "slow", function() {
								$( ".issueChartLayout" ).slideDown( "slow",function(){
									issueChartDataCall();
								});
								$( ".issueChartTimelineLayout" ).slideDown( "slow");
						});
						//});
					}else if(NOW_TAB=="tab3"){
					//	issueChartDataCall(function(){
						$( ".commentTimelineLayout" ).slideUp( "slow");
						$( ".commentCloudLayout" ).slideUp( "slow", function() {
								$( ".issueChartLayout" ).slideDown( "slow",function(){
									issueChartDataCall();
								});
								$( ".issueChartTimelineLayout" ).slideDown( "slow");
						});
					//});
					}
				//T4DATA_CALL_FLAG = false;
			}else{
					if(NOW_TAB=="tab1"){
						//commentCloudDataCall(function(){
						$( ".timelineLayout" ).slideUp( "slow");
							$( ".issuesCloudLayout" ).slideUp( "slow", function() {
								$( ".issueChartLayout" ).slideDown( "slow");
								$( ".issueChartTimelineLayout" ).slideDown( "slow");
							});
					//});
						
					}else if(NOW_TAB=="tab2"){
						//commentCloudDataCall(function(){
						$( ".commenterTimelineLayout" ).slideUp( "slow");
						$( ".commenterCloudLayout" ).slideUp( "slow", function() {
								$( ".issueChartLayout" ).slideDown( "slow");
								$( ".issueChartTimelineLayout" ).slideDown( "slow");
						});
					//	});
					}else if(NOW_TAB=="tab3"){
					//	commenterCloudDataCall(function(){
						$( ".commentTimelineLayout" ).slideUp( "slow");
						$( ".commentCloudLayout" ).slideUp( "slow", function() {
								$( ".issueChartLayout" ).slideDown( "slow");
								$( ".issueChartTimelineLayout" ).slideDown( "slow");
						});
					//});
					}
			}
			
			$('#issueChartSelector').fadeIn("slow");
			$('#issueSelectDiv').fadeIn("slow");
			NOW_TAB="tab4";
			
	});
	
	
	
	/* 차트 탭메뉴 리스너*/
		$("#tabChart1").on('click',function(e){
			if(NOW_TAB2=="tab2"){
				issueChartDataCall();
			}else{
				issueChartDataCall();
			}
		$('#issueSelectDiv').fadeIn("slow");
			NOW_TAB2 = "tab1";
		});
		
		$("#tabChart2").on('click',function(e){
			if(NOW_TAB2=="tab1"){
				top5IssueChartDataCall();	
			}else{
				top5IssueChartDataCall();
			}
			$('#issueSelectDiv').fadeOut("slow");
			NOW_TAB2 = "tab2";
		});
		
		$("#tabChart3").on('click',function(e){
			if(NOW_TAB2=="tab1"){
				commenterLkieBadChartDataCall();
			}else{
				commenterLkieBadChartDataCall();
			}
			$('#issueSelectDiv').fadeOut("slow");
			NOW_TAB2 = "tab3";
		});
	/*
	$('#issueSelctbox').on('change',function(e){
			$( ".issueChartLayout" ).slideUp( "slow");
			$("#issueChart").empty();

		issueChartDataCall(function(){
				$( ".issueChartLayout" ).slideDown( "slow");
		});
	});
	*/
	
	/*날짜변경 후 이슈 재로딩*/
	$("#issueGoBtn").on('click',function(){
		if(NOW_TAB=="tab1"){
			issueCloudDataCall();
			/*issueCloudDataCall(function(jdata){
				mainCloudChartLoad(jdata); 
			});*/
		}else if(NOW_TAB=="tab2"){
			commenterCloudDataCall();
	
		}else if(NOW_TAB=="tab3"){
			commentCloudDataCall();
		}else if(NOW_TAB=="tab4"){
			if(NOW_TAB2=="tab1"){
				issueChartDataCall();
			}else if(NOW_TAB2=="tab2"){
				top5IssueChartDataCall();
			}else if(NOW_TAB2=="tab3"){
				commenterLkieBadChartDataCall();
			}
		
		}
		
	});
	
	$(".topDatePicker").on('click',function(e){
			//document.body.style.cursor = 'pointer';
	});
	

//마우스 동작감지
/*
	var timeout;
	var timeSec;
	document.onmousemove = function(){
	  clearTimeout(timeout);
	  timeSec = 0;
	  timeout = setInterval(function(){
	  	timeSec++;
	  	if(timeSec==7){
	  		if(NOW_TAB=="tab1"){
						$("#tab2").trigger( "click" );
				}else if(NOW_TAB=="tab2"){
						$("#tab3").trigger( "click" );
				}else if(NOW_TAB=="tab3"){
						$("#tab1").trigger( "click" );
				}
	  		timeSec = 0;
	  	}
	  	
	  	}, 1000);
	}

*/
  	
  

overTimer_CallData = setInterval(function() {
			T1DATA_CALL_FLAG = true;
			T2DATA_CALL_FLAG = true;
			T3DATA_CALL_FLAG = true;
			T4DATA_CALL_FLAG= true;
	   //}, 300000); // 5분
	}, 1000);
    
});

