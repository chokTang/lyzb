package com.like.utilslib.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.like.utilslib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用的方法类 日常处理类
 * 提供:数据非空、类型强转、button防止多次点击
 * Created by longshao on 2017/5/9.
 */

public class CommonUtil {

    private static long lastClickTime = 0;
    private static long DIFF = 1000;
    private static int lastButtonId = -1;

    private static final String SEP1 = ",";

    /**
     * 判断是否非空
     *
     * @param obj
     * @return
     */
    public static boolean isNull(String obj) {
        if (TextUtils.isEmpty(obj) || obj.equals("null")) {
            return true;
        }
        return false;
    }


    /**
     * 类型强转化
     *
     * @param object
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T converToT(Object object, T defaultValue) {
        if (object == null || "".equals(object)) {
            return defaultValue;
        }
        Class<?> valueClass = defaultValue.getClass();
        if (valueClass == int.class || valueClass == Integer.class) {
            return (T) (Integer) Integer.parseInt(object.toString());
        }
        if (valueClass == double.class || valueClass == Double.class) {
            return (T) (Double) Double.parseDouble(object.toString());
        }
        if (valueClass == float.class || valueClass == Float.class) {
            return (T) (Float) Float.parseFloat(object.toString());
        }
        if (valueClass == boolean.class || valueClass == Boolean.class) {
            return (T) (Boolean) Boolean.parseBoolean(object.toString());
        }
        if (valueClass == String.class) {
            return (T) object.toString();
        }
        return defaultValue;
    }

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }

    public static String ListToString(List<?> list) {
        return ListToString(list, SEP1);
    }

    public static String ListToString(List<?> list, String spit) {
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i) == "") {
                    continue;
                }
                // 如果值是list类型则调用自己
                if (list.get(i) instanceof List) {
                    sb.append(ListToString((List<?>) list.get(i), spit));
                    if (i != list.size() - 1) {
                        sb.append(spit);
                    }
                } else {
                    sb.append(list.get(i));
                    if (i != list.size() - 1) {
                        sb.append(spit);
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * String转换List
     *
     * @param listText :需要转换的文本
     * @return List<?>
     */
    public static List<String> StringToList(String listText) {
        if (listText == null || listText.equals("")) {
            return null;
        }
        List<String> list = new ArrayList<>();
        String[] text = listText.split(SEP1);
        for (String str : text) {
            list.add(str);
        }
        return list;
    }

    /****
     *
     * @param split 分割的符号
     * @param listText
     * @return
     */
    public static List<String> StringToList(String split, String listText) {

        if (listText == null || listText.equals("")) {
            return null;
        }
        List<String> list = new ArrayList<>();
        String[] text = listText.split(split);
        for (String str : text) {
            list.add(str);
        }
        return list;
    }

    /**
     * 文字设置复制功能
     *
     * @param textView
     */
    public static void textViewCopy(final TextView textView) {
        if (textView == null) return;
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager) textView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", textView.getText().toString());
                cm.setPrimaryClip(mClipData);
                Toast.makeText(textView.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /**
     * 文字设置复制功能
     *
     * @param textView
     */
    public static void textViewClickCopy(final TextView textView,TextView clickView) {
        if (textView == null) return;
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) textView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", textView.getText().toString());
                cm.setPrimaryClip(mClipData);
                Toast.makeText(textView.getContext(), "内容已复制", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
