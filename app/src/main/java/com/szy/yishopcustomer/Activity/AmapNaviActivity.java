package com.szy.yishopcustomer.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.CoordinateConverter.CoordType;
import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.szy.yishopcustomer.Util.TTSController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class AmapNaviActivity extends Activity implements AMapNaviListener, AMapNaviViewListener, AMapLocationListener {

    /**
     * 导航方式标识
     */
    public static final String NAVI_WAY = "NAVI_WAY";
    /**
     * 导航数据标识
     */
    public static final String NAVI_DATA = "NAVI_DATA";
    private static final String TAG = AmapNaviActivity.class.getName();
    /**
     * 标识退出导航页面, 用于判断进入无起点导航闪退情形, 此时正在定位, 无法关闭
     */
    private static boolean isDestroy = false;
    /**
     * 起点坐标
     */
    private final List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    /**
     * 终点坐标
     */
    private final List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
    /**
     * 3D导航地图对象
     */
    private AMapNaviView mAMapNaviView;
    /**
     * 导航对象
     */
    private AMapNavi mAMapNavi;
    /**
     * 语音对象
     */
    private TTSController mTtsManager;
    /**
     * 途经点坐标
     */
    private List<NaviLatLng> mWayPointList = new ArrayList<NaviLatLng>();

    /**
     * 声明mLocationOption对象
     */
    private AMapLocationClientOption mLocationOption = null;

    /**
     * 声明mlocationClient对象
     */
    private AMapLocationClient mlocationClient = null;

    /**
     * 导航方式
     */
    private NaviWay naviWay;

    /**
     * 导航参数
     */
    private JSONArray naviData;

    /**
     * 坐标类型, 0: 高德坐标, 1: GPS坐标
     */
    private int type = 0;

    /**
     * 线程句柄
     */
    private Handler handler = new Handler();

    /**
     * 其他坐标转高德坐标
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public NaviLatLng convert(double latitude, double longitude, CoordType type) {
        LatLng latLng = new LatLng(latitude, longitude);
        CoordinateConverter converter = new CoordinateConverter(AmapNaviActivity.this);
        converter.from(type);
        converter.coord(latLng);
        latLng = converter.convert();
        return new NaviLatLng(latLng.latitude, latLng.longitude);
    }

    /**
     * 获取定位坐标
     */
    public void initLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    /**
     * 初始化导航
     */
    public void initNavi() {
        // 实例化语音引擎
        mTtsManager = TTSController.getInstance(getApplicationContext());
        mTtsManager.init();
        mTtsManager.startSpeaking();

        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.addAMapNaviListener(mTtsManager);

        // 设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);
    }

    /**
     * 初始化导航起始点、途经点参数
     */
    public void initNaviData() {
        JSONObject endPos = null;
        if (naviWay == NaviWay.WALK) {
            // 步行导航方式
            endPos = naviData.optJSONObject(1);
        } else {
            // 驾车导航方式
            endPos = naviData.optJSONObject(2);
            // 途经点,最多设置3个
            JSONArray wayPosArray = naviData.optJSONArray(
                    1) == null ? new JSONArray() : naviData.optJSONArray(1);
            int length = wayPosArray.length() > 3 ? 3 : wayPosArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject wayPos = naviData.optJSONObject(i);
                if (type == 0) {
                    mWayPointList.add(new NaviLatLng(wayPos.optDouble("latitude"),
                            wayPos.optDouble("KEY_LONGITUDE")));
                } else {
                    mWayPointList.add(
                            convert(wayPos.optDouble("latitude"), wayPos.optDouble("KEY_LONGITUDE"),
                                    CoordType.GPS));
                }
            }
        }
        if (type == 0) {
            endList.add(new NaviLatLng(endPos.optDouble("latitude"),
                    endPos.optDouble("KEY_LONGITUDE")));
        } else {
            endList.add(convert(endPos.optDouble("latitude"), endPos.optDouble("KEY_LONGITUDE"),
                    CoordType.GPS));
        }
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "init navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion,
         * avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         * 	说明:
         *		以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *		注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (naviWay == NaviWay.WALK) {
            mAMapNavi.calculateWalkRoute(startList.get(0), endList.get(0)); // 步行导航
        } else {
            mAMapNavi.calculateDriveRoute(startList, endList, mWayPointList, strategy);
        }
    }

    @Override
    public void onStartNavi(int type) {
    }

    @Override
    public void onTrafficStatusUpdate() {
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
    }

    @Override
    public void onGetNavigationText(int type, String text) {
    }

    /**
     * 模拟导航结束
     */
    @Override
    public void onEndEmulatorNavi() {
    }

    /**
     * 到达目的地
     */
    @Override
    public void onArriveDestination() {
    }

    @Override
    public void onArriveDestination(NaviStaticInfo naviStaticInfo) {

    }

    /**
     * 路径规划完毕,导航开始
     */
    @Override
    public void onCalculateRouteSuccess() {
        Log.i(TAG, "路径规划完毕,开始导航.");
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                boolean naviType = false; // true表示模拟导航，false表示真实GPS导航（默认true）
                if (naviWay == NaviWay.WALK) {
                    naviType = naviData.optBoolean(2, true);
                } else {
                    naviType = naviData.optBoolean(3, true);
                }
                if (naviType) {
                    mAMapNavi.startNavi(NaviType.EMULATOR);
                } else {
                    mAMapNavi.startNavi(NaviType.GPS);
                }
            }
        }, 3000);
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        switch (errorInfo) {
            case 2:
                showToast("请检查网络是否通畅，稍候再试!");
                break;

            case 3:
            case 6:
                showToast("请选择国内坐标点，确保经纬度格式正常!");
                break;

            case 4:
                showToast("协议解析错误!");
                break;

            default:
                showToast("路径规划失败： " + errorInfo);
                break;
        }
    }

    @Override
    public void onReCalculateRouteForYaw() {
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
    }

    @Override
    public void hideCross() {
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {
    }

    @Override
    public void hideLaneInfo() {
    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {
    }

    @Override
    public void notifyParallelRoad(int i) {
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
    }

    /**
     * 定位回调接口实现
     */
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (!isDestroy && amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                startList.add(
                        new NaviLatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
                initNavi();
                onInitNaviSuccess();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + amapLocation.getErrorCode() +
                        ", errInfo:" + amapLocation.getErrorInfo());
                Toast.makeText(AmapNaviActivity.this,
                        "location Error, ErrCode:" + amapLocation.getErrorCode() +
                                ", errInfo:" + amapLocation.getErrorInfo(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onNaviSetting() {
    }

    /**
     * 导航结束
     */
    @Override
    public void onNaviCancel() {
        Log.i(TAG, "导航结束.");
        finish();
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int isLock) {
    }

    @Override
    public void onNaviTurnClick() {
    }

    @Override
    public void onNextRoadClick() {
    }

    @Override
    public void onScanViewButtonClick() {
    }

    @Override
    public void onLockMap(boolean isLock) {
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功");
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    /**
     * 显示Toast
     *
     * @param message
     */
    public void showToast(String message) {
        Toast.makeText(AmapNaviActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDestroy = false;

        mAMapNaviView = new AMapNaviView(this);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mAMapNaviView.setLayoutParams(layoutParams);
        setContentView(mAMapNaviView);

        naviWay = (NaviWay) getIntent().getSerializableExtra(NAVI_WAY);
        try {
            naviData = new JSONArray(getIntent().getStringExtra(NAVI_DATA));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            finish();
        }

        if (naviWay == NaviWay.WALK) {
            type = naviData.optInt(3, type);
        } else {
            type = naviData.optInt(4, type);
        }

        initNaviData();
        try {
            // 起点坐标为空,则获取设备当前坐标作为起点
            JSONObject startPos = naviData.getJSONObject(0);
            if (type == 0) {
                startList.add(new NaviLatLng(startPos.optDouble("latitude"),
                        startPos.optDouble("KEY_LONGITUDE")));
            } else {
                startList.add(
                        convert(startPos.optDouble("latitude"), startPos.optDouble("KEY_LONGITUDE"),
                                CoordType.GPS));
            }
            initNavi();
        } catch (Exception e) {
            // 使用定位,获取当前坐标
            Log.i(TAG, "起点坐标为空,获取设备定位坐标作为起点.");
            initLocation();
        }

        mAMapNaviView.onCreate(savedInstanceState);
        setAmapNaviViewOptions();
        mAMapNaviView.setAMapNaviViewListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mAMapNaviView.onPause();
            // 仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
            mTtsManager.stopSpeaking();
            // 停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
            mAMapNavi.stopNavi();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
        try {
            mAMapNaviView.onDestroy();
            // since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
            mAMapNavi.stopNavi();
            mAMapNavi.destroy();
            mTtsManager.destroy();
            if (null != mlocationClient) {
                /**
                 * 如果AMapLocationClient是在当前Activity实例化的，
                 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
                 */
                mlocationClient.onDestroy();
                mlocationClient = null;
                mLocationOption = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * 设置导航参数
     */
    private void setAmapNaviViewOptions() {
        if (mAMapNaviView == null) {
            return;
        }
        AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
        // viewOptions.setTilt(0); // 0 表示使用2D地图
        // viewOptions.setSettingMenuEnabled(true);//设置菜单按钮是否在导航界面显示
        // viewOptions.setNaviNight(mDayNightFlag);//设置导航界面是否显示黑夜模式
        viewOptions.setReCalculateRouteForYaw(true);// 设置偏航时是否重新计算路径mDeviationFlag
        // viewOptions.setReCalculateRouteForTrafficJam(mJamFlag);//前方拥堵时是否重新计算路径
        // viewOptions.setTrafficInfoUpdateEnabled(mTrafficFlag);//设置交通播报是否打开
        // viewOptions.setCameraInfoUpdateEnabled(mCameraFlag);//设置摄像头播报是否打开
        // viewOptions.setScreenAlwaysBright(mScreenFlag);//设置导航状态下屏幕是否一直开启。
        // viewOptions.setNaviViewTopic(mThemeStle);//设置导航界面的主题
        mAMapNaviView.setViewOptions(viewOptions);
    }

    /**
     * 导航方式枚举
     */
    public enum NaviWay {
        DRIVE, // 驾车
        WALK   // 步行
    }

}
