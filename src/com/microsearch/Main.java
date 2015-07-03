package com.microsearch;

import java.io.*;
import edu.cmu.sphinx.api.*;

public class Main {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        for (String source : args) {
            String speechFileName = source + ".wav";
            String textFileName = source + ".txt";
            try {
                StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
                recognizer.startRecognition(new FileInputStream(speechFileName));

                BufferedWriter out = new BufferedWriter(new FileWriter(textFileName));
                out.write("source: " + speechFileName + "\n-------\n");

                SpeechResult result;
                while ((result = recognizer.getResult()) != null) {
                    out.write(result.getHypothesis());
                    out.newLine();
                }

                out.close();
                recognizer.stopRecognition();
            } catch (IOException ex) {
                System.out.println("IOException thrown");
                System.out.println(ex.getMessage());
            }
        }
    }
}
