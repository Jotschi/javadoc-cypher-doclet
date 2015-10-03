# javadoc-cypher-doclet

The doclet is able to parse cypher like code example within the class javadoc comment and transform those into a graph image.

```java
/**
 * The Job Domain model class
 * 
 * <pre>
 * {@code
 *  (j:Job)<-[r:WORKS_AT]-(u:User)
 *  (j)-[s:SOLVES]->(t1:Task)
 *  (j)-[s:SOLVES]->(t2:Task)
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
```

Example output:

![Example Output](https://raw.githubusercontent.com/Jotschi/javadoc-cypher-doclet/gh-pages/cypher/de.jotschi.javadoctest.Job.jpg)

## Requirements

* PhantomJS installation - (I tested it with 1.9.8)

## Cypher support 

The doclet is only able to parse code in a pseudo cypher like format. Node types do not need to be specified when the when node id is reused in another relationship line.
Currently only node, relationship, node lines are supported. If you want to create more complex graphs i suggest that you just append another line that extends the graph. 

## TODOs / Issues

* Doclet may not work under windows (not tested)
* Doclet is not published on maven central and must be build locally
* Clipping of images may occur (especially with larger examples)
* PNG support with alpha channel would be nice
* Error handling could be improved

## Usage Example

```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <executions>
            <!--First execution is used to generate normal html file output -->
            <execution>
                <phase>package</phase>
                <goals>
                    <goal>javadoc</goal>
                </goals>
            </execution>
            <!-- Second execution is used to generate the cypher images -->
            <execution>
                <id>cypher-images</id>
                <phase>package</phase>
                <goals>
                    <goal>javadoc</goal>
                </goals>
                <configuration>
                    <useStandardDocletOptions>false</useStandardDocletOptions>
                    <doclet>de.jotschi.javadoc.cypher.CypherDoclet</doclet>
                    <docletArtifact>
                        <groupId>de.jotschi</groupId>
                        <artifactId>javadoc-cypher-doclet</artifactId>
                        <version>0.0.1-SNAPSHOT</version>
                    </docletArtifact>
                </configuration>
            </execution>
        </executions>
    </plugin>
```