package com.cp3406test.photoeditor;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (Display)findViewById(R.id.display);

        Intent chooseImage = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(chooseImage , 1);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Position position = display.getPosition(event.getX(), event.getY());

        switch(position){
            case BOTTOM_LEFT:
                display.setMessage("message1");
                break;
            case BOTTON_RIGHT:
                display.setMessage("message2");
                break;
            case TOP_LEFT:
                display.setMessage("message3");
                break;
            case TOP_RIGHT:
                display.setMessage("message4");
                break;
            case MIDDLE:
                display.setMessage("message5");
                break;
        }
        return super.onTouchEvent(event);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Drawable backgroundImg;
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        backgroundImg = Drawable.createFromStream(inputStream, selectedImage.toString());
                        display.setBackground(backgroundImg);
                    } catch (FileNotFoundException e) {}
                }
                break;
        }
    }
}
