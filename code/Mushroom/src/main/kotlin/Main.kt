import java.util.*
import java.sql.*

val mushrooms : MutableSet<Mushroom> = mutableSetOf()
fun main() {
    //make the mushrooms array empty
    mushrooms.clear()
    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance()

    // Prepare credentials
    val credentials = Credential()

    // Prepare credentials
    val connectionProps = Properties()
    connectionProps["user"] = credentials.username
    connectionProps["password"] = credentials.password

    // Create the connection: this will allow us to run queries on it later
    val connection =  DriverManager.getConnection(
        "jdbc:" + "mysql" + "://" +
                credentials.host +
                ":" + "3306" + "/" +
                credentials.databaseName,
        connectionProps)

    //sql query creation which is then put into the variable 'statement'.
    //The m. refers to the mushrooms table, as I gave it an alias m with "mushrooms m". The same applies to c. (colors) & cm. (color_mushrooms) & .gp (growth_period) & .h (habitat) & .gpm (growth_periods_mushrooms)
    //GROUP_CONCAT(c.color) groups the colors for every mushroom. https://www.geeksforgeeks.org/mysql-group_concat-function/
    //MAX() retrieves the highest value

    val statement = connection.prepareStatement("SELECT m.id, m.name, m.hat_shape, m.spore_patern, m.odor, m.toxicity_level, m.size, m.stem_shape, m.lamellae_presence, m.statistic, GROUP_CONCAT(c.color) AS colors, MAX(gp.growth_period) AS growth_period, MAX(h.habitat) AS habitat FROM mushrooms m JOIN color_mushrooms cm ON m.id = cm.id_mushroom JOIN colors c ON cm.Id_color = c.id_color JOIN growth_periods_mushrooms gpm ON m.id = gpm.id_mushroom JOIN growth_periods gp ON gpm.id_growth_period = gp.id JOIN habitats h ON m.habitat_id = h.id GROUP BY m.id, m.name, m.hat_shape, m.spore_patern, m.odor, m.toxicity_level, m.size, m.stem_shape, m.lamellae_presence, m.habitat_id, m.statistic;")

    // the statement is going to be executed and put into the variable 'result'.
    val result = statement.executeQuery()

    //as long as there is a next one in the array of the variable 'result' this loop is executed.
    while (result.next()){
        //The data from the database is put into the data class 'Mushroom'.
        mushrooms.add(Mushroom(result.getString("name"),result.getString("hat_shape"),result.getString("spore_patern"),result.getString("odor"),result.getString("toxicity_level"),result.getString("size"),result.getString("stem_shape"),result.getString("lamellae_presence"),result.getString("colors"),result.getString("growth_period"),result.getString("habitat"),result.getInt("statistic")))
    }

    // After all this is done, the 'filterResults' function is called.
    //The connection is passed through, so that there can be another connection
    filterResults(connection)
}
fun filterResults(connection : Connection) {

    //There is an array 'Questions' created, in which I put all the questions needed to identify the mushroom.
    val questions = setOf(
        "How big is the mushroom? Small, Medium or Large",
        "What shape is the hat of the mushroom? Spherical, Bell-shaped, Funnel-shaped, Cylindrical, Conical, Oblong, Flat, Cone-shaped, Convex, Roundish",
        "What pattern do the spores have? Lamellae or No Lamellae",
        "What odor does the mushroom have? Unremarkable, Earthy, Mild, Fruity, Floury, Fragrant, Sulfur, Unremarkable, pleasant, Sour, Weak, Musty, Anise, Stinky",
        "How poisonous is the mushroom? Toxic, Edible, Hallucinogenic, Non Toxic",
        "What shape does the stem have? Cylindrical, Slim, Short, Long, Solid",
        "Is there a lamellaa present? Present or Absent",
        "What is(are) the color(s)? Red, White, Brown, Orange, Yellow",
        "What is the growth_period? Summer, Winter, Spring, Autumn",
        "What is the habitat? Grassland, Mixed forests, Deciduous forests"
    )

    //as long as there is a next question in the questions array this loop is executed.
    for (question in questions) {

        //Print the question in the terminal.
        println(question)
        val userAnswer = readln()
        //The removeIf function and its syntax I found on the website below
        //https://www.geeksforgeeks.org/arraylist-removeif-method-in-java/
        //It's going to loop over the different elements of the mushrooms array.
        //if current question in the 'question' array matches any of the questions,
        //then it will check if the 'Mushroom."x"' is not equal to 'userAnswer'.
        //If not, then it wil return 'true' and the mushroom will be deleted in 'mushrooms'.
        //if it is false, then it will not be deleted
        //Else, nothing will be deleted.
        mushrooms.removeIf { mushroom ->
            when (question) {
                "How big is the mushroom? Small, Medium or Large" -> mushroom.size != userAnswer
                "What shape is the hat of the mushroom? Spherical, Bell-shaped, Funnel-shaped, Cylindrical, Conical, Oblong, Flat, Cone-shaped, Convex, Roundish" -> mushroom.hatShape != userAnswer
                "What pattern do the spores have? Lamellae or No Lamellae" -> mushroom.sporePatern != userAnswer
                "What odor does the mushroom have? Unremarkable, Earthy, Mild, Fruity, Floury, Fragrant, Sulfur, Unremarkable, pleasant, Sour, Weak, Musty, Anise, Stinky" -> mushroom.odor != userAnswer
                "How poisonous is the mushroom? Toxic, Edible, Hallucinogenic, Non Toxic" -> mushroom.toxicityLevel != userAnswer
                "What shape does the stem have? Cylindrical, Slim, Short, Long, Solid", -> mushroom.stemShape != userAnswer
                "Is there a lamellaa present? Present or Absent" -> mushroom.lamellaePresence != userAnswer
                "What is(are) the color(s)? Red, White, Brown, Orange, Yellow",-> !mushroom.colors.contains(userAnswer)
                "What is the growth_period? Summer, Winter, Spring, Autumn",-> mushroom.growth_period != userAnswer
                "What is the habitat? Grassland, Mixed forests, Deciduous forests"-> mushroom.habitat != userAnswer
                else -> false
            }
        }

        // Print the remaining mushrooms.
        println("Remaining mushrooms:")
        for (mushroom in mushrooms){
            println(mushroom)
        }

        // if there is only 1 mushroom left and all the questions are asked, then the mushroom is identified.
        if (mushrooms.size == 1 && questions.indexOf(question) == questions.size -1) {

            //The identified mushroom will be placed in 'identifiedMushroom'.
            val identifiedMushroom = mushrooms.first()
            //Increase statistics and save it to the database.
            identifiedMushroom.statistics += 1
            updateStatisticsInDatabase(connection, identifiedMushroom.name, identifiedMushroom.statistics)

            println("The mushroom is identified, it is the ${identifiedMushroom.name}")

            //Give the user the option to reset the statistics
            println("Would you like to reset the statistics? 'y'/'n'")
            var validInput = false;
            while(!validInput){
                var answer = readln()
                when(answer){
                    "y"->{
                        resetStatistics(connection)
                        validInput = true
                    }
                    "n"->{
                        validInput = true
                    }else->{
                    println("Invalid input. Please enter 'y' or 'n'.")
                    }
                }
            }

            validInput = false
            println("Would you like to add a mushroom?")
            while(!validInput){
                var answer = readln()
                when(answer){
                    "y"->{
                        addMushroom(connection)
                        validInput = true
                    }
                    "n"->{
                        validInput = true
                    }else->{
                    println("Invalid input. Please enter 'y' or 'n'.")
                }
                }
            }

            validInput = false
            //Read the user input and store it in "answer"
            println("Would you like to identify a mushroom again ? 'y'/'n'")
            while(!validInput){
                var answer = readln()
                when(answer){
                    "y"->{
                        println("The application restarts...")
                        main()
                        validInput = true
                    }
                    "n"->{
                        println("The application stops...")
                        //stop the app
                        validInput = true
                        return
                    }else->{
                    println("Invalid input. Please enter 'y' or 'n'.")
                }
                }
            }

        } else if (mushrooms.size == 0) {
            println("There are no remaining mushrooms to identify.")

           var validInput = false
            //Read the user input and store it in "answer"
            println("Would you like to add a mushroom? 'y'/'n'")
            while(!validInput){
                var answer = readln()
                when(answer){
                    "y"->{
                        addMushroom(connection)
                        validInput = true
                    }
                    "n"->{
                        validInput = true
                    }else->{
                    println("Invalid input. Please enter 'y' or 'n'.")
                }
                }
            }

             validInput = false
            //Read the user input and store it in "answer"
            println("Would you like to identify a mushroom again ? 'y'/'n'")
            while(!validInput){
                var  answer = readln()
                when(answer){
                    "y"->{
                        println("The application restarts...")
                        main()
                        validInput = true
                    }
                    "n"->{
                        validInput = true
                        println("The application stops...")
                        //stop the app
                        return
                    }else->{
                    println("Invalid input. Please enter 'y' or 'n'.")
                }
                }
            }
        }
    }
}

