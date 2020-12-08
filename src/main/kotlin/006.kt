fun main() {
    val input = object {}.javaClass.getResourceAsStream("006.txt").bufferedReader().readText()

    val groups = input.split(Regex("\n\n"))

    val trimmedGroups = groups.map { it.replace(" ", "").replace("\n", "") }

    val groupedValues = trimmedGroups.map { it.toList().distinct().count() }

    println("The summed amounts are ${groupedValues.sum()}")

    val splitGroups = groups.map { it.trim().split(Regex("\n")) }

    val countOfUniqueCharactersPerGroup = splitGroups.map { group ->
        group.map { person ->
            person.toList()
        }.reduce { first, second ->
            first.intersect(second).toList()
        }
    }.map {
        it.count()
    }.sum()

    println("The sum of answers are $countOfUniqueCharactersPerGroup")
}


