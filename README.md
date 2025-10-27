git clone https://github.com/gwalka/Backend-Messanger.git
cd Backend-Messanger
./gradlew clean bootJar
docker-compose up --build
curl http://localhost:8080/auth/

nc -zv localhost 8080
Connection to localhost port 8080 [tcp/http-alt] succeeded!
