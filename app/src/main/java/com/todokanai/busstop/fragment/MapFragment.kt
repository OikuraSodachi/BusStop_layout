package com.todokanai.busstop.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.VisibleRegion
import com.todokanai.busstop.R
import com.todokanai.busstop.application.MyApplication
import com.todokanai.busstop.repository.StationRepository
import com.todokanai.busstop.room.MyDatabase
import com.todokanai.busstop.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {
    private val viewModel : MapViewModel by viewModels()
    private val stationRepository = StationRepository(MyDatabase.getInstance(MyApplication.appContext).stationDao())
    lateinit var visibleRegion : VisibleRegion // 화면에 표시되고있는 범위 좌표

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        //----------------

        val testLocation = LatLng(37.566168,126.901609)
        googleMap.addMarker(MarkerOptions().position(testLocation).title("testLocation"))

        /*
        //------------------
        //
        val stationList = stationRepository.getAll().value

        for(index in 0..stationList!!.size){
            val stationLocation = LatLng(stationList[index].latitude,stationList[index].longitude)
            googleMap.addMarker(MarkerOptions().position(stationLocation)).title("${stationList[index].name}")
        }
        //
        //----------------------
         */

        googleMap.setOnCameraIdleListener {
            visibleRegion = googleMap.projection.visibleRegion     //  lateinit var tester : VisibleRegion
            Log.d("oikura", "tester: ${visibleRegion}")
        }                                                   // 지도 이동에 반응해서 화면 위치 로그 찍기

        googleMap.setOnMarkerClickListener(OnMarkerClickListener { marker ->
            val text = ("[마커 클릭 이벤트] latitude ="
                    + marker.position.latitude + ", longitude ="
                    + marker.position.longitude)
            Toast.makeText(MyApplication.appContext,
                text,
                Toast.LENGTH_LONG)
                .show()
            false
        })      // 모든 마커 클릭 이벤트에 대해 일괄적으로 적용되는 이벤트

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        
    }
}