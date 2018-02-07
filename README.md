# Transactions

To run server and then to run all the tests from IntelliJ Idea:

1. Run "mvn compile"

2. Press "Shift + F10". It will start Tomcat Server with required REST API. Or run it via Run/Debug Configuration laucher in IDE's upper right corner. Controllers' Integration tests are not to be passed without running server

3. Run "mvn test"

4. Run "mvn site" to generate human frendly Allure-reports. The file you need will be here: target/site/allure-maven-plugin/index.html


P.S. The file due to which Idea will find a helpfull configuration to start Tomcat server with one click is ".idea/workspace.xml". Most likely you will not want to track this file with your Git. To tell Git leave this file alone not tracking it, type this in Idea terminal window:

git update-index --skip-worktree .idea/workspace.xml
