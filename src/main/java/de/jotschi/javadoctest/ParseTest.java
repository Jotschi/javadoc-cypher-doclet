package de.jotschi.javadoctest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ParseTest {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, InterruptedException {

		String xml = "<pre>\n" + "{@code\n" + "        (a:Person)<-[r:KNOWS]-(b:Friend)\n" + "        (a)<-[r:KNOWS]-(c:Atom)\n"
				+ "        (a)<-[r]->(d:Friend)\n" + " }\n" + " </pre>\n" + " <p>\n"
				+ " <img width=\"640\" height=\"301\" src=\"https://raw.github.com/wiki/ReactiveX/RxJava/images/cypher/ParseTest.png\" alt=\"\">\n"
				+ " <p>\n";

		int codeStart = xml.indexOf("@code") + 5;
		int codeStop = xml.indexOf("}", codeStart);
		System.out.println(xml.substring(codeStart, codeStop));

		int imageLinkStart = xml.indexOf("<img");
		int imageLinkStop = xml.indexOf(">", imageLinkStart) + 1;
		String imageLink = xml.substring(imageLinkStart, imageLinkStop);

		int imageSrcLinkStart = imageLink.indexOf("/cypher");
		int imageSrcLinkStop = imageLink.indexOf("\"", imageSrcLinkStart);

		String url = imageLink.substring(imageSrcLinkStart, imageSrcLinkStop);
		System.out.println(url);

		Process p = Runtime.getRuntime().exec("phantomjs cypher-renderer/generator.js");
		p.waitFor();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String output = reader.lines().collect(Collectors.joining("\n"));
		System.out.println(output);

	}
}
