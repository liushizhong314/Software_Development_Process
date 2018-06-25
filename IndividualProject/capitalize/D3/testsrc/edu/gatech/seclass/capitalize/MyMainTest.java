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

    private File createInputFile8() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Bill is," + System.getProperty("line.separator") +
                "in my opinion," + System.getProperty("line.separator") +
                "an easier name to spell than William." + System.getProperty("line.separator") +
                "Bill is shorter," + System.getProperty("line.separator") +
                "and Bill is" + System.getProperty("line.separator") +
                "first alphabetically.");

        fileWriter.close();
        return file1;
    }

    private File createInputFile9() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill, have you learned your abc and 123?\r\n" +
                "I know My Abc's.\r" +
                "It is important to know your abc's and 123's,\n" +
                "so repeat with me: abc! 123! Abc and 123!");

        fileWriter.close();
        return file1;
    }

    private File createInputFile10() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("123\\456?789\\0ab?cde\\fgh?ijk\\lmn?opq\\rst?uvw\\xyz");

        fileWriter.close();
        return file1;
    }

    private File createInputFile11() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("123|456|789|0ab|cde|fgh|ijk|lmn|opq|rst|uvw|xyz");

        fileWriter.close();
        return file1;
    }

    private File createInputFile12() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write(" ");

        fileWriter.close();
        return file1;
    }

    private File createInputFile13() throws Exception {
        File file13 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file13);

        fileWriter.write("@#$\r\f\n%");

        fileWriter.close();
        return file13;
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
    @Test
    public void capitalizeTest1() throws Exception {
        String args[] = {"-f"}; // the file is not present
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] [-i|-I] [-o] <filename>", errStream.toString().trim());
    }

    // Purpose: test wrong OPT input.
    // Frame 2: test case 4
    @Test
    public void capitalizeTest2() throws Exception {
        File inputFile2 = createInputFile1();
        String args[] = {"-c", inputFile2.getPath()}; // -c is not a correct OPT
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] [-i|-I] [-o] <filename>", errStream.toString().trim());
    }

    // Purpose: test -m where length of the string is longer than the file.
    // Frame 3: test case 14
    @Test
    public void capitalizeTest3() throws Exception {
        File inputFile3 = createInputFile5();
        String args[] = {"-m", "Heyyyy", inputFile3.getPath()}; // Heyyyy is longer than Hey in file
        Main.main(args);

        String expected = "Hey";

        String actual = getFileContent(inputFile3.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -m where none occurrence of the string in the file.
    // Frame 4: test case 15
    @Test
    public void capitalizeTest4() throws Exception {
        File inputFile4 = createInputFile2();
        String args[] = {"-m", "Hello", inputFile4.getPath()}; // No occurrence of the "Hello" in file.
        Main.main(args);

        String expected = "This dinner is so delicious I can't stop eating.\n" +
                "I can't Neither.";

        String actual = getFileContent(inputFile4.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -m where string occurs in the first line of the file.
    // Frame 5: test case 19
    @Test
    public void capitalizeTest5() throws Exception {
        File inputFile5 = createInputFile3();
        String args[] = {"-m", "MOM", inputFile5.getPath()}; // mom in first line in the file.
        Main.main(args);

        String expected = "My MOM drove me to school fifteen minutes late on Tuesday.\n" +
                "The girl wore her hair in two braids,\n" +
                "tied with two blue bows.\n" +
                "The tape got stuck on my lips so I couldn't talk anymore.";

        String actual = getFileContent(inputFile5.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test -w where delimiters are "md" occurring many times in the file.
    // Frame 6: test case 23
    @Test
    public void capitalizeTest6() throws Exception {
        File inputFile6 = createInputFile3();
        String args[] = {"-w", "md", inputFile6.getPath()};
        Main.main(args);

        String expected = "My mOm dRove mE to school fifteen mInutes late on TuesdAy.\n" +
                "The girl wore her hair in two braidS,\n" +
                "tied with two blue bows.\n" +
                "The tape got stuck on mY lips so I couldN't talk anymOre.";

        String actual = getFileContent(inputFile6.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test -m where string is "AaA" in the file.
    // Frame 7: test case 25
    @Test
    public void capitalizeTest7() throws Exception {
        File inputFile7 = createInputFile4();
        String args[] = {"-m", "AaA", inputFile7.getPath()}; // mom in first line in the file.
        Main.main(args);

        String expected = "This dinner, HAaAa, is so delicious I can't stop eating, HAaAa.";

        String actual = getFileContent(inputFile7.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test -m where "CS6" with one non-alphabetic char occurs.
    // Frame 8: test case 26
    @Test
    public void capitalizeTest8() throws Exception {
        File inputFile8 = createInputFile7();
        String args[] = {"-m", "CS6", inputFile8.getPath()};
        Main.main(args);

        String expected = "How do you think about CS6460?\n" +
                "I prefer CS6300 and CS6400.";

        String actual = getFileContent(inputFile8.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test -m-f combination where "HAPPY" as string occurs.
    // Frame 9: test case 44
    @Test
    public void capitalizeTest9() throws Exception {
        File inputFile9 = createInputFile6();
        String args[] = {"-m", "HAPPY", "-f", inputFile9.getPath()};
        Main.main(args);

        String expected = "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!\n" +
                "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!\n" +
                "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!";

        String actual = getFileContent(inputFile9.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test combination of -w-m where no delimiter is specified and "HAPPY" as string.
    // Frame 10: test case 29
    @Test
    public void capitalizeTest10() throws Exception {
        File inputFile10 = createInputFile6();
        String args[] = {"-w", "-m", "HAPPY", inputFile10.getPath()};
        Main.main(args);

        String expected = "This Dinner, HAPPY, Is So Delicious I Can't Stop Eating, HAPPY!\n" +
                "This Dinner, HAPPY, Is So Delicious I Can't Stop Eating, HAPPY!\n" +
                "This Dinner, HAPPY, Is So Delicious I Can't Stop Eating, HAPPY!";

        String actual = getFileContent(inputFile10.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test combination of -w-f where no delimiter is specified on a long file.
    // Frame 11: test case 41
    @Test
    public void capitalizeTest11() throws Exception {
        File inputFile11 = createInputFile3();
        String args[] = {"-w", "-f", inputFile11.getPath()}; // mom in first line in the file.
        Main.main(args);

        String expected = "mY mOM dROVE mE tO sCHOOL fIFTEEN mINUTES lATE oN tUESDAY.\n" +
                "tHE gIRL wORE hER hAIR iN tWO bRAIDS,\n" +
                "tIED wITH tWO bLUE bOWS.\n" +
                "tHE tAPE gOT sTUCK oN mY lIPS sO i cOULDN'T tALK aNYMORE.";

        String actual = getFileContent(inputFile11.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test -w-m where no delimiter is specified and "CAN'T" as string occurs.
    // Frame 12: test case 31
    @Test
    public void capitalizeTest12() throws Exception {
        File inputFile12 = createInputFile2();
        String args[] = {"-w", "-m", "CAN'T", inputFile12.getPath()};
        Main.main(args);

        String expected = "This Dinner Is So Delicious I CAN'T Stop Eating.\n" +
                "I CAN'T Neither.";

        String actual = getFileContent(inputFile12.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test combination of -w-m-f where no delimiter is specified and "HAPPY" as string occurs.
    // Frame 13: test case 48
    @Test
    public void capitalizeTest13() throws Exception {
        File inputFile13 = createInputFile6();
        String args[] = {"-w", "-m", "HAPPY", "-f", inputFile13.getPath()};
        Main.main(args);

        String expected = "tHIS dINNER, happy, iS sO dELICIOUS i cAN'T sTOP eATING, happy!\n" +
                "tHIS dINNER, happy, iS sO dELICIOUS i cAN'T sTOP eATING, happy!\n" +
                "tHIS dINNER, happy, iS sO dELICIOUS i cAN'T sTOP eATING, happy!";

        String actual = getFileContent(inputFile13.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test -w-m-f where no delimiter is specified and "CS6" as string occurs.
    // Frame 14: test case 50
    @Test
    public void capitalizeTest14() throws Exception {
        File inputFile14 = createInputFile7();
        String args[] = {"-w", "-m", "CS6", "-f", inputFile14.getPath()};
        Main.main(args);

        String expected = "hOW dO yOU tHINK aBOUT cs6460?\n" +
                "i pREFER cs6300 aND cs6400.";

        String actual = getFileContent(inputFile14.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test -f where no other OPT is specified.
    // Frame 15: test case 28
    @Test
    public void capitalizeTest15() throws Exception {
        File inputFile15 = createInputFile4();
        String args[] = {"-f", inputFile15.getPath()};
        Main.main(args);

        String expected = "tHIS DINNER, hAAAA, IS SO DELICIOUS i CAN'T STOP EATING, hAAAA.";

        String actual = getFileContent(inputFile15.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: test -m where "A" as the string with length of 1 occurs.
    // Frame 16: test case 13
    @Test
    public void capitalizeTest16() throws Exception {
        File inputFile16 = createInputFile6();
        String args[] = {"-m", "A", inputFile16.getPath()};
        Main.main(args);

        String expected = "This dinner, HAppy, is so delicious I cAn't stop eAting, HAppy!\n" +
                "This dinner, HAppy, is so delicious I cAn't stop eAting, HAppy!\n" +
                "This dinner, HAppy, is so delicious I cAn't stop eAting, HAppy!";

        String actual = getFileContent(inputFile16.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -w where "cqwrtuiopzx" delimiters are specified but none of them occurs in the file.
    // Frame 17: test case 7
    @Test
    public void capitalizeTest17() throws Exception {
        File inputFile17 = createInputFile5();
        String args[] = {"-m", "cqwrtuiopzx", inputFile17.getPath()};
        Main.main(args);

        String expected = "Hey";

        String actual = getFileContent(inputFile17.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -m where " SO I COULDN\'T" as the string with some non-alphabets.
    // Frame 18: test case 17
    @Test
    public void capitalizeTest18() throws Exception {
        File inputFile18 = createInputFile3();
        String args[] = {"-m", " SO I COULDN\'T", inputFile18.getPath()};
        Main.main(args);

        String expected = "My mom drove me to school fifteen minutes late on Tuesday.\n" +
                "The girl wore her hair in two braids,\n" +
                "tied with two blue bows.\n" +
                "The tape got stuck on my lips SO I COULDN'T talk anymore.";

        String actual = getFileContent(inputFile18.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -w where "MTola" as delimiters are specified.
    // Frame 19: test case 22
    // Type b
    @Test
    public void capitalizeTest19() throws Exception {
        File inputFile19 = createInputFile3();
        String args[] = {"-w", "MTola", inputFile19.getPath()};
        Main.main(args);

        String expected = "MY moM droVe me to schoOl fifteen minutes lAte oN TUesdaY.\n" +
                "THe girl woRe her haIr in two braIds,\n" +
                "tied with two blUe boWs.\n" +
                "THe taPe goT stuck oN my lIps so I coUlDn't taLk aNymoRe.";

        String actual = getFileContent(inputFile19.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -m where String "DINNER" without lower case occurs.
    // Frame 20: test case 24
    @Test
    public void capitalizeTest20() throws Exception {
        File inputFile20 = createInputFile6();
        String args[] = {"-m", "DINNER", inputFile20.getPath()};
        Main.main(args);

        String expected = "This DINNER, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This DINNER, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This DINNER, Happy, is so delicious I can't stop eating, Happy!";

        String actual = getFileContent(inputFile20.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -m where "cAn'T" as string with one non alphabet occurs.
    // Frame 21: test case 27
    @Test
    public void capitalizeTest21() throws Exception {
        File inputFile21 = createInputFile6();
        String args[] = {"-m", "cAn'T", inputFile21.getPath()};
        Main.main(args);

        String expected = "This dinner, Happy, is so delicious I cAn'T stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I cAn'T stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I cAn'T stop eating, Happy!";

        String actual = getFileContent(inputFile21.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -w-m where no delimiter is specified and "wIth" as string occurs.
    // Frame 22: test case 22
    @Test
    public void capitalizeTest22() throws Exception {
        File inputFile22 = createInputFile3();
        String args[] = {"-w", "-m", "wIth", inputFile22.getPath()};
        Main.main(args);

        String expected = "My Mom Drove Me To School Fifteen Minutes Late On Tuesday.\n" +
                "The Girl Wore Her Hair In Two Braids,\n" +
                "Tied wIth Two Blue Bows.\n" +
                "The Tape Got Stuck On My Lips So I Couldn't Talk Anymore.";

        String actual = getFileContent(inputFile22.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -w-m where "ThHc" as delimiters are specified and string "DiNneR" occurs.
    // Frame 23: test case 33
    // Type b
    @Test
    public void capitalizeTest23() throws Exception {
        File inputFile23 = createInputFile6();
        String args[] = {"-w", "ThHc", "-m", "DiNneR", inputFile23.getPath()};
        Main.main(args);

        String expected = "THIs DiNneR, HAppy, is so delicIous I cAn't stop eating, HAppy!\n" +
                "THIs DiNneR, HAppy, is so delicIous I cAn't stop eating, HAppy!\n" +
                "THIs DiNneR, HAppy, is so delicIous I cAn't stop eating, HAppy!";

        String actual = getFileContent(inputFile23.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -w-m where "aIcTe" as delimiters and "Is sO" as string are specified
    // Frame 24: test case 35
    @Test
    public void capitalizeTest24() throws Exception {
        File inputFile24 = createInputFile6();
        String args[] = {"-w", "aIcTe", "-m", "Is sO", inputFile24.getPath()};
        Main.main(args);

        String expected = "THis dinneR, HaPpy, Is sO deLicIOus I cAn't stop eAting, HaPpy!\n" +
                "THis dinneR, HaPpy, Is sO deLicIOus I cAn't stop eAting, HaPpy!\n" +
                "THis dinneR, HaPpy, Is sO deLicIOus I cAn't stop eAting, HaPpy!";;

        String actual = getFileContent(inputFile24.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -w-m where "dvctms" as delimiters and "sTOP" as string are specified.
    // Frame 25: test case 38
    @Test
    public void capitalizeTest25() throws Exception {
        File inputFile25 = createInputFile6();
        String args[] = {"-w", "dvctms", "-m", "sTOP", inputFile25.getPath()};
        Main.main(args);

        String expected = "This dInner, Happy, is sO dElicIous I cAn't sTOP eatIng, Happy!\n" +
                "This dInner, Happy, is sO dElicIous I cAn't sTOP eatIng, Happy!\n" +
                "This dInner, Happy, is sO dElicIous I cAn't sTOP eatIng, Happy!";

        String actual = getFileContent(inputFile25.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -w-f where "accdefg" as delimiters are specified.
    // Frame 26: test case 43
    // Type b
    @Test
    public void capitalizeTest26() throws Exception {
        File inputFile26 = createInputFile3();
        String args[] = {"-w", "abcdefg", "-f", inputFile26.getPath()};
        Main.main(args);

        String expected = "mY MOM DrOVE ME TO SChOOL FiFtEeN MINUTEs LAtE ON tUEsDaY.\n" +
                "tHE GiRL WORE HEr HAiR IN TWO BrAiDs,\n" +
                "TIEd WITH TWO BlUE BoWS.\n" +
                "tHE TApE GoT STUCk ON MY LIPS SO i CoULDn'T TAlK AnYMORE.";

        String actual = getFileContent(inputFile26.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -m-f where "HAPPY" as string is specified.
    // Frame 27: test case 46
    @Test
    public void capitalizeTest27() throws Exception {
        File inputFile27 = createInputFile6();
        String args[] = {"-m", " HAPPY", "-f", inputFile27.getPath()};
        Main.main(args);

        String expected = "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!\n" +
                "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!\n" +
                "tHIS DINNER, happy, IS SO DELICIOUS i CAN'T STOP EATING, happy!";

        String actual = getFileContent(inputFile27.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -w-m-f where no delimiter and "Is" as string is specified.
    // Frame 28: test case 49
    @Test
    public void capitalizeTest28() throws Exception {
        File inputFile28 = createInputFile6();
        String args[] = {"-w", "-m", "Is", "-f", inputFile28.getPath()};
        Main.main(args);

        String expected = "tHiS dINNER, hAPPY, iS sO dELICIOUS i cAN'T sTOP eATING, hAPPY!\n" +
                "tHiS dINNER, hAPPY, iS sO dELICIOUS i cAN'T sTOP eATING, hAPPY!\n" +
                "tHiS dINNER, hAPPY, iS sO dELICIOUS i cAN'T sTOP eATING, hAPPY!";

        String actual = getFileContent(inputFile28.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -w-m-f where "iI\'" as delimiters and "HAP" as string are specified.
    // Frame 29: test case 52
    // type b
    @Test
    public void capitalizeTest29() throws Exception {
        File inputFile29 = createInputFile6();
        String args[] = {"-w", "iI\' ", "-m", "HAP", "-f", inputFile29.getPath()};
        Main.main(args);

        String expected = "tHIs dInNER, hapPY, is sO dELIcIoUS i cAN't sTOP eATInG, hapPY!\n" +
                "tHIs dInNER, hapPY, is sO dELIcIoUS i cAN't sTOP eATInG, hapPY!\n" +
                "tHIs dInNER, hapPY, is sO dELIcIoUS i cAN't sTOP eATInG, hapPY!";

        String actual = getFileContent(inputFile29.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    // Purpose: test -w-m-f where "cdh" as delimiters and "pPy!" as string are specified.
    // Frame 30: test case 59
    @Test
    public void capitalizeTest30() throws Exception {
        File inputFile30 = createInputFile6();
        String args[] = {"-w", "cdh", "-m", "pPy!", "-f", inputFile30.getPath()};
        Main.main(args);

        String expected = "tHiS DiNNER, hAPPY, IS SO DeLICiOUS i CaN'T STOP EATING, hAPpY!\n" +
                "tHiS DiNNER, hAPPY, IS SO DeLICiOUS i CaN'T STOP EATING, hAPpY!\n" +
                "tHiS DiNNER, hAPPY, IS SO DeLICiOUS i CaN'T STOP EATING, hAPpY!";

        String actual = getFileContent(inputFile30.getPath());

        assertEquals("The files are the same!", expected, actual);
    }

    @Test
    public void capitalizeTest31() throws Exception {
        File inputFile31 = createInputFile6();

        String args[] = {inputFile31.getPath()};
        Main.main(args);

        String expected = "This dinner, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I can't stop eating, Happy!";

        String actual = getFileContent(inputFile31.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    @Test
    public void capitalizeTest32() throws Exception {
        File inputFile32 = createInputFile6();

        String args[] = {"-w", inputFile32.getPath()};
        Main.main(args);

        String expected = "This Dinner, Happy, Is So Delicious I Can't Stop Eating, Happy!\n" +
                "This Dinner, Happy, Is So Delicious I Can't Stop Eating, Happy!\n" +
                "This Dinner, Happy, Is So Delicious I Can't Stop Eating, Happy!";

        String actual = getFileContent(inputFile32.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Type c: capitalize fails into a RuntimeException when only non-alphabeitcs as argument
    // was passed as arguments to "-m" option
    @Test
    public void capitalizeTest33() throws Exception {
        File inputFile33 = createInputFile6();

        String args[] = {"-m", "!", "-f", inputFile33.getPath()};
        Main.main(args);

        String expected = "tHIS DINNER, hAPPY, IS SO DELICIOUS i CAN'T STOP EATING, hAPPY!\n" +
                "tHIS DINNER, hAPPY, IS SO DELICIOUS i CAN'T STOP EATING, hAPPY!\n" +
                "tHIS DINNER, hAPPY, IS SO DELICIOUS i CAN'T STOP EATING, hAPPY!";

        String actual = getFileContent(inputFile33.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest34() {
        String args[] = null; //invalid argument
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] [-i|-I] [-o] <filename>", errStream.toString().trim());
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest35() {
        String args[] = {"-m"}; //invalid argument
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] [-i|-I] [-o] <filename>", errStream.toString().trim());
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest36() throws Exception {
        File inputFile36 = createInputFile6();

        String args[] = {"-i", inputFile36.getPath()};
        Main.main(args);

        String expected = "this dinner, happy, is so delicious i can't stop eating, happy!\n" +
                "this dinner, happy, is so delicious i can't stop eating, happy!\n" +
                "this dinner, happy, is so delicious i can't stop eating, happy!";

        String actual = getFileContent(inputFile36.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest37() throws Exception {
        File inputFile37 = createInputFile6();

        String args[] = {"-I", inputFile37.getPath()};
        Main.main(args);

        String expected = "THIS DINNER, HAPPY, IS SO DELICIOUS I CAN'T STOP EATING, HAPPY!\n" +
                "THIS DINNER, HAPPY, IS SO DELICIOUS I CAN'T STOP EATING, HAPPY!\n" +
                "THIS DINNER, HAPPY, IS SO DELICIOUS I CAN'T STOP EATING, HAPPY!";

        String actual = getFileContent(inputFile37.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Type c: No usage() error showed when "-I" and "-i" were passed at the same time.
    @Test
    public void capitalizeTest38() throws Exception {
        File inputFile38 = createInputFile6();

        String args[] = {"-I", "-i", inputFile38.getPath()};; //invalid argument
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] [-i|-I] [-o] <filename>", errStream.toString().trim());
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest39() throws Exception {
        File inputFile39 = createInputFile6();

        String args[] = {"-o", "-f", inputFile39.getPath()};
        Main.main(args);

        String expected = "This dinner, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I can't stop eating, Happy!";

        String actual = getFileContent(inputFile39.getPath());

        assertEquals("The file was changed!", expected, actual);

        assertEquals("tHIS DINNER, hAPPY, IS SO DELICIOUS i CAN'T STOP EATING, hAPPY!\n" +
                "tHIS DINNER, hAPPY, IS SO DELICIOUS i CAN'T STOP EATING, hAPPY!\n" +
                "tHIS DINNER, hAPPY, IS SO DELICIOUS i CAN'T STOP EATING, hAPPY!", outStream.toString().trim());
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest40() throws Exception {
        File inputFile8= createInputFile8();

        String args[] = {inputFile8.getPath()};
        Main.main(args);

        String expected = "Bill is," + System.getProperty("line.separator") +
                "In my opinion," + System.getProperty("line.separator") +
                "An easier name to spell than William." + System.getProperty("line.separator") +
                "Bill is shorter," + System.getProperty("line.separator") +
                "And Bill is" + System.getProperty("line.separator") +
                "First alphabetically.";

        String actual = getFileContent(inputFile8.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest41() throws Exception {
        File inputFile10 = createInputFile10();

        String args[] = {"-w", "\\?", inputFile10.getPath()};
        Main.main(args);

        String expected = "123\\456?789\\0ab?Cde\\Fgh?Ijk\\Lmn?Opq\\Rst?Uvw\\Xyz";

        String actual = getFileContent(inputFile10.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest42() throws Exception {
        String args[] = {"nofile.txt"};
        Main.main(args);

        assertEquals("File Not Found", errStream.toString().trim());
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest43() throws Exception {
        File inputFile6 = createInputFile6();

        String args[] = {"-f", "-x", inputFile6.getPath()};
        Main.main(args);

        String expected = "This dinner, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I can't stop eating, Happy!\n" +
                "This dinner, Happy, is so delicious I can't stop eating, Happy!";

        String actual = getFileContent(inputFile6.getPath());

        assertEquals("The file was changed!", expected, actual);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] [-i|-I] [-o] <filename>", errStream.toString().trim());
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest44() throws Exception {
        File inputFile9 = createInputFile9();

        String args[] = {"-f", "-I", inputFile9.getPath()};
        Main.main(args);

        String expected = "howdy bill, have you learned your abc and 123?\r\n" +
                "i know my abc's.\r" +
                "it is important to know your abc's and 123's,\n" +
                "so repeat with me: abc! 123! abc and 123!";

        String actual = getFileContent(inputFile9.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Additional D2 Test
    @Test
    public void capitalizeTest45() throws Exception {
        File inputFile3 = createInputFile3();

        String args[] = {"-f", "-f", "-f", inputFile3.getPath()};
        Main.main(args);

        String expected = "mY MOM DROVE ME TO SCHOOL FIFTEEN MINUTES LATE ON tUESDAY.\n" +
                "tHE GIRL WORE HER HAIR IN TWO BRAIDS,\n" +
                "TIED WITH TWO BLUE BOWS.\n" +
                "tHE TAPE GOT STUCK ON MY LIPS SO i COULDN'T TALK ANYMORE.";

        String actual = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Type c: the first letter should not be capitalized when only "-o" option was applied.
    @Test
    public void capitalizeTest46() throws Exception {
        File inputFile3 = createInputFile3();

        String args[] = {"-o", "-o", inputFile3.getPath()};
        Main.main(args);

        String expected = "My mom drove me to school fifteen minutes late on Tuesday.\n" +
                "The girl wore her hair in two braids,\n" +
                "tied with two blue bows.\n" +
                "The tape got stuck on my lips so I couldn't talk anymore.";

        String actual = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected, actual);

        assertEquals("My mom drove me to school fifteen minutes late on Tuesday.\n" +
                "The girl wore her hair in two braids,\n" +
                "tied with two blue bows.\n" +
                "The tape got stuck on my lips so I couldn't talk anymore.", outStream.toString().trim());
    }

    @Test
    public void capitalizeTest47() throws Exception {
        File inputFile47 = createInputFile7();
        String args[] = {"-m", "-m", "-i", "-f", "-o", inputFile47.getPath()};
        Main.main(args);

        String expected = "How do you think about cS6460?\n" +
                "I prefer cs6300 and Cs6400.";

        String actual = getFileContent(inputFile47.getPath());

        assertEquals("The files differ!", expected, actual);

        assertEquals("HOW DO YOU THINK ABOUT CS6460?\n" +
                "I PREFER CS6300 AND CS6400.", outStream.toString().trim());
    }

    // Type a
    @Test
    public void capitalizeTest48() throws Exception {
        File inputFile48 = createInputFile7();
        String args[] = {"-w", "-w", "-m", "-w", inputFile48.getPath(), inputFile48.getPath()};
        Main.main(args);

        String expected = "How Do You ThiNk AbouT CS6460?\n" +
                "I Prefer Cs6300 AnD Cs6400.";

        String actual = getFileContent(inputFile48.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // args input checks
    @Test
    public void capitalizeTest49() throws Exception {
        File inputFile49 = createInputFile7();
        String args[] = {"-m", inputFile49.getPath()};
        Main.main(args);
        assertEquals("Usage: Capitalize  [-w [string]] [-m string] [-f] [-i|-I] [-o] <filename>", errStream.toString().trim());
    }

    // file has only a space.
    @Test
    public void capitalizeTest50() throws Exception {
        File inputFile9 = createInputFile12();

        String args[] = {"-f", inputFile9.getPath()};
        Main.main(args);

        String expected = " ";

        String actual = getFileContent(inputFile9.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Type c: capitalize fails into a RuntimeException when only numbers as argument
    // was passed to "-m" option.
    @Test
    public void capitalizeTest51() throws Exception {
        File inputFile8 = createInputFile11();

        String args[] = {"-m", "666", inputFile8.getPath()};
        Main.main(args);

        String expected = "123|456|789|0AB|cDE|fGH|iJK|lMN|oPQ|rST|uVW|xyz";

        String actual = getFileContent(inputFile8.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // looks like "-m" opt has one line for \r or \f
    @Test
    public void capitalizeTest52() throws Exception {
        File inputFile52 = createInputFile13();

        String args[] = {"-m", "AcD", inputFile52.getPath()};
        Main.main(args);

        String expected = "@#$\r\f\n%";

        String actual = getFileContent(inputFile52.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Type c: capitalize fails into a RuntimeException when an empty string as argument
    // was passed as arguments to "-m" option
    @Test
    public void capitalizeTest53() throws Exception {
        File inputFile53 = createInputFile6();

        String args[] = {"-m", "", "-f", inputFile53.getPath()};
        Main.main(args);

        String expected = "tHIS DINNER, hAPPY, IS SO DELICIOUS i CAN'T STOP EATING, hAPPY!\n" +
                "tHIS DINNER, hAPPY, IS SO DELICIOUS i CAN'T STOP EATING, hAPPY!\n" +
                "tHIS DINNER, hAPPY, IS SO DELICIOUS i CAN'T STOP EATING, hAPPY!";

        String actual = getFileContent(inputFile53.getPath());

        assertEquals("The files differ!", expected, actual);
    }
}