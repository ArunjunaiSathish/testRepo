@echo off

call mvn clean 

call mvn test -PSuiteLocalSystemRun

pause

@echo on

