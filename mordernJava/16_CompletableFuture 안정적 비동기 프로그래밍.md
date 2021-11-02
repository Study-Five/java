## 16.1 Future 의 단순 활용 

- 자바 5 Future 인터페이스 : 미래의 어느 시점에 결과를 얻는 모델. 비동기 계산 . 계산 종료 후 결과 접근 가능한 참조를 제공. 
- 스레드 결과 기다리는 동안 다른 작업 수행 가능. 
- 저수준의 스레드에 비해 직관적 이해 줍다는 장점. 
- 다른 작업 처리하다 Future의 get으로 결과 가져옴. 이때 준비 전이면 스레드 블록시킴. -> 작업 안끝나면 get() 오버로드해 타임아웃 시간 설정

### 16.1.1 Future 제한

기존 Future에 다음 선언형 기능 있음 유용할 것
- 두 비동기 계산결과를 하나도 합침. 두 계산결과는 서로 독립적일 수 있고 2결과가 1결과에 의존 하는 상황일 수 있음
- Future 집합이 실행하는 모든 태스크의 완료 기다림 
- Future 집합에서 가장 빠릴 완료되는 태스크 기다렸다 결과 얻는다
- 프로그램적으로 Future 완료시킴 (비동기 동작에 수동 겨로가 제공)
- Future 완료 동작에 반응 
  
### 16.1.2 CompletableFuture 로 비동기 애플리케이션 만들기

- 여러 온라인상점중 가장 저렴한 가격 상점 찾는 애플리케이션 예제 
- 고객에게 비동기 API제공 방법
- 동기 API사용시 코드 비블록으로 만드는 법. 두개 비동기동작을 파이프라인으로 만드는 방법/ 두 동작결과를 하나의 비동기 계산으로 합치는 방법 
- 비동기 동작의 완료에 대응 하는 방법 

## 16.2 비동기 API구현 

- api 호출 지현을 흉내내게 코드 
```java
  public static void delay() {
	int delay = 1000;
	//int delay = 500 + RANDOM.nextInt(2000);
	try {
	Thread.sleep(delay);
	} catch (InterruptedException e) {
	throw new RuntimeException(e);
	}
}
	
  private double calculatePrice(String product) {
    delay();
    if (true) {
      throw new RuntimeException("product not available");
    }
    return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
  }
```

### 16.2.1 동기 메서드를 비동기 메서드로 변환 

- 스레드가 블록되지 않고 다른 작업 실행가능한 비동기 계산 . 
- 결과를 포함할 Future 인스턴스 바로 반환
```java
  public Future<Double> getPriceAsync(String product) {
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();
    new Thread(() -> {
      double price = calculatePrice(product);
      futurePrice.complete(price);
    }).start();
    return futurePrice;
  }
```

- 다른 작업 처리하고 나중에 Future의 get 호출. 결과 있으면 읽고 없으면 계산될때까지 블록. 

### 16.2.2 에러처리방법

- 예외 발생시 해당 스레드만 영향을 미침. 계산은 계속 진행 but일의 순서 꼬임. 
- get 메서드 반환시까지 영원히 기다려야함. 
- completeExceptionally 메서드로 예외를 클라이언트로 전달 
```java
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();
    new Thread(() -> {
      try {
        double price = calculatePrice(product);
        futurePrice.complete(price);
      } catch (Exception ex) {
        futurePrice.completeExceptionally(ex);
      }
    }).start();
    return futurePrice;
```

#### 팩토리 메서드 supplyAsync로 CompletableFuture 만들기

```java
  public Future<Double> getPrice(String product) {
    return CompletableFuture.supplyAsync(() -> calculatePrice(product));
  }
```
- supplyAsync 는 Supplier 인수로 받아 CompletableFuture 리턴
- Supplier 실행해 비동기적 결과 생성. 두번째 인수받는 오버로드 버전 메서드로 Executor지정 가능 

## 16.3 비블록 코드 만들기 
```java
  public List<String> findPrices(String product) {
    return shops.stream()
    .map(shop -> String.format("",shop.getName(),shop.getPrice(product)))
    .collect(toList());
    //4032ms
  }
```
### 16.3.1 병렬스트림으로 요청 병렬화 하기 

- parallelStream() 으로 바꾸니 1180ms

