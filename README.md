# Basket Splitter

> A library designed to split products of Ocado clients into delivery groups

## Requirements

- **Java**: The application requires at least Java version 17 or higher.
- **Maven**

## Installation and usage

Clone this repository:

```bash
git clone https://github.com/IhoreQ/basket-splitter.git
```

Enter into the project's root directory:

```bash
cd basket-splitter
```

### Adding the library to your project

- Place the ```basket-splitter-1.0.jar``` file into your Maven project directory
- Add the following dependency to your ```pom.xml``` file, replacing ```${pathToJarDirectory}``` with the actual path to the JAR file within your project directory:

```xml
<dependencies>
        <dependency>
            <groupId>pl.ibobek</groupId>
            <artifactId>basket</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${pathToJarDirectory}/basket-splitter-1.0.jar</systemPath>
        </dependency>
    </dependencies>
```

- Import the ```BasketSplitter``` class into your Java project:

```java
import pl.ibobek.basket.service.BasketSplitter;
```

### Running tests
- Run the following command to execute tests:

```bash
mvn clean test
```

If your default `java` is not from JDK 17, set `JAVA_HOME`:

On MacOS or Linux:

```bash
export JAVA_HOME=<path_to_jdk_home> 
```

On Windows:

1. Launch _Control Panel > System > Advanced system settings > Advanced > Environment variables_
2. To _add_ a new environment variable choose _New_ and enter the variable _Name_ as `JAVA_HOME` and _Value_ as `path_to_jdk_home`.
3. To _change an existing environment variable_ choose _Edit_ and enter the new _Value_ for `JAVA_HOME` variable as `path_to_jdk_home`.
