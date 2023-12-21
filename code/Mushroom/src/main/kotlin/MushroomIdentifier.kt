import java.sql.Connection
class MushroomIdentifier(private val connection: Connection, private val mushrooms: MutableSet<Mushroom>) {

     fun filterResults() {
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

                val statisticsUpdater = UpdateStatistics(connection, identifiedMushroom.name, identifiedMushroom.statistics)

                // Call the updateStatisticsInDatabase method on the instance
                statisticsUpdater.updateStatisticsInDatabase()
                //updateStatisticsInDatabase(connection, identifiedMushroom.name, identifiedMushroom.statistics)

                println("The mushroom is identified, it is the ${identifiedMushroom.name}")

                //Give the user the option to reset the statistics
                println("Would you like to reset the statistics? 'y'/'n'")
                var validInput = false;
                while(!validInput){
                    var answer = readln()
                    when(answer){
                        "y"->{
                            val resetStatistics = resetStatistics(connection)
                            resetStatistics.resetStatistic()
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
                            // Call the addMushroom method from MushroomAdder
                            val mushroomAdder = MushroomAdder(connection)
                            mushroomAdder.addMushroom()
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
                            // Call the addMushroom method from MushroomAdder
                            val mushroomAdder = MushroomAdder(connection)
                            mushroomAdder.addMushroom()
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
}