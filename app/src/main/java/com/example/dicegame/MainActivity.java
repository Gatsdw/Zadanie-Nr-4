package com.example.dicegame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // inicjalizacja i podlinkowanie zmiennych z activity_main.xml
    private TextView textRes1, textRes2, textRes3, textRes4, textRes5, textCount, textThisResults, textResults;
    private Button buttonThrow, buttonReset;
    private int rollCount = 0;
    private int cumulativeSum = 0;
    int[] diceCounts = new int[6];
    int sumOfThisRoll = 0;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicjalizacja widokow
        textRes1 = findViewById(R.id.textRes1);
        textRes2 = findViewById(R.id.textRes2);
        textRes3 = findViewById(R.id.textRes3);
        textRes4 = findViewById(R.id.textRes4);
        textRes5 = findViewById(R.id.textRes5);
        textCount = findViewById(R.id.textCount);
        textThisResults = findViewById(R.id.textThisResults);
        textResults = findViewById(R.id.textResults);
        buttonThrow = findViewById(R.id.buttonThrow);
        buttonReset = findViewById(R.id.buttonReset);
        random = new Random();

        // przycisk do rzucania kostka
        buttonThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice(); // wywoluje funkcje (ponizej tak samo)
                updateRollCount();
                updateScore();
            }
        });

        // przycisk do resetu gry
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    // funkcja rolldice ktora przypisuje losowe liczby do viewtextow, resetuje sume wylosowanych liczb oraz ilosc wystapien
    private void rollDice() {
        sumOfThisRoll = 0;
        diceCounts = new int[6];

        textRes1.setText(String.valueOf(random.nextInt(6) + 1));
        textRes2.setText(String.valueOf(random.nextInt(6) + 1));
        textRes3.setText(String.valueOf(random.nextInt(6) + 1));
        textRes4.setText(String.valueOf(random.nextInt(6) + 1));
        textRes5.setText(String.valueOf(random.nextInt(6) + 1));
    }

    // funkcja ktora aktualizuje liczbe rzutow
    private void updateRollCount() {
        rollCount++;
        textCount.setText("Liczba rzutów: " + rollCount);
    }

    // funkcja ktora sumuje obecne wyniki
    private void updateScore() {
        int[] diceResults = new int[5];

        // Collect dice results from the text fields
        diceResults[0] = Integer.parseInt(textRes1.getText().toString());
        diceResults[1] = Integer.parseInt(textRes2.getText().toString());
        diceResults[2] = Integer.parseInt(textRes3.getText().toString());
        diceResults[3] = Integer.parseInt(textRes4.getText().toString());
        diceResults[4] = Integer.parseInt(textRes5.getText().toString());

        // zliczanie wystąpien liczb
        for(int i = 0; i < diceResults.length; i++) {
            diceCounts[diceResults[i] - 1]++;  // zliczamy wystąpienia
        }

        // zliczanie punktow na podstawie powtorzen
        for (int i = 0; i < diceCounts.length; i++) {
            if (diceCounts[i] >= 2) {  // jesli liczba wystapi wiecej lub rowno 2
                sumOfThisRoll += (i + 1) * diceCounts[i];  // dodajemy wartosc wyrzuconej liczby pomnozona przez liczbe powtorzen
            }
        }

        // dodanie wyniku rzutu do ogolnego wyniku gry
        cumulativeSum += sumOfThisRoll;

        // aktualizacja
        textThisResults.setText("Wynik tego losowania: " + sumOfThisRoll);
        textResults.setText("Wynik gry: " + cumulativeSum);
    }

    // funkcja resetujaca gre
    private void resetGame() {
        textRes1.setText("");
        textRes2.setText("");
        textRes3.setText("");
        textRes4.setText("");
        textRes5.setText("");

        textCount.setText("Liczba rzutów: 0");
        textThisResults.setText("Wynik tego losowania: 0");
        textResults.setText("Wynik gry: 0");

        rollCount = 0;
        cumulativeSum = 0;
        diceCounts = new int[6];
    }
}