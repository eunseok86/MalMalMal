
//$(function(){
function issueCloudDataCall(e){
	$("body").addClass("loading"); 
var startdate = $('#eSdate').val();
		startdate = startdate.replace(/-/gi, "");
	var enddate = $('#eEdate').val();
		enddate = enddate.replace(/-/gi, "");
$.ajax({         
        type:'POST',
        url:SERVER_IP+'WebService.asmx/IC0112',            
        dataType: 'json',                 
        data:{'StartDate':startdate,'EndDate':enddate},  
			
        error:function(xhr,status,e){      
             console.log(e);
        },
        success: function(jdata,data){       
        	console.log(jdata);    
					if(jdata[0]==null){
						alert("µ¥ÀÌÅÍ°¡ ¾ø½À´Ï´Ù");
						$("body").removeClass("loading"); 
					}else{
						tags = {};
						$("#issueSelctbox").empty();
						$("#issueSelctbox").append("<option value='-1'>¸ğµç´º½º</option>");
						
						
           // for(i=0;i < jdata.length;i++){
            for(i=0;i < 9;i++){
            	$("#issueSelctbox").append("<option value="+jdata[i].in_idx+">"+jdata[i].issuename+"</option>");
            		tags[jdata[i].issuename] = {};
            		tags[jdata[i].issuename].importance = (jdata[i].impotrence)*8+2;
            		tags[jdata[i].issuename].related=["null"];
            		tags[jdata[i].issuename].coords = {x:(jdata[i].x)*1100,y:(jdata[i].y)*500};
            		tags[jdata[i].issuename].defcolor = {r:jdata[i].nc_R,g:jdata[i].nc_G,b:jdata[i].nc_B};
            		tags[jdata[i].issuename].in_idx = jdata[i].in_idx;
            		tags[jdata[i].issuename].commentcnt = jdata[i].commentcnt;
            		tags[jdata[i].issuename].likescnt =jdata[i].likescnt;
            		tags[jdata[i].issuename].unlikescnt =jdata[i].unlikescnt;
            		tags[jdata[i].issuename].newscount = jdata[i].newscount;
            		tags[jdata[i].issuename].id = "issueCloudId"+jdata[i].in_idx;
            }
            
            if(jdata.length>9){
            	var targetDiv = $('#anotherIssueData');
            	targetDiv.empty();
							var addTable = $("<table class='timelinePage'></table>");
							targetDiv.append(addTable);
							var addTr;
            	for(var i=9; i<jdata.length;i++){
									
									var issuename = jdata[i].issuename;
									var newsCnt =  jdata[i].newscount;
									var likeCnt = jdata[i].likescnt;
									var unlikeCnt =jdata[i].unlikescnt;
									var commentCnt = jdata[i].commentcnt;
									var in_idx = jdata[i].in_idx;
											addTr = $("<tr class='trMenu'></tr>");
											addTr.append("<td width=52%>"+issuename+"</td>");
											addTr.append("<td width=10%>"+newsCnt+"</td>");
											addTr.append("<td width=13%>"+likeCnt+"</td>");
											addTr.append("<td width=15%>"+unlikeCnt+"</td>");
											addTr.append("<td width=10%>"+commentCnt+"</td>");
											addTable.append(addTr);
											
											(function(){
												var issueIdx = in_idx;
												var issue = issuename;
												addTr.on('click',function(){
												issueDetailCall(null,issueIdx,issue);
												});
											})();
								}
            	
            }
            
            
            
						//
						mainCloudChartLoad(jdata); 
						issuesCloud();   
						realTimeList();	
					}
					if(e)e(jdata);
					//	$("body").removeClass("loading"); 
						
        }
  }); 
}
//});


