package task1;

import task1.variant1.CustomParserVar1;
import task1.variant2.CustomParserVar2;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        File file=new File("src/main/resources/task1/input.xml");

        CustomParserVar1 customParserVar1 =new CustomParserVar1();
        File file1=customParserVar1.parse(file);

        CustomParserVar2 customParserVar2 =new CustomParserVar2();
        File file2= customParserVar2.parse(file);





    }
}
