package de.jotschi.javadoctest;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

public class CypherDoclet {
	public static boolean start(RootDoc root) throws ParserConfigurationException, SAXException {
		ClassDoc[] classes = root.classes();
		for (ClassDoc clazz : classes) {
			System.out.println("COMMENT:" + clazz.commentText());
			

			for (Tag tag : clazz.tags("@code")) {
				System.out.println("Tag text {" + tag.text() + "} name {" + tag.name() + "}");
			}
		}
		return true;
	}
}
