import java.sql.Connection
class UpdateStatistics(private val connection: Connection,private val mushroomName: String, private val newStatistic: Int) {
    fun updateStatisticsInDatabase() {
        //On this website I found the createStatement() and executeUpdate().
        //https://www.enterprisedb.com/docs/jdbc_connector/latest/06_executing_sql_commands_with_executeUpdate()/#:~:text=createStatement())%20%7B-,The%20executeUpdate()%20method%20returns%20the%20number%20of%20rows%20affected,int%20rowcount%20%3D%20stmt.
        val updateStatement = connection.createStatement()
        val sql = "UPDATE mushrooms SET statistic = $newStatistic WHERE name = '$mushroomName'"
        println(sql)
        updateStatement.executeUpdate(sql)
        val showStatistics = showStatistics(newStatistic, mushroomName)
        showStatistics.showStatistic()
    }
}