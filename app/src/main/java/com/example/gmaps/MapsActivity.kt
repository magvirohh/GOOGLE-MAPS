package com.example.gmaps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var map: GoogleMap
    private val TAG = MapsActivity::class.java.simpleName
//    private val permissiion: Array<String> = arrayOf(
//        Manifest.permission.ACCESS_FINE_LOCATION,
//        Manifest.permission.ACCESS_COARSE_LOCATION
//    )
//
//    private val requestPermission =1100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
//
//        caritempat.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                //untuk mencari tempat
//                val  cari=query.toString()
//
//                var ListAddres: List<Address> = emptyList()
//
//                //berguna sebagai conveter. dari titik koordinat menjadi suatu alamat atau sebaliknya
//                val gecoder = Geocoder(this@MapsActivity)
//
//                //dibuat try and catch untuk mrnghindari error
//                try {
//                    //mencari lokasi nama tempoat yang dimasukkan
//                    ListAddres = gecoder.getFromLocationName(cari, 1)
//
//                } catch (e: IOException){
//                    Log.e("Error Cari: ", e.toString())
//
//                }
//
//                if (ListAddres.isEmpty()){
//
//
//                    val address= ListAddres.get(0)//isinya berupa koordinat dari tempat yang dicari
//                    val latLng=LatLng(address.longitude, address.longitude)//kemudian dipisahkan menjadi latitude dan longitude
//
//                    //latLing digambarkan berupa marker
//                    map.addMarker(MarkerOptions().position(latLng).title(cari))
//                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
//
//                }
//
//                else{
//                    Toast.makeText(this@MapsActivity, cari + "Tidak Dikenali", Toast.LENGTH_SHORT).show()
//                }
//
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//
//        })


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val latitude = -7.74519
        val longitude = 110.35595
        val homeLatLng = LatLng(latitude, longitude)
        val zoomLevel = 15f
        val overlaySize = 100f

        val androidOverlay = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromResource(R.drawable.android))
            .position(homeLatLng, overlaySize)

        map.addGroundOverlay(androidOverlay)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        map.addMarker(MarkerOptions().position(homeLatLng))
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            if (ActivityCompat.checkSelfPermission(this.applicationContext, permissiion[0])== PackageManager.PERMISSION_GRANTED&&
//                    ActivityCompat.checkSelfPermission(this.applicationContext, permissiion[1])==PackageManager.PERMISSION_GRANTED) {
//                val uty = LatLng(-7.747033, 110.355398)
//                map.addMarker(MarkerOptions().position(uty).title("Universitas Teknologi Yogyakarta"))
//                map.animateCamera(CameraUpdateFactory.newLatLngZoom(uty, 18f))
//                map.isMyLocationEnabled=true
//
//                getAddressFromLocation(uty)
//
//            }else{
//                ActivityCompat.requestPermissions(this, permissiion, requestPermission)
//            }
//
//        } else{
//            val uty = LatLng(-7.747033, 110.355398)
//            map.addMarker(MarkerOptions().position(uty).title("Universitas Teknologi Yogyakarta"))
//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(uty, 18f))
//            map.isMyLocationEnabled=true
//
//            getAddressFromLocation(uty)
//        }
        setMapStyle(map)
        setMapLongClick(map)
        setPoiClick(map)
        enableMyLocation()
    }


//    private fun getAddressFromLocation(latLng: LatLng){
//
//        val geocoder= Geocoder(this)
//        var listAddress: List<Address> = emptyList()
//
//        try{
//            listAddress=geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//
//        } catch (e:IOException){
//            Log.e("Error Alamat: ", e.toString())
//        }
//        if (listAddress.isEmpty()){
//            val alamathasil=listAddress.get(0).getAddressLine(0)
//
//            alamatdisplay.text=alamathasil
//        }
//    }
    private fun setMapStyle(map: GoogleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.maps_style
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        }catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_option, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }
    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }
    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private fun enableMyLocation()
    {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}
