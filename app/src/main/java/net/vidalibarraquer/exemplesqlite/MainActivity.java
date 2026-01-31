package net.vidalibarraquer.exemplesqlite;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etNom, etCognoms, etTelefon, etMarca, etModel, etMatricula;
    EditText etBuscarMatricula;
    Button btnAdd, btnBuscar;
    RecyclerView recycler;

    ManegadorDades db;
    List<Vehicle> vehicles;
    VehicleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNom = findViewById(R.id.etNom);
        etCognoms = findViewById(R.id.etCognoms);
        etTelefon = findViewById(R.id.etTelefon);
        etMarca = findViewById(R.id.etMarca);
        etModel = findViewById(R.id.etModel);
        etMatricula = findViewById(R.id.etMatricula);
        btnAdd = findViewById(R.id.btnAdd);

        etBuscarMatricula = findViewById(R.id.etBuscarMatricula);
        btnBuscar = findViewById(R.id.btnBuscar);

        recycler = findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        db = new ManegadorDades(this);
        vehicles = db.getAllVehicles();
        adapter = new VehicleAdapter(vehicles);
        recycler.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            Vehicle vehicle = new Vehicle();
            vehicle.setNom(etNom.getText().toString());
            vehicle.setCognoms(etCognoms.getText().toString());
            vehicle.setTelefon(etTelefon.getText().toString());
            vehicle.setMarca(etMarca.getText().toString());
            vehicle.setModel(etModel.getText().toString());
            vehicle.setMatricula(etMatricula.getText().toString());

            if (!db.addVehicle(vehicle)) {
                Toast.makeText(this,
                        "Error: matrícula repetida",
                        Toast.LENGTH_LONG).show();
            } else {
                vehicles.add(vehicle);
                adapter.notifyItemInserted(vehicles.size() - 1);
                Toast.makeText(this,
                        "Vehicle afegit",
                        Toast.LENGTH_SHORT).show();

                etNom.setText("");
                etCognoms.setText("");
                etTelefon.setText("");
                etMarca.setText("");
                etModel.setText("");
                etMatricula.setText("");
            }

        });

        btnBuscar.setOnClickListener(v -> {
            String matricula = etBuscarMatricula.getText().toString().trim();
            if (matricula.isEmpty()) {
                Toast.makeText(this, "Introdueix una matrícula", Toast.LENGTH_SHORT).show();
                return;
            }

            Vehicle vehicle = db.getByMatricula(matricula);

            if (vehicle == null) {
                Toast.makeText(this,
                        "No existeix aquesta matrícula",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "Propietari: " + vehicle.getNom() + " " +
                                vehicle.getCognoms(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
