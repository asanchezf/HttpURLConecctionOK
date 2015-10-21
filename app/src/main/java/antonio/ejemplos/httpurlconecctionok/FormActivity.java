package antonio.ejemplos.httpurlconecctionok;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class FormActivity extends ActionBarActivity {

    /*
    Instancia del EditText
     */
    EditText txtNombre;
    EditText txtApellidos;
    EditText txtDireccion;
    EditText txtTelefono;
    EditText txtEmail;
    EditText txtObservaciones;

    Button btnEnviar;
    /*
    Comentario contenido
     */
    String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Obtener instancia del edit text
        txtNombre = (EditText) findViewById(R.id.txtnombre);
        txtApellidos = (EditText) findViewById(R.id.txtapellidos);
        txtDireccion = (EditText) findViewById(R.id.txtdireccion);
        txtTelefono = (EditText) findViewById(R.id.txttelefono);
        txtEmail = (EditText) findViewById(R.id.txtemail);
        txtObservaciones = (EditText) findViewById(R.id.txtobservaciones);



        btnEnviar = (Button) findViewById(R.id.SendButton);


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Conectamos con el Servidor para que nos devuleva los datos...

         /*
        Comprobar la disponibilidad de la Red
         */
                try {
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        /*
                        GetCommentsTask hilo=new GetCommentsTask();
                        hilo.cargarContexto(getApplicationContext());
                        new GetCommentsTask().execute(listaJSon);*/
                        new HiloInsertarClientes().execute(
//                                "1",//Androidid
//                                txtNombre.getText().toString(),
//                                txtApellidos.getText().toString(),
//                                txtDireccion.getText().toString(),
//                                txtTelefono.getText().toString(),
//                                txtEmail.getText().toString(),
//                                "1",
//                                txtObservaciones.getText().toString(),
//                                "Antonio");

                                "1",//Androidid
                                txtApellidos.getText().toString(),
                                txtDireccion.getText().toString(),
                                txtEmail.getText().toString(),
                                "21",
                                txtNombre.getText().toString(),
                                txtObservaciones.getText().toString(),
                                "Antonio",
                                txtTelefono.getText().toString());






                    } else {
                        Toast.makeText(FormActivity.this, "Error de conexion", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


    }

    public class HiloInsertarClientes extends AsyncTask<String, Integer, Boolean> {

        String[] arrayDeStrings;
        HttpURLConnection con;
        Context contexto;
        Clientes clientes;
        ArrayList<Clientes> listaClientes = new ArrayList<Clientes>();


        @Override
        protected Boolean doInBackground(String... params) {

            List<String> comments;

            Boolean result = true;

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost("http://192.168.0.154:8080/WebServicesRESTGlassFishJEE7/webresources/contactos");

            httpPost.setHeader("Content-Type", "application/json");
            params[0]="11";
            params[4]="2";

            try {
                JSONObject dato = new JSONObject();
//                dato.put("AndroidID", 1);
//                dato.put("nombre", params[1]);//No admite nulos
//                dato.put("apellidos", params[2]);
//                dato.put("direccion", params[3]);
//                dato.put("telefono", params[4]);
//                dato.put("email", params[5]);
//                dato.put("idCategoria", 2);//No admite nulos
//                dato.put("observaciones", params[7]);
//                dato.put("owner", params[8]);//No admite nulos

                dato.put("androidID", Integer.parseInt(params[0]));
                dato.put("apellidos", params[1]);
                dato.put("direccion", params[2]);
                dato.put("email", params[3]);
                dato.put("idCategoria", Integer.parseInt(params[4]));//No admite nulos
                dato.put("nombre", params[5]);//No admite nulos
                dato.put("observaciones", params[6]);
                dato.put("owner", params[7]);//No admite nulos
                dato.put("telefono", params[8]);

//                {
//                    "androidID": 1,
//                        "apellidos": "Sanchez",
//                        "direccion": "Mostoles",
//                        "email": "email",
//                        "id": 1,
//                        "idCategoria": 1,
//                        "nombre": "Antonio",
//                        "observaciones": "Observaciones",
//                        "owner": "Antonio",
//                        "telefono": "654889977"
//

//                        "1",//Androidid
//                        txtNombre.getText().toString(),
//                        txtApellidos.getText().toString(),
//                        txtDireccion.getText().toString(),
//                        txtTelefono.getText().toString(),
//                        txtEmail.getText().toString(),
//                        "1",
//                        txtObservaciones.getText().toString(),
//                        "Antonio"


                StringEntity entity = new StringEntity(dato.toString());
                httpPost.setEntity(entity);
                HttpResponse resp = httpclient.execute(httpPost);
                String respStr = EntityUtils.toString(resp.getEntity());

                StatusLine statusLine=resp.getStatusLine();
                int statusCode=statusLine.getStatusCode();

                if(statusCode!=200){
                    result = false;

                }

//                if (!respStr.equals("ok")) {//El json de respuesta devuelve ok y más....{\"serverId\":%d, \"androidId\":%d, \"operacion\":\"%s\", \"resultado\":\"%s\"}
//
//                    result = false;
//                }

            } catch (JSONException e) {
                //e.printStackTrace();
                Log.e("Servicio REST", "ERROR", e);
            } catch (UnsupportedEncodingException e) {
                //e.printStackTrace();
                Log.e("Servicio REST", "ERROR", e);
            } catch (ClientProtocolException e) {
                //e.printStackTrace();
                Log.e("Servicio REST", "ERROR", e);
            } catch (IOException e) {
                //e.printStackTrace();
                Log.e("Servicio REST", "ERROR", e);
            } catch (Exception ex) {
                Log.e("Servicio REST", "ERROR", ex);
            }

            //}


            return result;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {

            super.onPostExecute(aBoolean);

            if (aBoolean){

                Toast.makeText(getBaseContext(), "Insercción realizada", Toast.LENGTH_LONG).show();
            }

        }
    }

        public void onClickCancel(View v) {
            // Finalizar actividad
            finish();
        }

        /*
        La clase PostCommentTask permite enviar los datos hacia el servidor
        para guardar el comentario del usuario.
         */
        public class PostCommentTask extends AsyncTask<URL, Void, Void> {

            @Override
            protected Void doInBackground(URL... urls) {
                // Obtener la conexión
                HttpURLConnection con = null;

                try {
                    // Construir los datos a enviar
                    String data = "body=" + URLEncoder.encode(comment, "UTF-8");

                    con = (HttpURLConnection) urls[0].openConnection();


                    HttpPost httpPost = new HttpPost("conexion");

                    httpPost.setHeader("content-type", "application-json");


                    // Activar método POST
                    con.setDoOutput(true);

                    // Tamaño previamente conocido
                    con.setFixedLengthStreamingMode(data.getBytes().length);

                    // Establecer application/x-www-form-urlencoded debido al formato clave-valor
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    OutputStream out = new BufferedOutputStream(con.getOutputStream());

                    out.write(data.getBytes());
                    out.flush();
                    out.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null)
                        con.disconnect();
                }

                return null;

            }

            @Override
            protected void onPostExecute(Void s) {
                Toast.makeText(getBaseContext(), "Comentario posteado", Toast.LENGTH_LONG).show();
            }
        }
    }

