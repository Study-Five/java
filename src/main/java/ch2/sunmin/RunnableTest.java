package ch2.sunmin;

public class RunnableTest {
	
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {			
			@Override
			public void run() {
				System.out.println("Hi Five!");
				
			}
		});
		
		Thread lamda_t = new Thread(() -> System.out.println("Hello Lamda!"));
		
		lamda_t.run();
	}

}