fun updateStatisticsInDatabase(connection: Connection, mushroomName: String, newStatistic: Int) {
    //On this website i found the createStatement() and executeUpdate().
    //https://www.enterprisedb.com/docs/jdbc_connector/latest/06_executing_sql_commands_with_executeUpdate()/#:~:text=createStatement())%20%7B-,The%20executeUpdate()%20method%20returns%20the%20number%20of%20rows%20affected,int%20rowcount%20%3D%20stmt.
    val updateStatement = connection.createStatement()
    val sql = "UPDATE mushrooms SET statistic = $newStatistic WHERE name = '$mushroomName'"
    println(sql)
    updateStatement.executeUpdate(sql)

    //show the current statistics of the mushroom
    showStatistic(newStatistic, mushroomName)
}

fun showStatistic(statistic: Int, mushroomName: String){
    println("The mushroom: ${mushroomName} was ${statistic} times identified.")
}

fun resetStatistics(connection: Connection){
    //On this website I found the createStatement() and executeUpdate().
    //https://www.enterprisedb.com/docs/jdbc_connector/latest/06_executing_sql_commands_with_executeUpdate()/#:~:text=createStatement())%20%7B-,The%20executeUpdate()%20method%20returns%20the%20number%20of%20rows%20affected,int%20rowcount%20%3D%20stmt.
    val updateStatement = connection.createStatement()
    val sql = "UPDATE mushrooms SET statistic = 0"
    updateStatement.executeUpdate(sql)
    println("Statistics reset to 0 for all mushrooms in the database.")
}

