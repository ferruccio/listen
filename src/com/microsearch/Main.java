package com.microsearch;

import java.io.*;
import edu.cmu.sphinx.api.*;

public class Main {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        try {
            StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
            recognizer.startRecognition(new FileInputStream("speech.wav"));
            SpeechResult result;

            System.out.println("----------------------------------------------");
            while ((result = recognizer.getResult()) != null) {
                System.out.println(result.getHypothesis());
            }
            recognizer.stopRecognition();
        } catch (IOException ex) {
            System.out.println("IOException thrown");
            System.out.println(ex.getMessage());
        }
    }
}