### 16.3.2 CompletableFuture로 비동기 호출 구현하기
```java
  public  List<String> findPrice2(String product){
	List<CompletableFuture<String>> priceFuture =
	shops.stream()
	.map(shop -> CompletableFuture.supplyAsync(()-> shop.getName() +shop.getPrice(product)))
	.collect(Collectors.toList());

	return priceFuture.stream()
	.map(CompletableFuture::join)
	.collect(Collectors.toList());
	}
```
- 스트림연산은 게으른 특성이 있어 하나의 파이프라인으로 연산 처리하면 동기적,순차적으로 동작 이뤄지는 결과임
- 2005ms -> 만족x

### 16.3.3 더 확장성이 좋은 해결 방법 

- 상점이 4개에서 하나 추가된다면? 네 스레드중 누군가가 완료해야 다섯번쨰 질의 가능. 
- Rimtime.getRuntime().availableProcessors()가 반환하는 스레드 수 사용함
- CompletableFuture는 다양한 Executor지정 가능 -> 스레드 풀 크기 조절 등 최적화된 설정 가능.

#### 16.3.4 커스텀 Executor 사용하기 

```java
  private final Executor executor = Executors.newFixedThreadPool(shops.size(), (Runnable r) -> {
    Thread t = new Thread(r);
    t.setDaemon(true);
    return t;
  });
```
- 스레드풀 너무 크면 cpu와 메모리자원 경쟁하느라 시간 낭비 가능
- 스레드 너무 작으면 cpu의 일부코어는 활용 안될 수 있음 
- 공식에 따라 적정 스레트풀을 갖는 Executor 세팅 가능
- 데몬스레드 : 한없이 기다리며 미종료시 문제 -> 자바프로그램이 종료될때 강제 실행 종료. 일반스레드와 같은 성능.
```java
  public List<String> findPricesFuture(String product) {
    List<CompletableFuture<String>> priceFutures =
        shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is "
                + shop.getPrice(product), executor))
            .collect(Collectors.toList());

    List<String> prices = priceFutures.stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
    return prices;
  }
```
- 1022ms

#### 스트림 병렬화와 CompletableFuture 병렬화 

- 컬렉션 계산을 병렬화 하는 두 방법
  1. 병렬스트림으로 변환해서 처리 -> I/O 없는 계산 중심 동작. 
  2. 컬렉션 반복하며 CompletableFuture 내부의 연산을 생성.  -> 스트림은  게으르므로 I/O존재 작업에 유연, 스레드풀 조정 가능
    
## 16.4 비동기 작업 파이프라인 만들기 

- Enum으로 정의된 Discount, getPrice 의 리턴형식도 BestPrice:123.26:GOLD 식으로 코드 수정함 

### 16.4.1 할인 서비스 구현

- 상점에서 얻은 shopName, price, discountCode 를 캡슐화하는 Quote 클래스 생성
- Quote 를 받아 할인된 가격을 반환하는 Discount 서비스 생성 

### 16.4.2 할인 서비스 사용

- 일단 가장 쉬운방법 . 최적화X
```java
  public List<String> findPricesSequential(String product) {
    return shops.stream()
        .map(shop -> shop.getPrice(product))
        .map(Quote::parse)
        .map(Discount::applyDiscount)
        .collect(Collectors.toList());
  }
```
- 각 상점마다 시간 소요. 10028ms

### 16.4.3 동기작업과 비동기 작업 조합하기 
```java
  public List<String> findPrices(String product){
        List<CompletableFuture<String>> priceFutures = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
        .map(future -> future.thenApply(Quote::parse))
        .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
        .collect(Collectors.toList());

      return priceFutures.stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
        }
```
#### 가격 정보 얻기

- supplyAsync : 비동기로 상점에서 정보 조회. Stream<CompletableFuture<String>> 리턴됨.

#### Quote파싱하기 

- I/O없는 작업. 
- thenApply :  CompletableFuture가 끝날때까지 블록X. CompletableFutur의 동작 완전히 완료 후 thenAppl메서드로 
전달된 람다 적용.
  
### CompletableFuture를 조합해서 할인된 가격 계산하기

- 두 CompletableFuture 로 이루어진 연쇄적으로 수행되는 두개의 비동기 동작 생성 가능.
- thenCompose : 두 비동기 연산을 파이프라인으로 생성 가능. 첫 연산의 결과를 두번째 연산으로 전달. 
  - async 로 끝남나지 않음 : 이전 작업을 수행한 스레드와 같은 스레드에서 작업 실행. 적은 오버헤드.효욜성 굿 
  - async로 끝남 : 다음작업이 다른 스레드에서 실행되도록 스레드 풀로 작업을 제출
