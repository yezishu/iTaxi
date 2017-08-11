package yzs.map.view.amap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import yzs.commonlibrary.base.CommonBaseRxMvpActivity;
import yzs.commonlibrary.util.ToastUtil;
import yzs.map.R;
import yzs.map.presenter.AMapPresenter;

/**
 * Des：请输入标题
 * create by zishu.ye
 */
@Route(path = "/_map/AMapActivity")
public class AMapActivity extends CommonBaseRxMvpActivity<AMapPresenter>
        implements IAMapView, AMapLocationListener, AMap.OnCameraChangeListener, LocationSource, GeocodeSearch.OnGeocodeSearchListener {

    private MapView mapView;
    private AMap aMap;
    //    private LocationManagerProxy mLocationManagerProxy;
    private AMapLocationClient mLocationClient;
    private Handler handler = new Handler();
    private OnLocationChangedListener listener;
    private LatLng myLocation = null;
    private Marker centerMarker;
    private boolean isMovingMarker = false;
    private BitmapDescriptor movingDescriptor, chooseDescripter, successDescripter;
    private ValueAnimator animator = null;
    private GeocodeSearch geocodeSearch;
    private Toolbar toolbar;
    private FrameLayout containerLayout;
    private ImageView ivCircle;
    private TextView tvCurLocation, tvDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        initUI();
        initAmap();
        setUpLocationStyle();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void showFailInfo(String errorInfo) {

    }

    private void initUI() {
        findViewById(R.id.myLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUpdate update = CameraUpdateFactory.changeLatLng(myLocation);
                aMap.animateCamera(update);
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("现在用车");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        containerLayout = (FrameLayout) findViewById(R.id.container);
        tvCurLocation = (TextView) findViewById(R.id.location);
        tvDestination = (TextView) findViewById(R.id.destination);
        introAnimPrepare();
    }

    private void initAmap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setZoomControlsEnabled(false);

//        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(15);
        aMap.moveCamera(cameraUpdate);

        movingDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.m_icon_loaction_choose_moving);
        chooseDescripter = BitmapDescriptorFactory.fromResource(R.drawable.m_icon_loaction_choose);
        successDescripter = BitmapDescriptorFactory.fromResource(R.drawable.m_icon_usecarnow_position_succeed);

        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
    }

    private void setUpLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.m_img_location_now));
        myLocationStyle.strokeWidth(0);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        aMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        containerLayout.removeView(ivCircle);
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            if (listener != null) {
                listener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            }
            myLocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            tvCurLocation.setText(aMapLocation.getRoad() + aMapLocation.getStreet() + aMapLocation.getPoiName());
            addChooseMarker();
        }
    }

    private void addChooseMarker() {
        MarkerOptions centerMarkerOption = new MarkerOptions().position(myLocation).icon(chooseDescripter);
        centerMarker = aMap.addMarker(centerMarkerOption);
        centerMarker.setPositionByPixels(mapView.getWidth() / 2, mapView.getHeight() / 2);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CameraUpdate update = CameraUpdateFactory.zoomTo(17f);
                aMap.animateCamera(update, 1000, new AMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        aMap.setOnCameraChangeListener(AMapActivity.this);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        }, 1000);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        listener = onLocationChangedListener;

        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();

    }

    public void deactivate() {

        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();

        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (centerMarker != null) {
            setMovingMarker();
        }
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLonPoint point = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
        RegeocodeQuery query = new RegeocodeQuery(point, 50, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
        if (centerMarker != null) {
            animMarker();
        }
        showLocationView();
    }


    private void setMovingMarker() {
        if (isMovingMarker)
            return;

        isMovingMarker = true;
        centerMarker.setIcon(movingDescriptor);
        hideLocationView();
    }

    private void animMarker() {
        isMovingMarker = false;
        if (animator != null) {
            animator.start();
            return;
        }
        animator = ValueAnimator.ofFloat(mapView.getHeight() / 2, mapView.getHeight() / 2 - 30);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(150);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                centerMarker.setPositionByPixels(mapView.getWidth() / 2, Math.round(value));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                centerMarker.setIcon(chooseDescripter);
            }
        });
        animator.start();
    }

    private void endAnim() {
        if (animator != null && animator.isRunning())
            animator.end();
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null) {
                endAnim();
                centerMarker.setIcon(successDescripter);
                RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
                String formatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                String shortAdd = formatAddress.replace(regeocodeAddress.getProvince(), "").replace(regeocodeAddress.getCity(), "").replace(regeocodeAddress.getDistrict(), "");
                tvCurLocation.setText(shortAdd);
            } else {
                ToastUtil.showMessage("meijieguo");
            }
        } else {
            ToastUtil.showMessage("wangluocuowu ");
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {
    }

    private void introAnimPrepare() {
        toolbar.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                toolbar.getViewTreeObserver().removeOnPreDrawListener(this);
                toolbar.setTranslationY(-toolbar.getHeight());
                return false;
            }
        });
        ivCircle = new ImageView(this);
        ivCircle.setImageResource(R.drawable.m_tunahome_imageview_bottom);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        containerLayout.addView(ivCircle, params);
        ivCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ivCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                ivCircle.setTranslationY(containerLayout.getHeight() / 2 - ivCircle.getHeight());
                ivCircle.setScaleX(2f);
                ivCircle.setScaleY(2f);
                return false;
            }
        });
        containerLayout.post(new Runnable() {
            @Override
            public void run() {
                animIntroduce();
            }
        });
    }

    private void animIntroduce() {
        ObjectAnimator animToolbar = ObjectAnimator.ofFloat(toolbar, "TranslationY", 0f);
        animToolbar.setDuration(300);
        ObjectAnimator animCircle = ObjectAnimator.ofFloat(ivCircle, "TranslationY", 0);
        animCircle.setDuration(400);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivCircle, "ScaleX", 1f);
        scaleX.setDuration(400);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivCircle, "ScaleY", 1f);
        scaleY.setDuration(400);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animToolbar, animCircle, scaleX, scaleY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                containerLayout.removeView(ivCircle);
                mapView.setVisibility(View.VISIBLE);
                tvCurLocation.setVisibility(View.VISIBLE);
                tvDestination.setVisibility(View.VISIBLE);
            }
        });
        animatorSet.start();
    }

    private void hideLocationView() {
        ObjectAnimator animLocation = ObjectAnimator.ofFloat(tvCurLocation, "TranslationY", -tvCurLocation.getHeight() * 2);
        ObjectAnimator animDestinatiion = ObjectAnimator.ofFloat(tvDestination, "TranslationY", tvDestination.getHeight() * 2);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animDestinatiion, animLocation);
        set.setDuration(200);
        set.start();
    }

    private void showLocationView() {
        ObjectAnimator animLocation = ObjectAnimator.ofFloat(tvCurLocation, "TranslationY", 0);
        ObjectAnimator animDestinatiion = ObjectAnimator.ofFloat(tvDestination, "TranslationY", 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animDestinatiion, animLocation);
        set.setDuration(200);
        set.start();
    }
}

