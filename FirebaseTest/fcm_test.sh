KEY=AAAAgbNS69M:APA91bEnJ4_P45eG6yPYrvg3wdO6_LybNE-kzsjGUVVlsaNvUZ9nQcf3J-cm908H1eirmShT_YX-8a2_4XsuiaHoE3AbuWRVAxPXSJKKL1oBQsXWkMDFcMMhssUm23Z6U7iptNxObmrNRu5VjhCLv7oK2SPNG3xMxw
TOKEN=ePLprMzS__c:APA91bEXPouC6CPXaXzjKF0GCvXrAADP7PVAeG9ENwFCEcD3rSxgZ8Xwx4xVV2YktgHAqGw7u155ItP_q2vpi7cwU7AlKqol8HCUzppEKQYPF2y6l3e6FvJm7_ooTokG8Tr3AieiNfVJ
curl --header "Authorization: key=$KEY" --header Content-Type:"application/json" https://fcm.googleapis.com/fcm/send -d "{ \"notification\": { \"title\": \"title here\", \"text\": \"message body here\" }, \"to\" : \"$TOKEN\"}"
