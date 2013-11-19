
function mainCloudChartLoad(jdata){
	$('#Main_cloud_chartDiv').html('');
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'issues');
        data.addColumn('number', 'count');
        
        
        for(i=0;i < jdata.length;i++){
            		 data.addRow([jdata[i].issuename, jdata[i].newscount]);
            }
        
        var title = $('#eSdate').val()+"~"+$('#eEdate').val()+" ���̽�!" ;
     
        var options = {'title':title,
                       'width':500,
                       'height':500,
                       chartArea:{width:"90%",height:"70%"},
                      //pieSliceText: 'label',
                      titleTextStyle:{fontSize: 20,bold: true,},
                     // legend: {position: 'bottom',}
                      //legend: 'none',
                    };
        var chart = new google.visualization.PieChart(document.getElementById('Main_cloud_chartDiv'));
       
        chart.draw(data, options);
}


function mainComenterCloudChartLoad(jdata){
	$('#Main_commenterCloud_chartDiv').html('');
			
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'speaker');
        data.addColumn('number', 'count');
      
        
        //for(i=0;i < jdata.length;i++){
        for(i=0;i < 7;i++){
            		 data.addRow([jdata[i].name, jdata[i].speakcnt]);
            }
        
        var title = $('#eSdate').val()+"~"+$('#eEdate').val()+" �򸶿콺!" ;
     
        var options = {'title':title,
                       'width':500,
                       'height':500,
                      	 chartArea:{width:"90%",height:"70%"},
                      //pieSliceText: 'label',
                      titleTextStyle:{fontSize: 20,bold: true,},
                    };
        var chart = new google.visualization.PieChart(document.getElementById('Main_commenterCloud_chartDiv'));
       
        chart.draw(data, options);
}

function mainComentCloudChartLoad(jdata){
		
      
        $('#Main_commenteCloud_chartDiv').html('');
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'speak');
        data.addColumn('number', 'count');
        
        
        //for(i=0;i < jdata.length;i++){
        for(i=0;i < 7;i++){
        				var o = jdata[i].name+":"+jdata[i].o;
        				o = o.replace(/\"/gi, "");
            		 data.addRow([o, jdata[i].speakcnt]);
            		
            }
        
        var title = $('#eSdate').val()+"~"+$('#eEdate').val()+" �߰ſ� �߾�!" ;
     
        var options = {'title':title,
                       'width':500,
                       'height':500,
                      chartArea:{width:"90%",height:"70%"},
                      //pieSliceText: 'label',
                      titleTextStyle:{fontSize: 20,bold: true,},
                    };
        var chart = new google.visualization.PieChart(document.getElementById('Main_commenteCloud_chartDiv'));
       
        chart.draw(data, options);
}

function mainIssueChartLoad(jdata){
		
      
		//	$('#issueChart').empty();
    $('#issueChart').html('');
        var data = new google.visualization.DataTable();
        data.addColumn('date', '��¥');
        data.addColumn('number', '������');
        data.addColumn('number', '�߾��');
        //data.addColumn('number', '���ƿ�');
       // data.addColumn('number', '�Ⱦ��');
        //data.addColumn('number', '��ۼ�');
        
        for(i=0;i < jdata.length;i++){//jdata[i].dt.substring(4,6)+"-"+
								var year = jdata[i].dt.substring(0,4);
								var month = jdata[i].dt.substring(4,6)-1;
								var day =jdata[i].dt.substring(6,9);
            		 data.addRow([new Date(year, month, day),jdata[i].newscnt,jdata[i].ocnt]);
        }

        var options = {
          'width':1600,
          'height':800,
          chartArea:{width:"80%"},
          legend:{position:'top'},
          //seriesType:"line",
          seriesType:"bars",
        	series:[{targetAxisIndex:0,type:"line",color: 'blue'},
        	{targetAxisIndex:0,type:"line",color: 'red'},
        	//{targetAxisIndex:1, color: 'blue'},
        	//{targetAxisIndex:1,color: 'red'},
        	//{targetAxisIndex:1,type:"line"}
        	],
        	
        };

			//	var chart = new google.visualization.ColumnChart(document.getElementById('issueChart'));
       // chart.draw(data, options);
        var chart = new google.visualization.ComboChart(document.getElementById('issueChart'));
        chart.draw(data, options);
        
}

function top5IssueChartLoad(jdata){
	 $('#issueChart').html('');
	
	
	var label = jdata[0];
	var cdata = jdata[1];
	
        
	 
        var data = new google.visualization.DataTable();
        data.addColumn('date', '��¥');
        
        for(i=0;i<label.length;i++){
        	 data.addColumn('number', label[i].IssueName);
        }
       
        
        for(i=0;i < cdata.length;i++){
					
								var year = cdata[i].dt.substring(0,4);
								var month = cdata[i].dt.substring(4,6)-1;
								var day =cdata[i].dt.substring(6,9);

            		 data.addRow([new Date(year, month, day),cdata[i].i1,cdata[i].i2,cdata[i].i3,cdata[i].i4,cdata[i].i5]);
            		
            }
				//var titleText = $('#issueSelctbox option:selected').text();
			
				var options = {
          'width':1600,
          'height':800,
        };
   
				

        var chart = new google.visualization.LineChart(document.getElementById('issueChart'));
        chart.draw(data, options);
	
}

function commenterLkieBadChartLoad(jdata){
	 $('#issueChart').html('');
	 
	 
        var data = new google.visualization.DataTable();
        data.addColumn('string', '�̸�');
        data.addColumn('number', '���ƿ�');
        data.addColumn('number', '�Ⱦ��');
        data.addColumn('string', '����');
        data.addColumn('number', '�߾��');
        
        
        
       
        for(i=0;i < jdata.length;i++){
            		 data.addRow([jdata[i].name,jdata[i].likescnt,jdata[i].unlikescnt,jdata[i].response,jdata[i].speakcnt]);
            		
            }
				//var titleText = $('#issueSelctbox option:selected').text();
			

   
				 var options = {
          hAxis: {title: '���ƿ�'},
          vAxis: {title: '�Ⱦ��'},
          bubble: {textStyle: {fontSize: 11}},
           'width':1600,
          'height':800,
          sizeAxis:{minSize: 0,  maxSize: 40},
          series: {
          	'���ƿ�': {color: 'green'},
          	'�Ⱦ��': {color: 'red'},
          	}
         
          
          
        };

        var chart = new google.visualization.BubbleChart(document.getElementById('issueChart'));
        chart.draw(data, options);

       
	
}