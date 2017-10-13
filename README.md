[![Build Status](https://travis-ci.org/centic9/IntelliJ-Action-IDs.svg)](https://travis-ci.org/centic9/IntelliJ-Action-IDs) [![Gradle Status](https://gradleupdate.appspot.com/centic9/IntelliJ-Action-IDs/status.svg?branch=master)](https://gradleupdate.appspot.com/centic9/IntelliJ-Action-IDs/status)
[![Release](https://img.shields.io/github/release/centic9/IntelliJ-Action-IDs.svg)](https://github.com/centic9/IntelliJ-Action-IDs/releases)

A small tool to search an installation of IntelliJ for action-XML files and extract 
a list of action-ids that can be used in plugins.

### Structure

There is some Java code which scans a given folder. The resulting data is stored as 
CSV file at docs/_data/actions.csv. 

Some jekyll templates take that data and produce the resulting HTML file.

### Run it

Run the application `ProcessActionsXml` by passing it the location of an local IntelliJ 
installation. It will search through all the zip/jar-files recursively to find all files 
named ``*Action.xml`. The results will end up in the file `docs/_data/actions.csv`.

You can then run Jekyll via the script `jekyll` to get the html built in directory `build/jekyll`.

On Github, jekyll will be run automatically upon checkin and publish the results at 
https://centic9.github.io/IntelliJ-Action-IDs/

### Sources

The list at http://keithlea.com/idea-actions/ was used as starting point.

### Contribute

If you are missing things or have suggestions how to improve the list, please either send pull
requests or create [issues](https://github.com/centic9/IntelliJ-Action-IDs/issues).

### Licensing

   Copyright 2013-2017 Dominik Stadler

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
