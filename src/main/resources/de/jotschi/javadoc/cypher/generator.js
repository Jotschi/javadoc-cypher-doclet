var system = require('system');
var webPage = require('webpage');

var args = system.args;
if (args.length === 1) {
	console.log('Try to pass some arguments when invoking this script!');
} else {

	var viewPath = args[1];
	var outputFile = args[2];
	var cypher = args[3];
	console.log("View: " + viewPath);
	console.log("Output: " + outputFile);
	console.log("Cypher: " + cypher);

	if (cypher === undefined) {
		console.log("No cypher input specified");
		phantom.exit(10);
	}
	var page = webPage.create();
	/*
	 * cypher = "(a:Person)<-[r:KNOWS]-(b:Friend)\n"; cypher += "(a)<-[r:KNOWS]-(c:Atom)\n";
	 * cypher += "(a)<-[r]->(d:Friend)";
	 */
	page.viewportSize = {
		width : 400,
		height : 250
	};
	page.open(viewPath, function start(status) {
		page.evaluate(function(cypher) {
			console.log(render(cypher));
		}, cypher);

		page.render(outputFile, {
			format : 'jpeg',
			quality : '100'
		});

		phantom.exit();
	});

}