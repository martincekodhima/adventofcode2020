fun main(args: Array<String>) {
    val input = readFileToList("001.txt")
    val inputAsInteger = input.map { it.toInt() }

    inputAsInteger.forEach { first ->
        inputAsInteger.forEach { second ->
            if (first + second == 2020) {
                println(first * second)
            }
        }
    }

    inputAsInteger.forEach { first ->
        inputAsInteger.forEach { second ->
            inputAsInteger.forEach { third ->
                if (first + second + third == 2020) {
                    println(first * second * third)
                }
            }
        }
    }

    println("Numbers that sum up to 2020 not found.")
}
