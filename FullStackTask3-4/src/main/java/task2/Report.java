package task2;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * The main class for creating Report
 */
public class Report {
    private final String pathToSaveReport;
    private final String pathToRecords;

    public Report(String pathToSaveReport, String pathToRecords) {
        this.pathToSaveReport = pathToSaveReport;
        this.pathToRecords = pathToRecords;
    }

    public void createReport() {
        Map<String, Double> map = new HashMap<>();
        File directory = new File(pathToRecords);
        //Collecting XML files from directory
        File[] listOfFiles = directory.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile() && FileType.getFileExtension(file).equals("xml")) {
                StaxParser staxParser = new StaxParser();
                try {
                    map = staxParser.parse(file.getAbsolutePath(), map);
                } catch (FileNotFoundException | XMLStreamException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //Sorting map by amount
        List<Map.Entry<String, Double>> listFinal = map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();

        //Saving 'listFinal' to LinkedHashMap to work with JsonWriter
        Map<String, Double> stringDoubleMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> stringDoubleEntry : listFinal) {
            stringDoubleMap.put(stringDoubleEntry.getKey(), stringDoubleEntry.getValue());
        }
        //Calling JsonWriter to save LinkedHashMap to JSON file
        JsonWriter jsonWriter = new JsonWriter(pathToSaveReport);
        jsonWriter.write(stringDoubleMap);
    }
}
