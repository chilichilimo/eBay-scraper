# eBay-scraper
Data Scraper and mailer used to scrape data from eBay using eBay APIs and notity users of the site of updated low prices.

Site: https://ebayapp.azurewebsites.net/

Site repo: https://github.com/now-raymond/ebayapp

# Notes
Application requires a `config.properties` file in `resources/`. Copy `example.config.properties` content into a new `config.properties` file in the same directory. Change values labelled `add_this` to the appropriate values.

To run the project, do:
```bash
mvn clean package
java -jar target/ebayscraper-1.0-SNAPSHOT.jar
```

