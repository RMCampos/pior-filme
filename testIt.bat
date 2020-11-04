@ECHO OFF

SET "JAVA_HOME=C:\java-11-openjdk-11.0.8.10-1.windows.ojdkbuild.x86_64"

:: Unit Tests
mvn test -DfailIfNoTests=falsegit 