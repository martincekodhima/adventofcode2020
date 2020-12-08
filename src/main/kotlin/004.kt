fun main(args: Array<String>) {
    val input = readFileToList("004.txt")

    val passports = parsePassports(input, emptyList(), emptyList())

    val validPassports = passports.filter { it.isValidPassport() }.count()

    print("The number of valid passports are: $validPassports")

}

private tailrec fun parsePassports(input: List<String>, passports: List<Passport>, currentFields: List<Pair<String, String>>): List<Passport> {
    if (input.isEmpty()) return passports + Passport(currentFields)

    val currentEntry = input.first()

    // If current entry is empty then we finished a passport
    val newPassports = if (currentEntry.isEmpty()) passports + Passport(currentFields) else passports

    val newCurrentFields = if (currentEntry.isEmpty()) emptyList() else currentFields + parseValues(currentEntry)

    return parsePassports(input.drop(1), newPassports, newCurrentFields)
}

private fun parseValues(currentEntry: String): List<Pair<String, String>> {
    val entries = currentEntry.split(" ")
    return entries.map {
        val entry = it.split(":")
        entry.first() to entry.last()
    }
}

private data class Passport(
    val fields: List<Pair<String, String>>
) {
    private val requiredPassportFields =
        listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    fun isValidPassport(): Boolean {
        requiredPassportFields.map { requiredField ->
            val fieldsList = fields.map { it.first }

            // Check if the passport contains the required field
            if (!fieldsList.contains(requiredField)) return false

            // Check if the value of the field is valid
            if (!validValue(requiredField)) return false
        }
        return true
    }

    private fun validValue(requiredField: String): Boolean {
        // We know all fields are present at this point
        val valueToCheck = fields.find { it.first == requiredField }!!.second

        return when (requiredField) {
            "byr" -> valueToCheck.toInt() in 1920..2002
            "iyr" -> valueToCheck.toInt() in 2010..2020
            "eyr" -> valueToCheck.toInt() in 2020..2030
            "hgt" -> checkHeight(valueToCheck)
            "hcl" -> valueToCheck.matches(Regex("#([0-9a-f]{6})"))
            "ecl" -> listOf("amb", "blu", "brn", "gry", "grn", "hzl","oth").contains(valueToCheck)
            "pid" -> valueToCheck.count() == 9 || valueToCheck.matches(Regex("^\\d{9}\$"))
            else -> false
        }
    }

    private fun checkHeight(valueToCheck: String): Boolean {
        val unit = valueToCheck.substring(valueToCheck.lastIndex-1..valueToCheck.lastIndex)
        val value = valueToCheck.substring(0 until valueToCheck.lastIndex-1)

        return when (unit) {
            "cm" -> value.toInt() in 150..193
            "in" -> value.toInt() in 59..76
            else -> false
        }
    }
}
