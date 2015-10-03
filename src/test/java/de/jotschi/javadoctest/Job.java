package de.jotschi.javadoctest;

import java.util.Collections;
import java.util.List;

/**
 * The Job Domain model class
 * 
 * <pre>
 * {@code
 * 	(j:Job)<-[r:WORKS_AT]-(u:User)
 * 	(j)-[s:SOLVES]->(t1:Task)
 * 	(j)-[s:SOLVES]->(t2:Task)
 *  (j)-[s:SOLVES]->(t3:Task)
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
