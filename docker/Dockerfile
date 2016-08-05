FROM java
EXPOSE 9990
ADD /app/libs /app/libs
ADD 99/finatras-demo_2.11-1.0.jar /app/finatras-demo_2.11-1.0.jar
CMD ["java", "-cp", "\/app\/libs\/*:\/app\/finatras-demo_2.11-1.0.jar", "coole.co.finatra.App"]