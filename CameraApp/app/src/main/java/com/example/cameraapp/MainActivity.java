package com.example.cameraapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView imgHinh;
    Button btnSave, btnList;
    EditText editTextAlbum;
    Calendar time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        //Database
        SQLite db = new SQLite(getApplicationContext(),"Hinh.sqlite",null,1);

        db.QuerryData("CREATE TABLE IF NOT EXISTS Hinh(id INTEGER PRIMARY KEY AUTOINCREMENT, hinh BLOB , album VARCHAR)");

        //SAVE
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.INSERT_Hinh(
                            ImageViewToByte(imgHinh),
                            editTextAlbum.getText().toString()

                    );
                    Toast.makeText(getApplicationContext(), "Thanh cong!",Toast.LENGTH_SHORT).show();
                } catch (Exception e ){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //LIST

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent danhsach = new Intent(getApplicationContext(),ListPhoActivity.class);
                startActivity(danhsach);

            }
        });

        //Chup hinh
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chupHinh = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(chupHinh,888);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(photo);
        }
    }

    public void AnhXa() {
        imgHinh = (ImageView) findViewById(R.id.imageHinh);
        btnSave = (Button) findViewById(R.id.buttonSavePho);
        btnList = (Button) findViewById(R.id.buttonListPho);
        editTextAlbum = (EditText) findViewById(R.id.editTextAlbum);
    }

    public byte[] ImageViewToByte (ImageView h) {
        BitmapDrawable drawable = (BitmapDrawable) h.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void pushNotifications() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle("Camera App")
                .setContentText("Time to tank a photo")
                .setSmallIcon(R.drawable.camera)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.white))
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getNotificationID(), notification);
    }

    private int getNotificationID() { return (int) new Date().getTime(); }

    private void doNotificationID() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pushNotifications();
            }
        },5000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

            doNotificationID();

    }


}