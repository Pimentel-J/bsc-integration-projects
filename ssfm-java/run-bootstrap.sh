#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=shopfloor.app.bootstrap/target/shopfloor.app.bootstrap-1.3.0-SNAPSHOT.jar:shopfloor.app.bootstrap/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP eapli.shopfloor.app.bootstrap.BaseBootstrap
