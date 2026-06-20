package com.example.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CrearCitaFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.crearcita, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AutoCompleteTextView etServicio = view.findViewById(R.id.etServicio);
        AutoCompleteTextView etBarbero = view.findViewById(R.id.etBarbero);
        Button btnAgendar = view.findViewById(R.id.btnAgendarCita);

        // Opciones de ejemplo para los dropdowns
        String[] servicios = {"Corte de Cabello", "Barba", "Tinte", "Completo"};
        String[] barberos = {"Juan", "Pedro", "Luis", "Carlos"};

        ArrayAdapter<String> adapterServicios = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, servicios);
        etServicio.setAdapter(adapterServicios);

        ArrayAdapter<String> adapterBarberos = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, barberos);
        etBarbero.setAdapter(adapterBarberos);

        btnAgendar.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Cita agendada correctamente", Toast.LENGTH_SHORT).show();
            dismiss(); // Cerrar la pestaña
        });
    }
}
