FROM java
EXPOSE 9990 8888
ADD /app/libs /app/libs
ADD 99/finatra-example_2.11-1.0.jar /app/finatra-example_2.11-1.0.jar
CMD ["java", "-cp", "\/app\/libs\/*:\/app\/finatra-example_2.11-1.0.jar", "br.eng.rafaelsouza.App"]