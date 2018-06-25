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

    // Utilities

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

    // Purpose: test under file name missing circumstance.
    // Frame 1: test case 2
    // Presence of a file corresponding to the name :  Not present
    @Test
    public void capitalizeTest1() throws Exception {
        String args[] = {"-f"}; // the file is not present
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] [-i|-I] [-o] <filename>", errStream.toString().trim());
    }

    // Purpose: test wrong OPT input.
    // Frame 2: test case 4
    // Presence of characters in OPT flags :  Incorrect
    @Test
    public void capitalizeTest2() throws Exception {
        File inputFile2 = createInputFile1();
        String args[] = {"-c", inputFile2.getPath()}; // -c is not a correct OPT
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] [-i|-I] [-o] <filename>", errStream.toString().trim());
    }

    // Purpose: test -m where length of the string is longer than the file.
    // Frame 3: test case 14
    // Length of the string :  Longer than the file
    @Test
    public void capitalizeTest3() throws Exception {
        File inputFile3 = createInputFile5();
        String args[] = {"-m", "Heyyyy", inputFile3.getPath()}; // Heyyyy is longer than Hey in file
        Main.main(args);

        String expected3 = "Hey";

        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files are the same!", expected3, actual3);
    }

    // Purpose: test -m where none occurrence of the string in the file.
    // Frame 4: test case 15
    // Number of occurrences of the String in the file :  None
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

    // Purpose: test -m where string occurs in the first line of the file.
    // Frame 5: test case 19
    // Position of the String in the file :  First line
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

    // Purpose: test -w where delimiters are "md" occurring many times in the file.
    // Frame 6: test case 23
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

        String expected6 = "My mOm dRove mE to school fifteen mInutes late on TuesdAy.\n" +
                "The girl wore her hair in two braidS,\n" +
                "tied with two blue bows.\n" +
                "The tape got stuck on mY lips so I couldN't talk anymOre.";

        String actual6 = getFileContent(inputFile6.getPath());

        assertEquals("The files differ!", expected6, actual6);
    }

    // Purpose: test -m where string is "AaA" in the file.
    // Frame 7: test case 25
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

    // Purpose: test -m where "CS6" with one non-alphabetic char occurs.
    // Frame 8: test case 26
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

    // Purpose: test -m-f combination where "HAPPY" as string occurs.
    // Frame 9: test case 44
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

    // Purpose: test combination of -w-m where no delimiter is specified and "HAPPY" as string.
    // Frame 10: test case 29
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

    // Purpose: test combination of -w-f where no delimiter is specified on a long file.
    // Frame 11: test case 41
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
                "tHE tAPE gOT sTUCK oN mY lIPS sO i cOULDN'T tALK aNYMORE.";

        String actual11 = getFileContent(inputFile11.getPath());

        assertEquals("The files differ!", expected11, actual11);
    }

    // Purpose: test -w-m where no delimiter is specified and "CAN'T" as string occurs.
    // Frame 12: test case 31
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
        File inputFile12 = createInputFile2();
        String args[] = {"-w", "-m", "CAN'T", inputFile12.getPath()};
        Main.main(args);

        String expected12 = "This Dinner Is So Delicious I CAN'T Stop Eating.\n" +
                "I CAN'T Neither.";

        String actual12 = getFileContent(inputFile12.getPath());

        assertEquals("The files differ!", expected12, actual12);
    }

    // Purpose: test combination of -w-m-f where no delimiter is specified and "HAPPY" as string occurs.
    // Frame 13: test case 48
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

    // Purpose: test -w-m-f where no delimiter is specified and "CS6" as string occurs.
    // Frame 14: test case 50
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

        String expected14 = "hOW dO yOU tHINK aBOUT cs6460?\n" +
                "i pREFER cs6300 aND cs6400.";

        String actual14 = getFileContent(inputFile14.getPath());

        assertEquals("The files differ!", expected14, actual14);
    }

    // Purpose: test -f where no other OPT is specified.
    // Frame 15: test case 28
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

        String expected15 = "tHIS DINNER, hAAAA, IS SO DELICIOUS i CAN'T STOP EATING, hAAAA.";

        String actual15 = getFileContent(inputFile15.getPath());

        assertEquals("The files differ!", expected15, actual15);
    }

    // Purpose: test -m where "A" as the string with length of 1 occurs.
    // Frame 16: test case 13
    // Length of the string :  One
    @Test
    public void capitalizeTest16() throws Exception {
        File inputFile16 = createInputFile6();
        String args[] = {"-m", "A", inputFile16.getPath()};
        Main.main(args);

        String expected16 = "This dinner, HAppy, is so delicious I cAn't stop eAting, HAppy!\n" +
                "This dinner, HAppy, is so delicious I cAn't stop eAting, HAppy!\n" +
                "This dinner, HAppy, is so delicious I cAn't stop eAting, HAppy!";

        String actual16 = getFileContent(inputFile16.getPath());

        assertEquals("The files are the same!", expected16, actual16);
    }

    // Purpose: test -w where "cqwrtuiopzx" delimiters are specified but none of them occurs in the file.
    // Frame 17: test case 7
    // Number of occurrences of the delimiters in the file :  None
    @Test
    public void capitalizeTest17() throws Exception {
        File inputFile17 = createInputFile5();
        String args[] = {"-m", "cqwrtuiopzx", inputFile17.getPath()};
        Main.main(args);

        String expected17 = "Hey";

        String actual17 = getFileContent(inputFile17.getPath());

        assertEquals("The files are the same!", expected17, actual17);
    }

    // Purpose: test -m where " SO I COULDN\'T" as the string with some non-alphabets.
    // Frame 18: test case 17
    // Presence of characters that are not alphabets in the string :  Many
    @Test
    public void capitalizeTest18() throws Exception {
        File inputFile18 = createInputFile3();
        String args[] = {"-m", " SO I COULDN\'T", inputFile18.getPath()};
        Main.main(args);

        String expected18 = "My mom drove me to school fifteen minutes late on Tuesday.\n" +
                "The girl wore her hair in two braids,\n" +
                "tied with two blue bows.\n" +
                "The tape got stuck on my lips SO I COULDN'T talk anymore.";

        String actual18 = getFileContent(inputFile18.getPath());

        assertEquals("The files are the same!", expected18, actual18);
    }

    // Purpose: test -w where "MTola" as delimiters are specified.
    // Frame 19: test case 22
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  One
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w
    // Length of the delimiters                                             :  Many
    // Presence of characters in delimiters                                 :  Correct
    // Number of occurrences of the delimiters in the file                  :  Many
    // Number of occurrences of the delimiters in one line                  :  Many
    // Presence of characters that are lower case letters in the delimiters :  some
    // Position of the delimiters in the file                               :  Any
    @Test
    public void capitalizeTest19() throws Exception {
        File inputFile19 = createInputFile3();
        String args[] = {"-w", "MTola", inputFile19.getPath()};
        Main.main(args);

        String expected19 = "MY moM droVe me to school fifteen minutes laTe oN TUesdaY.\n" +
                "THe girl woRe her haIr in two braIds,\n" +
                "tied with two blUe boWs.\n" +
                "THe taPe goT stuck oN my lIps so I coUlDn't talK aNymoRe.";

        String actual19 = getFileContent(inputFile19.getPath());

        assertEquals("The files are the same!", expected19, actual19);
    }

    // Purpose: test -m where String "DINNER" without lower case occurs.
    // Frame 20: test case 24
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  One
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -m
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  None
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest20() throws Exception {
        File inputFile20 = createInputFile6();
        String args[] = {"-m", "DINNER", inputFile20.getPath()};
        Main.main(args);

        String expected20 = "This DINNER, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This DINNER, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This DINNER, Happy, is so delicious I can't stop eating, Happy!";

        String actual20 = getFileContent(inputFile20.getPath());

        assertEquals("The files are the same!", expected20, actual20);
    }

    // Purpose: test -m where "cAn'T" as string with one non alphabet occurs.
    // Frame 21: test case 27
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  One
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -m
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  One
    // Presence of characters that are lower case letters in the string     :  some
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest21() throws Exception {
        File inputFile21 = createInputFile6();
        String args[] = {"-m", "cAn'T", inputFile21.getPath()};
        Main.main(args);

        String expected21 = "This dinner, Happy, is so delicious I cAn'T stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I cAn'T stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I cAn'T stop eating, Happy!";

        String actual21 = getFileContent(inputFile21.getPath());

        assertEquals("The files are the same!", expected21, actual21);
    }

    // Purpose: test -w-m where no delimiter is specified and "wIth" as string occurs.
    // Frame 22: test case 22
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
    // Presence of characters that are lower case letters in the string     :  some
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest22() throws Exception {
        File inputFile22 = createInputFile3();
        String args[] = {"-w", "-m", "wIth", inputFile22.getPath()};
        Main.main(args);

        String expected22 = "My Mom Drove Me To School Fifteen Minutes Late On Tuesday.\n" +
                "The Girl Wore Her Hair In Two Braids,\n" +
                "Tied wIth Two Blue Bows.\n" +
                "The Tape Got Stuck On My Lips So I Couldn't Talk Anymore.";

        String actual22 = getFileContent(inputFile22.getPath());

        assertEquals("The files are the same!", expected22, actual22);
    }

    // Purpose: test -w-m where "ThHc" as delimiters are specified and string "DiNneR" occurs.
    // Frame 23: test case 33
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w-m
    // Length of the delimiters                                             :  Many
    // Presence of characters in delimiters                                 :  Correct
    // Number of occurrences of the delimiters in the file                  :  Many
    // Number of occurrences of the delimiters in one line                  :  Many
    // Presence of characters that are lower case letters in the delimiters :  some
    // Position of the delimiters in the file                               :  Any
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  None
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest23() throws Exception {
        File inputFile23 = createInputFile6();
        String args[] = {"-w", "ThHc", "-m", "DiNneR", inputFile23.getPath()};
        Main.main(args);

        String expected23 = "ThIs DiNneR, HAppy, is so delicIous I cAn't stop eating, HAppy!\n" +
                "ThIs DiNneR, HAppy, is so delicIous I cAn't stop eating, HAppy!\n" +
                "ThIs DiNneR, HAppy, is so delicIous I cAn't stop eating, HAppy!";

        String actual23 = getFileContent(inputFile23.getPath());

        assertEquals("The files are the same!", expected23, actual23);
    }

    // Purpose: test -w-m where "aIcTe" as delimiters and "Is sO" as string are specified
    // Frame 24: test case 35
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w-m
    // Length of the delimiters                                             :  Many
    // Presence of characters in delimiters                                 :  Correct
    // Number of occurrences of the delimiters in the file                  :  Many
    // Number of occurrences of the delimiters in one line                  :  Many
    // Presence of characters that are lower case letters in the delimiters :  some
    // Position of the delimiters in the file                               :  Any
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  One
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest24() throws Exception {
        File inputFile24 = createInputFile6();
        String args[] = {"-w", "aIcTe", "-m", "Is sO", inputFile24.getPath()};
        Main.main(args);

        String expected24 = "THis dinneR, HaPpy, Is sO deLicIous I caN't stop eaTing, HaPpy!\n" +
                "THis dinneR, HaPpy, Is sO deLicIous I caN't stop eaTing, HaPpy!\n" +
                "THis dinneR, HaPpy, Is sO deLicIous I caN't stop eaTing, HaPpy!";

        String actual24 = getFileContent(inputFile24.getPath());

        assertEquals("The files are the same!", expected24, actual24);
    }

    // Purpose: test -w-m where "dvctms" as delimiters and "sTOP" as string are specified.
    // Frame 25: test case 38
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w-m
    // Length of the delimiters                                             :  Many
    // Presence of characters in delimiters                                 :  Correct
    // Number of occurrences of the delimiters in the file                  :  Many
    // Number of occurrences of the delimiters in one line                  :  Many
    // Presence of characters that are lower case letters in the delimiters :  All
    // Position of the delimiters in the file                               :  Any
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  None
    // Presence of characters that are lower case letters in the string     :  some
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest25() throws Exception {
        File inputFile25 = createInputFile6();
        String args[] = {"-w", "dvctms", "-m", "sTOP", inputFile25.getPath()};
        Main.main(args);

        String expected25 = "This dInner, Happy, is sO dElicIous I cAn't sTOP eatIng, Happy!\n" +
                "This dInner, Happy, is sO dElicIous I cAn't sTOP eatIng, Happy!\n" +
                "This dInner, Happy, is sO dElicIous I cAn't sTOP eatIng, Happy!";

        String actual25 = getFileContent(inputFile25.getPath());

        assertEquals("The files are the same!", expected25, actual25);
    }

    // Purpose: test -w-f where "accdefg" as delimiters are specified.
    // Frame 26: test case 43
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w-f
    // Length of the delimiters                                             :  Many
    // Presence of characters in delimiters                                 :  Correct
    // Number of occurrences of the delimiters in the file                  :  Many
    // Number of occurrences of the delimiters in one line                  :  Many
    // Presence of characters that are lower case letters in the delimiters :  All
    // Position of the delimiters in the file                               :  Any
    @Test
    public void capitalizeTest26() throws Exception {
        File inputFile26 = createInputFile3();
        String args[] = {"-w", "abcdefg", "-f", inputFile26.getPath()};
        Main.main(args);

        String expected26 = "mY MOM DrOVE ME TO SChOOL FiFtEEn MINUTEs LAtE ON tUEsDAy.\n" +
                "tHE GiRL WORE HEr HAiR IN TWO BrAiDs,\n" +
                "TIED WITH TWO BlUE BoWS.\n" +
                "tHE TApE GoT STUCk ON MY LIPS SO i CoULDn'T TAlK AnYMORE.";

        String actual26 = getFileContent(inputFile26.getPath());

        assertEquals("The files are the same!", expected26, actual26);
    }

    // Purpose: test -m-f where "HAPPY" as string is specified.
    // Frame 27: test case 46
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -m-f
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  One
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest27() throws Exception {
        File inputFile27 = createInputFile6();
        String args[] = {"-m", " HAPPY", "-f", inputFile27.getPath()};
        Main.main(args);

        String expected27 = "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!\n" +
                "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!\n" +
                "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!";

        String actual27 = getFileContent(inputFile27.getPath());

        assertEquals("The files are the same!", expected27, actual27);
    }

    // Purpose: test -w-m-f where no delimiter and "Is" as string is specified.
    // Frame 28: test case 49
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
    // Presence of characters that are lower case letters in the string     :  some
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest28() throws Exception {
        File inputFile28 = createInputFile6();
        String args[] = {"-w", "-m", "Is", "-f", inputFile28.getPath()};
        Main.main(args);

        String expected28 = "tHiS dINNER, hAPPY, iS sO dELICIOUS i cAN'T sTOP eATING, hAPPY!\n" +
                "tHiS dINNER, hAPPY, iS sO dELICIOUS i cAN'T sTOP eATING, hAPPY!\n" +
                "tHiS dINNER, hAPPY, iS sO dELICIOUS i cAN'T sTOP eATING, hAPPY!";

        String actual28 = getFileContent(inputFile28.getPath());

        assertEquals("The files are the same!", expected28, actual28);
    }

    // Purpose: test -w-m-f where "iI\'" as delimiters and "HAP" as string are specified.
    // Frame 29: test case 52
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w-m-f
    // Length of the delimiters                                             :  Many
    // Presence of characters in delimiters                                 :  Correct
    // Number of occurrences of the delimiters in the file                  :  Many
    // Number of occurrences of the delimiters in one line                  :  Many
    // Presence of characters that are lower case letters in the delimiters :  some
    // Position of the delimiters in the file                               :  Any
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  None
    // Presence of characters that are lower case letters in the string     :  None
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest29() throws Exception {
        File inputFile29 = createInputFile6();
        String args[] = {"-w", "iI\' ", "-m", "HAP", "-f", inputFile29.getPath()};
        Main.main(args);

        String expected29 = "tHIs dInNER, hapPY, Is sO dELIcIoUS i cAN't sTOP eATInG, hapPY!\n" +
                "tHIs dInNER, hapPY, Is sO dELIcIoUS i cAN't sTOP eATInG, hapPY!\n" +
                "tHIs dInNER, hapPY, Is sO dELIcIoUS i cAN't sTOP eATInG, hapPY!";

        String actual29 = getFileContent(inputFile29.getPath());

        assertEquals("The files are the same!", expected29, actual29);
    }

    // Purpose: test -w-m-f where "cdh" as delimiters and "pPy!" as string are specified.
    // Frame 30: test case 59
    // File size                                                            :  Not empty
    // Presence of a file corresponding to the name                         :  Present
    // Number of OPT flags applied                                          :  Many
    // Presence of characters in OPT flags                                  :  Correct
    // Applied OPT flags                                                    :  -w-m-f
    // Length of the delimiters                                             :  Many
    // Presence of characters in delimiters                                 :  Correct
    // Number of occurrences of the delimiters in the file                  :  Many
    // Number of occurrences of the delimiters in one line                  :  Many
    // Presence of characters that are lower case letters in the delimiters :  All
    // Position of the delimiters in the file                               :  Any
    // Length of the string                                                 :  More than one
    // Number of occurrences of the String in the file                      :  Many
    // Number of occurrences of the String in one line                      :  Many
    // Presence of characters that are not alphabets in the string          :  One
    // Presence of characters that are lower case letters in the string     :  some
    // Position of the String in the file                                   :  Any
    @Test
    public void capitalizeTest30() throws Exception {
        File inputFile30 = createInputFile6();
        String args[] = {"-w", "cdh", "-m", "pPy!", "-f", inputFile30.getPath()};
        Main.main(args);

        String expected30 = "tHiS DiNNER, hAPPY, IS SO DeLICiOUS i CaN'T STOP EATING, hAPpY!\n" +
                "tHiS DiNNER, hAPPY, IS SO DeLICiOUS i CaN'T STOP EATING, hAPpY!\n" +
                "tHiS DiNNER, hAPPY, IS SO DeLICiOUS i CaN'T STOP EATING, hAPpY!";

        String actual30 = getFileContent(inputFile30.getPath());

        assertEquals("The files are the same!", expected30, actual30);
    }
}