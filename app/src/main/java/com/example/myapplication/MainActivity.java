package com.example.myapplication;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "live_update_channel";
    private static final int NOTIFICATION_ID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Live Update Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Shows live update notifications");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(false);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        showLiveUpdate(this,40);
    }

    @RequiresApi(api = Build.VERSION_CODES.P) // You can remove this or change to S if needed
    public static void showLiveUpdate(Context context, int progress) {
        // Create channel if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Live Update Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Shows live update notifications");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(false);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= 36) { // Android 16 (API 36)
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("Order Status")
                    .setContentText("Progress: " + progress + "%")
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .setStyle(new NotificationCompat.ProgressStyle().setStyledByProgress(false)
                            .setProgress(456)
                            .setProgressTrackerIcon(IconCompat.createWithResource(context, android.R.drawable.arrow_up_float))
                            .setProgressSegments(
                                    Arrays.asList(new NotificationCompat.ProgressStyle.Segment(41).setColor(Color.GREEN),
                                            new NotificationCompat.ProgressStyle.Segment(552).setColor(Color.GREEN),
                                            new  NotificationCompat.ProgressStyle.Segment(253).setColor(Color.GREEN),
                                            new NotificationCompat.ProgressStyle.Segment(152).setColor(Color.GREEN),
                                            new  NotificationCompat.ProgressStyle.Segment(153).setColor(Color.GREEN),
                                            new NotificationCompat.ProgressStyle.Segment(194).setColor(Color.GRAY),
                                            new NotificationCompat.ProgressStyle.Segment(124).setColor(Color.GRAY)
                                            )

                            ))
                    .setShortCriticalText("Placed")
                    .setOngoing(true)
                    .setProgress(100, progress, false)
                    .setRequestPromotedOngoing(true);
        } else {
            // Fallback for lower versions
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("Order Status")
                    .setContentText("Progress: " + progress + "%")
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .setOngoing(true)
                    .setProgress(100, progress, false);
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build());
    }
}