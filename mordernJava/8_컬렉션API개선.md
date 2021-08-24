## 8.1 컬렉션 팩토리 

- 적은 요소를 포함하는 리스트 생성
```java
List<String> referenceCodes = Arrays.asList("a12", "C14", "b13");
```
- 고정크리 리스트 생성 -> 요소생긴 가능, 새 요소 추가,삭제 시 OperationException발생

#### UnsupportedOperationException 발생

- 고정된 크기의 변환 가능한 배열로 내부구현 됐기에 예외 발생.
```java
Set<String> friends3 = new HashSet<>(Arrays.asList("Raphael", "Olivia", "Thibaut"));

Set<String> friends4 = Stream.of("Raphael", "Olivia", "Thibaut").collect(Collectors.toSet());
```
- 내부적으로 불필요한 객체 할당 필요 -> 매끄러운 방법x . 변환가능 집합.


### 8.1.1 리스트 팩토리 
```java
List<String> friends5 = List.of("Raphael", "Olivia", "Thibaut");
friends5.add("Chih-Chun"); // UnsupportedOperationException
```
- 변경 불가 리스트임.
- 컬렉션이 의도치 않게 변하는걸 막음. 

#### 오버로딩 vs 가변인수

- 요소 10개 미만 리스트 : List.of 의 오버로딩 버전
- 그 이상 : 가변인수 이용 메서드. 
- 가변인수 버전은 추가 배열 할당해 리스트로 감쌈-> 배열 할당,초기화,가비지컬렌션 비용 지불해야. 

- 데이터 처리 형식을 설정하거나 데이터 변환할 필요 없으면 간편한 팩토리 메서드 사용 ㄱ

### 8.1.2 집합 팩토리 
```java
Set<String> friends = Set.of("Raphael", "Olivia", "Thibaut");
Set<String> friends2 = Set.of("Raphael", "Olivia", "Olivia"); //IllegalArgumentException
```
- 집합은 고유 요소만 포함 원칙때매 중복 요소 존재시 예외발생

### 8.1.3 맵 팩토리 
```java
Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26); //10개이하 요소에서 유용

Map<String, Integer> ageOfFriends2 = Map.ofEntries(
entry("Raphael", 30),
entry("Olivia", 25),
entry("Thibaut", 26));
```
- Map.ofEntries : 키와 값을 감쌀 추가 객체 할당 필요.

## 8.2 리스트와 집합 처리 

- removeIf : 프레디케이트 만족 요소 제거. List,Set 구현/ 상속 받은 모든 클래스서 이용가능
- replaceAll : 리스트에서 이용할수 있음. UnaryOperator함수 이용해 요소 바꿈
- sort : List 에서 제공. 정렬

- 새 결과 만드는 스트림과 달리 기존 컬렉션을 바꿈. 

### 8.2.1 removeIf메서드 

- for-each 로 iterator 돌며 삭제하면 ConcurrentModificationException 발생
- Iterator 객체 : next(), hasNext() 이용해 소스를 질의
- Collection 객체 자체, remove() 호출해 요소 삭제 
- 반복자의 상태는 컬렉션 상태와 동기화 되지 않아 문제 
```java
transaction.removeIf(transaction ->
    Character.isDigit(transaction.getReferenceCode().charAt(0)));
```

### 8.2.2 replaceAll

- 리스트의 각 요소를 새로운 요소로 바꿈 
```java
List<String> referenceCodes = Arrays.asList("a12", "C14", "b13");
referenceCodes.stream()
.map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
.collect(Collectors.toList())
.forEach(System.out::println); // 새 컬렉션 만듬

for (ListIterator<String> iterator = referenceCodes.listIterator(); iterator.hasNext(); ) {
String code = iterator.next();
iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
} // 기존컬렉션 바꿈. but 코드복잡
	
referenceCodes.replaceAll(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1));
```

## 8.3 맵처리 

- 디폴트 메서드 : 기본적인 구현을 인터페이스에 제공

### 8.3.1 forEach 

- BiConsumer 인수로 받는 forEach 제공 
```java
    Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
    for (Map.Entry<String, Integer> entry: ageOfFriends.entrySet()) {
      String friend = entry.getKey();
      Integer age = entry.getValue();
      System.out.println(friend + " is " + age + " years old");
    }

    System.out.println("--> Iterating a map with forEach()");
    ageOfFriends.forEach((friend, age) -> System.out.println(friend + " is " + age + " years old"));
```

### 8.3.2 정렬

- Entry.comparingByValue
- Entry.comparingByKey

```java
 Map<String, String> favouriteMovies = Map.ofEntries(
        entry("Raphael", "Star Wars"),
        entry("Cristina", "Matrix"),
        entry("Olivia", "James Bond"));

favouriteMovies.entrySet().stream()
    .sorted(Entry.comparingByKey())
    .forEachOrdered(System.out::println);
```
- 자바 8 HashMap 내부구조가 키를 해쉬코드로 접근 가능한 버켓에 저장 -> 버킷이 너무 커지면 트리 이용해 성능 개선. key가 Comparable(String, Numver)형태여야 가능

### 8.3.3 getOrDefault