function commenterCloudDataCall(e){
	$("body").addClass("loading"); 
	$('#comenterCloud').empty();
	var startdate = $('#eSdate').val();
		startdate = startdate.replace(/-/gi, "");
	var enddate = $('#eEdate').val();
		enddate = enddate.replace(/-/gi, "");
	$.ajax({         
        type:'POST',
        url:SERVER_IP+'WebService.asmx/IC0113',               //ë°ì´í„°ë¥¼ ìš”ì²­í•  í˜ì´ì§€
        dataType: 'json',                   //ë°ì´í„° ìœ í˜•
        data:{'typecode':'t1','StartDate':startdate,'EndDate':enddate},//í•  íŒŒë¼ë©”í„°
				
        error:function(xhr,status,e){       //ì—ëŸ¬ ë°œìƒì‹œ ì²˜ë¦¬í•¨ìˆ˜
             console.log(e);
        },
        success: function(jdata,data){           //ì„±ê³µì‹œ ì²˜ë¦¬ í•¨ìˆ˜, ì¸ìˆ˜ëŠ” ìœ„ì—ì„œ dataë¥¼ ì‚¬ìš©í•œ ê²½ìš°
				if(jdata[0]==null){
						alert("µ¥ÀÌÅÍ°¡ ¾ø½À´Ï´Ù");
						$("body").removeClass("loading"); 
					}else{
				
						commenterTags = {};
						var firstCommenter = null;
            //for(i=0;i < jdata.length;i++){
           for(i=0;i < 9;i++){
           	if(i==0) firstCommenter =jdata[i].name;
            		commenterTags[jdata[i].name] = {};
            		commenterTags[jdata[i].name].importance = (jdata[i].impotrence)*8+2;
            		commenterTags[jdata[i].name].related=["null"];
            		commenterTags[jdata[i].name].coords = {x:(jdata[i].x)*1100,y:(jdata[i].y)*500};
            		commenterTags[jdata[i].name].defcolor = {r:jdata[i].nc_R,g:jdata[i].nc_G,b:jdata[i].nc_B};
            		commenterTags[jdata[i].name].in_idx = jdata[i].speakcnt;
								         
            }
            if(jdata.length>9){
            	var targetDiv = $('#anotherCommenterData');
            	targetDiv.empty();
							var addTable = $("<table class='timelinePage' ></table>");
							targetDiv.append(addTable);
							var addTr;
            	for(var i=9; i<jdata.length;i++){
									
									var commenter = jdata[i].name;
									var speakctn = jdata[i].speakcnt;
									//var newsCnt =  jdata[i].newscount;
									var likeCnt = 0;//jdata[i].likescnt;
									var unlikeCnt =0;//jdata[i].unlikescnt;
									var commentCnt = 0;//jdata[i].commentcnt;
											addTr = $("<tr class='trMenu'></tr>");
											addTr.append("<td width=32% style='text-align:center;'>"+commenter+"</td>");
											addTr.append("<td width=17% style='text-align:center;'>"+speakctn+"</td>");
											addTr.append("<td width=17% style='text-align:center;'>"+likeCnt+"</td>");
											addTr.append("<td width=17% style='text-align:center;'>"+unlikeCnt+"</td>");
											addTr.append("<td width=17% style='text-align:center;'>"+commentCnt+"</td>");
											addTable.append(addTr);
											
											(function(){
												var commenterT = commenter;
												addTr.on('click',function(){
												commenterDetailCall(null,commenterT);
												});
											})();
											
								}
            	
            }
           
            mainComenterCloudChartLoad(jdata);
            commenterCloud();
            commenterDetailCall(null,firstCommenter);
           // $("body").removeClass("loading"); 
         }
            if(e)e(jdata);
             
        }
  }); 
}

