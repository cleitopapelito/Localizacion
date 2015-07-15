package py.com.cleito.localizacion;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends Activity {

    private Button activar;
    private Button desactivar;

    private TextView longitud;
    private TextView latitud;
    private TextView precision;
    private TextView estado;

    private LocationManager locManager;
    private LocationListener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activar = (Button) findViewById(R.id.activar);
        desactivar = (Button) findViewById(R.id.desactivar);

        longitud = (TextView) findViewById(R.id.longitud);
        latitud = (TextView) findViewById(R.id.latitud);
        precision = (TextView) findViewById(R.id.precision);
        estado = (TextView) findViewById(R.id.estado);

        activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comenzarLocalizacion();
            }
        });

        desactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locManager.removeUpdates(locListener);
            }
        });
    }

    private void comenzarLocalizacion(){
        //Obtenemos una referencia al LocationManager

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mostrarPosicion(loc);

        //Nos registramos para recibir actualizaciónes de la posición.

        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                estado.setText("Provider Status: " + bundle);
                Log.i("LocAndroid", "Provider Status: " + bundle);
            }

            @Override
            public void onProviderEnabled(String s) {
                estado.setText("Provider On");
            }

            @Override
            public void onProviderDisabled(String s) {
                estado.setText("Provider Off");
            }
        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locListener);

    }

    private void mostrarPosicion(Location loc){
        if(loc != null){
            latitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            longitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            precision.setText("Precision: " + String.valueOf(loc.getAccuracy()));

            Log.i("LocAndroid: ", String.valueOf(loc.getLatitude()) + " - " + String.valueOf(loc.getLongitude()));
        }else{
            latitud.setText("Latitud: (n/a)");
            longitud.setText("Longitud: (n/a)");
            precision.setText("Precision: (n/a)");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
