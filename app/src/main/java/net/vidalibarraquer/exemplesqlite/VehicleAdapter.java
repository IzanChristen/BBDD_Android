package net.vidalibarraquer.exemplesqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {

    private List<Vehicle> list;

    public VehicleAdapter(List<Vehicle> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int pos) {
        Vehicle v = list.get(pos);
        h.txt.setText(
                "Matrícula: " + v.getMatricula() + "\n" +
                        "Vehicle: " + v.getMarca() + " " + v.getModel() + "\n" +
                        "Propietari: " + v.getNom() + " " + v.getCognoms()
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;

        ViewHolder(View v) {
            super(v);
            txt = v.findViewById(R.id.textElement);

            v.setOnClickListener(view -> {
                Vehicle vehicle = list.get(getAdapterPosition());
                Context context = view.getContext();
                ManegadorDades db = new ManegadorDades(context);

                String[] opcions = {"Modificar", "Eliminar"};

                new AlertDialog.Builder(context)
                        .setTitle("Opció")
                        .setItems(opcions, (dialog, which) -> {
                            if (which == 0) {
                                mostrarDialogModificar(context, db, vehicle, getAdapterPosition());
                            }
                            if (which == 1) {
                                db.deleteVehicle(vehicle);
                                list.remove(vehicle);
                                notifyItemRemoved(getAdapterPosition());
                                Toast.makeText(context, "Vehicle eliminat", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            });
        }

        private void mostrarDialogModificar(Context context, ManegadorDades db, Vehicle vehicle, int position) {
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_modificar, null);
            EditText etNom = dialogView.findViewById(R.id.etNomDialog);
            EditText etCognoms = dialogView.findViewById(R.id.etCognomsDialog);
            EditText etTelefon = dialogView.findViewById(R.id.etTelefonDialog);
            EditText etMarca = dialogView.findViewById(R.id.etMarcaDialog);
            EditText etModel = dialogView.findViewById(R.id.etModelDialog);

            etNom.setText(vehicle.getNom());
            etCognoms.setText(vehicle.getCognoms());
            etTelefon.setText(vehicle.getTelefon());
            etMarca.setText(vehicle.getMarca());
            etModel.setText(vehicle.getModel());

            new AlertDialog.Builder(context)
                    .setTitle("Modificar Vehicle")
                    .setView(dialogView)
                    .setPositiveButton("Guardar", (d, i) -> {
                        vehicle.setNom(etNom.getText().toString());
                        vehicle.setCognoms(etCognoms.getText().toString());
                        vehicle.setTelefon(etTelefon.getText().toString());
                        vehicle.setMarca(etMarca.getText().toString());
                        vehicle.setModel(etModel.getText().toString());

                        db.updateVehicle(vehicle);

                        notifyItemChanged(position);
                        Toast.makeText(context, "Vehicle modificat", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }
    }
}
