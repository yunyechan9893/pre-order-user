# Step 1: Base image 선택
FROM openjdk:17-jdk-alpine

# Step 2: 작업 디렉토리 설정
WORKDIR /app

# Step 3: JAR 파일 복사
COPY build/libs/UserSever-0.0.1-SNAPSHOT.jar /app/myapp.jar

# Step 4: 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/build/myapp.jar"]