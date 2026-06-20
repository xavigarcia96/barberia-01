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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroFragment extends Fragment {

    private TextInputEditText etRegEmail, etRegPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public RegistroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etRegEmail = view.findViewById(R.id.etRegEmail);
        etRegPassword = view.findViewById(R.id.etRegPassword);

        view.findViewById(R.id.tvBackToLogin).setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        view.findViewById(R.id.btnRegister).setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String email = etRegEmail.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etRegEmail.setError("Correo requerido");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etRegPassword.setError("Contraseña requerida");
            return;
        }
        if (password.length() < 6) {
            etRegPassword.setError("Mínimo 6 caracteres");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            guardarUsuarioEnDatabase(user.getUid(), email);
                        }
                    } else {
                        Toast.makeText(getContext(), "Error en el registro: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void guardarUsuarioEnDatabase(String userId, String email) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);

        mDatabase.child("Usuarios").child(userId).setValue(userData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireView()).navigate(R.id.perfil);
                    } else {
                        Toast.makeText(getContext(), "Error al guardar datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
