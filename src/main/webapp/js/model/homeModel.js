define([  'backbone', 'util/resource' ], 
function( Backbone  , resource        ) {
	var singleton;
	var LoginModel = Backbone.Model.extend({
		initialize : function() {
			_.bindAll(this);
		},
		
		urlRoot: resource.url('get')
	});
	
	singleton = new LoginModel();
	return singleton;
});