- nullPointException 말모말모
- 당시 null참조 및 예외로 값이 없는 상황 가장 단순하게 구현할 수 있었다...

## 11.1 값이 없는 상황을 어떻게 처리할까?
```java
public class CarV1 {
  private Insurance insurance;
  public Insurance getInsurance() {
    return insurance;
  }
}
public class Insurance {
	private String name;
	public String getName() {
		return name;
	}
}
public String getCarInsuranceName(Person person){
    return person.getCar().getInsuracne().getName();
}
```
- 차 없으면 런타임에 nullPointException 발생

### 11.1.1 보수적인 자세로nullPointException 줄이기

```java
  public String getCarInsuranceName(Person person){
	if(person != null){
	Car car = person.getCar();
	  if(car != null){
	    Insurance insurance = car.getInsurance();
	    if(insurance != null){
	      return insurance.getName();
	    }
	   }
	}
	return "unknown";
}
``` 
- 깊은 의심 : 모든 변수가null인지 의심해 변수 접근시마다 중첩된 if추가로 들여쓰기 수준 증가
```java
  public String getCarInsuranceNameNullSafeV2(PersonV1 person) {
    if (person == null) {
      return "Unknown";
    }
    CarV1 car = person.getCar();
    if (car == null) {
      return "Unknown";
    }
    Insurance insurance = car.getInsurance();
    if (insurance == null) {
      return "Unknown";
    }
    return insurance.getName();
  }
```
- 출구가 너무 많아

### 11.1.2 null때문에 발생하는 문제 

- 에러의 근원이다. 
- 코드를 어지럽힌다. 
- 아무의미가 없다. 
- 자바철학에 위배된다 : 자바는 모든 포인터를 숨겼는데 그 예외가 null임
- 형식 시스템에 구멍을 만든다 : null은 무형식인데 정보 없으니까 모든 형식에 할당 가능. 의미 파악 불가 

### 11.2.3 다른 언어는 null대신 무얼 사용 하나 ? 

- 그루비 : 네비게이션 연산자 (?.) 도입해 해결 
```groovy
def carInsuranceName = person?.car?.insurance?.name //예외 걱정 없이 객체에 접근 null참조 있음 null이 반환됨
```
- 하스칼 : 선택형값을 저장할 수 있는  Maybe형식 제공 
- 스칼라 : T형식 갖거나 아무 값도 갖지 않을 수 있는 Option[T] 제공. 값 존재 여부를 명시적 확인해야함 
- 자바8 : java.util.Optional<T>제공 

## 11.2 Optional 클래스 소개 

- Optional : 선택형 값을 캡슐화하는 클래스 . 값이 있으면 Optional 클래스는 값을 감싸고 없으면 Optional.empty메서드로 Optional반환.
- Optional.empty : 싱글턴 인스턴스를 반환하는 정적 팩토리 메서드. 
```java
public class Person {
  private Optional<Car> car;
  private int age;
  public Optional<Car> getCar() {
    return car;
  }
  public int getAge() {
    return age;
  }
}
```
- 모델의 의마 semantic가 더 명확새졌음. car가 있을 수도 없을 수도 
- 값이 없는 상황이 우리 데이터에 문제인지 아님 알고리즘의 버그인지 명확히 구분 가능 
- 모든 null을 Optional로 대치x -> 더 이해하기 쉬운api설계하도록 돕는것 
- 값이 없을 수 있는 상황에 적절하게 대응하도록 강제

## 11.3.Optional 적용 패턴 

### 11.3.1 Optional 객체 만들기

####빈 Optional
```java
Optional<Car> optCar = Optional.empty();
```

#### null이 아닌 값으로 Optional 만들기
```java
Optional<Car> optCar = Optional.of(car);
```
- car 가 null이면 바로 Nullpoint(Optional 아니면 접근시 에러발생)

#### null값으로 Optional만들기
```java
Optional<Car> optCar = Optional.ofNullable(car);
```
- car가 null이면 빈 Optional 객체 반환. 

### 11.3.2 맵으로 Optional 의값을 추출하고 반환하기 
```java
 Optional<Insurance> optionalInsurance = Optional.ofNullable(insurance);
    Optional<String> name = optionalInsurance.map(Insurance::getName);
```
- stream 의 map과 비슷. Optional객체는 최대 요소의 개수가 한개 이하인 데이터 컬렉션. 
- 값 있으면 map으로 제공된 함수가 값을 바꾸고, 없으면 아무일x

