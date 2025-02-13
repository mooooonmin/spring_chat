echo "Build Project"

./gradlew clean build

echo "Strat Server"

cd ./build/libs
java -jar demo-0.0.1-SNAPSHOT.jar