mvn clean install
rm -r sebi-alerts
rm -r dist

rm sebi-alerts.zip
mkdir -p sebi-alerts/view
mkdir dist


#cp ./target/sebi_alerts-1.0.0.jar sebi-alerts/sebi-alerts.jar
cp ./target/sebi_alerts-1.0.0-jar-with-dependencies.jar sebi-alerts/sebi-alerts.jar
cp src/main/resources/sample-visitor-record-1000.csv sebi-alerts
cp src/main/resources/*.txt sebi-alerts
cp src/main/resources/config-dist.properties sebi-alerts/config.properties
cp src/main/resources/application-dist.properties sebi-alerts/application.properties
cp src/main/resources/view/*.html sebi-alerts/view
cp src/main/resources/scripts/* sebi-alerts
zip -vr dist/sebi-alerts.zip sebi-alerts/* -x "*.DS_Store"

zip -jvr dist/lib.zip ./target/lib -x "*.DS_Store"