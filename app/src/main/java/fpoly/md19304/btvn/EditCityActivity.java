package fpoly.md19304.btvn;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditCityActivity extends AppCompatActivity {
    private EditText etCity;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userId;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_city);

        etCity = findViewById(R.id.etCity);
        btnSave = findViewById(R.id.btnSave);

        // Nhận userId từ Intent
        userId = getIntent().getStringExtra("USER_ID");
        loadUserData();

        btnSave.setOnClickListener(v -> {
            String newCity = etCity.getText().toString().trim();
            if (!newCity.isEmpty()) {
                updateCity(newCity);
            } else {
                Toast.makeText(EditCityActivity.this, "City cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserData() {
        db.collection("users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                user = documentSnapshot.toObject(User.class);
                if (user != null) {
                    etCity.setText(user.getCity());
                }
            }
        });
    }

    private void updateCity(String newCity) {
        db.collection("users").document(userId)
                .update("city", newCity)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditCityActivity.this, "City Updated", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditCityActivity.this, "Error updating city", Toast.LENGTH_SHORT).show());
    }
}
