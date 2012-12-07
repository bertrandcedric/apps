var require = {
	baseUrl: '/js',
	deps: ['util/resource', 'router'],
	config: {
		'util/resource': {
			baseUrl: '/resource'
		},
		'router': {
			baseUrl: '/'
		},
		'appConfig': {
			lang: 'FR'
		}
	}
	//, urlArgs: "bust=" +  (new Date()).getTime() // ne pas garder en prod
};