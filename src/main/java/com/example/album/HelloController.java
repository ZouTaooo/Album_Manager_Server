package com.example.album;
import com.tencent.cloud.CosStsClient;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.TreeMap;


@RestController
public class HelloController {
    @RequestMapping("/key")
    public String key()
    {
        //方式一
        TreeMap<String, Object> config = new TreeMap<String, Object>();

        try {
//            Properties properties = new Properties();
//            File configFile = new File("a.properties");
//            properties.load(new FileInputStream(configFile));

            // 云 API 密钥 secretId
            config.put("secretId", "");

            // 云 API 密钥 secretKey
            config.put("secretKey", "");
            //若需要设置网络代理，则可以如下设置
//            if (properties.containsKey("https.proxyHost")) {
//                System.setProperty("https.proxyHost", properties.getProperty("https.proxyHost"));
//                System.setProperty("https.proxyPort", properties.getProperty("https.proxyPort"));
//            }

            // 临时密钥有效时长，单位是秒
            config.put("durationSeconds", 1800);

            // 换成你的 bucket
            config.put("bucket", "ai-album-1253931649");

            // 换成 bucket 所在地区
            config.put("region", "ap-chengdu");

            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径，
            // 例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
            config.put("allowPrefix", "*");

            // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[] {"*"};
            config.put("allowActions", allowActions);

            // 请求临时密钥信息
            JSONObject credential = CosStsClient.getCredential(config);

            // 请求成功：打印对应的临时密钥信息
            String res = credential.toString(4);
            //System.out.println(res);
            return res;
        } catch (Exception e) {
            // 请求失败，抛出异常
            throw new IllegalArgumentException("no valid secret !");
        }
    }
}
