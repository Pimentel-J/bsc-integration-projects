#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=shopfloor.app.other.console/target/shopfloor.app.other.console-1.3.0-SNAPSHOT.jar:shopfloor.app.other.console/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP eapli.SCM.presentation.ServidorTCP
