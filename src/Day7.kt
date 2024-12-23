object Day7 {

    // recursive solution
    fun part1(): Long {
        val fileAsList = readFile("day7").also { println("input: $it") }

        val sum = fileAsList.fold(0L) { acc, s ->
            val testValue = s.substringBefore(":").toLong().also { println("testValue: $it") }
            val numbers = getNumbers(s)

            val calibratedNums = getCalibratedResults(numbers)

            println("calibrated list: $calibratedNums")

            acc + if (calibratedNums.contains(testValue)) testValue else 0
        }

        println("valid total is $sum")
        return sum
    }

    private fun getNumbers(s: String): List<Long> =
        s.substringAfter(":").trim().split(" ").map { it.toLong() }.also { println("numbers: $it") }

    private fun getCalibratedResults(
        numbers: List<Long>, index: Int = 0, currentResult: Long = 0, withConcat: Boolean = false
    ): List<Long> {
        if (index == numbers.size) {
            return listOf(currentResult)
        }
        val sumBranch = getCalibratedResults(numbers, index + 1, currentResult + numbers[index], withConcat)

        val mulBranch = getCalibratedResults(numbers, index + 1, currentResult * numbers[index], withConcat)

        val concatBranch = if (withConcat && currentResult != 0L) {
            val concat = "${currentResult}${numbers[index]}".toLong()
            getCalibratedResults(numbers, index + 1, concat, true)
        } else {
            emptyList()
        }

        return sumBranch + mulBranch + concatBranch
    }

    fun part2(): Long {
        val fileAsList = readFile("day7").also { println("input: $it") }
        val sum = fileAsList.fold(0L) { acc, s ->
            val testValue = s.substringBefore(":").toLong()
            val numbers = getNumbers(s)

            val calibratedResults = getCalibratedResults(numbers = numbers, withConcat = true)

            println("calibrated list: $calibratedResults")

            acc + if (calibratedResults.contains(testValue)) testValue else 0
        }

        println("valid total is $sum")
        return sum
    }
}