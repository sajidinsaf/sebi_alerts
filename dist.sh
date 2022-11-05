mvn clean install
rm -r sebi-alerts
rm sebi-alerts.zip
mkdir -p sebi-alerts
cp ./target/sebi_alerts-1.0.0-jar-with-dependencies.jar sebi-alerts/sebi-alerts.jar
cp src/main/resources/sample-visitor-record-1000.csv sebi-alerts
cp src/main/resources/*.txt sebi-alerts
cp src/main/resources/config-dist.properties sebi-alerts/config.properties
cp sebi-alerts.sh sebi-alerts
cp sebi-alerts.bat sebi-alerts
zip -vr sebi-alerts.zip sebi-alerts/* -x "*.DS_Store"