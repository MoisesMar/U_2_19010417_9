package net.n_19010417.basededatossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText codigo;
    private EditText descripcion;
    private EditText precio;

    private Button alta;
    private Button concod;
    private Button condes;
    private Button baja;
    private Button modificacion;
    private Button limpiar;
    private Button salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codigo = findViewById(R.id.codigo);
        descripcion = findViewById(R.id.descripcion);
        precio = findViewById(R.id.precio);

        alta = findViewById(R.id.alta);
        concod = findViewById(R.id.baja);
        condes = findViewById(R.id.condes);
        baja = findViewById(R.id.baja);
        modificacion = findViewById(R.id.modificacion);
        limpiar = findViewById(R.id.limpiar);
        salir = findViewById(R.id.salirr);

        alta.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alta(view);
                    }
                }
        );

        concod.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        concod(view);
                    }
                }
        );

        condes.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        condes(view);
                    }
                }
        );

        baja.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        baja(view);
                    }
                }
        );

        modificacion.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        modificacion(view);
                    }
                }
        );

        limpiar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alta.setText("");
                        descripcion.setText("");
                        precio.setText("");
                    }
                }
        );

        salir.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.exit(0);
                    }
                }
        );

    }

    private void alta(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "inventario", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String cad = codigo.getText().toString();
        Cursor fila = db.rawQuery("select descripcion, precio from articulos where codigo="+
                codigo, null);
        if (fila.moveToFirst()){
            descripcion.setText(fila.getString(0));
            precio.setText(fila.getString(1));
        }else {
            //Manejar la información como registro
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo.getText().toString());
            registro.put("descripcion", descripcion.getText().toString());
            registro.put("precio", precio.getText().toString());
            db.insert("articulos", null, registro);
            db.close();

            //Como SQL
            //db.execSQL("insert into articulos values ('"+codigo.getText().toString()+"','"+descripcion.getText().toString()+"',"+ precio.getText().toString()+")");
        }



    }

    private void concod(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this, "inventario", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String cod = codigo.getText().toString();
        Cursor fila = db.rawQuery(
                "select descripcion, precio from articulos where codigo ="+
                cod, null);
        if (fila.moveToFirst()){
            descripcion.setText(fila.getString(0));
            precio.setText(fila.getString(1));
        }else {
            Toast.makeText(this, "No existe un articulo con dicho código", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void condes(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this, "inventario", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String des = descripcion.getText().toString();
        Cursor fila = db.rawQuery(
                "select codigo, precio from articulos where descripcion ="+
                        des, null);
        if (fila.moveToFirst()){
            descripcion.setText(fila.getString(0));
            precio.setText(fila.getString(1));
        }else {
            Toast.makeText(this, "No existe un articulo con dicha descripción", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void baja(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this, "inventario", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String cod = codigo.getText().toString();
        int cant = db.delete("articulos", "codigo" + cod,
                null);
        //db.execSQL("delete from articulos where codigo = " + cod);

        db.close();
        codigo.setText("");
        descripcion.setText("");
        precio.setText("");
        if (cant == 1){
            Toast.makeText(this, "Se borro el articulo correctamente", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificacion(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this, "inventario", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String cod = codigo.getText().toString();
        String des = descripcion.getText().toString();
        String pre = precio.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("descripcion", des);
        registro.put("precio", pre);
        int cant = db.update("articulos", registro, "codigo"+cod, null);
        db.close();
        //db.execSQL("update articulos set codigo = "+cod, descripcion ='"+des');
        if (cant == 1){
            Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "No existe un articulo con ese código", Toast.LENGTH_SHORT).show();
        }

    }

}