package antonio.ejemplos.httpurlconecctionok;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*
  Lista de comentarios
   */
    private ListView listaJSon;
    private Button btnVerDatos;

    /*
    Cliente para la conexi�n al servidor
     */
    //private HttpURLConnection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener la instancia de la lista
        listaJSon = (ListView)findViewById(android.R.id.list);
        btnVerDatos=(Button)findViewById(R.id.btnVerDatos);



        btnVerDatos.setOnClickListener(new View.OnClickListener() {
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
                        new HiloVerClientes().execute();


                    } else {
                        Toast.makeText(MainActivity.this, "Error de conexion", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            startActivity(new Intent(this, FormActivity.class));
        }
       /* if (id == R.id.action_update) {
            try {
                new GetCommentsTask().
                        execute(
                                new URL("http://monstalkers.hostoi.com/data/get_all_comments.php"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }*/

        return super.onOptionsItemSelected(item);
    }




    /*
La clase GetCommentsTask representa una tarea asincrona que realizar�
las operaciones de red necesarias en segundo plano para obtener toda la
lista de comentarios alojada en el servidor.
 */
    public class HiloVerClientes extends AsyncTask<List<String>, Void, Boolean> {

        //private String[] clientes;//Para guardar los datos recogidos
        //ListView lista;
        String []arrayDeStrings;
        HttpURLConnection con;
        Context contexto;
        Clientes clientes;
        ArrayList<Clientes> listaClientes=new ArrayList<Clientes>();


        @Override
        protected Boolean  doInBackground(List<String>... params) {
            int id;
            int id_Servidor;
            int id_Android=0;
            String nombre;
            String apellidos;
            String direccion;
            String telefono;
            String email;
            int idCategoria;
            String observaciones;
            String propietario;

            Boolean result=true;



            List<String> comments = null;


            try {

                // Establecer la conexi�n
                URL url = new URL("http://192.168.0.154:8080/WebServicesRESTGlassFishJEE7/webresources/contactos");


                con = (HttpURLConnection) url.openConnection();

                //con = (HttpURLConnection).openConnection(url);
                //URLConnection con = url.openConnection();


                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();
                //int statusCode = url.getResponseCode();

                if(statusCode!=200) {
                    comments = new ArrayList<>();
                    comments.add("El recurso no est� disponible");
                    //return comments;
                }
                else{

                    /*
                    Parsear el flujo con formato JSON a una lista de Strings
                    que permitan crean un adaptador
                     */
                    InputStream in = new BufferedInputStream(con.getInputStream());

                    //JSONCommentsParser parser = new JSONCommentsParser();

                    //comments = parser.readJsonStream(in);//Es una Colección

////////////////NUEVO//////////////
                    BufferedReader lector=new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb= new StringBuilder();
                    String linea=null;

                    try {
                        while((linea=lector.readLine())!=null) {

                            sb.append(linea);
                        }
                    }
                    catch(IOException e){
                        e.printStackTrace();

                    }
                    try {
                        in.close();

                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }

                    String resultado=sb.toString();


                    try {

                        JSONArray respJson=new JSONArray(resultado);

                        //arrayDeStrings=new String[respJson.length()];

                        for (int i=0;i<respJson.length();i++) {
                            JSONObject objetoJSon=respJson.getJSONObject(i);

                            id_Servidor=objetoJSon.getInt("id");


//                            for (int i = 0; i < innerProjectarray.length(); i++) {
//                                JSONObject obj=innerProjectarray.getJSONObject(i);
//                                if(obj.has("androidID"))
//                                { Log.i("androidID ",obj.getString("androidID"));
//                                } else { Log.i("Project Number ","No Such Tag as PROJECT_NUMBER"); }
//                                if(obj.has("PROJECT_NAME")) { Log.i("Project Name ",obj.getString("PROJECT_NAME"));
//                                } else { Log.i("Project Name ","No Such Tag as PROJECT_NAME"); } }



                            //if((objetoJSon.getInt("androidID"))>0) {
                            //if(objetoJSon.length()>0) {


                            //id_Android = objetoJSon.getInt("androidID");//Cuando se dió de alta desde la página web no está informado...
                            if(objetoJSon.has("androidID")) {//Se comprueba previamente si está informado el campo androidID
                                Log.i("androidID ",objetoJSon.getString("androidID"));
                                id_Android = objetoJSon.getInt("androidID");

                            }
                            else { Log.i("Project Number ","androidID no tiene valor en el JSON"); }


                            nombre=objetoJSon.getString("nombre");
                            apellidos=objetoJSon.getString("apellidos");
                            direccion=objetoJSon.getString("direccion");
                            telefono=objetoJSon.getString("telefono");
                            email = objetoJSon.getString("email");
                            idCategoria = objetoJSon.getInt("idCategoria");
                            observaciones=objetoJSon.getString("observaciones");
                            propietario=objetoJSon.getString("owner");

                            //clientes = new Clientes(id_Servidor + "-" + id_Android + "-" + nombre + "-" + apellidos + "-" + direccion + "-" + telefono + "-" + email + "-" + idCategoria + "-" + observaciones + "-" + propietario);

                            //Se rea objeto de la clase Clientes para pasárselo al adaptador, se le pasa al arraylist y luego al adaptador
                            if(objetoJSon.has("androidID")) {
                                clientes = new Clientes(id_Servidor + "-" + id_Android + "-" + nombre + "-" + apellidos + "-" + direccion + "-" + telefono + "-" + email + "-" + idCategoria + "-" + observaciones + "-" + propietario);
                            }
                            else{
                                clientes = new Clientes(id_Servidor + "-" + 0 + "-" + nombre + "-" + apellidos + "-" + direccion + "-" + telefono + "-" + email + "-" + idCategoria + "-" + observaciones + "-" + propietario);
                            }

                            listaClientes.add(clientes);

                            //Si utilizamos un Array de String para pasárselo al adaptador
                            //arrayDeStrings[i]=objetoJSon.getString("nombre");--Funciona también
                            //arrayDeStrings[i]=id_Servidor+"-"+id_Android+"-"+nombre+"-"+apellidos+"-"+telefono;
                            //arrayDeStrings[i]=id_Servidor+"-"+nombre+"-"+apellidos+"-"+telefono;
                        }
                    }
                    catch (JSONException e){

                        e.printStackTrace();
                        //e.getMessage();
                        Log.e("SERVICIO REST", "ERROR LEYENDO JSON", e);
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                con.disconnect();
            }
            //return listaClientes;
            /// ArrayAdapter<Clientes> adaptador=new ArrayAdapter<Clientes>(contexto,android.R.layout.simple_list_item_2,listaClientes);

            //return adaptador;
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            /*
            Se crea un adaptador con el el resultado del
            que se realiz� al array JSON*/

            //ADAPTADOR UTILIZANDO UN ARRAYLIST DE LA CLASE CLIENTES
            ArrayAdapter<Clientes> adaptador=new ArrayAdapter<Clientes>(MainActivity.this,android.R.layout.simple_list_item_1,listaClientes);

           //ADAPTADOR UITLIZANDO UN ARRAY DE STRINGS
            //ArrayAdapter<String>adaptador=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,arrayDeStrings);

            // Relacionar adaptador a la lista
            listaJSon.setAdapter(adaptador);

        }
    }






    /*
    La clase GetCommentsTask representa una tarea asincrona que realizar�
    las operaciones de red necesarias en segundo plano para obtener toda la
    lista de comentarios alojada en el servidor.
     */
    public class GetCommentsTask extends AsyncTask<ListView, Void, Boolean> {

        //private String[] clientes;//Para guardar los datos recogidos
        ListView lista;
        HttpURLConnection con;
        Context contexto;
        Clientes clientes;
        ArrayList<Clientes> listaClientes=new ArrayList<Clientes>();

        public void cargarContexto(Context contexto){
            this.contexto=contexto;

        }

        @Override
        protected Boolean  doInBackground(ListView... params) {
            int id;
            int id_Servidor;
            String nombre;
            String apellidos;
            String direccion;
            String telefono;
            String email;
            String observaciones;

            Boolean result=true;



            List<String> comments = null;

            lista=params[0];

            try {

                // Establecer la conexi�n
                URL url = new URL("http://192.168.0.154:8080/WebServicesRESTGlassFishJEE7/webresources/contactos");


                 con = (HttpURLConnection) url.openConnection();

                //con = (HttpURLConnection).openConnection(url);
                //URLConnection con = url.openConnection();


                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();
                //int statusCode = url.getResponseCode();

                if(statusCode!=200) {
                    comments = new ArrayList<>();
                    comments.add("El recurso no est� disponible");
                    //return comments;
                }
                else{

                    /*
                    Parsear el flujo con formato JSON a una lista de Strings
                    que permitan crean un adaptador
                     */
                    InputStream in = new BufferedInputStream(con.getInputStream());

                    //JSONCommentsParser parser = new JSONCommentsParser();

                    //comments = parser.readJsonStream(in);//Es una Colección

////////////////NUEVO//////////////
                    BufferedReader lector=new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb= new StringBuilder();
                    String linea=null;

                    try {
                        while((linea=lector.readLine())!=null) {

                            sb.append(linea);
                        }
                        }
                    catch(IOException e){
                        e.printStackTrace();

                        }
                try {
                    in.close();

                }
                catch (IOException e){
                    e.printStackTrace();
                }

        String resultado=sb.toString();


                    try {

                        JSONArray respJson=new JSONArray(resultado);
                        for (int i=0;i<respJson.length();i++) {
                            JSONObject objetoJSon=respJson.getJSONObject(i);
                            id=objetoJSon.getInt("id");
                            nombre=objetoJSon.getString("nombre");
                            //Ver si crear objeto de la clase Clientes....
                            clientes=new Clientes(objetoJSon.getInt("id"),objetoJSon.getString("nombre"));
                            listaClientes.add(clientes);

                        }
                    }
                    catch (JSONException e){

                        e.printStackTrace();
                    }

                    //JSONArray respJson=new JSONArray(respStr);
                    //JSONArray respJson=new JSONArray(comments);

                    //clientes=new String[respJson.length()];
                    //comments=new ArrayList<>(respJson.length());

                /*    for (int i=0;1<clientes.length;i++){

                        JSONObject resp=new JSONObject();
                        id_Servidor=resp.getInt("id_Servidor");
                        nombre=resp.getString("nombre");
                        //apellidos=resp.getString("apellidos");
                        //direccion=resp.getString("direccion");
                        //telefono=resp.getString("telefono");
                        //email=resp.getString("email");
                        //clientes[i]=id_Servidor+nombre+apellidos+direccion+telefono+email;

                        clientes[i]=id_Servidor+nombre;
                        comments.add(id_Servidor + nombre);


                    }*/

                   /*for (int i=0;1<comments.size();i++) {
                        JSONObject resp=new JSONObject();
                        nombre=resp.getString("nombre");
                        //nombre=comments.get(nombre);
                        comments.add(nombre);


                    }*/

                }

            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                con.disconnect();
            }
            //return listaClientes;
           /// ArrayAdapter<Clientes> adaptador=new ArrayAdapter<Clientes>(contexto,android.R.layout.simple_list_item_2,listaClientes);

            //return adaptador;
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            /*
            Se crea un adaptador con el el resultado del parsing
            que se realiz� al arreglo JSON*/

            /*ArrayAdapter<Clientes> adapter = new ArrayAdapter<Clientes>(
                    getBaseContext(),
                    android.R.layout.simple_list_item_1,
                    s);*/


            ArrayAdapter<Clientes> adaptador=new ArrayAdapter<Clientes>(getBaseContext(),android.R.layout.simple_list_item_2,listaClientes);

            //ArrayAdapter<Clientes> adaptador=new ArrayAdapter<Clientes>(getBaseContext(),R.layout.activity_main,R.id.textview, countryArray,listaClientes);
            // Relacionar adaptador a la lista
            lista.setAdapter(adaptador);

           // ArrayAdapter<String> adaptador=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,clientes);
            //lista.setAdapter(adaptador);

        }
    }
}
