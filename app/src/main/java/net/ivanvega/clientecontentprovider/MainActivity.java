package net.ivanvega.clientecontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.btnQuery).setOnClickListener(
            v -> {
                consultarCP();

            }
        );

        findViewById(R.id.btnInsert).setOnClickListener(
                v -> {
                    insertCP();
                }
        );
        findViewById(R.id.btnUpdate).setOnClickListener(
                v -> {
                    EditText text = (EditText)findViewById(R.id.txtId);
                    updateCP(text.getText().toString());
                }
        );
        findViewById(R.id.btnDelete).setOnClickListener(
                v -> {
                    EditText text = (EditText)findViewById(R.id.txtId);
                    deleteCP(text.getText().toString());
                }
        );
        findViewById(R.id.btnSearch).setOnClickListener(
                v -> {
                    EditText text = (EditText)findViewById(R.id.txtId);
                    queryBusqueda(text.getText().toString());
                }
        );

    }

    private void consultarCP() {

        Cursor c =  getContentResolver().query(
                UsuarioProviderContract.CONTENT_URI,
                UsuarioProviderContract.COLUMNS,
                null,null,null
        );

        if(c!=null){
            while(c.moveToNext()){
                Log.d("providerusuario", "Usuario: "+ c.getInt(0)
                        + " - " + c.getString(1));
            }
        }else{
            Log.d("providerusuario", "Sin Usuario: ");
        }



    }

    private void queryBusqueda(String id_busqueda){
        Cursor c =  getContentResolver().query( UsuarioProviderContract.CONTENT_URI,
                UsuarioProviderContract.COLUMNS,
                null,null,id_busqueda
        );
        if(c!=null){
            while(c.moveToNext()){
                Toast.makeText(MainActivity.this,"Usuario: "+ c.getInt(0)
                        + " - " + c.getString(1)+ " "+ c.getString(2), Toast.LENGTH_SHORT).show();
                Log.d("providerusuario", "Usuario: "+ c.getInt(0)
                        + " - " + c.getString(1));
            }
        }else{
            Toast.makeText(MainActivity.this,"Este usuario no existe", Toast.LENGTH_SHORT).show();
            Log.d("providerusuario", "Sin Usuario: ");
        }



    }
    private void insertCP () {
        ContentValues cv = new ContentValues();
        cv.put(UsuarioProviderContract.FIRSTNAME_COLUMN, "Pablo");
        cv.put(UsuarioProviderContract.LASTNAME_COLUMN, "Secundino");
        Uri uriinsert = getContentResolver().insert(UsuarioProviderContract.CONTENT_URI,
                cv);
        Log.d("providerusuario", uriinsert.toString());
    }
    private void  deleteCP(String id_delete){
        Uri uriDelete = Uri.withAppendedPath(UsuarioProviderContract.CONTENT_URI,
                id_delete);

        int filasAfectas = getContentResolver().delete(uriDelete, null, null );

        Log.d("providerusuario", "Filas afectadas: "+ filasAfectas);
    }
    private void  updateCP(String id_update){
        ContentValues cv = new ContentValues();
        cv.put(UsuarioProviderContract.FIRSTNAME_COLUMN, "David");
        cv.put(UsuarioProviderContract.LASTNAME_COLUMN, "Vega");
        Uri uriUpdate = Uri.withAppendedPath(UsuarioProviderContract.CONTENT_URI,
                id_update);
        int filasAfectas = getContentResolver().update(uriUpdate, cv, null, null );
        Log.d("providerusuario", "Filas afectadas: "+ filasAfectas);
    }
    
}