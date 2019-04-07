# chuck-facts
## A simple android application that list Chuck Norris funny facts


Below you can find the versions of some of the used tools 

| Tool | Versão |
|--|--|
|Gradle |  4.10.1 |
|Gradle Build Plugin  |  3.3.2|
|Android API  |  28|
|Support Library  |  28.0.0|
|Android Studio | 3.3.2|

## Building and Running

### Running from IDE

- Use Android Studio 3.3.2 or later, import the project and install all dependencies needed.

### Build from command line

#### Make sure you have Java installed, go to the root directory and run the following tasks:
```
./gradlew assembleDebug
```
#### The apk will be generated in ./app/build/outputs/apk/release


To build and install on the connected device:
```
./gradlew installDebug
```
#### Running unit tests

```
./gradlew test
```

#### Running acceptance tests

Connect a device (usb or emulator) and run

```
./gradlew connectedCheck
```

## Used Technologies

#### App
- Kotlin
- Android Architecture Components 
- Retrofit
- Koin for DI
- Room for persistence
- RxJava2 for reactive programming
- Retrofit for http
- LiveData for lifecycle aware data holder
- Bitrise for CI
- Ktlint for code style checking

#### Tests
- JUnit 4
- Robolectric
- Espresso
- Mockito to mock data dependencies
