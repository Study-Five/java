package ch3.sunmin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProcessFile {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String test1 = processFile();
		
		// 람다 표현식으로 동작을 전달하면 더 좋겠다!
		String test2 = processFile((BufferedReader br) -> br.readLine() + br.readLine());
		
	}
	
	/**
	 * try-with-resources 구문 사용
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String processFile() throws FileNotFoundException, IOException {
		try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
			return br.readLine(); // <- 실제 필요한 작업을 하는 행
		}
	}
	
	@FunctionalInterface
	public interface BufferedReaderProcessor {
		String process(BufferedReader b) throws IOException;
	}
	
	/**
	 * BufferedReaderProcessor 함수형 인터페이스를 파라미터로 받는 메서드
	 * 이렇게 하면 함수형 인터페이스를 어떻게 구현하느냐에 따라, 아래 메서드를 다양하게 활용할 수 있다.
	 * 
	 * @param p
	 * @return
	 * @throws IOException
	 */
	public static String processFile(BufferedReaderProcessor p) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
			return p.process(br);
		}
	}
	
}
