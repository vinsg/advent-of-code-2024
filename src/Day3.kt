object Day3 {

    private val mulRegex = """mul\((?<first>\d+),(?<second>\d+)\)""".toRegex()

    fun part1(): Long {
        val fileAsList = readFile("day3").also { println("input: $it") }

        val sum = fileAsList.sumOf { s ->
            mulRegex.findAll(s).sumOf { r ->
                val (first, second) =  r.destructured
                first.toLong() * second.toLong()
            }
        }
        return sum.also { println("result is $it") }
    }

    fun part2(): Long {
        val fileAsList = readFile("day3").also { println("input: $it") }

        // parse input
        val regex = "(mul)\\(\\d+,\\d+\\)|(do\\(\\))|(don't\\(\\))".toRegex()
        val l = fileAsList.map { s ->
            regex.findAll(s).map { matchResult -> matchResult.value }.toList()
        }

        // process list
        var summing = true
        val sum = l.sumOf { line ->
            line.fold(0L) { acc, v ->
                when (v) {
                    "do()" -> {
                        summing = true
                        acc
                    }
                    "don't()" -> {
                        summing = false
                        acc
                    }

                    else -> {
                        if (summing){
                            acc + getMul(v)
                        } else acc
                    }
                }
            }
        }
        return sum.also { println("result is $it") }
    }

    private fun getMul(s: String): Long {
        return mulRegex.findAll(s).sumOf { pair ->
            val (first, second) = pair.destructured
            first.toLong() * second.toLong()
        }
    }
}