#!/bin/bash

./compile.sh

if [[ "$?" -ne 0 ]]; then
    exit 1
fi

java \
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5757 \
    -jar target/pior-filme-1.0-SNAPSHOT.jar