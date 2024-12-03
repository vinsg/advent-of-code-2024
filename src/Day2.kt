import kotlin.math.abs

object Day2 {

    fun part1(): Int {
        val fileAsList = readFile("day2_part1")

        val reports = fileAsList.map { line -> line.split(" ").map { it.toInt() } }
        println(reports)

        var sum = 0
        reports.forEach { report ->
            if (checkSafety(report.toMutableList())){
                sum += 1
            }
        }

        return sum.also { result ->
            println("result is $result")
        }
    }

    fun part2(): Int {
        val fileAsList = readFile("day2_part2")

        val reports = fileAsList.map { line -> line.split(" ").map { it.toInt() }.toMutableList() }
        println(reports)
        println("numbers of report = ${reports.size}")

        var sum = 0
        reports.forEach { report ->
            var safe: Boolean
            for (i in report.indices) {
                val newList = report.toMutableList()
                newList.removeAt(i)
                safe = checkSafety(newList)
                if (safe) {
                    sum += 1
                    return@forEach
                }
            }

        }
        return sum.also { result ->
            println("result is $result")
        }
    }

    private fun checkSafety(report: MutableList<Int>): Boolean {
        if (report.first() == report.last()) return false

        if (report.first() > report.last()) {
            // all decreasing
            for (i in report.indices) {
                val prev = report[i]
                val next = report.getOrNull(i + 1) ?: return true
                if (
                    prev == next
                    || prev < next
                    || abs(prev - next) < 1
                    || abs(prev - next) > 3
                ) {
                    return false
                }
            }
        } else {
            // all positive
            for (i in report.indices) {
                val prev = report[i]
                val next = report.getOrNull(i + 1) ?: return true
                if (
                    prev == next
                    || prev > next
                    || abs(prev - next) < 1
                    || abs(prev - next) > 3
                ) {
                    return false
                }
            }
        }

        return true
    }
}