- Future가 여러 상점에서 Quote 얻는 동안 메인 스레드는 UI이벤트에 반응 가능. 
- 마지막으로 CompletableFuture 가 완료되길 기다렸다가 join 으로 값 추출 


### 16.4.4 독립 CompletableFuture 와 비독립 CompletableFuture 합치기 

- 첫 CompletableFuture 동작완료과 관계없이 두번째 CompletableFuture 실행 가능해야. 
- thenCombine : 두번째 인수로 BiFunction받음( 결과를 어떻게 합칠지 정의 )
- thenCombineAsync : 조합동작이 스레드풀로 제출되면서 별도의 태스크에서 비동기적으로 수행
```java
    Stream<CompletableFuture<String>> priceFuturesStream = shops.stream()
        .map(shop -> CompletableFuture
            .supplyAsync(() -> shop.getPrice(product))
            .thenCombine(
                CompletableFuture.supplyAsync(() -> ExchangeService.getRate(Money.EUR, Money.USD)),
                (price, rate) -> price * rate));
```

### 16.4.5 Future 의 리플렉션과 CompletableFuture의 리플렉션

- CompletableFuture는 람다 표현식 사용. 덕분에 동기태스크, 비동기 태스크 활용해서 복잡한 연산 수행방법을 효과적으로 쉽게 정의할 수 있는 선언형api생성 가능
- 자바7 의 Future 보다 굿

### 16.4.6 타임아웃 효과적으로 사용하기 

- orTimeout : 지정된 시간이 지난 후 CompletableFuture를 TimeoutExceoption으로 완료하면서 또 다른 CompletableFuture 를 반화할 수 있도록 내부적으로 ScheduledThreadExecutor 활용. 
- completeOnTimeout : 지정한 시간 내 완료 안되면 디폴트값 사용 
```java
      CompletableFuture<Double> futurePriceInUSD =
          CompletableFuture.supplyAsync(() -> shop.getPrice(product))
          .thenCombine(
              CompletableFuture.supplyAsync(
                  () ->  ExchangeService.getRate(Money.EUR, Money.USD))
              .completeOnTimeout(ExchangeService.DEFAULT_RATE, 1, TimeUnit.SECONDS),
              (price, rate) -> price * rate
          ).orTimeout(3, TimeUnit.SECONDS);
```

## 16.5 CompletableFuture의 종료에 대응하는 방법

- 기존delay메서드를 05~2.5 초 임의지연으로 변경 
- 모든 상점 정보 기다리지 않고 각 상점에서 제공시마다 즉시 반영 요구사항

### 16.5.1 최저가격 검색 애플리케이션 리팩터링

- themAccept : 연산결과를 소비하는 Consumer를 인수로 받음.
  - CompletableFuture 가 생성한 결과를 어떻게 소비할지 미리 지정했으니 CompletableFuture<void>반환 
- async 버전은 CompletableFuture가 완료된 스레드가 아니라 새 스레드로 Consumer실행. 

```java
 findPriceStream("myPhone").map(f-> f.thenAccept(System.out::println));
```
```java
    CompletableFuture[] futures = findPricesStream(product)
        .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
        .toArray(size -> new CompletableFuture[size]);
    CompletableFuture.allOf(futures).join();
```

- allOf : CompletableFuture 배열을 입력으로 받아 CompletableFuture<Void> 반환. 전달받은게 모두 완료돼야 CompletableFuture<Void><완료 
  - join호출시 원래 스트림의 모든 CompletableFuture의 실행완료 기다릴 수 있음.
  - 사용자에 모든결과 완료 같은 메시지 노출 가능 

- anyOf : CompletableFuture 배열을 입력으로 받아 CompletableFuture<Object> 반환.
  - 처음으로 완료한 CompletableFuture 배열을 입력으로 받아 CompletableFuture<Void> 반환.의 값으로 동작을 완료 
  

## 15.5.2 응용
```java
  public void printPricesStream(String product) {
    long start = System.nanoTime();
    CompletableFuture[] futures = findPricesStream(product)
        .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
        .toArray(size -> new CompletableFuture[size]);
    CompletableFuture.allOf(futures).join();
    System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
  }
```
- 두배 빨라짐!