package com.csming.colorful;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.csming.colorfulnavigation.ColorfulNavigation;

public class MainActivity extends AppCompatActivity {

    private ColorfulNavigation mColorfulNavigation;

    private static final int ID_1 = 1;
    private static final int ID_2 = 2;
    private static final int ID_3 = 3;
    private static final int ID_4 = 4;
    private static final int ID_5 = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorfulNavigation = findViewById(R.id.colorful_navigation);
        mColorfulNavigation.add(new ColorfulNavigation.Item(ID_1, R.drawable.ic_home_black_24dp, R.color.test1, "Item 1"));
        mColorfulNavigation.add(new ColorfulNavigation.Item(ID_2, R.drawable.ic_home_black_24dp, R.color.test2, "Item 2"));
        mColorfulNavigation.add(new ColorfulNavigation.Item(ID_3, R.drawable.ic_home_black_24dp, R.color.test3, "Item 3"));
        mColorfulNavigation.add(new ColorfulNavigation.Item(ID_4, R.drawable.ic_home_black_24dp, R.color.test4, "Item 4"));
        mColorfulNavigation.add(new ColorfulNavigation.Item(ID_5, R.drawable.ic_home_black_24dp, R.color.test5, "Item 5"));

        mColorfulNavigation.setSelectedItem(0);
//        mColorfulNavigation.setOnItemSelectedListener(new ColorfulNavigation.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(ColorfulNavigation.Item item) {
//                Toast.makeText(MainActivity.this, "" + item.getId(), Toast.LENGTH_SHORT).show();
//                switch(item.getId()) {
//                    case ID_1 :{
//                        break;
//                    }
//                    case ID_2: {
//                        break;
//                    }
//                    case ID_3 :{
//                        break;
//                    }
//                    case ID_4: {
//                        break;
//                    }
//                    case ID_5: {
//                        break;
//                    }
//                    default:{
//                        break;
//                    }
//                }
//            }
//        });
    }
}
