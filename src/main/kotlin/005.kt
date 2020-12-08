const val ROWS = 127
const val COLUMNS = 7

fun main(args: Array<String>) {
    val input = readFileToList("005.txt")

    val seatPositions = getLocationOfSeats(input)
    val seatIds = seatPositions.map { it.getSeatId() }

    println("Maximum seat id found: ${seatIds.maxOrNull()}")

    val mySeat = findMySeat(seatPositions)
    println("My seat is: $mySeat")
}

fun findMySeat(seatPositions: List<SeatPosition>): Int {
    val rows = 0..ROWS
    val columns = 0..COLUMNS

    val matrix = rows.flatMap { row ->
        columns.map { column ->
            SeatPosition(row = row, column = column)
        }
    }

    val missingSeats = matrix.minus(seatPositions)
    val missingSeatIds = missingSeats.map { it.getSeatId() }

    val seatIds = seatPositions.map { it.getSeatId() }

    return missingSeatIds.single { seatIds.contains(it+1) && seatIds.contains(it-1) }
}

fun getLocationOfSeats(input: List<String>): List<SeatPosition> =
    input.map {
        val frontBackInstructions = it.substring(0..6).toList()
        val leftRightInstructions = it.substring(7..it.lastIndex).toList()

        val rowLocation = findLocation(frontBackInstructions, 0, ROWS, 'B', 'F')
        val columnLocation = findLocation(leftRightInstructions, 0, COLUMNS, 'R', 'L')

        SeatPosition(rowLocation, columnLocation)
    }

tailrec fun findLocation(frontBackInstructions: List<Char>, minRow: Int, maxRow: Int, upperChar: Char, lowerChar: Char): Int {
    val instruction = frontBackInstructions.first()

    if (frontBackInstructions.size == 1) {
        return when (instruction) {
            upperChar -> maxRow
            lowerChar -> minRow
            else -> throw IllegalArgumentException("Instruction: $instruction not expected exception")
        }
    }

    val halfwayPoint = (maxRow - minRow) / 2

    val newMaxRow = when (instruction) {
        upperChar -> maxRow
        lowerChar -> minRow + halfwayPoint
        else -> throw IllegalArgumentException("Instruction: $instruction not expected exception")
    }

    val newMinRow = when (instruction) {
        upperChar -> maxRow - halfwayPoint
        lowerChar -> minRow
        else -> throw IllegalArgumentException("Instruction: $instruction not expected exception")
    }

    return findLocation(frontBackInstructions.drop(1), newMinRow, newMaxRow, upperChar, lowerChar)
}

data class SeatPosition(
    val row: Int,
    val column: Int
) {
    fun getSeatId() =
        (row * 8) + column
}


