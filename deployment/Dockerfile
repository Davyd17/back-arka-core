# -------- Build Stage --------
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21 AS build

WORKDIR /boot

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test --no-daemon


# -------- Production Stage --------
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21 AS production

WORKDIR /app

ENV TZ=America/Bogota

COPY --from=build /boot/main-app/boot/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]