package edu.gatech.seclass.capitalize;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/*
DO NOT ALTER THIS CLASS.  Use it as an example for MyMainTest.java
 */

public class MainTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    // Some utilities

    private File createTmpFile() throws IOException {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }


    private File createInputFile1() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Billy,\n" +
                "I am going to take cs6300 and cs6400 next semester.\n" +
                "Did you take cs 6300 last semester? I want to\n" +
                "take 2 courses so that I will graduate Asap!");

        fileWriter.close();
        return file1;
    }

    private File createInputFile2() throws Exception {
        File file2 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file2);

        fileWriter.write("aaaaaa");

        fileWriter.close();
        return file2;
    }



    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    // test cases

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 1 from assignment directions
    @Test
    public void mainTest1() throws Exception {
        File inputFile1 = createInputFile1();

        String args[] = {inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Billy,\n" +
                "I am going to take cs6300 and cs6400 next semester.\n" +
                "Did you take cs 6300 last semester? I want to\n" +
                "Take 2 courses so that I will graduate Asap!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 2 from assignment directions
    @Test
    public void mainTest2() throws Exception {
        File inputFile1 = createInputFile1();

        String args[] = {"-w", inputFile1.getPath()};
        Main.main(args);

        String expected2 = "Howdy Billy,\n" +
                "I Am Going To Take Cs6300 And Cs6400 Next Semester.\n" +
                "Did You Take Cs 6300 Last Semester? I Want To\n" +
                "Take 2 Courses So That I Will Graduate Asap!";

        String actual2 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected2, actual2);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 3 from assignment directions
    @Test
    public void mainTest3() throws Exception {
        File inputFile1 = createInputFile1();

        String args[] = {"-m", "CS6300", inputFile1.getPath()};
        Main.main(args);

        String expected3 = "Howdy Billy,\n" +
                "I am going to take CS6300 and cs6400 next semester.\n" +
                "Did you take cs 6300 last semester? I want to\n" +
                "take 2 courses so that I will graduate Asap!";

        String actual3 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected3, actual3);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 4 from assignment directions
    @Test
    public void mainTest4() throws Exception {
        File inputFile2 = createInputFile2();

        String args[] = {"-m", "AAAA", inputFile2.getPath()};
        Main.main(args);

        String expected4 = "AAAAaa";

        String actual4 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected4, actual4);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 5 from assignment directions
    @Test
    public void mainTest5() throws Exception {
        File inputFile1 = createInputFile1();

        String args[] = {"-m", "ASAP", "-f", inputFile1.getPath()};
        Main.main(args);

        String expected5 = "hOWDY bILLY,\n" +
                "i AM GOING TO TAKE CS6300 AND CS6400 NEXT SEMESTER.\n" +
                "dID YOU TAKE CS 6300 LAST SEMESTER? i WANT TO\n" +
                "TAKE 2 COURSES SO THAT i WILL GRADUATE asap!";

        String actual5 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected5, actual5);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 6 from assignment directions
    @Test
    public void mainTest6() throws Exception {
        File inputFile1 = createInputFile1();

        String args[] = {"-w", "abc", inputFile1.getPath()};
        Main.main(args);

        String expected6 = "Howdy Billy,\n" +
        "I aM going to taKe cS6300 aNd cS6400 next semester.\n" +
                "Did you taKe cS 6300 laSt semester? I waNt to\n" +
                "taKe 2 cOurses so thaT I will graDuaTe AsaP!";

        String actual6 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected6, actual6);
    }


    // Purpose: To provide an example of a test case format
    // Frame #: Instructor error example
    @Test
    public void mainTest7() {
        String args[] = null; //invalid argument
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] <filename>", errStream.toString().trim());
    }

}