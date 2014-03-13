
# maven-profiling-logger #

Simple Maven extension to present time consumed by different maven plugins.

# How-to #

Perform a full build of maven-growl-plugin by running

    mvn clean install

Then go in `target`, pick the `maven-profiling-logger-${project.version}.jar` and drop that jar in your `$MVN_HOME\lib\ext` folder.

# Sample output #

[INFO] MOJO EXECUTION TIMES
[INFO] ------------------------------------------------------------------------
[INFO]  37% maven-compiler-plugin [2.00s]
[INFO]      * compile 37% [1.99s]
[INFO]      * testCompile 0% [0.01s]
[INFO]  21% project setup [1.16s]
[INFO]   8% maven-jar-plugin [0.47s]
[INFO]      * jar
[INFO]   7% plexus-component-metadata [0.41s]
[INFO]      * generate-metadata
[INFO]   6% maven-resources-plugin [0.35s]
[INFO]      * resources 6% [0.35s]
[INFO]      * testResources 0% [0.01s]
[INFO]   6% maven-surefire-plugin [0.33s]
[INFO]      * test
[INFO]   5% maven-clean-plugin [0.27s]
[INFO]      * clean
[INFO]   2% maven-install-plugin [0.16s]
[INFO]      * install
[INFO]   2% scanning for projects [0.12s]
[INFO]   1% other [0.05s]
[INFO] ------------------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 5.317s
[INFO] Finished at: Thu Mar 13 20:26:35 CET 2014
[INFO] Final Memory: 14M/35M


# Thanks #
It was build thanks to excelent work done by alexkli https://github.com/alexkli/maven/commit/efb72827d2df44abf1114bcc7aeff3efeca2cd55
Unfortunately it is not part of Maven release, so I found a way to use it for now as extension following https://github.com/Riduidel/maven-growl-plugin

