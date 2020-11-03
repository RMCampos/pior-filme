@ECHO OFF

CALL compile.bat
@IF %ERRORLEVEL% NEQ 0 (
    EXIT /B 1
)

java -jar target\pior-filme-1.0-SNAPSHOT.jar