function commentCloudDataCall(e){
	$("body").addClass("loading"); 
	$('#comentCloud').empty();
	var startdate = $('#eSdate').val();
		startdate = startdate.replace(/-/gi, "");
	var enddate = $('#eEdate').val();
		enddate = enddate.replace(/-/gi, "");
$.ajax({         
        type:'POST',
        url:SERVER_IP+'WebService.asmx/IC0113',               //ë°ì´í„°ë¥¼ ìš”ì²­í•  í˜ì´ì§€
        dataType: 'json',                   //ë°ì´í„° ìœ í˜•
        data:{'typecode':'t2','StartDate':startdate,'EndDate':enddate},//í•  íŒŒë¼ë©”í„°
				
        error:function(xhr,status,e){       //ì—ëŸ¬ ë°œìƒì‹œ ì²˜ë¦¬í•¨ìˆ˜
             console.log(e);
        },
        success: function(jdata,data){           //ì„±ê³µì‹œ ì²˜ë¦¬ í•¨ìˆ˜, ì¸ìˆ˜ëŠ” ìœ„ì—ì„œ dataë¥¼ ì‚¬ìš©í•œ ê²½ìš°
					if(jdata[0]==null){
						alert("µ¥ÀÌÅÍ°¡ ¾ø½À´Ï´Ù");
						$("body").removeClass("loading"); 
					}else{
						var firstCommenter, firstO, firstO2;
						commentTags = {};
          //  for(i=0;i < jdata.length;i++){
            for(i=0;i < 8;i++){
           			var sO;
           			if(30 < jdata[i].o.length){
           				sO = jdata[i].o.substring(0,22);
           				sO += "...\"";
           			}else{
           				sO = jdata[i].o;
           			}
           			if(i==0){
           				firstCommenter =jdata[i].name;
           				firstO =jdata[i].o;
           				firstO2 =jdata[i].o2;
           			}
            		commentTags[sO] = {};
            		commentTags[sO].importance = 3;//(jdata[i].impotrence)*8+2;
            		commentTags[sO].related=["null"];
            		commentTags[sO].coords = {x:(jdata[i].x)*1100,y:(jdata[i].y)*500};
            		commentTags[sO].defcolor = {r:jdata[i].nc_R,g:jdata[i].nc_G,b:jdata[i].nc_B};
            		commentTags[sO].in_idx = jdata[i].speakcnt;
            		commentTags[sO].name = jdata[i].name;
            		commentTags[sO].o = jdata[i].o;
            		commentTags[sO].o2 = jdata[i].o2;
            }
             if(jdata.length>8){
            	var targetDiv = $('#anotherCommentData');
            	targetDiv.empty();
							var addTable = $("<table class='timelinePage' ></table>");
							targetDiv.append(addTable);
							var addTr;
            	for(var i=8; i<jdata.length;i++){
									
									var comment = jdata[i].o;
									var o2 = jdata[i].o2;
									var speakctn = jdata[i].speakcnt;
									var commenter =  jdata[i].name;
									//var newsCnt =  jdata[i].newscount;
									var likeCnt = 0;//jdata[i].likescnt;
									var unlikeCnt =0;//jdata[i].unlikescnt;
									//var commentCnt = 0;//jdata[i].commentcnt;
											addTr = $("<tr class='trMenu'></tr>");
											addTr.append("<td width=60% style='text-align:center;'>"+comment+"</td>");
											addTr.append("<td width=11% style='text-align:center;'>"+commenter+"</td>");
											addTr.append("<td width=10% style='text-align:center;'>"+likeCnt+"</td>");
											addTr.append("<td width=11% style='text-align:center;'>"+unlikeCnt+"</td>");
											addTr.append("<td width=8% style='text-align:center;'>"+speakctn+"</td>");
											addTable.append(addTr);
											
											(function(){
												var commenterT = commenter;
												var commentO = comment;
												var commentO2 = o2;
												addTr.on('click',function(){
												commentDetailCall(null,commenterT,commentO,commentO2);
												});
											})();
								}
            	
            }
            mainComentCloudChartLoad(jdata);
            commentCloud();
            commentDetailCall(null,firstCommenter,firstO,firstO2);
            // $("body").removeClass("loading"); 
          }
            if(e)e(jdata);
            
           
        }
  }); 
}

