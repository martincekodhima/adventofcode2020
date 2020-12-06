fun main(args: Array<String>) {
    val input = readFileToList("003.txt")

    val matrix = input.map { it.toList() }

    val inputArguments = listOf(
        1 to 1,
        3 to 1,
        5 to 1,
        7 to 1,
        1 to 2
    )

    val output = inputArguments.map {
        countTreesRecursively(matrix, 0, 0, it.first, it.second)
    }.fold(1, { x, y -> x * y })

    print(output)
}

tailrec fun countTreesRecursively(matrix: List<List<Char>>, position: Int, treesLanded: Int, right: Int, down: Int): Int {
    if (matrix.isEmpty() || matrix.size < down) return treesLanded

    // Check if we landed on a tree
    val landedPosition = matrix.first()[position]
    val landedOnTree = if (landedPosition == '#') 1 else 0

    // Get a new position respecting the generation
    val newPosition = (position + right) % matrix.first().size

    return countTreesRecursively(matrix.drop(down), newPosition, treesLanded + landedOnTree, right, down)
}
