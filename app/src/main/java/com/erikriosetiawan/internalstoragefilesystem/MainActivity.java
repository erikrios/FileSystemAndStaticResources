package com.erikriosetiawan.internalstoragefilesystem;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button buttonSaveInternal, buttonLoadInternal;
    Button buttonSaveExternal, buttonLoadExternal;
    final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text_text);
        buttonSaveInternal = findViewById(R.id.button_save_internal);
        buttonLoadInternal = findViewById(R.id.button_load_internal);
        buttonSaveExternal = findViewById(R.id.button_save_external);
        buttonLoadExternal = findViewById(R.id.button_load_external);

        buttonSaveInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = editText.getText().toString();
                try {
                    FileOutputStream fOut = openFileOutput("myFile.txt", MODE_PRIVATE);
                    // Write the string to the file
                    fOut.write(string.getBytes());
                    fOut.close();
                    // Display the saved message
                    Toast.makeText(getApplicationContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
                    // Clear the Edit Text
                    editText.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonLoadInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fIn = openFileInput("myFile.txt");
                    InputStreamReader inputStreamReader = new InputStreamReader(fIn);
                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    String s = "";
                    int charRead;
                    while ((charRead = inputStreamReader.read(inputBuffer)) > 0) {
                        // Convert the chars to a string
                        String readString = String.copyValueOf(inputBuffer, 0, charRead);
                        s += readString;
                        inputBuffer = new char[READ_BLOCK_SIZE];
                    }
                    inputStreamReader.close();
                    // Set the Edit Text with the text has been read
                    editText.setText(s);
                    Toast.makeText(getApplicationContext(), "File loaded successfully!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonSaveExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = editText.getText().toString();
                try {
                    // SD Card Storage
                    File sdCard = Environment.getExternalStorageDirectory();
                    File directory = new File(sdCard.getAbsolutePath() + "/myfiles");

                    directory.mkdirs();
                    File file = new File(directory, "textfile.txt");
                    FileOutputStream fOut = new FileOutputStream(file);
                    OutputStreamWriter osw = new OutputStreamWriter(fOut);

                    // Write the string to the file
                    osw.write(string);
                    osw.flush();
                    osw.close();
                    // Dispay the saved message
                    Toast.makeText(getApplicationContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
                    // Clear the Edit Text
                    editText.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonLoadExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // SD Storage
                    File sdCard = Environment.getExternalStorageDirectory();
                    File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
                    File file = new File(directory, "textfile.txt");
                    FileInputStream fln = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fln);
                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    String s = "";
                    int charRead;
                    while ((charRead = isr.read(inputBuffer)) > 0) {
                        // Convert the chars to a string
                        String readString = String.copyValueOf(inputBuffer, 0, charRead);
                        s += readString;
                        inputBuffer = new char[READ_BLOCK_SIZE];
                    }
                    isr.close();
                    // Set the Edit Text with the text has been read
                    editText.setText(s);
                    Toast.makeText(getApplicationContext(), "File loaded sucessfully!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        InputStream inputStream = getResources().openRawResource(R.raw.rawtextfiles);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String string = null;
        try {
            while ((string = bufferedReader.readLine()) != null) {
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
            }
            inputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}