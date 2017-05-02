package utility;

public class AuthKeyGenerator {
	
	public int getAuthKey() {
		//range=> 1000-10000
		return (int)(Math.random()*9000)+1000;
	}

}
