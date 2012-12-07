define([ 'backbone', 'require', 'module' ],
function( Backbone , require  , module  ) {
	var baseUrl = module.config().baseUrl + "/";
	
	var AppRouter = Backbone.Router.extend({
		routes: {
			'': 'index'
		},
		
		init: function() {
			Backbone.history.start({
				root : baseUrl,
				pushState: true
			});
			this.triggerNavigate(this.getCurrentFragment());
		},
		
		getCurrentFragment: function() {
			return Backbone.history.getFragment(Backbone.history.fragment);
		},

		triggerNavigate: function(fragment) {
			var handler = _.find(Backbone.history.handlers, function(handler) {
				return handler.route.test(fragment);
			});
			this.trigger('navigate', handler.name);
		},
		
		route: function(route, name, callback) {
			var result = Backbone.Router.prototype.route(route, name, callback);
			Backbone.history.handlers[0].name = name;
			return result;
		},
		
		navigate: function(fragment, options) {
			var result, oldFragment, newFragment;
			oldFragment = Backbone.history.fragment;
			result = Backbone.Router.prototype.navigate.call(this, fragment, options);
			newFragment = this.getCurrentFragment();
			if (oldFragment !== newFragment) {
				this.triggerNavigate(newFragment);
			}
			return result;
		},
		
		index: function() {
			require([ 'view/homeView' ], 
			function( HomeView        ) {
				HomeView.showMe();
			});
		}
	});

	return new AppRouter();
});
