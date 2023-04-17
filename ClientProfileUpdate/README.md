в МКС CPUpdate, CPService сделан перевод средств по номеру телефона от одного клиенту к другому.
от icp 1220 к icp 1219.

в контроллере MoneyTransferController МКС CPUpdate (Producer) принмаетс запрос из постман (MoneyTransferDto, МКС коммон)

localhost:8088/transfer/?icpFromParam=1220

{
"event": "MoneyTransfer",
"accepterPhonenumber": "+9053398607",
"payment": "1100",
"currency": "RUB",
"senderIcp": "1220"
}

далее, действие переходит в МКС CPService (Consumer), в классе MainApp настроен kafkaListener.
Далее действие переходит в WalletServiceImpl.

Потом результат (TransferResultDto) возвращается в MainApp, далее в MoneyTransferController , далее
идет json TransferResultDto в постман с ответом по транзакции.


ВАЖНО!:
у меня иногда (редко, но у кого-то может будет чаще) возникала проблема: в постман вместо json TransferResultDto  
была ошибка "java.util.concurrent.ExecutionException". 
Транзакция при этом проходит! Средства переводятся. (это видно в логах класса MainApp в консоли, ну и запросом кошелька клиента можно проверить).
Проблема именно в том , что kafkaTemplate (http response) не возвращается в MoneyTransferController (проходит слишком много времени при ожидании, 
или у продюсера и консьюмера возникает рассинхрон)

Я эту проблему обходил своеобразным костылем - в MoneyTransferController напихивал вывод логов в консоль рядом с ProducerRecord, RequestReplyFuture
ConsumerRecord, что увеличивало время работы контроллера, и он успевал дождаться ответа. Что-то вроде:

"
...class MoneyTransferController {
else { // transaction is successful?
ProducerRecord<String, MoneyTransferDto> record = new ProducerRecord<>(requestTopic, null, moneyTransferDto.getIcp(), moneyTransferDto);
log.info("222 RequestReplyFuture");
RequestReplyFuture<String, MoneyTransferDto, TransferResultDto> future = replyingKafkaTemplate.sendAndReceive(record);


            log.info("333 future.get();");
            log.info("333 future.get();");
            log.info("333 future.get();");

            ConsumerRecord<String, TransferResultDto> response = future.get();
            log.info("444 finish response");
            log.info("444 finish response");
            log.info("444 finish response");

            if(response.value().getTransactionResult().equals("the transaction was successful")) {
                log.info("### successful");
}
"
Но это , конечно, не дело...

До конца ту проблеу решить не успел. Нужно либо увеличить в кафке время ожидания ответа на запрос, а лучше использовать
Template for asynchronous call (конфигурация продюсера и консьюмера для асинхронного вызова)

 