function issueChartDataCall(e){
	$("body").addClass("loading"); 
	$('#issueChart').empty();
	var startdate = $('#eSdate').val();
		startdate = startdate.replace(/-/gi, "");
	var enddate = $('#eEdate').val();
		enddate = enddate.replace(/-/gi, "");
	$.ajax({         
        type:'POST',
        url:SERVER_IP+'WebService.asmx/IC0114',               //?°ì´?°ë? ?”ì²­???˜ì´ì§€
        dataType: 'json',                   //?°ì´??? í˜•
        data:{'in_idx':$('#issueSelctbox option:selected').val(),'StartDate':startdate,'EndDate':enddate},//???Œë¼ë©”í„°
        // data:{'in_idx':','StartDate':startdate,'EndDate':enddate},//???Œë¼ë©”í„°
        error:function(xhr,status,e){       //?ëŸ¬ ë°œìƒ??ì²˜ë¦¬?¨ìˆ˜
             console.log(e);
        },
        success: function(jdata,data){           //?±ê³µ??ì²˜ë¦¬ ?¨ìˆ˜, ?¸ìˆ˜???„ì—??dataë¥??¬ìš©??ê²½ìš°
						mainIssueChartLoad(jdata);
					

             $("body").removeClass("loading"); 
            if(e)e();
           // mainComentCloudChartLoad(jdata);
           
        }
  }); 
}

function top5IssueChartDataCall(e){
	$("body").addClass("loading"); 
	$('#issueChart').empty();
	var startdate = $('#eSdate').val();
		startdate = startdate.replace(/-/gi, "");
	var enddate = $('#eEdate').val();
		enddate = enddate.replace(/-/gi, "");
	$.ajax({         
        type:'POST',
        url:SERVER_IP+'WebService.asmx/IC0115',               //?°ì´?°ë? ?”ì²­???˜ì´ì§€
        dataType: 'json',                   //?°ì´??? í˜•
        data:{'StartDate':startdate,'EndDate':enddate},//???Œë¼ë©”í„°
        // data:{'in_idx':','StartDate':startdate,'EndDate':enddate},//???Œë¼ë©”í„°
        error:function(xhr,status,e){       //?ëŸ¬ ë°œìƒ??ì²˜ë¦¬?¨ìˆ˜
             console.log(e);
        },
        success: function(jdata,data){           //?±ê³µ??ì²˜ë¦¬ ?¨ìˆ˜, ?¸ìˆ˜???„ì—??dataë¥??¬ìš©??ê²½ìš°
						top5IssueChartLoad(jdata);
					

             $("body").removeClass("loading"); 
            if(e)e();
           // mainComentCloudChartLoad(jdata);
           
        }
  }); 
}


function commenterLkieBadChartDataCall(e){
	$("body").addClass("loading"); 
	$('#issueChart').empty();
	var startdate = $('#eSdate').val();
		startdate = startdate.replace(/-/gi, "");
	var enddate = $('#eEdate').val();
		enddate = enddate.replace(/-/gi, "");
	$.ajax({         
        type:'POST',
        url:SERVER_IP+'WebService.asmx/IC0116',               //?°ì´?°ë? ?”ì²­???˜ì´ì§€
        dataType: 'json',                   //?°ì´??? í˜•
        data:{'StartDate':startdate,'EndDate':enddate},//???Œë¼ë©”í„°
        // data:{'in_idx':','StartDate':startdate,'EndDate':enddate},//???Œë¼ë©”í„°
        error:function(xhr,status,e){       //?ëŸ¬ ë°œìƒ??ì²˜ë¦¬?¨ìˆ˜
             console.log(e);
        },
        success: function(jdata,data){           //?±ê³µ??ì²˜ë¦¬ ?¨ìˆ˜, ?¸ìˆ˜???„ì—??dataë¥??¬ìš©??ê²½ìš°
						commenterLkieBadChartLoad(jdata);
					

             $("body").removeClass("loading"); 
            if(e)e();
           // mainComentCloudChartLoad(jdata);
           
        }
  }); 
}

