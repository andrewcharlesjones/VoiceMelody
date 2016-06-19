package edu.brown.cs.acj.voicemelody;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;
import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * High-level control of bacon program. Manages system exiting.
 *
 * @author acj
 *
 */
public final class Main {

	/**
	 * main method for bacon.
	 *
	 * @param args
	 *            args from command line.
	 * @throws IOException
	 *             if file doens't exist.
	 * @throws SQLException
	 *             if the database can't be accessed.
	 * @throws ClassNotFoundException
	 *             if the SQL connection is invalid.
	 */
	public static void main(String[] args) {

		new Main(args).run();
	}

	private String[] args;
	private static String db;

	private Main(String[] args) {
		this.args = args;
	}

	private void run() {
		OptionParser parser = new OptionParser();
		parser.accepts("gui");
		OptionSet options = parser.parse(args);

		ClarifaiClient clarifai = new ClarifaiClient("YzMYgXRFeJXUQqUId1U9QsaID3Mg9tp5IuE8CIyy", "gpFtPGj3xh1ym2HDybq3buvPF3z4AtOodDTMVsp3");
		List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest("http://www.clarifai.com/img/metro-north.jpg"));

		for (Tag tag : results.get(0).getTags()) {
			System.out.println(tag.getName() + ": " + tag.getProbability());
		}

		runSparkServer();

	}

	/**
	 * Runs the spark server.
	 */
	private void runSparkServer() {
		Spark.externalStaticFileLocation("src/main/resources/static");

		FreeMarkerEngine freeMarker = createEngine();

		// Setup Spark Routes
		Spark.get("/index.html", new FrontHandler(), freeMarker);
	}

	/**
	 * creates freemarker engine.
	 * 
	 * @return
	 */
	private static FreeMarkerEngine createEngine() {
		Configuration config = new Configuration();
		File templates = new File("src/main/resources/spark/template/freemarker");
		try {
			config.setDirectoryForTemplateLoading(templates);
		} catch (IOException ioe) {
			System.out.printf("ERROR: Unable use %s for template loading.\n", templates);
			System.exit(1);
		}
		return new FreeMarkerEngine(config);
	}

	/**
	 * Handler for the homepage frontend.
	 * 
	 * @author idk
	 */
	private class FrontHandler implements TemplateViewRoute {
		@Override
		public ModelAndView handle(Request req, Response res) {

			Map<String, String> variables = ImmutableMap.of("title", "HelpMe!");
			return new ModelAndView(variables, "index.html");
		}
	}
}
