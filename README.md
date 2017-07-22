# FlightCalculator

FlightCalculator is a utility application that pulls the google flights api and gets all possible from an origin and a destination on selected date. After multiple requests it creates an average price and emails you of flights that are significantly less than the average. The collected data can also be used to detect long term flight trends.

## How to use?
First fill out the config.properties file with the required information. 
The api key can be attrieved from [https://developers.google.com/qpx-express/](https://developers.google.com/qpx-express/).
The password must be the correct for the from password. 
Also configure the gmail from to allow insecure emails. [https://support.google.com/accounts/answer/6010255?hl=en](https://support.google.com/accounts/answer/6010255?hl=en)

mvn clean install

java -jar flightcalculator-jar-with-dependencies.jar [origin] [destination] [flightDate]
```
java -jar fightcalculator-jar-with-dependencies.jar den sea 8-1-2017
```
Also a cron job can be set up to run the java application on a regular basis. 
```
crontab -e
0 */4 * * * java -jar fightcalculator-jar-with-dependencies.jar den sea 8-1-2017  // run the job every 4 hours
```

## Built With

* [QPX Express Api](https://developers.google.com/qpx-express/) - Google Flights RestAPI
* [Maven](https://maven.apache.org/) - Dependency Management
* [Mail](http://www.oracle.com/technetwork/java/javamail/index.html) - Java mail


## Authors

* **Jimmy Chen** [github.com/jschen3](https://github.com/jschen3)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

