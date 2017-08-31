/**
 * @author lierl
 * @create 2017-08-30 17:51
 **/
public class ParseCookie {
	public static void main(String[] args) {
		String cookies="www51cto=23B0AE4105E8E2B136A116C4D1AD5F62rhfA; pub_smile=1D1D0D9; Cto_lvt_d2cb9d6ccefce8d1e89582c2a2a24cc0=1493192293,1493193329,1493704573,1495440140; UM_distinctid=15dcf1066e710b-0cb9aef9997d5-414a0229-100200-15dcf1066e8117; Hm_lvt_110fc9b2e1cae4d110b7959ee4f27e3b=1502417545; acw_tc=AQAAAEL7NmH49wcA0tV8e1oUx/RrzQKx; _csrf=8c663fd4a25d7b1205a2fb4656826f8eaed11e39bbaf76656f1efd49f99bb255a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22IRHNwSNr4edAhZZeOb4tJOz1iIGJmKy6%22%3B%7D; acw_tc=59a6850c|ec005ab978f76a56742a7b4128e08499; pub_cookietime=0; _ourplusFirstTime=117-8-30-17-47-4; _ourplusReturnCount=1; _ourplusReturnTime=117-8-30-17-47-4; bdshare_firstime=1504086424285; Cto_lvt_=1504086425; Cto_lpvt_=1504086425; Cto_uid_=3793066; blog_new=3qm3nfmmmrvbioi32mnoup76f6; lastvisit=0%091504143738%09%2Fmod%2Fedu_void.php%3F; Hm_lvt_2283d46608159c3b39fc9f1178809c21=1503643431,1504085260,1504085272,1504144511; Hm_lpvt_2283d46608159c3b39fc9f1178809c21=1504144511";

		String cookis[] = cookies.split(";");

		StringBuffer buf = new StringBuffer("");

		for(String cookie : cookis){
			String name = cookie.split("=")[0];
			String val = cookie.split("=")[1];
			buf.append(".addCookie(").append("\""+name.trim()+"\"").append(",").append("\""+val.trim()+"\"").append(")\n");
		}

		System.out.println(buf.toString());

		     String url = "http://www.angularjs.cn/api/article/latest?p=1&s=20";
		String url_list = "http://angularjs\\.cn/api/article/latest.*";

		System.out.println(url.matches(url_list));

		System.out.println("2".matches("\\d{1,3}"));
	}
}
