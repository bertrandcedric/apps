define([ 'jquery', // modules qui exportent une valeur
         'jquery.ui', 'jquery.ui.timepicker', 'appConfig' ], // modules qui n'exportent pas de valeur
function($) {
	return {
		parse : function(dateString) {
			return $.datepicker.parseDate($.datepicker._defaults.dateFormat, dateString);
		},

		format : function(date) {
			return $.datepicker.formatDate($.datepicker._defaults.dateFormat, date);
		},
		
		formatDateTime : function(date) {
			return this.format(date) + " " + $.datepicker.formatTime('hh:mm:ss', { hour: date.getHours(), minute: date.getMinutes(), second: date.getSeconds() });
		}
	};
});