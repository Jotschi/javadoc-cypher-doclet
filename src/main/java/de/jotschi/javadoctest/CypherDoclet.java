package de.jotschi.javadoctest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

public class CypherDoclet {

	private static void parseDoc(String comment) throws IOException, InterruptedException {

		int codeIdx = comment.indexOf("@code");
		if (codeIdx == -1) {
			return;
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
		System.out.println(code);

		int imageLinkStart = comment.indexOf("<img");
		int imageLinkStop = comment.indexOf(">", imageLinkStart) + 1;
		String imageLink = comment.substring(imageLinkStart, imageLinkStop);

		int imageSrcLinkStart = imageLink.indexOf("/cypher");
		int imageSrcLinkStop = imageLink.indexOf("\"", imageSrcLinkStart);

		String url = imageLink.substring(imageSrcLinkStart, imageSrcLinkStop);
		// System.out.println(url);

		String imageName = url.substring(url.lastIndexOf("/") + 1);
		// System.out.println(imageName);

		File cwd = new File("../../..");
		String execLine = "phantomjs " + cwd.getAbsolutePath() + "/cypher-renderer/generator.js " + cwd.getAbsolutePath()
				+ "/cypher-renderer/view.html " + cwd.getAbsolutePath() + "/target/cypher/" + imageName + " \"" + code + "\"";
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
		ClassDoc[] classes = root.classes();
		for (ClassDoc clazz : classes) {
			parseDoc(clazz.commentText());
		}
		return true;
	}
}
