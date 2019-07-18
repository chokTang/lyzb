package com.szy.yishopcustomer.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.SpeechRecognizer;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.baidu.speech.asr.SpeechConstant;
import com.szy.common.Other.CommonEvent;
import com.szy.common.Other.CommonRequest;
import com.szy.common.View.CommonRecyclerView;
import com.szy.yishopcustomer.Activity.GoodsListActivity;
import com.szy.yishopcustomer.Activity.SearchActivity;
import com.szy.yishopcustomer.Activity.ShopStreetActivity;
import com.szy.yishopcustomer.Adapter.SearchAdapter;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.EventWhat;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.szy.yishopcustomer.Constant.Key;
import com.szy.yishopcustomer.Constant.ViewType;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.ResponseModel.Checkout.CheckoutDividerModel;
import com.szy.yishopcustomer.ResponseModel.Search.KeyWordModel;
import com.szy.yishopcustomer.ResponseModel.Search.Model;
import com.szy.yishopcustomer.ResponseModel.Search.SearchHintModel;
import com.szy.yishopcustomer.Util.HttpResultManager;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.Util.Utils;
import com.szy.yishopcustomer.ViewModel.SearchTitleModel;
import com.szy.yishopcustomer.core.voice.CustomVolumeView;
import com.szy.yishopcustomer.core.voice.control.MyRecognizer;
import com.szy.yishopcustomer.core.voice.recognization.ChainRecogListener;
import com.szy.yishopcustomer.core.voice.recognization.CommonRecogParams;
import com.szy.yishopcustomer.core.voice.recognization.IRecogListener;
import com.szy.yishopcustomer.core.voice.recognization.RecogResult;
import com.szy.yishopcustomer.core.voice.recognization.online.OnlineRecogParams;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by liuzhifeng on 2017/2/15.
 * All Rights Reserved By 秦皇岛商之翼网络科技有限公司.
 */

public class SearchFragment extends YSCBaseFragment implements View.OnTouchListener {

    @BindView(R.id.fragment_search_recycler_view)
    public CommonRecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private ArrayList<Object> mList;
    private ArrayList<String> mSearchHistoryList;
    private String searchWord;
    private Model model;

    /*** 搜索提示语 联动数据 list**/
    private List<String> Tags = new ArrayList<>();

    public static final int STATE_DEFAULT = 0;
    public static final int STATE_SHOWING = 1;
    public static final int STATE_WIPEUP = 2;

    public boolean isSearchAction = false;

    private View relativeLayout_voice;

    private View linearLayout_button;
    private View linearLayout_voice;

    private View textView_cancel_default;
    private View textView_cancel;

    private ImageView imageView_voice;
    private MyRecognizer myRecognizer;

    private TextView textView_message;

    private CustomVolumeView customVolumeView;

    private ChainRecogListener listener;
    private CommonRecogParams apiParams;

    private Handler mHandler = new Handler();

