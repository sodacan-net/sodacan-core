package test.net.sodacan.color;

import java.awt.Color;

import junit.framework.TestCase;

public class TestColor extends TestCase {
	public static final String BROADCAST = "10.255.255.255";

	public static String host = BROADCAST;

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test1() {
		Color c1 = Color.RED;
		float[] hsb1 = Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), null);
		hsb1[2] = 0.5f;
		Color c2 = new Color(Color.HSBtoRGB(hsb1[0], hsb1[1], hsb1[2]));
		System.out.println(hsb1);
	}
//	public void test2()throws Exception {
//		System.out.println("delay 500 red 10 black");
//		System.out.println("delay 500 red 10 black then black red 10");
//		System.out.println("delay 500 shift 2 red 10 black");
//		System.out.println("delay 500 shift 2 red 10 black then purple 50 black 5");
//		System.out.println("delay 500 shift 2 bounce red 10 black");
//		
//        DatagramSocket ds = new DatagramSocket(); 
//        ds.setBroadcast(true);
//        InetAddress ip = InetAddress.getByName(host); 
//        Scanner sc = new Scanner(System.in); 
//        CommandScanner cs = new CommandScanner();
//        Sender ss = null;
//        // loop for commands which execute synchronously
//		// convert the String input into the byte array that we send to the device
//		while (sc.hasNextLine()) {
//			String commandLine = sc.nextLine().toLowerCase();
//			// Parse the command line to yield a list of DisplayGroups
//			List<DisplayGroup> displayGroups = cs.scan(commandLine);
//			// Kill the previous sender if needed
//			if (ss !=null) {
//				ss.interrupt();
//			}
//			// The sender will cause the groups to be displayed 
////			ss = new Sender(ds,ip,inp);
//			ss.start();
//		}
//		sc.close();
//	}

}
