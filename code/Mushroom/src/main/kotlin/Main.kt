import java.util.*
import java.sql.*

val mushrooms : MutableSet<Mushroom> = mutableSetOf()






fun main() {
    mushrooms.clear()
    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance()
    // Prepare credentials
    val credentials = Credential()
    //println(credentials)

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
    val statement = connection.prepareStatement("SELECT m.id, m.name, m.hat_shape, m.spore_patern, m.odor, m.toxicity_level, m.size, m.stem_shape, m.lamellae_presence, m.statistic, GROUP_CONCAT(c.color) AS colors, MAX(gp.growth_period) AS growth_period, MAX(h.habitat) AS habitat FROM mushrooms m JOIN color_mushrooms cm ON m.id = cm.id_mushroom JOIN colors c ON cm.Id_color = c.id_color JOIN growth_periods_mushrooms gpm ON m.id = gpm.id_mushroom JOIN growth_periods gp ON gpm.id_growth_period = gp.id JOIN habitats h ON m.habitat_id = h.id GROUP BY m.id, m.name, m.hat_shape, m.spore_patern, m.odor, m.toxicity_level, m.size, m.stem_shape, m.lamellae_presence, m.habitat_id, m.statistic;")
    println(statement)
    // the statement is going to be executed and put into the variable 'result'.
    val result = statement.executeQuery()

    //as long as there is a next one in the array of the variable 'result' this loop is executed.
    while (result.next()){
        //The data from the database is put into the data class 'Mushroom'.
        mushrooms.add(Mushroom(result.getString("name"),result.getString("hat_shape"),result.getString("spore_patern"),result.getString("odor"),result.getString("toxicity_level"),result.getString("size"),result.getString("stem_shape"),result.getString("lamellae_presence"),result.getString("colors"),result.getString("growth_period"),result.getString("habitat"),result.getInt("statistic")))
    }

    // After all this is done, the 'filterResults' function is called.
    filterResults(connection)
}
fun filterResults(connection : Connection) {

    //There is an array 'Questions' created, in which I put all the questions needed to identify the mushroom.
    val questions = setOf(
        "How big is the mushroom?",
        "What shape is the cap of the mushroom?",
        "What pattern do the spores have?",
        "What smell does the mushroom have?",
        "How poisonous is the mushroom?",
        "What shape does the stem have?",
        "Is there a lamellaa present?",
        "What is(are) the color(s)?",
        "What is the growth_period?",
        "What is the habitat?"
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
        mushrooms.removeIf { Mushroom ->
            when (question) {
                "How big is the mushroom?" -> Mushroom.size != userAnswer
                "What shape is the cap of the mushroom?" -> Mushroom.hatShape != userAnswer
                "What pattern do the spores have?" -> Mushroom.sporePatern != userAnswer
                "What smell does the mushroom have?" -> Mushroom.odor != userAnswer
                "How poisonous is the mushroom?" -> Mushroom.toxicityLevel != userAnswer
                "What shape does the stem have?" -> Mushroom.stemShape != userAnswer
                "Is there a lamellaa present?" -> Mushroom.lamellaePresence != userAnswer
                "What is(are) the color(s)?",-> !Mushroom.colors.contains(userAnswer)
                "What is the growth_period?",-> Mushroom.growth_period != userAnswer
                "What is the habitat?"-> Mushroom.habitat != userAnswer
                else -> false
            }
        }

        // Print the remaining mushrooms.
        println("Remaining mushrooms:")
        for (mushroom in mushrooms){
            println(mushroom)
        }

        // if there is and all the questions are asked, then the mushroom is identified.
        if (mushrooms.size == 1 && questions.indexOf(question) == questions.size -1) {

            //The identified mushroom will be placed in 'identifiedMushroom'.
            val identifiedMushroom = mushrooms.first()
            // Verhoog de statistieken en sla deze op in de database
            identifiedMushroom.statistics += 1
            updateStatisticsInDatabase(connection, identifiedMushroom.name, identifiedMushroom.statistics)

            println("The mushroom is identified, it is the ${identifiedMushroom.name}")
            println("Would you like to reset the statistics? 'y'/'n'")
            var answer = readln()
            if(answer == "y"){
                resetStatistics(connection)
            }
            println("Would you like to add a mushroom?")
            answer = readln()
            if(answer == "y"){
                addMushroom(connection)
            }

            println("Would you like to identify a mushroom again ? 'y'/'n'")
            //Read the user input and store it in "answer"
            answer = readln()
            if (answer =="y"){
                println("The application restarts...")
                main()
            } else if(answer =="n"){
                println("The application stops...")
                //stop the app
                return
            }

        } else if (mushrooms.size == 0) {
            println("There are no remaining mushrooms to identify.")
            println("Would you like to add a mushroom? 'y'/'n'")

            var answer = readln()
            if(answer == "y"){
                addMushroom(connection)
            }

            println("Would you like to identify a mushroom again ? 'y'/'n'")
            if (answer =="y"){
                println("The application restarts...")
                main()

            }else if(answer =="n"){
                println("The application stops...")
                //stop the app
                return
            }
        }
    }
}

