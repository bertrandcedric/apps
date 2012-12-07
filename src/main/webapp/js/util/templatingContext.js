define(['util/dateHelper'],
function(dateHelper) {
	var baseUrl = "";

	return {
		baseUrl: function(newUrl) {
			baseUrl = newUrl;
		},
		context: {
			image: function(ref) {return baseUrl + "/img/" + ref;},
			parseDate: dateHelper.parse,
			formatDate: dateHelper.format
		}
	};
});
