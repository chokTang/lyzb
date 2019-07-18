package com.szy.yishopcustomer.Activity.im;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.szy.yishopcustomer.Constant.Api;
import com.szy.yishopcustomer.Constant.HttpWhat;
import com.lyzb.jbx.R;
import com.szy.yishopcustomer.Util.RequestAddHead;
import com.szy.yishopcustomer.View.CommonDialog;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wyx
 * @role 群组资料
 * @time 2018 2018/12/24 15:43
 */

public class GroupDetailActivity extends Activity {

    //退出群组的动作标识
    public static boolean isExitGroup = false;

    @BindView(R.id.img_user_group_back)
    ImageView backImg;
    @BindView(R.id.img_user_message_free)
    ImageView freeImg;
    @BindView(R.id.tv_user_group_clear)
    TextView clearText;
    @BindView(R.id.tv_ext_group)
    TextView exitText;

    private String mGroupId = null;
    private String mUserId = null;

    private CommonDialog clearDialog;
    private CommonDialog exitDialog;

    private EMGroup group;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_group_info);
        ButterKnife.bind(this);

        mGroupId = getIntent().getExtras().getString("groupId");
        mUserId = getIntent().getExtras().getString("userId");

        group = EMClient.getInstance().groupManager().getGroup(mGroupId);

        if (group.isMsgBlocked()) {
            //已开启消息免打扰
            freeImg.setImageResource(R.mipmap.ic_scan_open);
        } else {
            freeImg.setImageResource(R.mipmap.ic_scan_close);
        }

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        freeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (group.isMsgBlocked()) {
                    //接受群消息
                    setAcceptMessage(mGroupId);
                } else {
                    //屏蔽群消息
                    setNoMessage(mGroupId);
                }
            }
        });

        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clearDialog == null) {
                    clearDialog = new CommonDialog.Builder(GroupDetailActivity.this)
                            .setNegativeButton("取消", new CommonDialog.OnClickListener() {
                                @Override
                                public void onClick(String content) {
                                    clearDialog.dismiss();
                                }
                            })
                            .setPositiveButton("确认", new CommonDialog.OnClickListener() {
                                @Override
                                public void onClick(String content) {
                                    //清空消息内容
                                    clearMessage(mGroupId);
                                    clearDialog.dismiss();
                                }
                            })
                            .setHeight(60)
                            .setContent("清空聊天记录后,消息列表将无法显示群组消息,是否清空?")
                            .create();
                }
                clearDialog.show();
            }
        });

        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (exitDialog == null) {
                    exitDialog = new CommonDialog.Builder(GroupDetailActivity.this)
                            .setNegativeButton("取消", new CommonDialog.OnClickListener() {
                                @Override
                                public void onClick(String content) {
                                    exitDialog.dismiss();
                                }
                            })
                            .setPositiveButton("确认", new CommonDialog.OnClickListener() {
                                @Override
                                public void onClick(String content) {
                                    //清空消息内容
                                    exitGroup();
                                    exitDialog.dismiss();
                                }
                            })
                            .setHeight(60)
                            .setContent("退出群聊后,消息列表无法显示群消息,是否退出？")
                            .create();
                }
                exitDialog.show();
            }
        });
    }

    /****
     * 接受此群的消息
     * @param groupId
     */
    void setAcceptMessage(final String groupId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().unblockGroupMessage(groupId);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            freeImg.setImageResource(R.mipmap.ic_scan_close);
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GroupDetailActivity.this, R.string.group_set_acc_message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    /****
     * 屏蔽此群的消息
     * @param groupId
     */
    void setNoMessage(final String groupId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().blockGroupMessage(groupId);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            freeImg.setImageResource(R.mipmap.ic_scan_open);
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GroupDetailActivity.this, R.string.group_set_no_message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }


    /****
     * 清空 此群的聊天内容
     *
     * @param groupId
     */
    void clearMessage(final String groupId) {

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(groupId, EMConversation.EMConversationType.GroupChat);
        if (conversation != null) {
            conversation.clearAllMessages();
        }

        Toast.makeText(this, R.string.group_message_clear_success, Toast.LENGTH_SHORT).show();
    }

    void exitGroup() {

        RequestQueue requestQueue = NoHttp.newRequestQueue();

        final Request<String> request = NoHttp.createStringRequest(Api.API_IM_GROUP_USER_EXIT, RequestMethod.POST);
        RequestAddHead.addNoHttpHead(request, this, Api.API_IM_GROUP_USER_EXIT, "POST");

        JSONObject object = new JSONObject();
        try {
            object.put("id", mGroupId);
            object.put("username", mUserId);

            request.setDefineRequestBodyForJson(object.toString());
            requestQueue.add(HttpWhat.HTTP_IM_GROUP_EXIT.getValue(), request, new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onSucceed(int what, Response<String> response) {
                    if (response.responseCode() == Api.SUCCESS_INT) {
                        String status = com.alibaba.fastjson.JSONObject.parseObject(response.get()).getString("status");
                        if (status.equals(Api.SUCCESS_STRING)) {
                            isExitGroup = true;

                            //同时清空聊天内容
                            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mGroupId, EMConversation.EMConversationType.GroupChat);
                            if (conversation != null) {
                                conversation.clearAllMessages();
                            }

                            EaseConstant.IS_EXIT_GROUP = true;

                            Toast.makeText(GroupDetailActivity.this, "已退出该群", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(GroupDetailActivity.this, R.string.net_request_exc, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(GroupDetailActivity.this, R.string.net_request_exc, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    Toast.makeText(GroupDetailActivity.this, R.string.net_request_failed, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish(int what) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