- key 없으면 NullPointException 방지 . 기본값 반환
```java
    System.out.println(favouriteMovies.getOrDefault("Olivia", "Matrix"));
    System.out.println(favouriteMovies.getOrDefault("Thibaut", "Matrix"));
```
- 키 있더라도 값이 널이면 널 반환 가능.

### 8.3.4 계산패턴

- computeIfAbsent : 해당 키에 값 없으면(널이면) 키를 이용해 새 값 계산해 맵에 추가 
- computeIfPresent : 키가 존재시 새값 계산해 맵에 추가 
- compute : 제공된 키로 새값을 계산하고 맵에 저장
```java
    Map<String, List<String>> friendsToMovies = new HashMap<>();

    String friend = "Raphael";
    List<String> movies = friendsToMovies.get(friend);
    if (movies == null) {
       movies = new ArrayList<>();
       friendsToMovies.put(friend, movies);
    }
    movies.add("Star Wars");
    
    friendsToMovies.clear();
    
    friendsToMovies.computeIfAbsent("Raphael", name -> new ArrayList<>())
        .add("Star Wars");
```

### 8.3.5 삭제 패턴

- 자바8  키가 특정 값과 연과됐을때만 항목 제거하는 오버로드 버전 메서드 제공 
```java
 // 바꿀 수 있는 맵 필요!
    Map<String, String> favouriteMovies = new HashMap<>();
    favouriteMovies.put("Raphael", "Jack Reacher 2");
    favouriteMovies.put("Cristina", "Matrix");
    favouriteMovies.put("Olivia", "James Bond");
    String key = "Raphael";
    String value = "Jack Reacher 2";

    private static <K, V> boolean remove(Map<K, V> favouriteMovies, K key, V value) {
	if (favouriteMovies.containsKey(key) && Objects.equals(favouriteMovies.get(key), value)) {
	favouriteMovies.remove(key);
	return true;
	}
	return false;
	}
	
    boolean result = remove(favouriteMovies, key, value);
    
    favouriteMovies.remove(key, value);
```

### 8.3.6 교체 패턴

- replaceAll : BiFunction 적용한 결과로 각 항목의 값을 교체. 
- replace : 키가 존재하면 맵의 값을 바꿈. 키가 특정 값으로 매핑되었을 때만 값을 교체하는 오버로드 버전도 존재.
```java
    Map<String, String> favouriteMovies = new HashMap<>();
    favouriteMovies.put("Raphael", "Star Wars");
    favouriteMovies.put("Olivia", "james bond");

    favouriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
```

### 8.3.7 합침

- putAll : 두 맵을 합침. 

- merge : 중복된 키 합치는 방법 결정하는 BiFunction 받아 수행
```java
    Map<String, String> family = Map.ofEntries(
        entry("Teo", "Star Wars"),
        entry("Cristina", "James Bond"));
    Map<String, String> friends = Map.ofEntries(entry("Raphael", "Star Wars"));
    
    Map<String, String> everyone = new HashMap<>(family);
    everyone.putAll(friends);

    Map<String, String> friends2 = Map.ofEntries(
        entry("Raphael", "Star Wars"),
        entry("Cristina", "Matrix"));
    
    Map<String, String> everyone2 = new HashMap<>(family);
    friends2.forEach((k, v) -> everyone2.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
```

## 8.4 개선된 ConcurrentHashMap

- 내부 자료구조의 특정 부분만 잠궈 동시추가, 갱신 작업을 허용. 동기화된 HashTable보다 읽기쓰기 연산 성능 월등 (hasMap은 비동기)

### 8.4.1 리듀스와 검색 

- forEach : 각 쌍에 주어진 액션을 수행
- reduce : 모든 쌍을 제공된 리슈드 함수를 이용해 결과로 합침
- search : 널이 아닌 값을 반환할때까지 각 쌍에 함수를 적용 


- 키,값으로 연산 - forEach, reduce, search
- 키로 연산 - forEachKey, reduceKeys, searchKeys
- 값으로 연산 - forEachValue, reduceValues,searchValues
- Map.Entry객체로 연산 - forEachEntry, reduceEntries, searchEntries


- ConcurrentHashMap의 상태를 잠그지 않고 연산을 수행함. 함수는 게산 진행동안 바뀔 수 있는 객체,값,순서에 의존하면x
- 병렬성기준값 지정. 
  - 1: 공통스레드풀을 이용해 병렬성 극대화
  - Long.MAX_VALUE  : 한개의 스레드로 연산을 실행
```java
  ConcurrentHashMap<String, Long> map = new ConcurrentHashMap;
  long parallelismThreshold = 1;
  Optional<Long> maxValue = Optional.ofNullable(map.reduceValues(parallelismThreshold,Long::max));
```

### 8.4.2 계수 

-  mappingCount : 맵의 매핑 개수를 반환. size() 대신 사용해야 매핑 개수가 int범위 넘어선 상황 대처가능

### 8.4.3 집합 뷰

- keySet : ConcurrentHashMap 을 집합뷰로 반환. 
- 맵 바꾸면 집합 바뀌고 반대도 영향 받음. 
- newKeySet : ConcurrentHashMap으로 유지되는 집합뷰 생성 가능 