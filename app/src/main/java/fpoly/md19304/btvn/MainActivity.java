package fpoly.md19304.btvn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo adapter và truyền OnItemClickListener, OnDeleteClickListener, OnEditClickListener
        userAdapter = new UserAdapter(userList, this::onUserClick, this::onDeleteUserClick, this::onEditUserClick);
        recyclerView.setAdapter(userAdapter);

        Button btnthem = findViewById(R.id.btnthem);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ThemCityActivity.class));
            }
        });

        loadUserList();
    }

    private void loadUserList() {
        db.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    User user = document.toObject(User.class);
                    user.setId(document.getId());
                    userList.add(user);
                }
                userAdapter.setUserList(userList);
            }
        });
    }

    private void onUserClick(User user) {
        Toast.makeText(this, "User: " + user.getName(), Toast.LENGTH_SHORT).show();
    }

    private void onDeleteUserClick(User user) {
        deleteUser(user);
    }

    private void onEditUserClick(User user) {
        Intent intent = new Intent(MainActivity.this, EditCityActivity.class);
        intent.putExtra("USER_ID", user.getId());
        startActivity(intent);
    }

    private void deleteUser(User user) {
        db.collection("users")
                .document(user.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                    loadUserList();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Error deleting user", Toast.LENGTH_SHORT).show();
                });
    }
}