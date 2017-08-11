package yzs.map.view.amap

import android.animation.*
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.*
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import kotlinx.android.synthetic.main.activity_amap.*
import yzs.commonlibrary.base.CommonBaseRxMvpActivity
import yzs.commonlibrary.util.ToastUtil
import yzs.map.R
import yzs.map.presenter.AMapPresenter
import yzs.map.view.poisearch.POISearchFragment

/**
 * Des：请输入标题
 * create by zishu.ye
 */
@Route(path = "/_map/AMapActivity")
class AMapActivity : CommonBaseRxMvpActivity<AMapPresenter>(), IAMapView, AMapLocationListener, AMap.OnCameraChangeListener, LocationSource, GeocodeSearch.OnGeocodeSearchListener {

    private var mapView: MapView? = null
    private var aMap: AMap? = null
    private var mLocationClient: AMapLocationClient? = null
    private val handler = Handler()
    private var listener: LocationSource.OnLocationChangedListener? = null
    private var myLocation: LatLng? = null
    private var centerMarker: Marker? = null
    private var isMovingMarker = false
    private var movingDescriptor: BitmapDescriptor? = null
    private var chooseDescripter: BitmapDescriptor? = null
    private var successDescripter: BitmapDescriptor? = null
    private var animator: ValueAnimator? = null
    private var geocodeSearch: GeocodeSearch? = null
    private var toolbar: Toolbar? = null
    private var containerLayout: FrameLayout? = null
    private var ivCircle: ImageView? = null
    private var tvCurLocation: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amap)
        mapView = findViewById(R.id.map) as MapView
        mapView!!.onCreate(savedInstanceState)
        initCreate()
    }

    override fun initView() {
        initUI()
        initAmap()
        setUpLocationStyle()
        attachGeoSearchFragment()//绑定 geo搜索fragment
    }

    override fun initData() {

    }

    override fun initListener() {
        tv_start_location.setOnClickListener { fl_geo_search.visibility=View.VISIBLE }
    }

    override fun initPresenter() {

    }

    private fun attachGeoSearchFragment() {
        var fragment = supportFragmentManager.findFragmentById(R.id.fl_geo_search)
        if (fragment !is POISearchFragment) {
            fragment = POISearchFragment.getInstance()
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_geo_search, fragment)
                .commit()
    }

    override fun showFailInfo(errorInfo: String) {

    }

    private fun initUI() {
        btn_my_location.setOnClickListener {
            val update = CameraUpdateFactory.changeLatLng(myLocation)
            aMap!!.animateCamera(update)
        }
        toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar!!.title = "现在用车"
        toolbar!!.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        containerLayout = findViewById(R.id.container) as FrameLayout
        tvCurLocation = findViewById(R.id.location) as TextView
        introAnimPrepare()
    }

    private fun initAmap() {
        if (aMap == null) {
            aMap = mapView!!.map
        }
        aMap!!.setLocationSource(this)// 设置定位监听
        aMap!!.isMyLocationEnabled = true
        aMap!!.uiSettings.isZoomControlsEnabled = false

        //        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        val cameraUpdate = CameraUpdateFactory.zoomTo(15f)
        aMap!!.moveCamera(cameraUpdate)

        movingDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.m_icon_loaction_choose_moving)
        chooseDescripter = BitmapDescriptorFactory.fromResource(R.drawable.m_icon_loaction_choose)
        successDescripter = BitmapDescriptorFactory.fromResource(R.drawable.m_icon_usecarnow_position_succeed)

        geocodeSearch = GeocodeSearch(this)
        geocodeSearch!!.setOnGeocodeSearchListener(this)
    }

    /**
     * 自定义系统定位蓝点
     */
    private fun setUpLocationStyle() {
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.m_img_location_now))
        myLocationStyle.strokeWidth(0f)
        myLocationStyle.radiusFillColor(Color.TRANSPARENT)
        aMap!!.setMyLocationStyle(myLocationStyle)
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
        deactivate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        mapView!!.onDestroy()
        containerLayout!!.removeView(ivCircle)
        super.onDestroy()
    }

    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        if (aMapLocation != null && aMapLocation.errorCode == 0) {
            if (listener != null) {
                listener!!.onLocationChanged(aMapLocation)// 显示系统小蓝点
            }
            myLocation = LatLng(aMapLocation.latitude, aMapLocation.longitude)
            tvCurLocation!!.text = aMapLocation.road + aMapLocation.street + aMapLocation.poiName
            addChooseMarker()
        }
    }

    private fun addChooseMarker() {
        val centerMarkerOption = MarkerOptions().position(myLocation).icon(chooseDescripter)
        centerMarker = aMap!!.addMarker(centerMarkerOption)
        centerMarker!!.setPositionByPixels(mapView!!.width / 2, mapView!!.height / 2)
        handler.postDelayed({
            val update = CameraUpdateFactory.zoomTo(17f)
            aMap!!.animateCamera(update, 1000, object : AMap.CancelableCallback {
                override fun onFinish() {
                    aMap!!.setOnCameraChangeListener(this@AMapActivity)
                }

                override fun onCancel() {}
            })
        }, 1000)
    }


    /**
     * 开启定位
     */
    override fun activate(onLocationChangedListener: LocationSource.OnLocationChangedListener) {
        listener = onLocationChangedListener
        mLocationClient = AMapLocationClient(this)
        //初始化定位参数
        val mLocationOption = AMapLocationClientOption()
        //设置定位监听
        mLocationClient!!.setLocationListener(this)
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.interval = 2000
        //设置单次定位
        mLocationOption.isOnceLocation = true
        //设置定位参数
        mLocationClient!!.setLocationOption(mLocationOption)
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient!!.startLocation()

    }

    override fun deactivate() {
        if (mLocationClient != null) {
            mLocationClient!!.stopLocation()
            mLocationClient!!.onDestroy()

        }
    }

    override fun onCameraChange(cameraPosition: CameraPosition) {
        if (centerMarker != null) {
            setMovingMarker()
        }
    }

    override fun onCameraChangeFinish(cameraPosition: CameraPosition) {
        val point = LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude)
        val query = RegeocodeQuery(point, 50f, GeocodeSearch.AMAP)
        geocodeSearch!!.getFromLocationAsyn(query)
        if (centerMarker != null) {
            animMarker()
        }
        showLocationView()
    }


    private fun setMovingMarker() {
        if (isMovingMarker)
            return

        isMovingMarker = true
        centerMarker!!.setIcon(movingDescriptor)
        hideLocationView()
    }

    private fun animMarker() {
        isMovingMarker = false
        if (animator != null) {
            animator!!.start()
            return
        }
        animator = ValueAnimator.ofFloat((mapView!!.height / 2).toFloat() , (mapView!!.height / 2 - 30).toFloat())
        animator!!.interpolator = DecelerateInterpolator()
        animator!!.duration = 150
        animator!!.repeatCount = 1
        animator!!.repeatMode = ValueAnimator.REVERSE
        animator!!.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            centerMarker!!.setPositionByPixels(mapView!!.width / 2, Math.round(value))
        }
        animator!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                centerMarker!!.setIcon(chooseDescripter)
            }
        })
        animator!!.start()
    }

    private fun endAnim() {
        if (animator != null && animator!!.isRunning)
            animator!!.end()
    }

    override fun onRegeocodeSearched(regeocodeResult: RegeocodeResult?, i: Int) {
        if (i == 1000) {
            if (regeocodeResult != null && regeocodeResult.regeocodeAddress != null) {
                endAnim()
                centerMarker!!.setIcon(successDescripter)
                val regeocodeAddress = regeocodeResult.regeocodeAddress
                val formatAddress = regeocodeResult.regeocodeAddress.formatAddress
                val shortAdd = formatAddress.replace(regeocodeAddress.province, "").replace(regeocodeAddress.city, "").replace(regeocodeAddress.district, "")
                tvCurLocation!!.text = shortAdd
            } else {
                ToastUtil.showMessage("meijieguo")
            }
        } else {
            ToastUtil.showMessage("wangluocuowu ")
        }
    }

    override fun onGeocodeSearched(geocodeResult: GeocodeResult, rCode: Int) {}

    private fun introAnimPrepare() {
        toolbar!!.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                toolbar!!.viewTreeObserver.removeOnPreDrawListener(this)
                toolbar!!.translationY = (-toolbar!!.height).toFloat()
                return false
            }
        })
        ivCircle = ImageView(this)
        ivCircle!!.setImageResource(R.drawable.m_tunahome_imageview_bottom)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        containerLayout!!.addView(ivCircle, params)
        ivCircle!!.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                ivCircle!!.viewTreeObserver.removeOnPreDrawListener(this)
                ivCircle!!.translationY = (containerLayout!!.height / 2 - ivCircle!!.height).toFloat()
                ivCircle!!.scaleX = 2f
                ivCircle!!.scaleY = 2f
                return false
            }
        })
        containerLayout!!.post { animIntroduce() }
    }

    private fun animIntroduce() {
        val animToolbar = ObjectAnimator.ofFloat(toolbar, "TranslationY", 0f)
        animToolbar.duration = 300
        val animCircle = ObjectAnimator.ofFloat(ivCircle, "TranslationY", 0f)
        animCircle.setDuration(400)
        val scaleX = ObjectAnimator.ofFloat(ivCircle, "ScaleX", 1f)
        scaleX.duration = 400
        val scaleY = ObjectAnimator.ofFloat(ivCircle, "ScaleY", 1f)
        scaleY.duration = 400
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animToolbar, animCircle, scaleX, scaleY)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                containerLayout!!.removeView(ivCircle)
                mapView!!.visibility = View.VISIBLE
                tvCurLocation!!.visibility = View.VISIBLE
                ll_location_info!!.visibility = View.VISIBLE
            }
        })
        animatorSet.start()
    }

    private fun hideLocationView() {
        val animLocation = ObjectAnimator.ofFloat(tvCurLocation, "TranslationY", (-tvCurLocation!!.height * 2).toFloat())
        val animDestinatiion = ObjectAnimator.ofFloat(ll_location_info, "TranslationY", (ll_location_info!!.height * 2).toFloat())
        val set = AnimatorSet()
        set.playTogether(animDestinatiion, animLocation)
        set.duration = 200
        set.start()
    }

    private fun showLocationView() {
        val animLocation = ObjectAnimator.ofFloat(tvCurLocation, "TranslationY", 0f)
        val animDestinatiion = ObjectAnimator.ofFloat(ll_location_info, "TranslationY", 0f)
        val set = AnimatorSet()
        set.playTogether(animDestinatiion, animLocation)
        set.duration = 200
        set.start()
    }
}

