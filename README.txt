Instructions on how to access demo:

1.Import all three projects- DAG-client, DAG-Server, DAG-MicroServer
2.Update maven dependencies for DAG-Server and DAG-MicroServer, and add following jar dependencies for DAG-client (json, compression filter, commons logging, jersey client, jersey api and jersey core)
3.Convert these projects into .war files.
4.Host jersey client on apache Tomcat1 (Port 8443) and web server and micro server on tomcat2(Port 8444).
5.Also, create a database named DAG in MySQL.
6.To access on local host use link: https://localhost:8443/DAG-client 
(This will tak you to the login page/ first page)