define(['underscore', 'util/templatingContext'],
function(_, templatingContext) {
	var Context = function (data) {
		_.extend(this, data);
	};
	Context.prototype = templatingContext.context;
	return function(templateString, data, settings) {
		if(_.isUndefined(data)) {
			var template = _.template(templateString, data, settings);
			return function(data) {
				return template(new Context(data));
			};
		} else {
			return _.template(templateString, new Context(data), settings);
		}
	};
});
