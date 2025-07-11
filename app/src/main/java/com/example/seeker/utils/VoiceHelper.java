package com.example.seeker.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class VoiceHelper {
    private static TextToSpeech tts;

    public static void playAlert(Context context, int soundRes, String message) {
        // Play sound
        MediaPlayer.create(context, soundRes).start();

        // Speak message
        speak(context, message);
    }

    public static void speak(Context context, String message) {
        if (tts == null) {
            tts = new TextToSpeech(context, status -> {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                    tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            });
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public static void shutdown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}