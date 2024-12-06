object Day5 {

    fun part1(): Long {
        val fileAsList = readFile("day5").also { println("input: $it") }

        val rules = fileAsList.subList(0, fileAsList.indexOf(""))
        val updates = fileAsList.subList(fileAsList.indexOf("") + 1, fileAsList.size).map { it.split(",") }

        val rulesGroup = rules.groupBy { it.substringBefore("|") }


        val sum = updates.fold(0L) { acc, update ->
            acc + checkUpdate(update, rulesGroup)
        }
        println("sum: $sum")
        return sum
    }

    private fun checkUpdate(update: List<String>, rulesGroup:  Map<String, List<String>>): Long {
        update.forEachIndexed { i, n ->
            // check update
            rulesGroup[n]?.forEach { rule ->
                val second = rule.substringAfter("|")
                if (
                    update.contains(second)
                    && !update.subList(i, update.size).contains(second)
                ) {
                    println("bad: ${rule}")
                    return 0L
                }
            }
        }
        val good = update[update.size / 2]
        println("good: $good, rule: $update")
        return good.toLong()
    }

    // 6515 > too big
    fun part2(): Long {
        val fileAsList = readFile("day5").also { println("input: $it") }

        val rules = fileAsList.subList(0, fileAsList.indexOf(""))
        val updates = fileAsList.subList(fileAsList.indexOf("") + 1, fileAsList.size).map { it.split(",") }

        val rulesGroup = rules.groupBy { it.substringBefore("|") }


        val sum = updates.fold(0L) { acc, update ->
            acc + checkUpdateAndFix(update, rulesGroup)
        }
        println("sum: $sum")
        return sum
    }

    private fun checkUpdateAndFix(update: List<String>, rulesGroup:  Map<String, List<String>>): Long {
        println("----------------")
        println("for update: $update")

        var pick = false
        val result = mutableListOf<String>()
        for (item in update) {
            println("add $item")
            // add item
            result.add(item)
            println("result: $result")

            // iterate until fix
            var fixed = false
            while (!fixed) {
                fixed = true
                val nn = result.toList()
                println("nn: $nn")
                nn.forEachIndexed { i, n ->
                    rulesGroup[n]?.forEach { rule ->
                        val second = rule.substringAfter("|")
                        if (
                            nn.contains(second)
                            && !nn.subList(i, nn.size).contains(second)
                        ) {
                            println("wrong rule: $rule")
                            val wrongNumIndex = result.indexOf(second)
                            result.removeAt(wrongNumIndex)
                            result.add(second)
                            println("replace $second at $wrongNumIndex")
                            pick = true
                            println("result: $result")
                            fixed = false
                        }
                    }
                }
            }
        }
        if (pick) {
            val middle = result[result.size / 2]
            println("fixed rule: $result, middle: $middle")
            return middle.toLong()
        } else {
            return 0L
        }
    }
}