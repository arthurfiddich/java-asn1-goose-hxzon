package org.hxzon.asn1.core.type.ext;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hxzon.asn1.core.type.BerOctetString;
import org.hxzon.util.BytesUtil;

public class UtcTime extends BerOctetString {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
    private static final DecimalFormat decimalFormat = new DecimalFormat();
    private static final String numberPattern = "0.000000";
    static {
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        decimalFormat.applyPattern(numberPattern);
    }

    //前4个字节是从1970年1月1日0时0分0秒开始的秒数，紧跟的3个字节是秒的小数部分，最后一个字节是时间品质和精度。
    //秒值=0x49 56 dc 03 = 1230429187s；
    //秒的小数部分=(0x6d 0e 56) / (2^24) = 0.426000s；
    //时间品质=0x50=0101 0000B：时钟错误，精度为16（25微秒，T3级时钟）。
    //84 08 49 56 dc 03 6d 0e 56 50
    //参见：61850-7-2：5.5.3.7.2，61850-7-2：5.5.3.7.3，61850-8-1：8.1.3.6。
    public String getValueAsString() {
        try {
            String str = BytesUtil.toHexString(getValue());
            long seconds = Long.parseLong(str.substring(0, 8), 16);
            long millis = Long.parseLong(str.substring(8, 14), 16);
            double millisD = millis / Math.pow(2, 24);
//		Date date = new Date(seconds * 1000 + millisD * 1000);
            Date date = new Date(seconds * 1000);
            return format.format(date) + decimalFormat.format(millisD).substring(1) + "," + str.substring(14);
        } catch (Exception e) {
            return super.getValueAsString();
        }
    }
}