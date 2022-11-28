package task2;

/**
 * Main Clas to show that programme is working.
 */
public class Main {
    public static void main(String[] args)  {
        String pathToRecords="src/main/resources/task2";
        String pathToSaveReport="src/main/resources/task2/report.json";
        Report report = new Report(pathToSaveReport,pathToRecords);
        report.createReport();

    }
}