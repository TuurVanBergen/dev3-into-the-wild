import java.util.*
import java.sql.*

val mushrooms : MutableSet<Mushroom> = mutableSetOf()
fun main() {
    println("Starting app..")
    //make the mushrooms array empty
    mushrooms.clear()

    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance()
    // Prepare credentials
    val credentials = Credential()
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
    //MAX() retrieves the highest value.
    val statement = connection.prepareStatement("SELECT m.id, m.name, m.hat_shape, m.spore_patern, m.odor, m.toxicity_level, m.size, m.stem_shape, m.lamellae_presence, m.statistic, GROUP_CONCAT(c.color) AS colors, MAX(gp.growth_period) AS growth_period, MAX(h.habitat) AS habitat FROM mushrooms m JOIN color_mushrooms cm ON m.id = cm.id_mushroom JOIN colors c ON cm.Id_color = c.id_color JOIN growth_periods_mushrooms gpm ON m.id = gpm.id_mushroom JOIN growth_periods gp ON gpm.id_growth_period = gp.id JOIN habitats h ON m.habitat_id = h.id GROUP BY m.id, m.name, m.hat_shape, m.spore_patern, m.odor, m.toxicity_level, m.size, m.stem_shape, m.lamellae_presence, m.habitat_id, m.statistic;")

    // the statement is going to be executed and put into the variable 'result'.
    val result = statement.executeQuery()

    //as long as there is a next one in the array of the variable 'result' this loop is executed.
    while (result.next()){
        //The data from the database is put into the data class 'Mushroom'.
        mushrooms.add(Mushroom(result.getString("name"),result.getString("hat_shape"),result.getString("spore_patern"),result.getString("odor"),result.getString("toxicity_level"),result.getString("size"),result.getString("stem_shape"),result.getString("lamellae_presence"),result.getString("colors"),result.getString("growth_period"),result.getString("habitat"),result.getInt("statistic")))
    }
    //The connection is passed through, so that there can be another connection
    // Create an instance of MushroomIdentifier
    val mushroomIdentifier = MushroomIdentifier(connection, mushrooms)

    // Call the filterResults method on the instance
    mushroomIdentifier.filterResults()
}