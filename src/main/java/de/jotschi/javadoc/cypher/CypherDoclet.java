package de.jotschi.javadoc.cypher;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

public class CypherDoclet {

	/**
	 * Extract the phantomjs script and the d3 view to a temporary directory.
	 * 
	 * @return
	 * @throws IOException
	 */
	private static File extractPhantomJSFiles() throws IOException {
		String tmpdir = System.getProperty("java.io.tmpdir");
		File baseDir = new File(tmpdir, "cypherdoclet");
		baseDir.mkdirs();
		for (File file : baseDir.listFiles()) {
			file.delete();
		}
		InputStream ins = CypherDoclet.class.getResourceAsStream("view.html");
		Files.copy(ins, new File(baseDir, "view.html").toPath());

		InputStream insJS = CypherDoclet.class.getResourceAsStream("generator.js");
		Files.copy(insJS, new File(baseDir, "generator.js").toPath());
		return baseDir;
	}

	private static String parseDoc(String comment) throws IOException, InterruptedException {

		int codeIdx = comment.indexOf("@code");
		if (codeIdx == -1) {
			System.err.println("Did not find comment with @code");
			return null;
		}
		int codeStart = codeIdx + 5;
		int codeStop = comment.indexOf("}", codeStart);
		String code = comment.substring(codeStart, codeStop);
		code = code.replace("\n", ",").replaceAll(" ", "").replaceAll("\t", "");
		if (code.startsWith(",")) {
			code = code.substring(1);
		}
		if (code.endsWith(",")) {
			code = code.substring(0, code.length() - 1);
		}
		return code;
		// System.out.println(code);

		// int imageLinkStart = comment.indexOf("<img");
		// if (imageLinkStart == -1) {
		// System.err.println("Did not find image link");
		// return;
		// }
		// int imageLinkStop = comment.indexOf(">", imageLinkStart) + 1;
		// String imageLink = comment.substring(imageLinkStart, imageLinkStop);

		// int imageSrcLinkStart = imageLink.indexOf("/cypher");
		// int imageSrcLinkStop = imageLink.indexOf("\"", imageSrcLinkStart);

		// String url = imageLink.substring(imageSrcLinkStart, imageSrcLinkStop);
		// System.out.println(url);

		// String imageName = url.substring(url.lastIndexOf("/") + 1);
		// System.out.println(imageName);

	}

	public static void invokeRender(File outputDir, String imageName, String code) throws IOException, InterruptedException {

		File baseDir = extractPhantomJSFiles();

		String execLine = "phantomjs " + baseDir.getAbsolutePath() + "/generator.js " + baseDir.getAbsolutePath() + "/view.html "
				+ outputDir.getAbsolutePath() + "/" + imageName + " \"" + code + "\"";
		System.out.println(execLine);
		Process p = Runtime.getRuntime().exec(execLine);
		p.waitFor();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String output = reader.lines().collect(Collectors.joining("\n"));
		System.out.println(output);

		BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String outputError = errorReader.lines().collect(Collectors.joining("\n"));
		System.out.println(outputError);

	}

	public static boolean start(RootDoc root) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		System.out.println(System.getProperty("bla"));
		ClassDoc[] classes = root.classes();
		for (ClassDoc clazz : classes) {
			// System.out.println(clazz.name());
			String code = parseDoc(clazz.commentText());
			if (code == null) {
				continue;
			}
			File outputDir = new File("cypher");
			invokeRender(outputDir, clazz.qualifiedName() + ".jpg", code);
		}
		return true;
	}
}
