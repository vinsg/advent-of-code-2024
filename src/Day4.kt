object Day4 {

    private const val XMAS = "XMAS"


    /**
     * Letters can match multiple times
     */
    fun part1(): Long {
        val fileAsList = readFile("day4").also { println("input: $it") }

        var sum = 0L

        fileAsList.forEachIndexed line@{ i, line ->
            line.forEachIndexed char@{ j, char ->
                if (char != 'X') {
                    return@char
                }

                // check if every any words around the char match XMAS
                // right
                sum += checkXMAS(
                    char, line.getOrNull(j + 1), line.getOrNull(j + 2), line.getOrNull(j + 3)
                )

                // bottom right
                sum += checkXMAS(
                    char,
                    fileAsList.getOrNull(i + 1)?.getOrNull(j + 1),
                    fileAsList.getOrNull(i + 2)?.getOrNull(j + 2),
                    fileAsList.getOrNull(i + 3)?.getOrNull(j + 3)
                )

                // bottom
                sum += checkXMAS(
                    char,
                    fileAsList.getOrNull(i + 1)?.getOrNull(j),
                    fileAsList.getOrNull(i + 2)?.getOrNull(j),
                    fileAsList.getOrNull(i + 3)?.getOrNull(j),
                )

                // bottom left
                sum += checkXMAS(
                    char,
                    fileAsList.getOrNull(i + 1)?.getOrNull(j - 1),
                    fileAsList.getOrNull(i + 2)?.getOrNull(j - 2),
                    fileAsList.getOrNull(i + 3)?.getOrNull(j - 3),
                )

                // left
                sum += checkXMAS(
                    char,
                    line.getOrNull(j - 1),
                    line.getOrNull(j - 2),
                    line.getOrNull(j - 3),
                )

                // top left
                sum += checkXMAS(
                    char,
                    fileAsList.getOrNull(i - 1)?.getOrNull(j - 1),
                    fileAsList.getOrNull(i - 2)?.getOrNull(j - 2),
                    fileAsList.getOrNull(i - 3)?.getOrNull(j - 3),
                )

                // top
                sum += checkXMAS(
                    char,
                    fileAsList.getOrNull(i - 1)?.getOrNull(j),
                    fileAsList.getOrNull(i - 2)?.getOrNull(j),
                    fileAsList.getOrNull(i - 3)?.getOrNull(j),
                )

                // top right
                sum += checkXMAS(
                    char,
                    fileAsList.getOrNull(i - 1)?.getOrNull(j + 1),
                    fileAsList.getOrNull(i - 2)?.getOrNull(j + 2),
                    fileAsList.getOrNull(i - 3)?.getOrNull(j + 3),
                )
            }
        }
        return sum.also { result -> println("sum is $result") }
    }

    /**
     * Return 1 if the list contains XMAS
     */
    private fun checkXMAS(vararg chars: Char?) = if (chars.filterNotNull().joinToString("").contains(XMAS)) 1 else 0

    fun part2(): Long {
        val fileAsList = readFile("day4").also { println("input: $it") }
        println(fileAsList)

        var sum = 0L
        fileAsList.forEachIndexed line@{ i, line ->
            line.forEachIndexed char@{ j, char ->
                if (char != 'M' && char != 'S') return@char
                sum += checkMAS(
                    "$i, $j",/*
                     |x * *|
                     |* x *|
                     |* * x|
                     */
                    listOf(
                        char,
                        fileAsList.getOrNull(i + 1)?.getOrNull(j + 1),
                        fileAsList.getOrNull(i + 2)?.getOrNull(j + 2),
                    ),/*
                     |* * x|
                     |* x *|
                     |x * *|
                     */
                    listOf(
                        line.getOrNull(j + 2),
                        fileAsList.getOrNull(i + 1)?.getOrNull(j + 1),
                        fileAsList.getOrNull(i + 2)?.getOrNull(j),
                    )
                )
            }
        }
        return sum.also { result -> println("sum is $result") }
    }

    private fun checkMAS(index: String, firstCross: List<Char?>, secondCross: List<Char?>): Int {
        val firstString = firstCross.filterNotNull().joinToString("")
        val secondString = secondCross.filterNotNull().joinToString("")

        val isContainedInFirstString = firstString.contains("MAS") || firstString.contains("SAM")
        val isContainedInSecondString = secondString.contains("MAS") || secondString.contains("SAM")
        return if (isContainedInFirstString && isContainedInSecondString) {
            println("cross found at $index: $firstCross, $secondCross")
            1
        } else {
            0
        }
    }
}