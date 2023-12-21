class showStatistics(private val statistic: Int, private val mushroomName: String) {
    fun showStatistic(){
        //show the statistics
        println("The mushroom: ${mushroomName} was ${statistic} times identified.")
    }
}


