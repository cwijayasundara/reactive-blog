CREATE KEYSPACE blog WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor': 3};
CREATE KEYSPACE blogtest WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor': 3};