fun addMushroom(connection: Connection){
    val sqlList = mutableListOf<String>()

    var validInput = false
    println("Do you want to add habitat, or reuse it? add/reuse")
    while(!validInput){
        var answer = readln()
        when(answer){
            "add"->{
                println("Enter the habitat")
                answer = readln()
                sqlList.add("INSERT INTO `habitats` (`id`, `habitat`) VALUES (NULL, '${answer}')")
                validInput = true
            }
            "reuse"->{
                println("You can choose from these habitats: Coniferous forests, Mixed forests, Grassland, Deciduous forests")
                var input = false
                while(!input){
                    var userAnswer = readln()
                    when(userAnswer){
                        "Coniferous forests"->{
                     //       answer = "1"
                     //       sqlList.add("INSERT INTO `habitats` (`id`, `habitat`) VALUES (NULL, '${answer}')")
                            input = true
                        }
                        "Mixed forests"->{

                            input = true
                        }
                        "Grassland"->{

                            input = true
                        }
                        "Deciduous forests"->{

                            input = true
                        }
                        else->{
                            println("Try again! You have made a typo.")
                        }
                    }
                }
                validInput = true
            }else->{
            println("Invalid input. Please enter 'add' or 'reuse'.")
        }
        }
    }

        val answerList = mutableListOf<String>()
        // Add elements to the list
        println("What is the name of the mushroom?")
        answerList.add(readln())
        println("How big is the mushroom?")
        answerList.add(readln())
        println("What shape is the hat of the mushroom?")
        answerList.add(readln())
        println("What pattern do the spores have?")
        answerList.add(readln())
        println("What smell does the mushroom have?")
        answerList.add(readln())
        println("How poisonous is the mushroom?")
        answerList.add(readln())
        println("What shape does the stem have?")
        answerList.add(readln())
        println("Is there a lamellaa present?")
        answerList.add(readln())

        val prompt =
            "INSERT INTO `mushrooms` (`id`, `name`, `hat_shape`, `spore_patern`, `odor`, `toxicity_level`, `size`, `stem_shape`, `lamellae_presence`, `habitat_id`, `statistic`) VALUES (NULL, '${answerList[0]}', '${answerList[1]}', '${answerList[2]}', '${answerList[3]}', '${answerList[4]}', '${answerList[5]}', '${answerList[6]}', '${answerList[7]}', (SELECT MAX(id) FROM habitats), '0')"
        sqlList.add(prompt)

    println("What is the growth period? Winter, Spring, Summer, Autumn")
    validInput = false
    var userGrowthPeriod = ""
    while(!validInput){
   var answer = readln()
    when (answer) {
        "Winter" ->{
            userGrowthPeriod = "1"
            validInput = true
        }
        "Spring" ->{
            userGrowthPeriod = "2"
            validInput = true
        }
        "Summer" ->{
        userGrowthPeriod = "3"
            validInput = true
        }
        "Autumn" ->{
        userGrowthPeriod = "4"
            validInput = true
        }
        else ->{
            println("Invalid input. Please enter 'Winter','Spring', 'Summer' or 'Autumn'.")
        }
    }
    }
    sqlList.add("INSERT INTO `growth_periods_mushrooms` (`id`, `id_mushroom`, `id_growth_period`) VALUES (NULL, (SELECT MAX(id) FROM mushrooms), '${userGrowthPeriod}')")

     validInput = false
    println("Do you want to add a new or an existing color? new/existing")
    while(!validInput){
        var answer = readln()
        when(answer){
            "new"->{
                println("Enter the color")
                answer = readln()
                sqlList.add("INSERT INTO `habitats` (`id`, `habitat`) VALUES (NULL, '${answer}')")
                validInput = true
            }
            "existing"->{
                println("Choose between these colors. Red, White, Brown, Orange, Yellow")
                 validInput = false
                var userColor = ""
                while(!validInput){
                    var userAnswer = readln()
                    when (userAnswer) {
                        "Orange" ->{ userColor = "1"

                                    sqlList.add("INSERT INTO `color_mushrooms` (`id`, `id_mushroom`, `Id_color`) VALUES (NULL, (SELECT MAX(id) FROM mushrooms), '${userColor}')")
                            validInput = true
                        }
                        "Red" ->{ userColor = "2"

                                    sqlList.add("INSERT INTO `color_mushrooms` (`id`, `id_mushroom`, `Id_color`) VALUES (NULL, (SELECT MAX(id) FROM mushrooms), '${userColor}')")
                            validInput = true
                        }
                        "Brown" ->{ userColor = "3"

                                    sqlList.add("INSERT INTO `color_mushrooms` (`id`, `id_mushroom`, `Id_color`) VALUES (NULL, (SELECT MAX(id) FROM mushrooms), '${userColor}')")
                            validInput = true}
                        "White" ->{ userColor = "4"

                                    sqlList.add("INSERT INTO `color_mushrooms` (`id`, `id_mushroom`, `Id_color`) VALUES (NULL, (SELECT MAX(id) FROM mushrooms), '${userColor}')")
                            validInput = true
                        }
                        "Yellow" ->{ userColor = "5"
                                    sqlList.add("INSERT INTO `color_mushrooms` (`id`, `id_mushroom`, `Id_color`) VALUES (NULL, (SELECT MAX(id) FROM mushrooms), '${userColor}')")
                            validInput = true
                        }
                        else -> {
                            println("Try again! You have made a typo.")
                        }
                    }
                }
            }else ->{
            println("Try again! You have made a typo.")
            }
        }
    }
    val updateStatement = connection.createStatement()
    for(element in sqlList){
        updateStatement.executeUpdate(element)
    }
    println("The mushroom was added.")
    main()
}
