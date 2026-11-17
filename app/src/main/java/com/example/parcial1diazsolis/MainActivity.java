package com.example.parcial1diazsolis;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnDia1, btnDia2, btnDia3;

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

        // modulo Integrante 1: Cuenta

        TextView tvCuenta = findViewById(R.id.tvCuenta);

        tvCuenta.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(MainActivity.this, v);

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String userEmail = (currentUser != null && currentUser.getEmail() != null)
                    ? currentUser.getEmail()
                    : "Usuario conectado";

            popup.getMenu().add(userEmail);
            popup.getMenu().add("Cerrar Sesión");

            popup.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("Cerrar Sesión")) {
                    FirebaseAuth.getInstance().signOut();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            });

            popup.show();
        });


        // modulo Integrante 2: planificador de Rutinas

        btnDia1 = findViewById(R.id.btnDia1);
        btnDia2 = findViewById(R.id.btnDia2);
        btnDia3 = findViewById(R.id.btnDia3);

        // Eventos temporales para pruebas (reemplazar con lógica real)
        btnDia1.setOnClickListener(v -> Toast.makeText(this, "Cargando rutina de Pecho...", Toast.LENGTH_SHORT).show());
        btnDia2.setOnClickListener(v -> Toast.makeText(this, "Cargando rutina de Piernas...", Toast.LENGTH_SHORT).show());
        btnDia3.setOnClickListener(v -> Toast.makeText(this, "Cargando rutina de Espalda...", Toast.LENGTH_SHORT).show());
    }
}