    private float firstTouch = 0;
    //向上移动距离，才可以取消
    private int moveDistance = 50;
    boolean isCancleRegon = false;
    /**
     * 是否正在识别
     */
    private volatile boolean mIsRunning = false;
    private SearchActivity parentActivty;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivty = (SearchActivity) context;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.linearLayout_button:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        start();
                        mIsRunning = true;
                        isCancleRegon = false;
                        firstTouch = event.getY();
                        updateVoiceState(STATE_SHOWING);
                        break;
                    case MotionEvent.ACTION_UP:
                        mIsRunning = false;
                        updateVoiceState(STATE_DEFAULT);
                        speakFinish();
                        if (isCancleRegon) {
                            cancleRecognition();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(event.getY() - firstTouch) > moveDistance) {
                            isCancleRegon = true;
                            updateVoiceState(STATE_WIPEUP);
                        } else {
                            isCancleRegon = false;
                            updateVoiceState(STATE_SHOWING);
                        }
                        break;
                }
                return true;
        }
        return false;
    }

    private Runnable closeTips = new Runnable() {

        @Override
        public void run() {
            closeTipOrSearch();
        }
    };


    void closeTipOrSearch() {
        relativeLayout_voice.setVisibility(View.INVISIBLE);

        if (!isCancleRegon && TextUtils.isEmpty(mErrorRes)) {
            if (getActivity() instanceof SearchActivity) {
                ((SearchActivity) getActivity()).setKeyword(textView_message.getText().toString());
                search();
            } else {
                setKeyword(textView_message.getText().toString());
                search();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = R.layout.fragment_search;
        mAdapter = new SearchAdapter();
        mAdapter.onCLickListener = this;
        mList = new ArrayList<Object>();
        mSearchHistoryList = new ArrayList<String>();
    }

    //搜索词 提示列表 文本
    private String search_hint_text = null;
    //搜索词 提示列表 文本标签
    private String search_hint_tag = null;

    @Override
    public void onClick(View view) {
        ViewType viewType = Utils.getViewTypeOfTag(view);
        int position = Utils.getPositionOfTag(view);
        int extra = Utils.getExtraInfoOfTag(view);
        switch (viewType) {
            case VIEW_TYPE_SEARCH_DELETE:
                showDialog();
                break;
            case VIEW_TYPE_SEARCH_BUTTON:
                clickHot(position, extra);
                break;
            case VIEW_TYPE_SEARCH_BUTTON_HISTORY:
                clickHistory(position);
                break;
            case VIEW_TYPE_SEARCH_HINT_LIST:
                search_hint_text = mAdapter.mHintModels.get(position).row;
                openGoodsListActivity(search_hint_text);
                break;


            case VIEW_TYPE_SEARCH_HINT_SHOP_NAME:
                String shop = mAdapter.mHintModels.get(position).row;
                openShopStreetActivity(shop);
                break;
            case VIEW_TYPE_SEARCH_HINT_ONE_TAG:
                search_hint_text = mAdapter.mHintModels.get(position).row;
                search_hint_tag = mAdapter.mHintModels.get(position).tags.get(0).toString();
                openGoodsListActivity(search_hint_text + " " + search_hint_tag);
                break;
            case VIEW_TYPE_SEARCH_HINT_TWO_TAG:
                search_hint_text = mAdapter.mHintModels.get(position).row;
                search_hint_tag = mAdapter.mHintModels.get(position).tags.get(1).toString();
                openGoodsListActivity(search_hint_text + " " + search_hint_tag);
                break;
            case VIEW_TYPE_SEARCH_HINT_THR_TAG:
                search_hint_text = mAdapter.mHintModels.get(position).row;
                search_hint_tag = mAdapter.mHintModels.get(position).tags.get(2).toString();
                openGoodsListActivity(search_hint_text + " " + search_hint_tag);
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    private List<SearchHintModel> mHintModels = new ArrayList<>();
    private List<SearchHintModel> mHintModelsall = new ArrayList<>();

    /*****
     * 根据输入内容 动态展示 提示结果List
     * @param content
     */
    public void searchHint(final String content) {

        RequestQueue requestQueue = NoHttp.newRequestQueue();
        final Request<String> request = NoHttp.createStringRequest(Api.API_SEARCH_KEY_LIST, RequestMethod.GET);
        RequestAddHead.addNoHttpHead(request, getActivity(), Api.API_SEARCH_KEY_LIST, "GET");
        request.add("msg", content);

        requestQueue.add(HttpWhat.HTTP_SEARCH_KEY_LIST.getValue(), request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response.responseCode() == Utils.CITY_NET_SUCES) {
                    JSONObject data = JSONObject.parseObject(response.get());
                    try {
                        mHintModels = JSONObject.parseArray(data.getString("list"), SearchHintModel.class);

                        mAdapter.data.clear();
                        mAdapter.mHintModels.clear();

                        if (mHintModels.size() > 0 || mHintModels != null) {
//                            添加第一个数据为店铺搜索
                            SearchHintModel bean = new SearchHintModel();
                            bean.row = content;
                            mHintModelsall.add(bean);
                            mHintModelsall.addAll(mHintModels);
                            mAdapter.mHintModels = mHintModelsall;
                            mAdapter.isHintList = true;
                        }
                        mAdapter.notifyDataSetChanged();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

    }


    private void clickHot(int position, int extra) {
        List<KeyWordModel> item = (List<KeyWordModel>) mAdapter.data.get(position);
        openGoodsListActivity(item.get(extra).keyword);
    }

    private void clickHistory(int position) {
        String item = (String) mAdapter.data.get(position);
        openGoodsListActivity(item);
    }

    private void clearHistoryAll() {
        mSearchHistoryList.clear();
        saveArray(mSearchHistoryList);
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i) instanceof String) {
                mList.remove(mList.get(i));
                i--;
            }

            if (mList.get(i) instanceof Integer) {
                mList.remove(mList.get(i));
                i--;
            }

            if (mList.get(i) instanceof SearchTitleModel) {
                SearchTitleModel searchTitleModel = (SearchTitleModel) mList.get(i);
                if ("historySearch".equals(searchTitleModel.titleType)) {
                    mList.remove(mList.get(i));
                    i--;
                }
            }
        }
//        CheckoutDividerModel itemEmpty = new CheckoutDividerModel();
//        itemEmpty.textString = "暂无搜索记录";
//        mList.add(itemEmpty);
        mAdapter.setData(mList);

        mAdapter.isHaveHistory = false;
        mAdapter.notifyDataSetChanged();
    }

    private void clearHistory(int position) {
        mSearchHistoryList.clear();
        saveArray(mSearchHistoryList);
        int removeSize = mList.size() - position - 1;
        for (int i = 0; i < removeSize; i++) {
            mList.remove(position + 1);
        }
//        CheckoutDividerModel itemEmpty = new CheckoutDividerModel();
//        itemEmpty.textString = "暂无搜索记录";
//        mList.add(itemEmpty);
        mAdapter.setData(mList);

        mAdapter.isHaveHistory = false;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        upData();
        refresh();

        linearLayout_button = view.findViewById(R.id.linearLayout_button);
        linearLayout_voice = view.findViewById(R.id.linearLayout_voice);
        textView_cancel_default = view.findViewById(R.id.textView_cancel_default);
        textView_cancel = view.findViewById(R.id.textView_cancel);

        relativeLayout_voice = view.findViewById(R.id.relativeLayout_voice);

        textView_message = (TextView) view.findViewById(R.id.textView_message);

        imageView_voice = (ImageView) view.findViewById(R.id.imageView_voice);
        customVolumeView = (CustomVolumeView) view.findViewById(R.id.customVolumeView);


        linearLayout_button.setOnTouchListener(this);

        initRecog();
        updateVoiceState(STATE_DEFAULT);


        return view;
    }


    private void upData() {
        SearchTitleModel item = new SearchTitleModel();
        item.titleName = "热搜";
        item.titleType = "hotSearch";
        mList.add(item);

        CheckoutDividerModel itemEmpty = new CheckoutDividerModel();
        itemEmpty.textString = "暂无内容";
        mList.add(itemEmpty);

//        item = new SearchTitleModel();
//        item.titleName = "最近搜索";
//        item.titleType = "historySearch";
//        if (loadArray(mSearchHistoryList).size() == 0) {
//            item.titleType = "hotSearch";
//        } else {
//            item.titleType = "historySearch";
//        }
//        mList.add(item);

//        itemEmpty = new CheckoutDividerModel();
//        itemEmpty.textString = "暂无历史搜索记录";
//        mList.add(itemEmpty);

        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
    }

    public void refresh() {
        CommonRequest request = new CommonRequest(Api.API_SEARCH, HttpWhat.HTTP_GET_HOT_SEARCH.getValue());
        addRequest(request);
    }

    @Override
    public void onRequestSucceed(int what, String response) {
        switch (HttpWhat.valueOf(what)) {
            case HTTP_GET_HOT_SEARCH:
                refreshCallback(response);
                break;
            default:
                break;
        }
    }

    private void refreshCallback(String response) {
        HttpResultManager.resolve(response, Model.class, new HttpResultManager.HttpResultCallBack<Model>() {
            @Override
            public void onSuccess(Model back) {
                model = back;

                if (Utils.isNull(back.data.hot_search_keywords) && Utils.isNull(mSearchHistoryList)) {

                    mList.clear();
                    CheckoutDividerModel itemEmpty = new CheckoutDividerModel();
                    itemEmpty.textString = "暂无内容";
                    mList.add(itemEmpty);
                    mAdapter.setData(mList);
                    mAdapter.notifyDataSetChanged();

                } else {

                    mList.remove(1);
                    mList.add(1, back.data.hot_search_keywords);

                    if (loadArray(mSearchHistoryList).size() != 0) {
//                        mList.remove(mList.size() - 1);

                        SearchTitleModel item = new SearchTitleModel();
                        item.titleName = "最近搜索";
                        item.titleType = "historySearch";
                        mList.add(item);

                        ArrayList<String> tempList = loadArray(mSearchHistoryList);
                        Collections.reverse(tempList);
                        mList.addAll(mList.size(), tempList);
                        mList.add(-1);
                    }


                    mAdapter.setData(mList);
                    mAdapter.notifyDataSetChanged();

                    if (!back.data.show_keywords.keyword.equals("")) {
                        setKeyword(back.data.show_keywords.keyword);
                        EventBus.getDefault().post(new CommonEvent(EventWhat.EVENT_SHOW_KEYWORDS.getValue()));
                    }
                }
            }
        });
    }

    public void setKeyword(String value) {
        searchWord = value;
    }

    public String showKeyword() {
        return model.data.show_keywords.show_words;
    }

    public void search() {
        if (searchWord != null && searchWord.trim().length() > 0) {
            mSearchHistoryList.add(searchWord);
            saveArray(mSearchHistoryList);
        }

        //跳转相关页面
        openGoodsListActivity(searchWord);
//        openShopStreetActivity(searchWord);
    }


    public boolean saveArray(ArrayList<String> list) {
        SharedPreferences sp = getActivity().getSharedPreferences("HistoryList", MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit1.remove("Status_" + i);
            if (!TextUtils.isEmpty(list.get(i))) {
                mEdit1.putString("Status_" + i, list.get(i));
            }
        }
        return mEdit1.commit();
    }

    public ArrayList<String> loadArray(ArrayList<String> list) {
        SharedPreferences mSharedPreference1 = getActivity().getSharedPreferences("HistoryList",
                MODE_PRIVATE);
        List<String> newList = new ArrayList();


        int size = mSharedPreference1.getInt("Status_size", 0);
        for (int i = 0; i < size; i++) {
            newList.add(mSharedPreference1.getString("Status_" + i, null));
        }

        Set set = new HashSet();

        list.clear();
        for (String cd : newList) {
            if (set.add(cd)) {
                list.add(cd);
            }
        }

        return list;
    }

    private void openGoodsListActivity(String value) {
        Intent intent = new Intent();

        if (isSearchAction) {
            intent.putExtra(Key.KEY_KEYWORD_ACTION.getValue(), 1);
        }
        intent.putExtra(Key.KEY_KEYWORD.getValue(), value);
        intent.setClass(getActivity(), GoodsListActivity.class);
        startActivity(intent);
        finish();

    }

    private void openShopStreetActivity(String value) {
        Intent intent = new Intent();
        intent.putExtra(Key.KEY_KEYWORD.getValue(), searchWord);
        intent.setClass(getActivity(), ShopStreetActivity.class);
        startActivity(intent);
        finish();
    }

    public void showDialog() {
        showConfirmDialog(R.string.searchClearHistory, ViewType.VIEW_TYPE_CLEAR_HISTORY.ordinal());
    }

    @Override
    public void onConfirmDialogConfirmed(int viewType, int position, int extraInfo) {
        switch (ViewType.valueOf(viewType)) {
            case VIEW_TYPE_CLEAR_HISTORY:
                clearHistoryAll();
                break;
        }
    }

    private void initRecog() {
        listener = new ChainRecogListener();
        listener.addListener(new DialogListener());
        myRecognizer = new MyRecognizer(getActivity(), listener);
        apiParams = getApiParams();
    }

    private CommonRecogParams getApiParams() {
        return new OnlineRecogParams(getActivity());
    }


    /**
     * 开始录音，点击“开始”按钮后调用。
     */
    private void start() {
        mErrorRes = "";
        textView_message.setText("");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Map<String, Object> params = apiParams.fetch(sp);

        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, true);
        params.put(SpeechConstant.VAD, SpeechConstant.VAD_TOUCH);
        myRecognizer.start(params);

        mHandler.removeCallbacks(closeTips);
    }

    /**
     * 取消当前识别
     */
    private void cancleRecognition() {
        myRecognizer.cancel();
        onEndOfSpeech();
    }

    private void speakFinish() {
        myRecognizer.stop();
    }

    public static final int STATUS_None = 0;
    public static final int STATUS_WaitingReady = 2;
    public static final int STATUS_Ready = 3;
    public static final int STATUS_Speaking = 4;
    public static final int STATUS_Recognition = 5;
    protected int status = STATUS_None;

    int currentState = -1;

    void updateVoiceState(int state) {
        if (currentState == state) {
            return;
        }

        currentState = state;
        switch (state) {
            case STATE_DEFAULT:
                linearLayout_voice.setVisibility(View.INVISIBLE);
                break;
            case STATE_SHOWING:
                relativeLayout_voice.setVisibility(View.VISIBLE);
                linearLayout_voice.setVisibility(View.VISIBLE);
                imageView_voice.setImageResource(R.mipmap.ic_voice_speech);
                textView_cancel_default.setVisibility(View.VISIBLE);
                textView_cancel.setVisibility(View.GONE);
                break;
            case STATE_WIPEUP:
                linearLayout_voice.setVisibility(View.VISIBLE);
                imageView_voice.setImageResource(R.mipmap.ic_voice_cancellation);
                textView_cancel_default.setVisibility(View.GONE);
                textView_cancel.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void onVolumeChanged(int volume) {
        customVolumeView.setProgress(volume);
    }

    private void onPrepared() {
    }

    private void onBeginningOfSpeech() {
    }

    // 过这么长时间自动跳转
    private long SHOW_SUGGESTION_INTERVAL = 1500;

    private void onEndOfSpeech() {
        if (isCancleRegon) {
            textView_message.setText("语音搜索已取消");
            mHandler.postDelayed(closeTips, SHOW_SUGGESTION_INTERVAL);
        } else {
            textView_message.setText("正在为您识别...");
        }
    }

    private void onPartialResults(String[] results) {
    }

    protected static final int ERROR_NONE = 0;
    private CharSequence mErrorRes = "";
    private static final int ERROR_NETWORK_UNUSABLE = 0x90000;

    private void onFinish(int errorType, int errorCode) {
        if (errorType != ERROR_NONE) {

            switch (errorType) {
                case SpeechRecognizer.ERROR_NO_MATCH:
                    mErrorRes = "没有匹配的识别结果";
                    break;
                case SpeechRecognizer.ERROR_AUDIO:
                    mErrorRes = "启动录音失败";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    mErrorRes = "未检测到语音，请重试";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    mErrorRes = "网络超时，再试一次";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    if (errorCode == ERROR_NETWORK_UNUSABLE) {
                        mErrorRes = "网络不可用，请检查后重试";
                    } else {
                        mErrorRes = "网络不稳定，请稍后重试";
                    }
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    mErrorRes = "客户端错误"; // TODO
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    mErrorRes = "权限不足，请检查设置";// TODO
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    mErrorRes = "引擎忙"; // TODO
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    mErrorRes = "未能识别，请重试";
                    break;
                default:
                    mErrorRes = "未能识别，请重试";

                    break;
            }
            textView_message.setText(mErrorRes);
            mHandler.postDelayed(closeTips, SHOW_SUGGESTION_INTERVAL);
        }
    }

    protected class DialogListener implements IRecogListener {

        /**
         * ASR_START 输入事件调用后，引擎准备完毕
         */
        @Override
        public void onAsrReady() {
            status = STATUS_Ready;
            onPrepared();
        }

        @Override
        public void onAsrBegin() {
            status = STATUS_Speaking;
            onBeginningOfSpeech();
        }

        @Override
        public void onAsrEnd() {
            status = STATUS_Recognition;
            //检测到用户说话结束
            SearchFragment.this.onEndOfSpeech();
        }

        @Override
        public void onAsrPartialResult(String[] results, RecogResult recogResult) {
            onPartialResults(results);
        }

        @Override
        public void onAsrFinalResult(String[] results, RecogResult recogResult) {
            status = STATUS_None;
            mIsRunning = false;
            onFinish(0, 0);
            textView_message.setText(results[0]);
            mHandler.postDelayed(closeTips, SHOW_SUGGESTION_INTERVAL);
        }

        @Override
        public void onAsrFinish(RecogResult recogResult) {
            mIsRunning = false;
            //识别一段话结束。如果是长语音的情况会继续识别下段话。
        }

        @Override
        public void onAsrFinishError(int errorCode, int subErrorCode, String errorMessage, String descMessage, RecogResult recogResult) {
            onFinish(errorCode, subErrorCode);
        }

        @Override
        public void onAsrLongFinish() {
            mIsRunning = false;
        }

        @Override
        public void onAsrVolume(int volumePercent, int volume) {
//            Log.i("TTT","音量百分比"+volumePercent +" ; 音量"+ volume);
            onVolumeChanged(volumePercent);
        }

        @Override
        public void onAsrAudio(byte[] data, int offset, int length) {
        }

        @Override
        public void onAsrExit() {
            //识别引擎结束并空闲中
        }

        @Override
        public void onAsrOnlineNluResult(String nluResult) {
        }

        @Override
        public void onOfflineLoaded() {
            //使用离线命令词时，有该回调说明离线语法资源加载成功
        }

        @Override
        public void onOfflineUnLoaded() {
            //使用离线命令词时，有该回调说明离线语法资源加载成功(离线资源卸载成功。)
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!mIsRunning) {
            myRecognizer.release();
            finish();
        }
    }

    /**
     * 销毁时需要释放识别资源。
     */
    @Override
    public void onDestroy() {
        myRecognizer.release();
        super.onDestroy();
    }
}
