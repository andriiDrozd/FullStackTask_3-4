package task1.variant1;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The first variant is not so closed to the format of input XML file but more clean.
 * The method is reading file line by line, changing that line and saving to the output file.
 */
public class CustomParserVar1 {

    //IDEA recommended me to transfer this 3 Class variable to local variable in method 'parse'.
// What is the solution better?
    private final String nameRegex = "\\s{1}\\bname\\s?=\\s?\"(\\W+)\"";
    private final String surnameRegex = "\\s+\\bsurname\\s?=\\s?\"(\\W+)\"|\\bsurname = \"(\\W+)\"";
    private final String replaceReg = "\"(\\W+)\"";

    public File parse(File file) throws IOException {

        File outputFile = new File("src/main/resources/task1/variant1/output.xml");

        String name = null;
        String surname;

        Pattern namePt = Pattern.compile(nameRegex);
        Pattern surnamePt = Pattern.compile(surnameRegex);

        try (FileReader fileReader = new FileReader(file);
             FileWriter fileWriter = new FileWriter(outputFile)) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                //Reading file line by line.
                String row = scanner.nextLine();

                Matcher nameMt = namePt.matcher(row);
                Matcher surnameMt = surnamePt.matcher(row);
                //Saving name and replacing it by empty space.
                if (nameMt.find()) {
                    name = nameMt.group(1);
                    row = row.replaceAll(nameRegex, "");
                }
                //Saving surname and replacing it by 'name+surname'.
                if (surnameMt.find()) {
                    surname = surnameMt.group(1);
                    String newRow = row.replaceFirst(replaceReg, "\"" + name + " " + surname + "\"");
                    row = newRow.replaceAll("surname", "name");
                }
                //Saving changed line to the Output File.
                if (!row.isBlank()) {
                    fileWriter.write(row + "\n");
                }
            }
        }
        return outputFile;
    }
}
