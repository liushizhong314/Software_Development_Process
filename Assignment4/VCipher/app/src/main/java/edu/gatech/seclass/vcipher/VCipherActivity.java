package edu.gatech.seclass.vcipher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class VCipherActivity extends AppCompatActivity {

    private EditText text;
    private EditText keyPhrase;
    private EditText answer;
    private RadioButton encrypt;
    private RadioButton decrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcipher);

        text = (EditText) findViewById(R.id.text);
        keyPhrase = (EditText) findViewById(R.id.keyphrase);
        answer = (EditText) findViewById(R.id.answer);
        encrypt = (RadioButton) findViewById(R.id.encrypt);
        decrypt = (RadioButton) findViewById(R.id.decrypt);
    }

    public void handleRunClick(View view) {

        switch(view.getId()) {
            case R.id.runButton:
                answer.setText("");
                String stringText = text.getText().toString();
                String stringKeyPhrase = keyPhrase.getText().toString();

                CharSequence txt1 = "Non-alphabetic character(s) in keyphrase";
                CharSequence txt2 = "Keyphrase required";
                CharSequence txt3 = "Nothing to encode/decode";

                // Errors detections
                if ((!hasAlphabetic(stringKeyPhrase)) || (!hasAlphabetic(stringText)) || stringKeyPhrase.isEmpty() || stringText.isEmpty()) {
                    if (!hasAlphabetic(stringKeyPhrase)) keyPhrase.setError(txt1);
                    if (!hasAlphabetic(stringText) || stringText.isEmpty()) text.setError(txt3);
                    if (stringKeyPhrase.isEmpty()) keyPhrase.setError(txt2);
                //encrypt and decrypt functions
                } else {
                    if (encrypt.isChecked()) {
                        answer.setText(encryptFunction(stringText, stringKeyPhrase));
                    }
                    if (decrypt.isChecked()) {
                        answer.setText(decryptFunction(stringText, stringKeyPhrase));
                    }
                }
                break;
        }
    }

    public boolean hasAlphabetic (String str) {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isAlphabetic(str.charAt(i))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public String encryptFunction(String txt, String key) {
        String encryptAnswer = "";
        key = key.toUpperCase();

        for (int i = 0, j = 0; i < txt.length(); i++) {
            char c = txt.charAt(i);
            if(c>='A' && c<='Z') {
                encryptAnswer += (char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
                j = ++j % key.length();
            } else if (c>='a' && c<='z') {
                encryptAnswer += (char)((c + key.charAt(j) - 'a' - 'A') % 26 + 'a');
                j = ++j % key.length();
            } else {
                encryptAnswer += c;
            }
        }
        return encryptAnswer;
    }

    public String decryptFunction(String txt, String key) {
        String decryptAnswer = "";
        key = key.toUpperCase();

        for (int i = 0, j = 0; i < txt.length(); i++) {
            char c = txt.charAt(i);
            if (c>='A' && c<='Z') {
                decryptAnswer += (char)((c - key.charAt(j) + 26) % 26 + 'A');
                j = ++j % key.length();
            } else if (c>='a' && c<='z') {
                decryptAnswer += (char)((c - key.charAt(j) - 6) % 26 + 'a');
                j = ++j % key.length();
            } else {
                decryptAnswer += c;
            }
        }
        return decryptAnswer;
    }

}

//One possible method to show error text:
//Context context = getApplicationContext();
//CharSequence text = "Non-alphabetic character(s) in keyphrase";
//int duration = Toast.LENGTH_SHORT;
//Toast toast = Toast.makeText(context, text, duration);
//toast.show();