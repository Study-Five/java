# java 파일 공간~

모던인 자바 스터디 내용 기록

## 프로젝트 구조
``
.
├── main
│     ├── generated
│     └── java
│         ├── ch1
│         │     └── sunmin
│         │         └── StreamTest.java
│         ├── ch2
│         │     └── sunmin
│         │         ├── AppleTest.java
│         │         ├── CallableTest.java
│         │         ├── ComparatorTest.java
│         │         └── RunnableTest.java
│         ├── ch3
│         │     └── sunmin
│         │         ├── AppleTest.java
│         │         ├── AppleTotalTest.java
│         │         ├── ConstructorReference.java
│         │         ├── FunctionInterfaceTest.java
│         │         ├── LambdaComposite.java
│         │         ├── MethodReference.java
│         │         ├── ProcessFile.java
│         │         └── TargetTyping.java
│         ├── ch4
│         │     └── sunmin
│         │         ├── Dish.java
│         │         ├── LazinessTest.java
│         │         └── StreamTest.java
│         └── ch5
│             └── sunmin
│                 ├── Trader.java
│                 ├── TraderExcercise.java
│                 └── Transaction.java
└── test
└── ch5
└── sunmin
└── TraderExcerciseTest.java
``

## gradle 을 사용한 junit 및 lombok 의존성 추가

``` gradle
plugins {
id 'java'
}

repositories {
mavenCentral()
}

test {
useJUnitPlatform()
}

dependencies {
compileOnly 'org.projectlombok:lombok:1.18.12'
annotationProcessor 'org.projectlombok:lombok:1.18.12'
testImplementation 'org.junit.jupiter:junit-jupiter-api:5.6.2'
testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine'
}
```