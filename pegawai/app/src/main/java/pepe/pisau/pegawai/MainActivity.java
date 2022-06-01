package pepe.pisau.pegawai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

import pepe.pisau.pegawai.data.Data;

public class MainActivity extends AppCompatActivity {

    PackageInfo packageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance(Data.DATABASE_URL);

        try {
            packageInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        int curVersionCode = packageInfo.versionCode;
        final int[] version = new int[1];
        Query queryversion = database.getReference().child("version-pegawai");
        queryversion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                version[0] = snapshot.getValue(Integer.class);
                if (curVersionCode < version[0]) {
                    alertVersion();
                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }, 3000);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void alertVersion() {
        AlertDialog.Builder b1 = new AlertDialog.Builder(MainActivity.this);
        b1.setMessage("Silahkan Download yang terbaru dari Web");
        b1.setCancelable(false);

        b1.setPositiveButton(
                "TUTUP",
                (dialog, id) -> {
                    Uri uri = Uri.parse(Data.URL_DOWNLOAD);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                });

        AlertDialog alert11 = b1.create();
        alert11.show();
    }
}