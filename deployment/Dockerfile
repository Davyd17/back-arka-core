# -------- Build Stage --------
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21 AS build

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew clean build -x test --no-daemon


# -------- Production Stage --------
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21 AS production

# Install curl using the Amazon Linux package manager
RUN yum update -y && \
    yum install -y curl && \
    yum clean all

WORKDIR /app

ENV TZ=America/Bogota

COPY --from=build /app/main-app/boot/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]