@echo off

call mvn clean 

call mvn test -PSuiteDockerRun

pause

@echo on

