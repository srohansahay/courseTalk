package com.example.coursetalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent Course1 = new Intent(getApplicationContext(), Course1.class);
                startActivity(Course1);
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("Button Clicked");
                Intent Course2 = new Intent(getApplicationContext(), Course2.class);
                startActivity(Course2);
            }

        });
        button3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent Course3 = new Intent(getApplicationContext(), Course3.class);
                startActivity(Course3);
            }

        });
        button4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent Course4 = new Intent(getApplicationContext(), Course4.class);
                startActivity(Course4);
            }

        });
        button5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent Course5 = new Intent(getApplicationContext(), Course5.class);
                startActivity(Course5);
            }

        });
    }
}