fun updateStatisticsInDatabase(connection: Connection, mushroomName: String, newStatistic: Int) {
    val updateStatement = connection.createStatement()
    val sql = "UPDATE mushrooms SET statistic = $newStatistic WHERE name = '$mushroomName'"
    println(sql)
    updateStatement.executeUpdate(sql)
    showStatistic(newStatistic, mushroomName)
}

fun showStatistic(statistic: Int, mushroomName: String){
    println("The mushroom: ${mushroomName} was ${statistic} times identified.")
}

fun resetStatistics(connection: Connection){
    val updateStatement = connection.createStatement()
    val sql = "UPDATE mushrooms SET statistic = 0"
    updateStatement.executeUpdate(sql)
    println("Statistics reset to 0 for all mushrooms in the database.")
}

fun addMushroom(connection: Connection){
    val sqlList = mutableListOf<String>()



    println("wil je een habitat toevoegen, of hergebruiken? y/n")
    var useranswer = readln()

    if(useranswer =="y"){
        println("Geef je habitat in")
        useranswer = readln()
        sqlList.add("INSERT INTO `habitats` (`id`, `habitat`) VALUES (NULL, '${useranswer}')")
        val answerList = mutableListOf<String>()

        // Add elements to the list
        println("What is the name of the mushroom?")
        answerList.add(readln())
        println("How big is the mushroom?")
        answerList.add(readln())
        println("What shape is the cap of the mushroom?")
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

    }else if(useranswer =="n"){
        println("Kies uit de gebruikte habitats, .....")
        useranswer = readln()
        var userHabitat = ""
        when (useranswer) {
            "Coniferous forests" -> userHabitat = "1"
            "Mixed forests" -> userHabitat = "2"
            "Grassland" ->userHabitat = "3"
            "Deciduous forests" ->userHabitat = "4"
            else -> false
        }
        val answerList = mutableListOf<String>()

        // Add elements to the list
        println("What is the name of the mushroom?")
        answerList.add(readln())
        println("How big is the mushroom?")
        answerList.add(readln())
        println("What shape is the cap of the mushroom?")
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
            "INSERT INTO `mushrooms` (`id`, `name`, `hat_shape`, `spore_patern`, `odor`, `toxicity_level`, `size`, `stem_shape`, `lamellae_presence`, `habitat_id`, `statistic`) VALUES (NULL, '${answerList[0]}', '${answerList[1]}', '${answerList[2]}', '${answerList[3]}', '${answerList[4]}', '${answerList[5]}', '${answerList[6]}', '${answerList[7]}', '${userHabitat}', '0')"
        sqlList.add(prompt)
    }
    println("What is the growth period?")
    useranswer = readln()
    var userGrowthPeriod = ""
    when (useranswer) {
        "Winter" -> userGrowthPeriod = "1"
        "Spring" -> userGrowthPeriod = "2"
        "Summer" ->userGrowthPeriod = "3"
        "Autumn" ->userGrowthPeriod = "4"
        else -> false
    }
    sqlList.add("INSERT INTO `growth_periods_mushrooms` (`id`, `id_mushroom`, `id_growth_period`) VALUES (NULL, (SELECT MAX(id) FROM mushrooms), '${userGrowthPeriod}')")

    println("Wil je een nieuwe of een bestaande kleur toeveogen? n/b")
    var userAnswer = readln()
    if(userAnswer  == "n"){
        sqlList.add("INSERT INTO `colors` (`id_color`, `color`) VALUES (NULL, '${userAnswer}')")
        sqlList.add("INSERT INTO `color_mushrooms` (`id`, `id_mushroom`, `Id_color`) VALUES (NULL, (SELECT MAX(id) FROM mushrooms), (SELECT MAX(id_color) FROM colors));")
    }else if(userAnswer =="b"){
        println("Choose the color.")
        userAnswer = readln()
        var userColor = ""
        when (userAnswer) {
            "Orange" -> userColor = "1"
            "Red" -> userColor = "2"
            "Brown" -> userColor = "3"
            "White" -> userColor = "4"
            "Yellow" -> userColor = "5"
            else -> false
        }
        sqlList.add("INSERT INTO `color_mushrooms` (`id`, `id_mushroom`, `Id_color`) VALUES (NULL, (SELECT MAX(id) FROM mushrooms), '${userColor}')")
    }

    val updateStatement = connection.createStatement()
    for(element in sqlList){
        updateStatement.executeUpdate(element)
    }
    main()
}
