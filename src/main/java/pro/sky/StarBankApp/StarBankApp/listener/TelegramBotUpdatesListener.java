package pro.sky.StarBankApp.StarBankApp.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.StarBankApp.StarBankApp.dto.Recommendation;
import pro.sky.StarBankApp.StarBankApp.service.RecommendationService;
import pro.sky.StarBankApp.StarBankApp.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener{

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        for (Update update : updates) {
            logger.info("Processing update: {}", update);

            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text().trim();
                long chatId = update.message().chat().id();

                if (messageText.equals("/start")) {
                    SendMessage message = new SendMessage(chatId,
                            "Здравствуйте! Я бот банка Star. " +
                                    "Используйте команду /recommend <username>, " +
                                    "чтобы получить рекомендации.");
                    telegramBot.execute(message);
                    continue;
                }

                if (messageText.startsWith("/recommend")) {
                    String username = messageText.substring(10).trim();

                    if (username.isEmpty()) {
                        SendMessage errorMessage = new SendMessage(chatId, "Пожалуйста, " +
                                "укажите имя пользователя после команды /recommend.");
                        telegramBot.execute(errorMessage);
                        continue;
                    }


                    String fullName = userService.getFullNameByUsername(username);

                    if (fullName == null) {
                        SendMessage errorMessage = new SendMessage(chatId, "Пользователь " +
                                "с таким именем не найден.");
                        telegramBot.execute(errorMessage);
                        continue;
                    }

                    UUID userId = userService.getUserIdByUsername(username);

                    List<Recommendation> recommendations = recommendationService.getRecommendations(userId);

                    StringBuilder recommendationsText = new StringBuilder("Здравствуйте, "
                            + fullName + "!\n\nНовые продукты для вас:\n");
                    for (Recommendation recommendation : recommendations) {
                        recommendationsText.append(recommendation.getName()).append(": ").append(recommendation.getText()).append("\n");
                    }

                    SendMessage message = new SendMessage(chatId, recommendationsText.toString());
                    telegramBot.execute(message);
                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
