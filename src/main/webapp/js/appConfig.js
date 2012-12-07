define(['jquery', 'module', 'jquery.ui'],
function($      , module  ) {
	var appConfig = module.config();
	$.datepicker.setDefaults($.datepicker.regional[appConfig.lang.toLowerCase()]);
	return appConfig;
});
