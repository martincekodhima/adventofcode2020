fun main(args: Array<String>) {
    val input = readFileToList("002.txt")
    val expectedToPassword = input.map {
        val validationsWithPassword = it.split(":")
        validationsWithPassword[0] to validationsWithPassword[1]
    }
    var validPasswordsFirstPart = 0
    var validPasswordsSecondPart = 0

    expectedToPassword.forEach {
        if (validatePasswordFirstPart(it.first, it.second.trim())) validPasswordsFirstPart++
        if (validatePasswordSecondPart(it.first, it.second.trim())) validPasswordsSecondPart++
    }

    println("# of valid passwords according to part one: $validPasswordsFirstPart")
    println("# of valid passwords according to part two: $validPasswordsSecondPart")

}

private fun validatePasswordFirstPart(expectation: String, password: String): Boolean {
    val passwordRule = parsePasswordRule(expectation)
    val leastNumberOfTimesLetterCanAppear = passwordRule.firstPosition
    val mostNumberOfTimesLetterCanAppear = passwordRule.secondPosition

    val numberOfTimesLetterAppears = password.count {  it == passwordRule.character }

    return numberOfTimesLetterAppears in leastNumberOfTimesLetterCanAppear..mostNumberOfTimesLetterCanAppear
}

private fun validatePasswordSecondPart(expectation: String, password: String): Boolean {
    val passwordRule = parsePasswordRule(expectation)
    val positionOneWhereLetterMustBeFound = passwordRule.firstPosition-1
    val positionTwoWhereLetterMustBeFound = passwordRule.secondPosition-1
    val letterThatMustBeFound = passwordRule.character

    val passwordChars = password.toCharArray()

    return (passwordChars[positionOneWhereLetterMustBeFound] == letterThatMustBeFound).xor(
        passwordChars[positionTwoWhereLetterMustBeFound] == letterThatMustBeFound
    )
}

private fun parsePasswordRule(expectation: String): PasswordRule {
    val parsedExpectation = expectation.split(" ")
    val indexes = parseIndexes(parsedExpectation.first())
    val letterExpected = parsedExpectation.last().first()

    return PasswordRule(letterExpected, indexes.first, indexes.second)
}

private fun parseIndexes(expectation: String): Pair<Int, Int> {
    val splitNumberOfTimes = expectation.split("-")
    return splitNumberOfTimes.first().toInt() to splitNumberOfTimes.last().toInt()
}

private data class PasswordRule(
    val character: Char,
    val firstPosition: Int,
    val secondPosition: Int
)
