COMPONENT=AsteroidsEntityFramework
JAVA_FILES=$(shell find ${COMPOENT} -type f -name "*.java")
PACKAGE=bin/GameLab.jar

default all:run 

install: ${COMPONENT}/pom.xml
	mvn install -f ${COMPONENT}/pom.xml

test: ${JAVA_FILES}
	mvn test -f ${COMPONENT}/pom.xml

${PACKAGE}: install
	mvn package -f ${COMPONENT}/Core/pom.xml

package: ${PACKAGE}

run: ${PACKAGE}
	java -jar ${PACKAGE}
