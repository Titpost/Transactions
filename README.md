# Transactions

To run server and then to run all the tests from IntelliJ Idea use Java SDK 8. To be able to compile without MVN make sure Lombok Plugin is installed.

1. Run "mvn compile"

2. Press "Shift + F10". It will start Tomcat Server with required REST API. Or run it via Run/Debug Configuration laucher in IDE's upper right corner. UI and Controller's Integration tests are not to be passed without running server

3. Run "mvn test". UI tests will be run on Google Chrome, so it must be already installed. Unit tests do not require any DataBase connection. Service Integration tests use inner (H2) database. Controller Integration tests and UI tests use real remote (MySQL) DataBase, so internet connection is required

4. Run "mvn site" to generate human frendly Allure-reports. The report file can be found here: 
"target/site/allure-maven-plugin/index.html". It is recommended to open it in Mozilla Firefox

5. To play with FrontEnd go to http://localhost:8080/index.html



P.S. The file due to which Idea will find a helpfull configuration to start Tomcat server with one click is ".idea/workspace.xml". Most likely you will not want to track this file with your Git. To tell Git leave this file alone not tracking it, type this in Idea terminal window:
"git update-index --skip-worktree .idea/workspace.xml"
