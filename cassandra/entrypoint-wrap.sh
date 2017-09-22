#!/bin/bash

if [[ ! -z "$CASSANDRA_KEYSPACE" && $1 = 'cassandra' ]]; then
  # Create default keyspace for single node cluster
  CQL="CREATE KEYSPACE $CASSANDRA_KEYSPACE WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};"
  CQL+="CREATE TABLE mitosis.cell (name varchar, cellType varchar, PRIMARY KEY (name, cellType));"
  until echo $CQL | cqlsh; do
    echo "cqlsh: Cassandra is unavailable - retry later"
    sleep 2
  done &
fi

exec /docker-entrypoint.sh "$@"