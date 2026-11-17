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


        btnDia1.setOnClickListener(v -> abrirDetalle(
                "Pecho, Hombro y Tríceps",
                "1. Press de Banca Plano\n2. Press Inclinado\n3. Press Francés con Barra W\n4. Elevaciones Laterales",
                "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?q=80&w=800&auto=format&fit=crop"
        ));

        btnDia2.setOnClickListener(v -> abrirDetalle(
                "Día de Piernas",
                "1. Sentadilla Pesada\n2. Prensa 45°\n3. Curl Femoral\n4. Elevación de Talones",
                "https://images.unsplash.com/photo-1434682881908-b43d0467b798?q=80&w=800&auto=format&fit=crop"
        ));

        btnDia3.setOnClickListener(v -> abrirDetalle(
                "Espalda y Bíceps",
                "1. Peso Muerto\n2. Dominadas (Agarre Supino)\n3. Remo en Máquina\n4. Curl de Bíceps",
                "https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?q=80&w=800&auto=format&fit=crop"
        ));
    }

    // Método auxiliar para armar el Intent y empaquetar los Extras
    private void abrirDetalle(String titulo, String ejercicios, String urlImagen) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("TITULO", titulo);
        intent.putExtra("EJERCICIOS", ejercicios);
        intent.putExtra("URL_IMAGEN", urlImagen);
        startActivity(intent);
    }

}