ULB Algorithmique 2 (info-f203)
Projet R-trees
Hugo Charels & Mickael Kovel

Because of hight ammount of use of memory, you have to enter this command to allow maven to use more memory :
    export MAVEN_OPTS="-Xmx2048m"
(only beacause of the shapefile of France)

You need maven to compile and run this project

To compile:
    mvn clean compile

To run the code:
    mvn clean compile exec:java

To clean the directory:
    mvn clean

