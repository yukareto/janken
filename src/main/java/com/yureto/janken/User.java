package com.yureto.janken;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String id;
    private final String password;
    private final List<String> history = new ArrayList<>();

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getHistory() {
        return history;
    }

    public void addResult(String result) {
        history.add(result);
        if (history.size() > 10) {
            history.remove(0); // 古い結果を削除（最大10件）
        }
    }

    public int countWins() {
        int count = 0;
        for (String result : history) {
            if (result.equals("勝ち")) count++;
        }
        return count;
    }
}
