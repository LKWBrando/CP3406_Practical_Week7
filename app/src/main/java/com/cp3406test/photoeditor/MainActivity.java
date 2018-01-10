package com.cp3406test.photoeditor;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {

    private Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        display = findViewById(R.id.display);
        Intent chooseImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(chooseImage , 1);
    }

    /*
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
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap loadedBitmap = BitmapFactory.decodeFile(picturePath);
            Bitmap drawableBitmap = loadedBitmap.copy(Bitmap.Config.ARGB_8888, true);
            display.setCanvasBitmap(drawableBitmap);

        }
    }
}
