package com.example.myslidemenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.myslidemenu.view.SlideMenu;

public class MainActivity extends AppCompatActivity {

    private SlideMenu slideMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slideMenu = (SlideMenu)findViewById(R.id.slideMenu);

    }

}
