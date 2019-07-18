package com.lyzb.jbx.util.label;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lyzb.jbx.inter.ITextLableClickListener;

import java.util.regex.Pattern;

public class TextLabelUtil {

    private static String label_1 = "#";//标签1
    private static String label_2 = "<url>";

    private static String labelColor = "#247cf0";

    public static void detailLabel(final TextView textView, String content, final ITextLableClickListener clickListener) {
        textView.setTag("");
        if (TextUtils.isEmpty(content)) return;
        //处理#号
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String urlStr = "△";
        Pattern pattern = Pattern.compile("<url>");
        content = pattern.matcher(content).replaceAll(urlStr).trim();
        Pattern pattern1 = Pattern.compile("</url>");
        content = pattern1.matcher(content).replaceAll(urlStr).trim();
        builder.append(content);

        int position = 0;
        int lablelCount = countStr(content, label_1);
        for (int i = 0; i < lablelCount; i++) {
            if (i % 2 == 0) {//处理#前面的数据
                position = content.indexOf(label_1, position);
            } else {
                int endPosition = content.indexOf(label_1, position + 1);
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor(labelColor));
                builder.setSpan(foregroundColorSpan, position, endPosition + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                builder.setSpan(new TypeClickableSpan(content.substring(position, endPosition + 1)) {
                    @Override
                    public void onClick(String value, View widget) {
                        if (clickListener != null) {
                            clickListener.onTypeClick(label_1, value);
                        }
                    }
                }, position, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                position = endPosition + 1;
                textView.setTag("i have click");
            }
        }

        int lablelCountUrl = countStr(content, urlStr);
        position = 0;
        for (int i = 0; i < lablelCountUrl; i++) {
            if (i % 2 == 0) {//处理--前面的数据
                position = content.indexOf(urlStr, position);
            } else {
                int endPosition = content.indexOf(urlStr, position + 1);
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor(labelColor));
                builder.setSpan(foregroundColorSpan, position, endPosition + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                builder.setSpan(new TypeClickableSpan(content.substring(position + 1, endPosition)) {
                    @Override
                    public void onClick(String value, View widget) {
                        if (clickListener != null) {
                            clickListener.onTypeClick(label_2, value);
                        }
                    }
                }, position, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                position = endPosition + 1;
                textView.setTag("i have click");
            }
        }

        position = 0;
        for (int i = 0; i < lablelCountUrl; i++) {
            position = content.indexOf(urlStr, position);
            builder.replace(position, position + 1, " ");
            position++;
        }

        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


    }

    /**
     * 判断str1中包含str2的个数
     *
     * @param str1
     * @param str2
     * @return counter
     */
    public static int countStr(String str1, String str2) {
        int counter = 0;
        while (str1.indexOf(str2) != -1) {
            str1 = str1.substring(str1.indexOf(str2) +
                    str2.length());
            counter++;
        }
        return counter;
    }
}
