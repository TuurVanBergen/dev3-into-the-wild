import java.sql.Connection

class MushroomAdder(private val connection: Connection) {
        //this function will add a mushroom to the database
        fun addMushroom(){
            //This list is going to save al the sql.
            val sqlList = mutableListOf<String>()

            //default value of validInput is false
            var validInput = false

            //ask a question
            println("What is the habitat? You can choose between: Coniferous forests, Mixed forests, Grassland, Deciduous forests")

            //As long as validinput is false, then this loop wil keep executing
            while(!validInput){
                var answer = readln()
                when(answer){
                    "Coniferous forests"->{
                        sqlList.add("INSERT INTO `habitats` (`id`, `habitat`) VALUES (NULL, '${answer}')")
                        validInput = true
                    }
                    "Mixed forests"->{
                        sqlList.add("INSERT INTO `habitats` (`id`, `habitat`) VALUES (NULL, '${answer}')")
                        validInput = true
                    }
                    "Grassland"->{
                        sqlList.add("INSERT INTO `habitats` (`id`, `habitat`) VALUES (NULL, '${answer}')")
                        validInput = true
                    }
                    "Deciduous forests"->{
                        sqlList.add("INSERT INTO `habitats` (`id`, `habitat`) VALUES (NULL, '${answer}')")
                        validInput = true
                    }
                    else->{
                    println("Invalid input.")
                }
                }
            }

            val answerList = mutableListOf<String>()
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

            //add the data into an sql query.
            val prompt =
                "INSERT INTO `mushrooms` (`id`, `name`, `hat_shape`, `spore_patern`, `odor`, `toxicity_level`, `size`, `stem_shape`, `lamellae_presence`, `habitat_id`, `statistic`) VALUES (NULL, '${answerList[0]}', '${answerList[1]}', '${answerList[2]}', '${answerList[3]}', '${answerList[4]}', '${answerList[5]}', '${answerList[6]}', '${answerList[7]}', (SELECT MAX(id) FROM habitats), '0')"

            //put the query into the sqlList
            sqlList.add(prompt)

            //ask a question
            println("What is the growth period? Winter, Spring, Summer, Autumn")
            validInput = false
            var userGrowthPeriod = ""

            //As long as validinput is false, then this loop wil keep executing
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
            //add the sql to the list.
            sqlList.add("INSERT INTO `growth_periods_mushrooms` (`id`, `id_mushroom`, `id_growth_period`) VALUES (NULL, (SELECT MAX(id) FROM mushrooms), '${userGrowthPeriod}')")

            validInput = false
            //ask a question

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

            println("The data will be added")
            val updateStatement = connection.createStatement()
            for(element in sqlList){
                updateStatement.executeUpdate(element)
            }
            println("The mushroom was added.")
            main()
        }

 }