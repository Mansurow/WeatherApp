# Android WeatherApp with API [OpenWeatherMap](https://openweathermap.org/)

Этот проект посвящен созданию приложения погоды для Android с использованием Java/Android Studio. 
Чтобы получить информацию о погоде, я использовал OpenWeatherMap API. Такая информация, как температура, давление, влажность, состояние погоды, 
время восхода и захода солнца и т. д. передается из API.

Создайте приложение погоды с помощью Java в Android - [Видео](https://www.youtube.com/watch?v=zzV2aML_zNg)

## Версия 0.1

### Взаисодействие с прораммой

Взаимодействие с программой достаточно простое, пользователю необходимо ввести свой город и нажать на кнопку "Find", по результату поиска выводится акктуальная информация с достаточно реализованным дизайном для просмотра:

- 1 - Ввод________________________Вывод

<img height="500px" src='https://user-images.githubusercontent.com/62243773/152522746-85f410dd-3db1-4c5a-8e75-c4ddbcf74bfd.png' > <img height="500px" src='https://user-images.githubusercontent.com/62243773/152523618-5d17ff53-b45a-4296-8226-f0c7bfa181b5.png' > <img height="500px" src='https://user-images.githubusercontent.com/62243773/152524036-8261ddd2-9e0c-4a36-98b2-a26ee629a099.png' > <img height="500px" src='https://user-images.githubusercontent.com/62243773/152524355-f41679b2-242d-4667-85d8-dbc8b4f3c5f7.png' >

### Работа с APi
Для получения данных мы будем использовать OpenWeatherAPI , и для этого нам понадобится ключ API. Прежде чем продолжить, получите ключ API, зарегистрировавшись. Вы можете либо следовать прикрепленному видео выше, либо шагам ниже.

- 1 - Создайте новую учетную запись [отсюда](https://home.openweathermap.org/users/sign_up) .
- 2 - После входа перейдите [сюда](https://openweathermap.org/price) , чтобы получить ключ API.

Описание запросов будут [здесь](https://openweathermap.org/current)
У меня в Java коде вышло следующим языком:
```java
String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key + "&units=" + mode +"&lang=" + language;
```
где
```java
city - название города
key - Api ключ
mode - вид вывода характеристик
language - язык возвращаемой информации
```