function realTimeList(){
	
	$("body").addClass("loading"); 
	var startdate = $('#eSdate').val();
		startdate = startdate.replace(/-/gi, "");
	var enddate = $('#eEdate').val();
		enddate = enddate.replace(/-/gi, "");
	$.ajax({         
        type:'POST',
        url:SERVER_IP+'WebService.asmx/IC0131',               //µ¥ÀÌÅÍ¸¦ ¿äÃ»ÇÒ ÆäÀÌÁö
        dataType: 'json',                   //µ¥ÀÌÅÍ À¯Çü
        data:{'StartDate':startdate,'EndDate':enddate},  //¿äÃ»ÇÒ ÆäÀÌÁö¿¡ Àü¼ÛÇÒ ÆÄ¶ó¸ŞÅÍ
        error:function(xhr,status,e){       //¿¡·¯ ¹ß»ı½Ã Ã³¸®ÇÔ¼ö
             console.log(e);
        },
        success: function(jdata,data){           //¼º°ø½Ã Ã³¸® ÇÔ¼ö, ÀÎ¼ö´Â À§¿¡¼­ data¸¦ »ç¿ëÇÑ °æ¿ì	
        	console.log(jdata);
					var targetDiv = $('#timeData');
					var addTable = $("<table class='timelinePage'></table>");
					targetDiv.append(addTable);
					var addTr;
					for(var i=0; i<jdata.length;i++){
						
						var comment = jdata[i].o;
						var commenter =  jdata[i].s;
						
						var commentId = commenter.replace(/ /gi, '');
						
						var commentParser = parseSpString(commentId);
						if(i==0){
							var dateString = jdata[i].regdt.substring(8,10)+":";
								dateString += jdata[i].regdt.substring(10,12);
								addTr = $("<tr></tr>");
								addTr.append("<td width=5%>"+dateString+"</td>");
								addTr.append("<td width=84% id='"+commentId+"'><a href='"+jdata[i].outlinkUrl+"' target='_blank'>"+jdata[i].title+"</a>"+jdata[i].o+"</td>");
								addTr.append("<td width=11%>"+jdata[i].s+"</td>");
								
								addTable.append(addTr);
						}else{
							if(addTr.find('#'+commentParser+'').length != 1){
								var dateString = jdata[i].regdt.substring(8,10)+":";
								dateString += jdata[i].regdt.substring(10,12);
								addTr = $("<tr></tr>");
								addTr.append("<td width=5%>"+dateString+"</td>");
								addTr.append("<td width=84% id='"+commentId+"'><a href='"+jdata[i].outlinkUrl+"' target='_blank'>"+jdata[i].title+"</a>"+jdata[i].o+"</td>");
								addTr.append("<td width=11%>"+jdata[i].s+"</td>");
							
								addTable.append(addTr);
							}else{
									addTr.find('#'+commentParser+'').append("<br>"+jdata[i].o);
							}						
						}
					}
					
					$("body").removeClass("loading"); 
					
				
  			}
        
  		}); 
	
}


function issueDetailCall(tag,issueIdx,issueT){
	$("body").addClass("loading");
	
	var in_idx, issueText;
	if(tag){
		in_idx = tag.in_idx;
		issueText = tag.group.children[1].partialText;
	}else{
		in_idx = issueIdx;
		issueText = issueT;
	}
	var startdate = $('#eSdate').val();
		startdate = startdate.replace(/-/gi, "");
	var enddate = $('#eEdate').val();
		enddate = enddate.replace(/-/gi, "");
	
	$.ajax({         
        type:'POST',
        url:SERVER_IP+'WebService.asmx/TL0111',               //µ¥ÀÌÅÍ¸¦ ¿äÃ»ÇÒ ÆäÀÌÁö
        dataType: 'json',                   //µ¥ÀÌÅÍ À¯Çü
        data:{'in_idx':in_idx,'StartDate':startdate,'EndDate':enddate},  //¿äÃ»ÇÒ ÆäÀÌÁö¿¡ Àü¼ÛÇÒ ÆÄ¶ó¸ŞÅÍ
				
        error:function(xhr,status,e){       //¿¡·¯ ¹ß»ı½Ã Ã³¸®ÇÔ¼ö
             console.log(e);
        },
        success: function(jdata,data){           //¼º°ø½Ã Ã³¸® ÇÔ¼ö, ÀÎ¼ö´Â À§¿¡¼­ data¸¦ »ç¿ëÇÑ °æ¿ì	
					console.log(jdata);
					$('#issuesTitle').append(issueText);
					$("#issueGoBtn").fadeOut("slow");
					$('#datePicker').fadeOut("slow");
					detailView(jdata,in_idx);
					
  			}
        
  }); 
}




