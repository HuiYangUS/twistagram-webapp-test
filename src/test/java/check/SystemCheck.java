package check;

public class SystemCheck {

	/**
	 * <intel> == amd64, x86_64
	 * <mchip> == aarch64
	 */
	public static void main(String[] args) {
		System.out.println(System.getProperty("os.arch"));
	}

}
