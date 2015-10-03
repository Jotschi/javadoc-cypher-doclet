package de.jotschi.javadoctest;

import java.util.Collections;
import java.util.List;

/**
 * The Job Domain model class
 * 
 * <pre>
 * {@code
 * 	(a:Person)<-[r:KNOWS]-(b:Friend)
 * 	(a)<-[r:KNOWS]-(c:Atom)
 * 	(a)<-[r]->(d:Friend)
 * }
 * </pre>
 * <p>
 * <img src="https://raw.github.com/Jotschi/javadoc-cypher-doclet/gh-pages/cypher/de.jotschi.javadoctest.Job.jpg" alt="">
 * <p>
 * 
 * @author jotschi
 *
 */
public class Job {

	private List<User> getEmployees() {
		return Collections.emptyList();
	}

}
