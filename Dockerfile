# 1. Use the official Microsoft Playwright Java image as the base
FROM mcr.microsoft.com/playwright/java:v1.60.0

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy the pom.xml file first to take advantage of Docker's layer caching
COPY pom.xml .

# 4. Download Maven dependencies and force download of the Playwright CLI tool components
RUN mvn dependency:go-offline -B

# 5. CRITICAL FIX: Explicitly download and bake browser binaries into the image layers
RUN mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"

# 6. Copy the rest of your application source code
COPY src ./src

# 7. Default command to execute your parallel test suites
CMD ["mvn", "test"]