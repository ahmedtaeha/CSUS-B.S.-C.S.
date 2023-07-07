/**
 * This program uses a CSV grammar from the ANTLR grammar repository
 * (https://github.com/antlr/grammars-v4/blob/master/csv/CSV.g4)
 * The grammar allows us to parse CSV files with rows that can have variable numbers of fields.
 * Each field can be either TEXT (any characters except for commas, newline, and double quotes)
 * or a STRING (characters enclosed by double quotes).
 *
 * We generate the JAVA Files for the Parser and Lexer from CSV.g4 file (included here) using
 * the following antlr4 command:
 * antlr4 CSV.g4 -Dlanguage=Java
 *
 * The program reads two input files: "input1.txt" and "input2.txt". The first input is a valid
 * CSV file that follows the CSV grammar, while the second input is an invalid CSV file.
 * In the second input, there is a field that contains a non-escaped double quote, which
 * is not allowed by the grammar. Therefore, the second input should be rejected by the parser.
 *
 * To compile this program, the ANTLR 4 runtime library must be included in the classpath.
 * The command to compile is:
 * javac -cp .:/usr/local/lib/antlr-4.13.0-complete.jar Recognizer.java
 *
 * To run this program, the ANTLR 4 runtime library must also be included in the classpath.
 * The command to run is:
 * java -cp .:/usr/local/lib/antlr-4.13.0-complete.jar Recognizer
 */
import org.antlr.v4.runtime.*;

public class Recognizer {
    public static void main(String[] args) throws Exception {
        String[] files = {"input1.txt", "input2.txt"};
        for (String file : files) {
            CharStream input = CharStreams.fromFileName(file);
            CSVLexer lexer = new CSVLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CSVParser parser = new CSVParser(tokens);
            parser.csvFile();
            if (parser.getNumberOfSyntaxErrors() == 0) {
                System.out.println(file + " is a valid CSV file");
            } else {
                System.out.println(file + " is not a valid CSV file");
            }
        }
    }
}