function commenterDetailCall(tag,speakText){
	$("body").addClass("loading");
	var speakName;
	if(tag){
		speakName = tag.group.children[1].partialText;
	}else{
		 speakName = speakText;
		 NOW_COMMENTER_CLOUD.blobColorInit();
	}
	$('#timeHeadSpeaker').text(speakName+"ÀÇ ¸»¸»¸»");
	var targetDiv = $('#timeDataSpeaker');
	targetDiv.empty();
	
	var startdate = $('#eSdate').val();
		startdate = startdate.replace(/-/gi, "");
	var enddate = $('#eEdate').val();
		enddate = enddate.replace(/-/gi, "");
	//$('.commenterTimelineLayout').slideUp("slow");
	$.ajax({         
        type:'POST',
        url:SERVER_IP+'WebService.asmx/TL0151',               //µ¥ÀÌÅÍ¸¦ ¿äÃ»ÇÒ ÆäÀÌÁö
        dataType: 'json',                   //µ¥ÀÌÅÍ À¯Çü
        data:{'SpeakName':speakName,'StartDate':startdate,	'EndDate':enddate},  //¿äÃ»ÇÒ ÆäÀÌÁö¿¡ Àü¼ÛÇÒ ÆÄ¶ó¸ŞÅÍ
				
        error:function(xhr,status,e){       //¿¡·¯ ¹ß»ı½Ã Ã³¸®ÇÔ¼ö
             console.log(e);
        },
        success: function(jdata,data){           //¼º°ø½Ã Ã³¸® ÇÔ¼ö, ÀÎ¼ö´Â À§¿¡¼­ data¸¦ »ç¿ëÇÑ °æ¿ì	
					
				
					
					var addTable = $("<table class='timelinePage'></table>");
					targetDiv.append(addTable);
					var addTr;
					for(var i=0; i<jdata.length;i++){
						var newsTitle = jdata[i].title;
						var newsCp = jdata[i].cpKorName;
						var comment = jdata[i].o;
						var commenter =  jdata[i].SpeakName;
						
						var newsId = jdata[i].n_idx;
						
						//var commentParser = parseSpString(commentId);
						if(i==0){
							var dateString = jdata[i].dt.substring(0,4)+"-";
								dateString += jdata[i].dt.substring(4,6)+"-";
								dateString += jdata[i].dt.substring(6,9);
								addTr = $("<tr></tr>");
								addTr.append("<td width=5%>"+dateString+"</td>");
								addTr.append("<td width=89% id='"+newsId+"'><b><a href='"+jdata[i].outlinkUrl+"' target='_blank'>"+newsTitle+"</a></b>"+jdata[i].o+"</td>");
								//addTr.append("<td width=9%>"+jdata[i].SpeakName+"</td>");
								addTr.append("<td width=6%>"+jdata[i].cpKorName+"</td>");
								addTable.append(addTr);
						}else{
							if(addTr.find('#'+newsId+'').length != 1){
								var dateString = jdata[i].dt.substring(0,4)+"-";
								dateString += jdata[i].dt.substring(4,6)+"-";
								dateString += jdata[i].dt.substring(6,9);
								addTr = $("<tr></tr>");
								addTr.append("<td width=5%>"+dateString+"</td>");
								addTr.append("<td width=89% id='"+newsId+"'><b><a href='"+jdata[i].outlinkUrl+"' target='_blank'>"+newsTitle+"</a></b>"+jdata[i].o+"</td>");
								//addTr.append("<td width=9%>"+jdata[i].SpeakName+"</td>");
								addTr.append("<td width=6%>"+jdata[i].cpKorName+"</td>");
								addTable.append(addTr);
							}else{
									addTr.find('#'+newsId+'').append("<br>"+jdata[i].o);
							}						
						}
					}
					
					$("body").removeClass("loading");
					//$('.commenterTimelineLayout').slideDown("slow");
  			}
  		}); 
}






