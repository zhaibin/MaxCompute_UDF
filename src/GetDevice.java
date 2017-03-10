import com.aliyun.odps.udf.UDF;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetDevice extends UDF {

    public String evaluate(String s) {
        Pattern pattern = Pattern.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
        Matcher matcher = pattern.matcher(s);
        String ua = "";
        if (matcher.find()) {
            ua = matcher.group(1).trim();
            //处理有些机型前面有“zh-cn;”的问题；
            if(ua.contains(";")){
                String[] device = ua.split(";");
                ua = device[device.length - 1];
            }
            ua = ua.trim();
            if(ua.equals("")){
                //处理金立ua在bulid前无机型
                Integer pos1 = s.indexOf(" RV/");
                Integer pos2 = s.lastIndexOf(" ",pos1-1);
                Integer pos3 = s.lastIndexOf("/",pos1-1);
                ua = s.substring(pos2,pos3);
            }
        }
        return ua.trim();
    }
}