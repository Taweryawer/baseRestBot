package taweryawer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.StateMachineService;

public class RestBot extends TelegramLongPollingBot {

    private StateMachineService stateMachineService;
    private Log log = LogFactory.getLog(RestBot.class);

    @Autowired
    public RestBot(StateMachineService stateMachineService) {
        this.stateMachineService = stateMachineService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
            }
        } catch (Exception e) {
            log.error("Something went wrong", e);
        }

    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }
}
