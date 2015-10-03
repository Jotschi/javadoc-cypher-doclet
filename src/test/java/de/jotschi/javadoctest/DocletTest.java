package de.jotschi.javadoctest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import de.jotschi.javadoc.cypher.CypherDoclet;

public class DocletTest {

	@Test
	public void testRendering() throws IOException, InterruptedException {
		String code = "(a:Person)<-[r:KNOWS]-(b:Friend)";
		File outDir = new File("target/output");
		FileUtils.deleteDirectory(outDir);
		CypherDoclet.invokeRender(outDir, "test.jpg", code);
		assertTrue(new File(outDir, "test.jpg").exists());
	}
}
