package com.example.menu;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilFragment extends Fragment {

    private TextInputEditText etLoginEmail, etLoginPassword;
    private FirebaseAuth mAuth;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etLoginEmail = view.findViewById(R.id.etLoginEmail);
        etLoginPassword = view.findViewById(R.id.etLoginPassword);

        // Si ya hay una sesión iniciada, podrías mostrar datos o redirigir
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Ejemplo: cambiar texto para indicar que ya está logueado
            view.findViewById(R.id.textView4).setVisibility(View.GONE);
            view.findViewById(R.id.tilEmail).setVisibility(View.GONE);
            view.findViewById(R.id.tilPassword).setVisibility(View.GONE);
            view.findViewById(R.id.btnLogin).setVisibility(View.GONE);
            
            // Podrías añadir un botón de cerrar sesión aquí
        }

        view.findViewById(R.id.tvGoToRegister).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_perfil_to_registroFragment);
        });

        view.findViewById(R.id.btnLogin).setOnClickListener(v -> iniciarSesion());
    }

    private void iniciarSesion() {
        String email = etLoginEmail.getText().toString().trim();
        String password = etLoginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etLoginEmail.setError("Correo requerido");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etLoginPassword.setError("Contraseña requerida");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Sesión iniciada", Toast.LENGTH_SHORT).show();
                        // Redirigir a inicio o refrescar vista
                        Navigation.findNavController(requireView()).navigate(R.id.inicio);
                    } else {
                        Toast.makeText(getContext(), "Error: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
