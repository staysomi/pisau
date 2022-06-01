package pepe.pisau.bos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pepe.pisau.bos.data.Data;
import pepe.pisau.bos.data.Pref;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText username, password;
    Button cek, login;
    String strusername, strpassword;
    String TAG = LoginActivity.class.getSimpleName();
    Pref pref;
    ArrayList<String> arrUID = new ArrayList<>();
    ArrayList<String> arrEmail = new ArrayList<>();
    ArrayList<String> arrPass = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = new Pref(this);
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance(Data.DATABASE_URL);
        DatabaseReference userRef = database.getReference();

//        DAFTAR USER
//        mAuth.createUserWithEmailAndPassword(strusername, strpassword)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(LoginActivity.this, "Salah Password.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        userRef.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String uid = ds.child("id").getValue(String.class);
                    String email = ds.child("email").getValue(String.class);
                    arrUID.add(uid);
                    arrEmail.add(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Fail");
            }
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cek = findViewById(R.id.btn_cek_user);
        login = findViewById(R.id.btn_login);
        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strusername = String.valueOf(username.getText());
                if (strusername.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Isi Data", Toast.LENGTH_SHORT).show();
                } else {
                    if (arrUID.contains(strusername)) {
                        ValueEventListener postListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String pass = dataSnapshot.child("user").child(strusername).child("password").getValue(String.class);
                                arrPass.add(pass);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d(TAG, "onCancelled: " + databaseError);
                            }
                        };
                        userRef.addValueEventListener(postListener);
                        cek.setVisibility(View.GONE);
                        password.setVisibility(View.VISIBLE);
                        login.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(LoginActivity.this, "Username Tidak terdaftar", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strpassword = String.valueOf(password.getText());
                if (arrPass.get(0).equals(strpassword)) {
                    mAuth.signInWithEmailAndPassword("bos@gmail.com", "bosbos123")
                            .addOnCompleteListener(LoginActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    Intent inte = new Intent(LoginActivity.this, Dashboard.class);
                                    inte.putExtra("nama", strusername);
                                    startActivity(inte);
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}