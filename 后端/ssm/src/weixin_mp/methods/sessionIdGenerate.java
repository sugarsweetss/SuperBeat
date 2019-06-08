package weixin_mp.methods;

import java.util.UUID;;

public class sessionIdGenerate {
	public static String getSessionId() {
		return UUID.randomUUID().toString();
	}
}
