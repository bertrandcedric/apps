define([  'jquery', 'util/renderTemplate', 'backbone', 'text!template/home.html' ],  
function( $       , renderTemplate       , Backbone  , template                  ) {
	var singleton,
		HomeView = Backbone.View.extend({
		el: $('#container'),

		initialize: function() {
			_.bindAll(this);
		},
		
		setModel: function(model) {
			this.model = model;
		},
		
		render: function() {
			this.$el.html(renderTemplate(template, {
				sessionID: this.model.toJSON().count
			}));
		}
	});
	
	singleton = new HomeView();
	
	return {
		showMe: function() {
			require([ 'model/homeModel' ], 
			function( homeModel ) {
				singleton.setModel(homeModel);
				homeModel.fetch({
					success: function(homeModel, response){
						singleton.render();
					}
				});
			});
		}
	};
});