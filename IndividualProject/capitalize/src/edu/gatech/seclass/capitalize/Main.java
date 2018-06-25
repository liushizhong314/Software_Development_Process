package edu.gatech.seclass.capitalize;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args ) {
        final Charset charset = StandardCharsets.UTF_8;
        final List<String> operators = Arrays.asList("-m", "-w", "-f", "-o", "-i", "-I");

        // if input is null or empty
        if (args == null || args.length == 0) {
            usage();
            return;
        }

        for (String arg : args) {
            if (arg.startsWith("-")) {
                if (!operators.contains(arg)) {
                    usage();
                    return;
                }
            }
        }

        String filename = args[args.length-1];

        if (operators.contains(filename)) { // check filepath is operator
            usage();
            return;
        }

        String content; // file content

        try { // check filepath valid
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e){
            fileNotFoundError();
            return;
        }

        if (content.isEmpty()) return;

        if (args.length == 1) {
            content = c_Helper(content);
            writeToFile(filename, content);
            return;
        }

        boolean f_flag = false;
        boolean i_flag = false;
        boolean I_flag = false;
        boolean o_flag = false;

        for (String arg : args) {
            if (arg.toLowerCase().equals("-i")) {
                if (i_flag || I_flag) {
                    usage();
                    return;
                }

                if (arg.equals("-i")) i_flag = true;
                if (arg.equals("-I")) I_flag = true;
            }
        }

        if (i_flag) content = content.toLowerCase();
        if (I_flag) content = content.toUpperCase();

        for (int i = 0; i < args.length; i++) {
            // check -w
            if(args[i].equals("-w")) if (args[i + 1].equals("-f") || args[i + 1].equals("-m")
                    || args[i + 1].equals(filename) || args[i + 1].toLowerCase().equals("-i")) {
                content = w_Helper(content, null);
            } else {
                String delimiters = args[i + 1];
                content = w_Helper(content, delimiters);
            }

            // check -m
            if (args[i].equals("-m")) {
                if (args[i+1].equals("-w") || args[i+1].equals("-f")
                        || args[i+1].equals(filename) || args[i+1].toLowerCase().equals("-i")) {
                    usage();
                    return;
                }
                if (args[i+1].isEmpty()) {
                    usage();
                    return;
                }
                content = m_Helper(content, args[i+1]);
            }

            // check -f
            if (args[i].equals("-f")) {
                f_flag = !f_flag;
            }

            // check -o
            if (args[i].equals("-o")) {
                o_flag = true;
            }
        }

        if (f_flag) content = f_Helper(content);
        if (o_flag) System.out.println(content);
        else writeToFile(filename, content);
    }

    private static void writeToFile(String filePath, String content) {
        try {
            File f = new File(filePath);
            PrintWriter out = new PrintWriter(f);

            out.print(content);
            out.close();
        } catch (FileNotFoundException e) {
            fileNotFoundError();
        }
    }

    // usage method
    private static void usage() {
        System.err.println("Usage: Capitalize  [-w [string]] [-m string] [-f] [-i|-I] [-o] <filename>");
    }

    // fileNotFoundError
    private static void fileNotFoundError() {
        System.err.println("File Not Found");
    }

    // capitalize first letter in line
    private static String c_Helper(String string) {
        if (string == null || string.isEmpty()) return string;

        string = string.substring(0, 1).toUpperCase() + string.substring(1);
        for (int i = 1; i < string.length() - 1; i++) {
            if (string.charAt(i-1) == '\n') {
                string = string.substring(0, i) + string.substring(i, i+1).toUpperCase() + string.substring(i+1);
            }
        }
        return string;
    }

    // -w method
    private static String w_Helper(String string, String delimiters) {

        String newDelimiters;
        boolean next;

        if (delimiters == null) {
            newDelimiters = " \n\t\r\f";
            next = true;
        } else {
            newDelimiters = delimiters;
            next = false;
        }

        if (string == null || string.isEmpty() || newDelimiters.isEmpty()) return string;

        char[] deli = newDelimiters.toCharArray();
        StringBuilder buffer = new StringBuilder(string.length());

        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if (inCharArray(ch, deli) && next != true) {
                buffer.append(ch);
                next = true;
            } else if (next) {
                buffer.append(Character.toUpperCase(ch));
                next = false;
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    private static boolean inCharArray(char ch, char[] CA) {
        for (char aCA : CA) {
            if (ch == aCA) return true;
        }
        return false;
    }

    // -m replace
    private static String m_Helper (String string, String replacement) {
        if (string == null || string.isEmpty() || replacement.isEmpty()) return string;

        String searchText = string.toLowerCase();
        String searchString = replacement.toLowerCase();
        int searchLength = replacement.length();

        int start = 0;
        int end = searchText.indexOf(searchString, start);
        if (end == -1) return string;

        StringBuilder buffer = new StringBuilder(string.length());
        while (end != -1) {
            buffer.append(string, start, end).append(replacement);
            start = end + searchLength;
            end = searchText.indexOf(searchString, start);
        }
        buffer.append(string, start, string.length());

        return buffer.toString();
    }

    // -f swap
    private static String f_Helper(String string) {
        if (string == null || string.isEmpty()) return string;

        StringBuilder buffer = new StringBuilder(string.length());

        for(int i = 0; i < string.length(); i++) {
            char oldChar = string.charAt(i);
            char newChar;
            if (Character.isUpperCase(oldChar)) {
                newChar = Character.toLowerCase(oldChar);
            } else if (Character.isLowerCase(oldChar)) {
                newChar = Character.toUpperCase(oldChar);
            } else {
                newChar = oldChar;
            }
            buffer.append(newChar);
        }
        return buffer.toString();
    }

}