# ULB Algorithmique 2 (info-f203)
## Projet R-trees
### Hugo Charels & Mickael Kovel

#### Because of hight ammount of use of memory, you have to enter this command to allow maven to use more memory :
    export MAVEN_OPTS="-Xmx2048m"
(only for the France shapefile)

You need maven to compile and run this project

#### To compile and run:
    mvn clean compile exec:java

#### To compile:
    mvn clean compile

#### To run:
    mvn exec:java

#### To clean the directory:
    mvn clean

#### To generate the JavaDoc:
    mvn javadoc:javadoc
open target/site/apidocs/index.html in a web browser


We have put only the shapefile of USA in the data directory Otherwise the zip is too big.
