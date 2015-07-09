package com.microsearch;

import java.io.*;
import java.util.Collection;

import edu.cmu.sphinx.api.*;
import edu.cmu.sphinx.result.WordResult;

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
                    DumpSpeechResult(out, result);
                }

                out.close();
                recognizer.stopRecognition();
            } catch (IOException ex) {
                System.out.println("IOException: " + ex.getMessage());
            }
        }
    }

    private static void DumpSpeechResult(BufferedWriter out, SpeechResult result) throws IOException {
        out.write(result.getHypothesis());
        out.newLine();
        for (WordResult wp : result.getWords()) {
            out.write("  " + wp.toString());
            out.write(" conf: ");
            out.write(Double.toString(wp.getConfidence()));
            out.write(", score: ");
            out.write(Double.toString(wp.getScore()));
            out.write("\n    ");
            out.write(wp.getPronunciation().toString());
            out.newLine();
        }
    }
}
