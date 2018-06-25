package edu.gatech.seclass.capitalize;

import static org.junit.Assert.*;

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

public class MyMainTest {

/*
Place all of your tests in this class, optionally using MainTest.java as an example.
*/

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

    // create empty file
    private File createInputFile1() throws Exception {
        File file1 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("");

        fileWriter.close();
        return file1;
    }

    // create a file with one single line.
    private File createInputFile2() throws Exception {
        File file2 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file2);

        fileWriter.write("This dinner is so delicious I can't stop eating.\n" +
                            "I can't Neither.");

        fileWriter.close();
        return file2;
    }

    // create a file with multi-line text.
    private File createInputFile3() throws Exception {
        File file3 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file3);

        fileWriter.write("My mom drove me to school fifteen minutes late on Tuesday.\n" +
                            "The girl wore her hair in two braids,\n" +
                            "tied with two blue bows.\n" +
                            "The tape got stuck on my lips so I couldn't talk anymore.");

        fileWriter.close();
        return file3;
    }

    // create a file with one single line with special combination "Haaa".
    private File createInputFile4() throws Exception {
        File file4 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file4);

        fileWriter.write("This dinner, Haaaa, is so delicious I can't stop eating, Haaaa.");

        fileWriter.close();
        return file4;
    }

    // create a file with very short content.
    private File createInputFile5() throws Exception {
        File file5 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file5);

        fileWriter.write("Hey");

        fileWriter.close();
        return file5;
    }

    // create a file with multiple lines with special combination "Happy".
    private File createInputFile6() throws Exception {
        File file6 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file6);

        fileWriter.write("This dinner, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I can't stop eating, Happy!");

        fileWriter.close();
        return file6;
    }

    // create a file with non-alphabets.
    private File createInputFile7() throws Exception {
        File file7 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file7);

        fileWriter.write("How do you think about cS6460?\n" +
                "I prefer cs6300 and Cs6400.");

        fileWriter.close();
        return file7;
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

    // Purpose: test under file name missing circumstance
    // Frame 2: Presence of a file corresponding to the name :  Not present
    @Test
    public void capitalizeTest1() throws Exception {
        File inputFile1 = createInputFile1();
        String args[] = {"-f"}; // the file is not present
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] <filename>", errStream.toString().trim());
    }

    // Purpose: test OPT input
    // Frame 4: Presence of characters in OPT flags :  Incorrect
    @Test
    public void capitalizeTest2() throws Exception {
        File inputFile2 = createInputFile1();
        String args[] = {"-c", inputFile2.getPath()}; // -c is not a correct OPT
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] <filename>", errStream.toString().trim());
    }

    // Purpose: test -m under special circumstance
    // Frame 14: Length of the string :  Longer than the file
    @Test
    public void capitalizeTest3() throws Exception {
        File inputFile3 = createInputFile5();
        String args[] = {"-m", "Heyyyy", inputFile3.getPath()}; // Heyyyy is longer than Hey in file
        Main.main(args);

        String expected3 = "Hey";

        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files are the same!", expected3, actual3);
    }

    // Purpose: test -m under special circumstance
    // Frame 15: Number of occurrences of the String in the file :  None
    @Test
    public void capitalizeTest4() throws Exception {
        File inputFile4 = createInputFile2();
        String args[] = {"-m", "Hello", inputFile4.getPath()}; // No occurrence of the "Hello" in file.
        Main.main(args);

        String expected4 = "This dinner is so delicious I can't stop eating.\n" +
                "I can't Neither.";

        String actual4 = getFileContent(inputFile4.getPath());

        assertEquals("The files are the same!", expected4, actual4);
    }

    // Purpose: test -m under special circumstance
    // Frame 19: Position of the String in the file :  First line
    @Test
    public void capitalizeTest5() throws Exception {
        File inputFile5 = createInputFile3();
        String args[] = {"-m", "MOM", inputFile5.getPath()}; // mom in first line in the file.
        Main.main(args);

        String expected5 = "My MOM drove me to school fifteen minutes late on Tuesday.\n" +
                "The girl wore her hair in two braids,\n" +
                "tied with two blue bows.\n" +
                "The tape got stuck on my lips so I couldn't talk anymore.";

        String actual5 = getFileContent(inputFile5.getPath());

        assertEquals("The files differ!", expected5, actual5);
    }

    // Purpose: test -w under special circumstance
    // Frame 23:
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  One
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w
    // Length of the delimiters                                             :  Many
    // Presence of characters in delimiters                                 :  Correct
    // Number of occurrences of the delimiters in the file                  :  Many
    // Number of occurrences of the delimiters in one line                  :  Many
    // Presence of characters that are lower case letters in the delimiters :  All
    // Position of the delimiters in the file                               :  Any
    @Test
    public void capitalizeTest6() throws Exception {
        File inputFile6 = createInputFile3();
        String args[] = {"-w", "md", inputFile6.getPath()};
        Main.main(args);

        String expected6 = "MY mOm dRove mE to school fifteen mInutes late on TuesdAy.\n" +
                "The girl wore her hair in two braidS,\n" +
                "tied with two blue bows.\n" +
                "The tape got stuck on mY lips so I couldN't talk anymOre.";

        String actual6 = getFileContent(inputFile6.getPath());

        assertEquals("The files differ!", expected6, actual6);
    }

    // Purpose: test -m under special circumstance
    // Frame 25:
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  One
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -m
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  None
    // Presence of characters that are lower case letters in the string     :  some
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest7() throws Exception {
        File inputFile7 = createInputFile4();
        String args[] = {"-m", "AaA", inputFile7.getPath()}; // mom in first line in the file.
        Main.main(args);

        String expected7 = "This dinner, HAaAa, is so delicious I can't stop eating, HAaAa.";

        String actual7 = getFileContent(inputFile7.getPath());

        assertEquals("The files differ!", expected7, actual7);
    }

    // Purpose: test -m under special circumstance
    // Frame 26:
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  One
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -m
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  One
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest8() throws Exception {
        File inputFile8 = createInputFile7();
        String args[] = {"-m", "CS6", inputFile8.getPath()};
        Main.main(args);

        String expected8 = "How do you think about CS6460?\n" +
                "I prefer CS6300 and CS6400.";

        String actual8 = getFileContent(inputFile8.getPath());

        assertEquals("The files differ!", expected8, actual8);
    }

    // Purpose: test -m-f combination under special circumstance
    // Frame 44:
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -m-f
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  None
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest9() throws Exception {
        File inputFile9 = createInputFile6();
        String args[] = {"-m", "HAPPY", "-f", inputFile9.getPath()};
        Main.main(args);

        String expected9 = "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!\n" +
                "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!\n" +
                "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!";

        String actual9 = getFileContent(inputFile9.getPath());

        assertEquals("The files differ!", expected9, actual9);
    }

    // Purpose: test combination of -w-m under special circumstance
    // Frame 29:
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w-m
    // Length of the delimiters                                             :  Empty
    // Presence of characters in delimiters                                 :  Correct
    // Position of the delimiters in the file                               :  Any
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  None
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest10() throws Exception {
        File inputFile10 = createInputFile6();
        String args[] = {"-w", "-m", "HAPPY", inputFile10.getPath()};
        Main.main(args);

        String expected10 = "This Dinner, HAPPY, Is So Delicious I Can't Stop Eating, HAPPY!\n" +
                "This Dinner, HAPPY, Is So Delicious I Can't Stop Eating, HAPPY!\n" +
                "This Dinner, HAPPY, Is So Delicious I Can't Stop Eating, HAPPY!";

        String actual10 = getFileContent(inputFile10.getPath());

        assertEquals("The files differ!", expected10, actual10);
    }

    // Purpose: test combination of -w-m under special circumstance
    // Frame 41:
    // File size                                                     :  Not empty
    // Presence of a file corresponding to the name                  :  Present
    // Number of OPT flags applied                                   :  Many
    // Presence of characters in OPT flags                           :  Correct
    // Applied OPT flags                                             :  -w-f
    // Length of the delimiters                                      :  Empty
    // Presence of characters in delimiters                          :  Correct
    // Position of the delimiters in the file                        :  Any
    @Test
    public void capitalizeTest11() throws Exception {
        File inputFile11 = createInputFile3();
        String args[] = {"-w", "-f", inputFile11.getPath()}; // mom in first line in the file.
        Main.main(args);

        String expected11 = "mY mOM dROVE mE tO sCHOOL fIFTEEN mINUTES lATE oN tUESDAY.\n" +
                "tHE gIRL wORE hER hAIR iN tWO bRAIDS,\n" +
                "tIED wITH tWO bLUE bOWS.\n" +
                "The tape got stuck on mY lips so I couldN't talk anymOre.";

        String actual11 = getFileContent(inputFile11.getPath());

        assertEquals("The files differ!", expected11, actual11);
    }

    // Purpose: test combination of -w-m under special circumstance
    // Frame 25:
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w-m
    // Length of the delimiters                                             :  Empty
    // Presence of characters in delimiters                                 :  Correct
    // Position of the delimiters in the file                               :  Any
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  One
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest12() throws Exception {
        File inputFile12 = createInputFile4();
        String args[] = {"-w", "-m", "CAN'T", inputFile12.getPath()};
        Main.main(args);

        String expected12 = "This Dinner is So Delicious I CAN'T Stop Eating.\n" +
                        "I CAN'T Neither.";

        String actual12 = getFileContent(inputFile12.getPath());

        assertEquals("The files differ!", expected12, actual12);
    }

    // Purpose: test combination of -w-m-f under special circumstance
    // Frame 48:
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w-m-f
    // Length of the delimiters                                             :  Empty
    // Presence of characters in delimiters                                 :  Correct
    // Position of the delimiters in the file                               :  Any
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  None
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest13() throws Exception {
        File inputFile13 = createInputFile6();
        String args[] = {"-w", "-m", "HAPPY", "-f", inputFile13.getPath()};
        Main.main(args);

        String expected13 = "tHIS dINNER, happy, iS sO dELICIOUS i cAN'T sTOP eATING, happy!\n" +
                "tHIS dINNER, happy, iS sO dELICIOUS i cAN'T sTOP eATING, happy!\n" +
                "tHIS dINNER, happy, iS sO dELICIOUS i cAN'T sTOP eATING, happy!";

        String actual13 = getFileContent(inputFile13.getPath());

        assertEquals("The files differ!", expected13, actual13);
    }

    // Purpose: test -w under special circumstance
    // Frame 50:
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w-m-f
    // Length of the delimiters                                             :  Empty
    // Presence of characters in delimiters                                 :  Correct
    // Position of the delimiters in the file                               :  Any
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  One
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest14() throws Exception {
        File inputFile14 = createInputFile7();
        String args[] = {"-w", "-m", "CS6", "-f", inputFile14.getPath()};
        Main.main(args);

        String expected14 = "hOW dO yOU tHICK aBOUT cs6460?\n" +
                "i pREFER cs6300 and cs6400.";

        String actual14 = getFileContent(inputFile14.getPath());

        assertEquals("The files differ!", expected14, actual14);
    }

    // Purpose: test -f
    // Frame 28:
    // File size                                                     :  Not empty
    // Presence of a file corresponding to the name                  :  Present
    // Number of OPT flags applied                                   :  One
    // Presence of characters in OPT flags                           :  Correct
    // Applied OPT flags                                             :  -f
    @Test
    public void capitalizeTest15() throws Exception {
        File inputFile15 = createInputFile4();
        String args[] = {"-f", inputFile15.getPath()};
        Main.main(args);

        String expected15 = "tHIS DINNER SO DELICIOUS i CAN'T STOP EATING.";

        String actual15 = getFileContent(inputFile15.getPath());

        assertEquals("The files differ!", expected15, actual15);
    }
}
