package JDTest;


public class Test {

	public static void main(String[] args) {
		String s ="1";
		int i =0;
		while(s.equals("1")){
			System.out.println(i++);
			if("2".equals("2")){
				break;
			}
		}
		System.out.println("循环结束");
	}
}
