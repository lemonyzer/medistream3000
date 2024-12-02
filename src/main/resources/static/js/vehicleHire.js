/**
 * 
 */

$('document').ready(function() {	
	$('.table #editButton').on('click',function(event){		
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(appointment, status){
			$('#idEdit').val(appointment.id);
//			$('#ddlClientEdit').val(appointment.clientid);
			
			var startTimestamp = appointment.startTimestamp;
			var appointmentDate = appointment.appointmentDate;
			$('#startTimestampEdit').val(startTimestamp);
			$('#appointmentDateEdit').val(appointmentDate);
			
//			$('#timeInEdit').val(appointment.timeIn);
//			$('#timeOutEdit').val(appointment.timeOut);
//
//			$('#ddlRoomEdit').val(appointment.room);
			$('#medistreamIdEdit').val(appointment.medistreamId);
//			$('#remarksEdit').val(appointment.remarks);
//			$('#ddlVehicleEdit').val(appointment.vehicleid);
		});			
		$('#editModal').modal();		
	});
	
	$('.table #detailsButton').on('click',function(event) {
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(appointment, status){
			$('#idDetails').val(appointment.id);
			$('#ddlClientDetails').val(appointment.clientid);
			
			var dateIn = appointment.dateIn.substr(0,10);
			var dateOut = appointment.dateOut.substr(0,10);
			$('#dateInDetails').val(appointment.dateIn);
			$('#dateOutDetails').val(appointment.dateOut);
			$('#ddlLocationDetails').val(appointment.locationid);
			$('#priceDetails').val(appointment.price);
			$('#remarksDetails').val(appointment.remarks);
			$('#ddlVehicleDetails').val(appointment.vehicleid);
			$('#lastModifiedByDetails').val(appointment.lastModifiedBy);
			$('#lastModifiedDateDetails').val(appointment.lastModifiedDate.substr(0,19).replace("T", " "));
		});			
		$('#detailsModal').modal();		
	});	
	
	$('.table #deleteButton').on('click',function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$('#deleteModal #delRef').attr('href', href);
		$('#deleteModal').modal();		
	});	
});