### 11.3.3 flatMap으로 Optional 객체 연결
```java
   Optional<Person> optPerson = Optional.of(person);
    Optional<String> name = optPerson.map(Person::getCar) 
        .map(Car::getInsurance) 
        .map(Insurance::getName); 
    //컴파일안됨
```
- Optional<Optional<Car>> 가 map의 연산결과라 컴파일 안됨. 
- flatMap : 인수로 받음 함수를 적용해 생성된 각가의 스트림에서 콘텐츠만 남김.

#### Optional 로 자동차의 보험회사 이름 찾기
```java
 public String getCarInsuranceName(Optional<Person> person) {
    return person.flatMap(Person::getCar)
        .flatMap(Car::getInsurance)
        .map(Insurance::getName)
        .orElse("Unknown"); //비어있으면 기본값 사용 
  }
```
- null 확인 위해 조건 분기 추가해서 코드 복잡하지 않게 쉽게 이해가능한 코드. 
- 도메인 모델과 관련된 암묵적 지식에 의존치 않고 명시적으로 형식 시스템 정의 가능. 

#### Optional 을 이용한 Person/Car/Insurance참조 체인

### 11.3.4 Optional스트림 조작

- 자바 9 부터 Optionaldp stream() 메서드 추가함. 
```java
  public Set<String> getCarInsuranceNames(List<Person> persons) {
    return persons.stream()
        .map(Person::getCar)
        .map(optCar -> optCar.flatMap(Car::getInsurance))
        .map(optInsurance -> optInsurance.map(Insurance::getName))
        .flatMap(Optional::stream)
        .collect(toSet());
  }
  
  
```
### 11.3.5 디폴트 액션과 Optional 언랩

- get() : 가장 간단/ 가장 안전x 메서드. 값 없으면 NoSuchElementException 발생. 값이 반드시 있다고 가정할때만 사용해야. .
- orElse(T other) : 값 없을때 기본값 제공.
- orElseGet : orElse 의 게으른 버전. 디폴트 메서드 만드는데 시간 걸리너가 값비었을때만 기본값 생성.
- orElseThrow : 비었을때 예외발생. 예외 종류 선택 가능
-  ifPresent : 값 존재시 인수로 받은 동작 실행. 없으면 아무일도x
- ifPresentOrElse : 값 비었을떄 실행할 수 있느 ㄴRunnable을 인수로 받음 

### 11.3.6 두 Optional합치기 

- 두 Optional 인수로 받아 Optional<Insurance> 반환 메서드 
```java
 public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
    if (person.isPresent() && car.isPresent()) {
      return Optional.of(findCheapestInsurance(person.get(), car.get()));
    } else {
      return Optional.empty();
    }
  }

public Optional<Insurance> nullSafeFindCheapestInsuranceQuiz(Optional<Person> person, Optional<Car> car) {
	return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
	}
```
- person과 car시그니처 만으로 둘다 아무값 반환 않을 수 있다는 정보 명시적으로 제공. 

### 11.3.7 필터로 특정값 거르기 

- filter메서드는 프레디케이트 인수로 받아. 값 있고 프레디케이트와 일치하면 그 갑 ㅅ반환 아니면 빈 Optional 반환. 
```java
  public String getCarInsuranceName(Optional<Person> person, int minAge) {
    return person.filter(p -> p.getAge() >= minAge)
     .flatMap(Person::getCar)
     .flatMap(Car::getInsurance)
     .map(Insurance::getName)
     .orElse("Unknown");
  }
```

## 11.4 Optioanl 을 사용한 실용 예제 

### 11.4.1 잠재적으로 null이 될 수 있는 대상을 Optional로 감싸기
```java
Object value = map.get("key");
Optional<Object> optVal = Optioanl.ofNullable(map.get("key"));
```

### 11.4.2 예외와 Optional클래스 
```java
  public static Optional<Integer> StringToInt(String s){
    try{
      return Optional.of(Integer.parseInt(s));
    }catch (NumberFormatException e){
      return Optional.empty();
    }
  }
```
- OptionalUtility 클래스 만들어서 사용 권장

### 11.4.3 기본형 Optional을 사용하지 말아야 하는 이유

- Optional도 기본형 특화된 OptionalInt,OptionalLong,OptionalDouble 제공
- Optional 의 최대 요소수는 한개 이므로 기본형 특화로 성능 개선 안됨. 
- 기본형특화 Optional 은 map, flatMap, filter지원 안함
- 다른 일반 Optional 과 혼용 불가. 

#### 11.4.4 응용

- pass~