#!/bin/bash

mvn clean package
cp -rv siavs-ear/target/siavs-ear.ear ../build/siavs-ear.ear
cp -rv siavs-web/target/siavs.war ../build/siavs.war