require.paths = {
	// Major libraries
	jquery: 'lib/jquery/jquery.min',
	underscore: 'lib/underscore/underscore.min',
	backbone: 'lib/backbone/backbone.min',
	bootstrap: 'lib/bootstrap/bootstrap.min',
	json2: 'lib/json2',

	// Require.js plugins
	text: 'lib/require/text',

	// Just a short cut so we can put our html outside the js dir
	// When you have HTML/CSS designers this aids in keeping them out of
	// the js directory
	template: '../template',
	
	'jquery.ui': 'lib/jquery-ui/jquery-ui.min',
	'jquery-ui-i18n': 'lib/jquery-ui/jquery-ui-i18n',
	'jquery.ui.timepicker': 'lib/jquery-ui/jquery-ui-timepicker-addon'
};

require.shim = {
	'backbone': {
		deps: ['underscore', 'jquery', 'json2'],
		exports: 'Backbone'
	},
	'underscore': {
		deps: [],
		exports: '_'
	},
	'bootstrap': ['jquery'],
	'jquery.ui': ['jquery'],
	'jquery-ui-i18n': ['jquery', 'jquery.ui'],
	'jquery.ui.timepicker': ['jquery.ui'],
};