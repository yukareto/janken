package com.yureto.janken;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class JankenController {
    private final String[] hands = {"グー", "チョキ", "パー"};
    private final Random random = new Random();

    @GetMapping("/menu")
    public String showMenu() {
        return "menu";
    }

    @GetMapping("/play")
    public String showPlayForm() {
        return "janken";
    }

    @PostMapping("/play")
    public String playJanken(@RequestParam String hand,
                             HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new IllegalStateException("ログイン情報がありません。 再度ログインしてください。");
        }

        int computerIndex = random.nextInt(3);
        String computerHand = hands[computerIndex];

        String result = judge(hand, computerHand);

        String resultMessage = "あなた: " + hand + " / 相手: " + computerHand + " → 結果: " + result;
        user.addResult(result);

        model.addAttribute("result", resultMessage);
        model.addAttribute("history", user.getHistory());
        model.addAttribute("winCount", user.countWins());

        return "result";
    }

    @GetMapping("/result")
    public String showResult(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("history", user.getHistory());
        model.addAttribute("winCount", user.countWins());
        return "result";
    }

    private String judge(String player, String computer) {
        if (player.equals(computer)) return "引き分け";

        if ((player.equals("グー") && computer.equals("チョキ")) ||
                (player.equals("チョキ") && computer.equals("パー")) ||
                (player.equals("パー") && computer.equals("グー"))) {
            return "勝ち";
        }
        return "負け";
    }
}
