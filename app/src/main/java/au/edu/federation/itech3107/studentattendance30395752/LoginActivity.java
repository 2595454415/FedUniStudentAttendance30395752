package au.edu.federation.itech3107.studentattendance30395752;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText etLoginUserId, etLoginPassword;
    private Button btnLogin;
    private AppDatabase database;
    private TeacherDao teacherDao;
    private Teacher teacher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Use Room to get databases and DAOs
        database = AppDatabase.getDatabase(this);
        teacherDao = database.teacherDao();

        etLoginUserId = findViewById(R.id.etLoginUserId);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String userId = etLoginUserId.getText().toString().trim();
            String password = etLoginPassword.getText().toString().trim();

            Handler handler = new Handler(Looper.getMainLooper());

            new Thread(() -> {

                Teacher teacher = teacherDao.getTeacherById(userId);

                handler.post(() -> {
                    if (teacher != null && teacher.getUsername().equals(userId)) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "There is no information about this teacher, please register first", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();


        });
    }
}