function commentDetailCall(tag,speakText,oText,o2Text){
	$("body").addClass("loading");
	var speakName,o,o2;
	if(tag){
		speakName = tag.name;
		o=tag.o;
		o2=tag.o2;
	}else{
		 speakName = speakText;
		 o = oText;
		 o2 = o2Text;
		 NOW_COMMENT_CLOUD.blobColorInit();
	}
	var startdate = $('#eSdate').val();
		startdate = startdate.replace(/-/gi, "");
	var enddate = $('#eEdate').val();
		enddate = enddate.replace(/-/gi, "");
	
	$('#timeHeadComment').text(speakName+": "+o+"");
					var targetDiv = $('#timeDataComment');
					targetDiv.empty();
	$.ajax({         
        type:'POST',
        url:SERVER_IP+'WebService.asmx/TL0161',               //µ¥ÀÌÅÍ¸¦ ¿äÃ»ÇÒ ÆäÀÌÁö
        dataType: 'json',                   //µ¥ÀÌÅÍ À¯Çü
        data:{'SpeakName':speakName,'o':o,'o2':o2,'StartDate':startdate,	'EndDate':enddate},  //¿äÃ»ÇÒ ÆäÀÌÁö¿¡ Àü¼ÛÇÒ ÆÄ¶ó¸ŞÅÍ
				
        error:function(xhr,status,e){       //¿¡·¯ ¹ß»ı½Ã Ã³¸®ÇÔ¼ö
             console.log(e);
        },
        success: function(jdata,data){           //¼º°ø½Ã Ã³¸® ÇÔ¼ö, ÀÎ¼ö´Â À§¿¡¼­ data¸¦ »ç¿ëÇÑ °æ¿ì	
        
					
					
					var addTable = $("<table class='timelinePage'></table>");
					targetDiv.append(addTable);
					var addTr;
					
					for(var i=0; i<jdata.length;i++){
						var newsTitle = jdata[i].title;
						var newsCp = jdata[i].cpKorName;
						//var comment = jdata[i].o;
						var commenter =  jdata[i].SpeakName;
						
						var newsId = jdata[i].n_idx;
						
						//var commentParser = parseSpString(commentId);
						if(i==0){
							var dateString = jdata[i].dt.substring(0,4)+"-";
								dateString += jdata[i].dt.substring(4,6)+"-";
								dateString += jdata[i].dt.substring(6,9);
								addTr = $("<tr></tr>");
								addTr.append("<td width=9%>"+dateString+"</td>");//"+jdata[i].o+"
								addTr.append("<td width=80% id='"+newsId+"'><b><a href='"+jdata[i].outlinkUrl+"' target='_blank'>"+newsTitle+"</a></b></td>");
								//addTr.append("<td width=9%>"+jdata[i].SpeakName+"</td>");
								addTr.append("<td width=11%>"+jdata[i].cpKorName+"</td>");
								addTable.append(addTr);
						}else{
							if(addTr.find('#'+newsId+'').length != 1){
								var dateString = jdata[i].dt.substring(0,4)+"-";
								dateString += jdata[i].dt.substring(4,6)+"-";
								dateString += jdata[i].dt.substring(6,9);
								addTr = $("<tr></tr>");
								addTr.append("<td width=9%>"+dateString+"</td>");//"+jdata[i].o+"
								addTr.append("<td width=80% id='"+newsId+"'><b><a href='"+jdata[i].outlinkUrl+"' target='_blank'>"+newsTitle+"</a></b></td>");
								//addTr.append("<td width=9%>"+jdata[i].SpeakName+"</td>");
								addTr.append("<td width=11%>"+jdata[i].cpKorName+"</td>");
								addTable.append(addTr);
							}else{
									//addTr.find('#'+newsId+'').append("<br>"+jdata[i].o);
							}						
						}
					}
					$("body").removeClass("loading");
					
			}  
  	}); 
	
		
}


