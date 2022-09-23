#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=shopfloor.app.backoffice.console/target/shopfloor.app.backoffice.console-1.3.0-SNAPSHOT.jar:shopfloor.app.backoffice.console/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP eapli.shopfloor.app.backoffice.console.BaseBackoffice
