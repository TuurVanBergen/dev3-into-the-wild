# Mushroom Identification Application

## Overview

This application allows you to identify mushrooms based on their characteristics by querying a MySQL database. It uses various classes to interact with the database, manage mushroom data, and handle user interactions.

## Requirements

- Java 17 or higher
- MySQL database
- Kotlin

## Steps to Run the Application

### 1. Set up the Database

Make sure you have a MySQL database running and configure the `Credential` class with your database credentials.

### 2. Database connection and Queries
The application connects to a MySQL database via JDBC. User data such as username and password are stored in the Credential class. The application runs an SQL query to retrieve data of mushrooms, including name, cap shape, spore pattern, smell, toxicity, size, stem shape, lamellae, colour, growth period and habitat.

The SQL query retrieves data from multiple tables such as:

- mushrooms
- colour_mushrooms and colours
- growth_periods_mushrooms and growth_periods
- habitats
The retrieved data is stored in a Mushroom object.

### 3. Data Classes
Mushroom Class
The Mushroom class stores various properties of a mushroom, such as:

- Name
- Hat shape, spore pattern, smell, toxicity, size, stem shape, lamellae
- Colours, growth period, habitat
- This class represents the data of a mushroom in the application.

### 4. Add new mushrooms
The MushroomAdder class provides an interface to add new mushrooms to the database. Users are prompted for attributes such as:

- Name, size, cap shape, smell, toxicity, stem shape, lamellae
- Colour, habitat, growth period
- New mushrooms and colours are stored in the respective tables in the database.


### 5. Mushroom Identification and Statistics.
Users can identify mushrooms through a series of questions on characteristics such as size, cap shape, smell, toxicity, stem shape, lamellae, colour and growth period. The application compares the answers with the database and displays the matching mushrooms. It also tracks how often a mushroom is identified.

### Sources
- JDBC (Java Database Connectivity)
https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html

- MySQL and SQL Queries
https://dev.mysql.com/doc/

- Object-Oriented Design and Classes
https://docs.oracle.com/javase/tutorial/java/concepts/

- Mushroom Identification
https://www.mushroomexpert.com/
