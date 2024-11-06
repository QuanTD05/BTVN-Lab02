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

import java.util.HashMap;
import java.util.Map;

public class ThemCityActivity extends AppCompatActivity {

    private EditText etName, etCity;
    private Button btnAddCity;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_city);

        etName = findViewById(R.id.etName);
        etCity = findViewById(R.id.etCity);
        btnAddCity = findViewById(R.id.btnAddCity);

        btnAddCity.setOnClickListener(v -> addCityToFirestore());
    }

    private void addCityToFirestore() {
        String name = etName.getText().toString();
        String city = etCity.getText().toString();

        if (!name.isEmpty() && !city.isEmpty()) {
            Map<String, Object> user = new HashMap<>();
            user.put("name", name);
            user.put("city", city);

            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(ThemCityActivity.this, "City Added", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ThemCityActivity.this, "Error adding city", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}