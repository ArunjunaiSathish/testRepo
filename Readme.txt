-- navigate to project location and execute below file ( MAVEN setup is expected to run )
testrunner.exe 
--command to execute the using command line
--Cleaning the workspace
--navigate to the location where project is located and execute below commands
mvn clean
--adding the dependencies
mvn install
--run the tests
mvn test -PLocalSuite
