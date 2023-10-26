package au.edu.federation.itech3107.studentattendance30395752;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    private EditText etUserId, etPassword, etConfirmPassword;
    private Button btnRegister;
    private AppDatabase database;
    private TeacherDao teacherDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Use Room to get databases and DAOs.
        database = AppDatabase.getDatabase(this);
        teacherDao = database.teacherDao();

        etUserId = findViewById(R.id.etUserId);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String userId = etUserId.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Use Room to insert teacher information into the database.
            Teacher teacher = new Teacher();

            teacher.setUsername(userId);
            teacher.setPassword(password);
            new Thread(() -> {
                teacherDao.insert(teacher);
            }).start();

            Toast.makeText(this, "Login WasSuccessful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
