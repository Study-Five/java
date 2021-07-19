package ch2.sunmin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableTest {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService =  Executors.newCachedThreadPool();
		
		// 현재 수행하고 있는 스레드의 이름을 반환한다.
		Future<String> threadName = executorService.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				return Thread.currentThread().getName();
			}
		});
		
		System.out.println(threadName.get()); // pool-1-thread-1
		
		Future<String> threadName_lamda = executorService.submit(() -> Thread.currentThread().getName());
		
		System.out.println(threadName_lamda.get());

	}

}
