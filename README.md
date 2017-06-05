![logo](https://github.com/sorabhm/crawler/blob/master/logo.jpg)

[![Build Status](https://travis-ci.org/sorabhm/crawler.svg?branch=master)](https://travis-ci.org/sorabhm/crawler)

#SpideyTheCrawler with Fork/Join

A simple crawler based on Fork/Join framework. With the given parameters it parses through the website and pulls out list of Unique URLs accessed (all urls will be of the same domain), list of URLs to external websites which are ignored, and the list of images found while parsing the pages. 
It uses JSoup framework to connect and parses a web page. 

##Pre-requisites
* You must have Maven and Java 8 installed on your system to build and execute this application
* You must also be able to work with [GitHub](https://help.github.com/articles/set-up-git) repositories.

## Setup:
* Clone repository.
	git clone https://github.com/sorabhm/crawler.git
* Open cmd (Windows) or terminal for MacOS
* Move to the main folder where the repository is cloned
	* Execute command 'mvn install'
	* Execute command 'mvn exec:java -Dexec.args="http://wiprodigital.com/ 20" 
	(NOTE: Change the arguments as per requirement, where 1st argument is the Initial URL and 2nd argument after space is number of maximum threads you want to run in parallel. Above values are also used as default by the application if no arguments are passed.)

The main file is [Application.java](https://github.com/sorabhm/crawler/blob/master/src/main/java/com/crawler/core/Application.java)

##Eclipse Instructions
--------------------

* **Prerequisites:**
    * Install [Eclipse](http://www.eclipse.org/downloads/), the [Maven plugin](http://eclipse.org/m2e/), and optionally the [GitHub plugin](http://eclipse.github.com/).

* Set up workspace
* Import the project from the checkout location
* Go to pom.xml
	* Right click on the file and choose 'Run as' -> 'maven install'
	* All the dependencies will be downloaded, wait till build is passed
	* If build fails, analyse the logs for the issues. It could be due to network connectivity or some other configuration
	
* Go to pom.xml
	* Right click on the file and choose 'Run as' -> 'Run configurations'
	* In the Dialog box, set a name for the configuration e.g. 'Default config'
	* In the "Goals:" field add `exec:java -Dexec.args="http://wiprodigital.com/ 4"`
	* Click on "Apply" and then "Run"
	
#What's In Progress
--------------------
* Javadoc
* Unit test cases for Static components using PowerMock and testing Exception cases
* Performance data matrix during and after the run to check how much time and memory application has consumed during the run
* Ability to terminate the application after a specific threshold e.g. Maximum Depth or Max number of URLs accessed
* Log files archival process
* Better formatting of the output file