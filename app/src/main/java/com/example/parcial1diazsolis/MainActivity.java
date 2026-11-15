package com.example.parcial1diazsolis;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Spinner spEjercicios;
    private Button btnAgregar;
    private LinearLayout layoutContenedorEjercicios;

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

        // mapeo de vistas
        spEjercicios = findViewById(R.id.spEjercicios);
        btnAgregar = findViewById(R.id.btnAgregar);
        layoutContenedorEjercicios = findViewById(R.id.layoutContenedorEjercicios);

        // opciones del Spinner
        String[] ejercicios = {
                "Press de Banca Plano",
                "Press Frances con Barra W",
                "Sentadilla Pesada",
                "Peso Muerto"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ejercicios);
        spEjercicios.setAdapter(adapter);

        // evento del botón para agregar ejercicio
        btnAgregar.setOnClickListener(v -> agregarEjercicio());
    }

    private void agregarEjercicio() {
        if (spEjercicios.getSelectedItem() == null) return;

        String nombreEjercicio = spEjercicios.getSelectedItem().toString();

        // inflar el CardView desde item_ejercicio.xml
        LayoutInflater inflater = LayoutInflater.from(this);
        View tarjetaView = inflater.inflate(R.layout.item_ejercicio, layoutContenedorEjercicios, false);

        // mapear elementos internos de la tarjeta inflada
        TextView tvNombre = tarjetaView.findViewById(R.id.tvNombreEjercicio);
        TextView tvDetalle = tarjetaView.findViewById(R.id.tvDetalleEjercicio);
        ImageView imgEjercicio = tarjetaView.findViewById(R.id.imgEjercicio);
        Button btnEliminar = tarjetaView.findViewById(R.id.btnEliminar);

        // asignar datos dinámicos
        tvNombre.setText(nombreEjercicio);
        tvDetalle.setText(getString(R.string.detalle_ejercicio));

        // cambiar la imagen dinámicamente según el texto
        if (nombreEjercicio.contains("Banca")) {
            imgEjercicio.setImageResource(R.drawable.img_press_banca);
        } else if (nombreEjercicio.contains("Frances")) {
            imgEjercicio.setImageResource(R.drawable.img_press_frances);
        } else if (nombreEjercicio.contains("Sentadilla")) {
            imgEjercicio.setImageResource(R.drawable.img_sentadilla);
        } else if (nombreEjercicio.contains("Peso Muerto")) {
            imgEjercicio.setImageResource(R.drawable.img_peso_muerto);
        } else {
            // imagen por defecto si no coincide con ninguna
            imgEjercicio.setImageResource(R.drawable.img_press_banca);
        }

        // boton de eliminar
        btnEliminar.setOnClickListener(v -> {
            layoutContenedorEjercicios.removeView(tarjetaView);
            Toast.makeText(this, getString(R.string.toast_eliminado), Toast.LENGTH_SHORT).show();
        });

        // boton de listo y saltar
        Button btnListo = tarjetaView.findViewById(R.id.btnListo);
        Button btnSaltar = tarjetaView.findViewById(R.id.btnSaltar);

        // logica del botón Listo
        btnListo.setOnClickListener(v -> {
            tvNombre.setPaintFlags(tvNombre.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            // pone el fondo de la tarjeta verde
            ((androidx.cardview.widget.CardView) tarjetaView).setCardBackgroundColor(ContextCompat.getColor(this, R.color.fondo_completado));

            // deshabilita los botones de listo y saltar, pero no el de elimiar
            btnListo.setEnabled(false);
            btnSaltar.setEnabled(false);
        });

        // logica del botón Saltar
        btnSaltar.setOnClickListener(v -> {
            tarjetaView.setAlpha(0.5f);

            // lo mismo que en de listo
            btnListo.setEnabled(false);
            btnSaltar.setEnabled(false);
        });

        // agregar la tarjeta al ScrollView principal
        layoutContenedorEjercicios.addView(tarjetaView);
    }
}