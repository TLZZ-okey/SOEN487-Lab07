# SOEN 487 - Lab 7
## Included Projects ##

1. The server/REST API
    * Located in /server
    * Is used to hold the REST API built in the previous tutorial

2. The example client
    * Located in /clients/example
    * Is used to hold the related classes for building the client side for our tutorial
    
## Maven Instructions ##

    Run the following commands in the associated project directory:

    Compile:
        mvn install

    Run (Server)
        mvn exec:java -Pserver

    Run (Customer Client)
        mvn -e exec:java -Pclient

    Clean:
        mvn clean
