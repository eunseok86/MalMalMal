function parseSpString(data){

	var SpArray=new Array("\\","\&","\/","\#","\,","\+","\(","\)","\$","\~","\%","\.","\'","\"","\:","\*","\?","\<","\>","\{","\}");
	
	var rep = data;
	
	for(var i=0;i<SpArray.length;i++){	
		 rep = rep.split(SpArray[i]).join('\\'+SpArray[i]);
	}
	
	return rep
}
function getDate (x){
 var dateObj = new Date( );
 var diffDate = getAddDay ( dateObj, -x );
 return (diffDate.getFullYear() + "-" + (diffDate.getMonth()+1 < 10? "0": "") + (diffDate.getMonth()+1) + "-"  + (diffDate.getDate() < 10 ? "0":"") + diffDate.getDate());
}

function getAddDay ( targetDate  , dayPrefix ){
 var newDate = new Date( );
 var processTime = targetDate.getTime ( ) + ( parseInt ( dayPrefix ) * 24 * 60 * 60 * 1000 );
  newDate.setTime ( processTime );
 return newDate;
}

function setDatePicker(s,e){
	var startDateTextBox = $("#"+s+"");
	var endDateTextBox = $("#"+e+"");

	startDateTextBox.datepicker({ 
		dateFormat: "yy-mm-dd",
		//timeFormat: "hh:mm:ss",
		onClose: function(dateText, inst) {
			if (endDateTextBox.val() != '') {
				var testStartDate = startDateTextBox.datetimepicker('getDate');
				var testEndDate = endDateTextBox.datetimepicker('getDate');
				if (testStartDate > testEndDate)
					endDateTextBox.datetimepicker('setDate', testStartDate);
			}
			else {
				endDateTextBox.val(dateText);
			}
		},
		onSelect: function (selectedDateTime){
			endDateTextBox.datetimepicker('option', 'minDate', startDateTextBox.datetimepicker('getDate') );
		}
	});
	endDateTextBox.datepicker({ 
		dateFormat: "yy-mm-dd",
		//timeFormat: "hh:mm:ss",
		onClose: function(dateText, inst) {
			if (startDateTextBox.val() != '') {
				var testStartDate = startDateTextBox.datetimepicker('getDate');
				var testEndDate = endDateTextBox.datetimepicker('getDate');
				if (testStartDate > testEndDate)
					startDateTextBox.datetimepicker('setDate', testEndDate);
			}
			else {
				startDateTextBox.val(dateText);
			}
		},
		onSelect: function (selectedDateTime){
			startDateTextBox.datetimepicker('option', 'maxDate', endDateTextBox.datetimepicker('getDate') );
		}
	});
}