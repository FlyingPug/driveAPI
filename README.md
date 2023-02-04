# Проект driveAPI 
Данный проект под собой подразумевает файловое хранилище.

#Установка и запуск
Для начала потребуется docker desktop
Затем в директории проекта нужно выполнить команду
  ```console
   docker-compose up -d
   ```
Для запуска проекта либо 
а) соберите его сами
б) запустите jar/war файлы (они должны лежать в папке executable)

В файле application yml вы можете настроить подключение к БД, время жизни токена (сессиии), название корневой директории, в которой будут хранится файлы пользователей

примеры работы API:

регистрация пользователя
POST http://localhost:8081/driveAPI/users/
JSON BODY
{
    "username" : "code_monkey",
    "password" : "banana",
    "confirmPassword" : "banana"
}
ans 
Http status 201
Http status 400
Http status 409
ауентификация пользователя
POST http://localhost:8081/driveAPI/tokens/
JSON BODY
{
      "username" : "dronsfd",
      "password" : "qwerty123"
} 
ANS
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkcm9uc2ZkIiwiaWF0IjoxNjc1NDYxNjAzLCJleHAiOjE2NzU0NjUyMDN9.jzQM_DHXgHJMGLMTdS6nr8iquRF9QM-w-6DhgzwBdJcHG9f5V7UkM-0MxSlbLJAw6AiIO9wR8chErILmyrRfUw"
}
#Данный токен нужно сохранить в хедер Authorization, чтобы иметь доступ к полному функционалу проекта
создание директории
POST http://localhost:8081/driveAPI/users/folders/
JSON BODY
{
    "id" : 3,//в данном случае под id подразумевается айди родительской директории. Корневая директория по умолчанию равна 1
    "name" : "monkey"
}
ANS 
{
    "id": 4 // id созданной директории
}
Http status 409
Смена имени директории
PATCH http://localhost:8081/driveAPI/users/folders/
JSON BODY
{
    "name" : "codeMonkeys",//требуемое имя директории
    "id" : "4"//id, по которому ищется директория
}
ANS 
Http status 200
Http status 409
Http status 404
Получение содержимого директории
// sortyType = 0 - получение по алфавитному порядку, sortType = 1 - получение в обратном порядке
GET http://localhost:8081/driveAPI/users/folders?directoryId=2&sortType=1
ANS
{
    "files": [
        {
            "id": 8,
            "name": "h62zJ5_Q8K4.jpg"
        },
        {
            "id": 7,
            "name": "capybara.jpg"
        },
        {
            "id": 9,
            "name": "HPpUcrojFT8.jpg"
        }
    ],
    "folders": []
}
Http status 404
Удаление директории
DELETE http://localhost:8081/driveAPI/users/folders/id
ANS
Http status 200
Http status 404
Загрузка файла в директорию
POST http://localhost:8081/driveAPI/users/files
form-data:
	directoryID:2 // директория, в которую будет помещен файл
	file:--insert file here--
ans
{
    "id": 9 // id файла
}
Http status 409
изменение свойств файла
PATCH http://localhost:8081/driveAPI/users/files
form-data
	fileId:7
	name:capybara.jpg
	isPublic:1
	fileData:--you Can insert file here--
ans
Http status 200
Http status 404
Http status 409
удаление файла
DELETE http://localhost:8081/driveAPI/users/files/3
ans
Http status 200
Http status 404
получение личного файла
GET http://localhost:8081/driveAPI/users/files/7
ANS
--ваш файл :)--
Http status 404
получение публичного файла
GET http://localhost:8081/driveAPI/public/files/7
ANS
--не только ваш файл--
Http status 404
получить список пользователей 
GET http://localhost:8081/driveAPI/users/
ANS
[
    "admin",
    "dronsfd",
    "code_monkey"
]
