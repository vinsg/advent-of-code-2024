import kotlin.math.abs



object Day1 {

    /**
     * Sum the distance of the min num of 2 lists.
     *
     */
    fun part1(): Int {
        val fileAsList = readFile("day1_part1")
        val lfd = fileAsList
            .map { line -> line.split(" ").first().toInt() }
            .sorted()
        val lld = fileAsList
            .map { line -> line.split(" ").last().toInt() }
            .sorted()
        require(lfd.size == lld.size) { "both lists should be the same size" }

        return lfd.zip(lld) { a, b -> abs(a - b) }
            .sum()
            .also { result -> println("the result is $result") }
    }

    /**
     * Sum all the occurrences of all the numbers of the first list in the second list.
     *
     */
    fun part2(): Int {
        val file = readFile("day1_part2")

        val lfd = file
            .map { line -> line.split(" ").first().toInt() }
        val occurrences = file
            .map { line -> line.split(" ").last().toInt() }
            .groupingBy { it }
            .eachCount()

        return lfd.fold(0) { acc, num ->
            acc + (num * occurrences.getOrDefault(num, 0))
        }.also { result -> println("the result is $result") }
    }

}