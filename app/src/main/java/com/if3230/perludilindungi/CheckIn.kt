package com.if3230.perludilindungi

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.android.gms.location.*
import com.if3230.perludilindungi.Model.CheckInRequest
import com.if3230.perludilindungi.Model.CheckInResponse
import com.if3230.perludilindungi.Model.UserStatus
import com.if3230.perludilindungi.databinding.ActivityCheckInBinding

private const val PERMISSION_ALL = 1

class CheckIn : AppCompatActivity(), SensorEventListener{
    private lateinit var codeScanner: CodeScanner

    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private var qrCode: String = ""
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private val scannerPermissions = arrayOf(
        android.Manifest.permission.CAMERA
    )
    private val locationPermissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    private lateinit var sensorManager: SensorManager
    private var temperature: Float = 0.0f

    lateinit var viewModel: MainViewModel
    private val perduliLindungiAPI = PerduliLindungiAPI.getInstance()

    private lateinit var binding: ActivityCheckInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // setup initial permissions
        setupPermissions()

        // setup location service, scanner, sensor
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationUpdates()
        codeScanner()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // setup view-model for check-in http request
        viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(perduliLindungiAPI))).get(MainViewModel::class.java)
        viewModel.checkInStatus.observe(this) {
            val intent = Intent(this, CheckInResponseActivity::class.java).apply {
                putExtra("userStatus", it.data.userStatus.toString())
                putExtra("reason", it.data.reason)
            }
            startActivity(intent)
        }
        viewModel.errorMessage.observe(this) {
            Log.e("error", it)
        }
    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        return ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun setupPermissions() {
        if(!hasPermissions(this, *(locationPermissions + locationPermissions))) {
            ActivityCompat.requestPermissions(this, locationPermissions + scannerPermissions, PERMISSION_ALL)
        }
    }

    private fun getLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            // 50s
            interval = 50000
            fastestInterval = 50000
            // 170m
            smallestDisplacement = 170f
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return

                    if (locationResult.locations.isNotEmpty()) {
                        // get latest location
                        val location  = locationResult.lastLocation
                        longitude = location.longitude
                        latitude = location.latitude

                    }
                }
            }
        }
    }

    private fun startLocationUpdates() {
        fun hasLocationPermissions(context: Context): Boolean = locationPermissions.all {
            return ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if(!hasLocationPermissions(this)) {
            ActivityCompat.requestPermissions(this, locationPermissions, PERMISSION_ALL)
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null,
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    private fun codeScanner() {
        codeScanner = CodeScanner(this, binding.scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    if(qrCode != it.text) {
                        qrCode = it.text

                        val checkInRequest = CheckInRequest(qrCode, latitude, longitude)

                        // do http request
                        viewModel.doCheckIn(checkInRequest)
                    }
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }
    }

    private fun startScanner() {
        fun hasScannerPermissions(context: Context): Boolean = scannerPermissions.all {
            return ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if(!hasScannerPermissions(this)) {
            ActivityCompat.requestPermissions(this, scannerPermissions, PERMISSION_ALL)
        }

        codeScanner.startPreview()
    }


    override fun onResume() {
        super.onResume()
        startScanner()
        startLocationUpdates()
        loadAmbientTemperature()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
        stopLocationUpdates()
        unregisterAllSensor()
    }

    private fun loadAmbientTemperature() {
        val sensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        if(sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        } else {
            Toast.makeText(this, "No Ambient Temperature Sensor !", Toast.LENGTH_LONG).show();
        }
    }

    private fun unregisterAllSensor() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        if(sensorEvent?.values?.isNotEmpty() == true) {
            temperature = sensorEvent.values[0]
            binding.checkInTemperatureVal.text = sensorEvent.values[0].toString() + "\u2103"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_ALL -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Missing one or some permissions!", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, Content::class.java)
        startActivity(intent)
    }
}