package com.lyzb.jbx.webscoket;

import android.os.Handler;
import android.os.Message;

import com.like.utilslib.app.AppUtil;
import com.like.utilslib.other.LogUtil;
import com.lyzb.jbx.api.UrlConfig;
import com.lyzb.jbx.util.AppCommonUtil;
import com.szy.yishopcustomer.Util.App;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Map;

public class BaseClient extends WebSocketClient {

    private static BaseClient client;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (isOpen()) {
                        LogUtil.loge("soknet发送的数据为：" + msg.obj.toString());
                        send(msg.obj.toString());
                        handler.sendMessageDelayed(handler.obtainMessage(1), 100);
                    }
                    break;
                case 1:
                    close();
                    break;
            }
            return true;
        }
    });

    public static BaseClient getInstance() {
        if (client == null) {
            client = new BaseClient(AppCommonUtil.getUrl(UrlConfig.WEB_API));
        }
        return client;
    }

    public BaseClient(URI serverUri) {
        super(serverUri);
    }

    public BaseClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    public BaseClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public BaseClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders) {
        super(serverUri, protocolDraft, httpHeaders);
    }

    public BaseClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        LogUtil.logd("webscoket--opened connection");
    }

    @Override
    public void onMessage(String message) {
        LogUtil.logd("webscoket--received：" + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LogUtil.logd("webscoket--Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        LogUtil.logd("webscoket--" + ex.getMessage());
        ex.printStackTrace();
    }

    /**
     * 传入socket通信
     *
     * @param viewType 浏览类型 1 名片 2 动态 3商品
     * @param id       操作对像 id(名片id,动态id,商品id)
     * @param ownerId  操作对象所有者ID
     * @param operId   操作对像记录id(商品时用其它地方不管)
     */
    public void setMessage(int viewType, String id, String ownerId, String operId) {
        if (!App.getInstance().isLogin() || App.getInstance().userId.equals(ownerId)) return;
        JSONObject object = new JSONObject();
        try {
            object.put("viewType", viewType);
            object.put("userId", App.getInstance().userId);
            object.put("id", id);
            object.put("source", 4);
            object.put("version", AppUtil.getVersionName());
            object.put("operId", operId);
            object.put("shareView", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (isClosed()) {
            reconnect();
        } else {
            connect();
        }
        handler.sendMessageDelayed(handler.obtainMessage(0, object.toString()), 500);
    }

    public void setMessage(int viewType, String id, String ownerId) {
        setMessage(viewType, id, ownerId, "");
    }

    @Override
    public void close() {
        super.close();
        if (client != null) {
            client = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        LogUtil.loge("soknet发送的数据为：关闭了数据");
    }
}
