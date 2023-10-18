# twitchAutoPotato

![Java](https://img.shields.io/badge/Java-17-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)


An automation service to help you farm potatoes from Potatbotat on Twitch.tv

## General Info

twitchAutoPotato is a service designed to assist people in farming potatoes from a minigame on Twitch.tv. It's a straightforward service that uses Puppeteer to automate the process of issuing commands to farm potatoes.

## Running the Application

To run the application, you'll need a machine with Java 17 and a PostgreSQL database (locally with Docker or remote). Use environment variables to configure the required credentials.

## Access Information

To run this application, you may need Twitch authentications, which can be obtained by creating a new bot on https://dev.twitch.tv/

The information you'll need includes:
- Twitch account Username
- Client ID
- Client Secret
- OAuth token
- Channel name to run the bot

## Endpoint Information
To make sure the user will not send the information in the twitch chat, we created an endpoint you can call to add yourself in the database.

endpoint:
```
curl --location --request POST 'http://localhost:8080/user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "channels": "",
    "clientId": "",
    "clientSecret": "",
    "oauth": "",
    "user_name": ""
}'
```

## Contributing

To contribute to this project, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b feature/your-feature`.
3. Commit your changes: `git commit -m 'feat: My new feature'`.
4. Push your changes: `git push origin feature/your-feature`.
5. Submit a pull request.

## Authors

- Duperez