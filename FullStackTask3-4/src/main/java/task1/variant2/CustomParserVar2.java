package task1.variant2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The second variant.
 * The method 'parse' is reading file line by line and saving it to the List until found '>',
 * changing the List of String and saving to the output file, clean the List and continue reading input file.
 */
public class CustomParserVar2 {
    private final String nameRegex = "\\bname\\s?=\\s?\"(\\W+)\"";
    private final String surnameRegex = "\ssurname\\s?=\\s?\"(\\W+)\"|\\bsurname = \"(\\W+)\"";
    private final List<String> input = new ArrayList<>();

    public File parse(File file) throws IOException {

        File outputFile = new File("src/main/resources/task1/variant2/output.xml");

        Pattern namePt = Pattern.compile(nameRegex);
        Pattern surnamePt = Pattern.compile(surnameRegex);

        String name = null;
        String surname = null;


        try (FileReader fileReader = new FileReader(file); FileWriter fileWriter = new FileWriter(outputFile)) {
            Scanner scanner = new Scanner(fileReader);
            //Reading file
            while (scanner.hasNext()) {
                String row = scanner.nextLine();
                input.add(row);
                //After finding '>' start reading list of previous lines
                if (row.contains(">")) {
                    //Extracting name and surname, deleting surname
                    for (int i = 0; i < input.size(); i++) {
                        String x = input.get(i);
                        Matcher nameMt = namePt.matcher(x);
                        Matcher surnameMt = surnamePt.matcher(x);

                        if (nameMt.find()) {
                            name = nameMt.group(1);
                        }

                        if (surnameMt.find()) {
                            surname = surnameMt.group(1);
                            x = x.replaceAll(surnameRegex, "");
                            input.remove(i);
                            input.add(i, x);
                        }
                    }
                    //Reading list and replacing name => name+surname
                    for (int k = 0; k < input.size(); k++) {
                        String x2 = input.get(k);
                        Matcher nameMt = namePt.matcher(x2);

                        if (nameMt.find()) {
                            String replaceReg = "\"(\\W+)\"";
                            x2 = x2.replaceAll(replaceReg, "\"" + name + " " + surname + "\"");
                            input.remove(k);
                            input.add(k, x2);
                        }

                        fileWriter.write(x2 + "\n");
                    }
                    //After clearing the list continue reading file
                    input.clear();
                }
            }
        }
        return outputFile;
    }
}
