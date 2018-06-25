package edu.gatech.seclass;

/**
 * Created by Jiajie Cen on 1/21/18.
 * Implement functions for MyCustomStringInterface
 *
 */

public class MyCustomString implements MyCustomStringInterface{

    private String string;

    public String getString(){
        return string;
    }

    public void setString(String string){
        this.string = string;
    }

    public int countNumbers(){
        if (string == null) {throw new NullPointerException();}
        int count = 0;
        boolean flag = false;
        for (int i = 0; i < string.length(); i++){
            if (Character.isDigit(string.charAt(i)) && flag == false) {
                count++;
                flag = true;
            } else if (Character.isDigit(string.charAt(i)) && flag == true){
                flag = true;
            } else {
                flag = false;
            }
        }
        return count;
    }

    public String increaseDigits(int n, boolean wrap){
        char[] chars = string.toCharArray();
        int a;

        if (string == null){throw new NullPointerException();}
        if (n > 9 || n < -9){throw new IllegalArgumentException();}

        if (wrap){
            for (int i = 0; i < string.length(); i++){
                if (Character.isDigit(string.charAt(i))){
                    a = Character.getNumericValue(string.charAt(i)) + n;
                    if (a >= 0){
                        chars[i] = (char)(a % 10 + '0');
                    } else if (a < 0){
                        chars[i] = (char)((a % 10) + 10 + '0');
                    }
                }
            }
        } else {
            for (int i = 0; i < string.length(); i++){
                if (Character.isDigit(string.charAt(i))){
                    a = Character.getNumericValue(string.charAt(i)) + n;
                    if (a < 10 && a >= 0){
                        chars[i] = (char)(a + '0');
                    } else if (a >= 10){
                        chars[i] = '9';
                    } else if (a < 0){
                        chars[i] = '0';
                    }
                }
            }
        }
        return new String(chars);
    }

    public void convertLettersToDigitsInSubstring(int startPosition, int endPosition){
        if (getString() == null){
            throw new NullPointerException();
        }
        if (endPosition > string.length()){
            throw new MyIndexOutOfBoundsException();
        }
        if (startPosition > endPosition || startPosition < 1){
            throw new IllegalArgumentException();
        }

        String tempString = "";
        String tempString2;
        int tempInt;

        for (int i = startPosition-1; i < endPosition; i++){
            if(Character.isAlphabetic(string.charAt(i))){
                tempInt = (int) Character.toLowerCase(string.charAt(i)) - 96;
                tempString2 = (tempInt < 10 ? "0" : "") + tempInt;
                tempString = tempString.concat(tempString2);
            } else {
                tempString = tempString + string.charAt(i);
            }
        }
        string = string.substring(0,startPosition-1) + tempString + string.substring(endPosition);
    }
}