import java.io.File

/**
 * Read a file from resources
 * @return a list of lines
 */
fun readFile(fileName: String): List<String> {
    val resource = {}.javaClass.getResource("/${fileName}.txt")
        ?: throw IllegalArgumentException("File $fileName not found in resources")
    return File(resource.toURI()).readLines()
}