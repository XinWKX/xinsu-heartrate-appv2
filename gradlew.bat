@ECHO OFF

SET DIRNAME=%~dp0
SET APP_BASE_NAME=%~n0
SET APP_HOME=%DIRNAME%

SET DEFAULT_JVM_OPTS=

SET CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
IF %ERRORLEVEL% EQU 0 GOTO execute

ECHO.
ECHO ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
ECHO.
GOTO fail

:execute

"%JAVA_EXE%" %DEFAULT_JVM_OPTS% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

:end
IF %ERRORLEVEL% EQU 0 GOTO mainEnd

:fail
EXIT /B 1

:mainEnd
IF "%OS%"=="Windows_NT" ENDLOCAL
