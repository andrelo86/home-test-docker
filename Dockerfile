FROM mcr.microsoft.com/playwright:v1.49.0-jammy

RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-21-jdk \
    maven \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH="${JAVA_HOME}/bin:${PATH}"

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -q

RUN mvn exec:java -e \
    -Dexec.mainClass=com.microsoft.playwright.CLI \
    -Dexec.args="install" \
    -Dexec.classpathScope=test

COPY src ./src

CMD ["mvn", "test", "--no-transfer-progress"]
