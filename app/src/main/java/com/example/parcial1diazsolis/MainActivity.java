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


        // Eventos reales usando Intents y Extras
        btnDia1.setOnClickListener(v -> abrirDetalle(
                "Pecho, Hombro y Tríceps",
                "• Machine Press (1 serie x 100kg)\n• Aperturas en Pec Deck (1 serie al fallo)\n• Elevaciones Laterales (1 serie pesada)\n• Press Francés (1 serie al fallo)",
                "https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?q=80&w=1000&auto=format&fit=crop"
        ));

        btnDia2.setOnClickListener(v -> abrirDetalle(
                "Día de Piernas",
                "• Prensa 45° (1 serie x 270kg)\n• Hack Squat (1 serie x 160kg)\n• Peso Muerto Rumano / RDL (1 serie x 80kg)\n• Elevación de Talones (1 serie al fallo)",
                "https://images.unsplash.com/photo-1574680178050-55c6a6a96e0a?q=80&w=1000&auto=format&fit=crop"
        ));

        btnDia3.setOnClickListener(v -> abrirDetalle(
                "Espalda y Bíceps",
                "• Dominadas Agarre Supino (1 serie al fallo)\n• Remo T-Bar (1 serie x 75kg)\n• Pullover en Polea Alta (1 serie pesada)\n• Curl de Bíceps con Barra W (1 serie al fallo)",
                "https://images.unsplash.com/photo-1603287681836-b174ce5074c2?q=80&w=1000&auto=format&fit=crop"
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