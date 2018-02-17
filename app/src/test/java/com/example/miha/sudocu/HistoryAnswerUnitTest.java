package com.example.miha.sudocu;

import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.model.HistoryAnswer;

import org.junit.Test;

import static org.junit.Assert.*;

public class HistoryAnswerUnitTest {
    private HistoryAnswer historyAnswer1 = new HistoryAnswer(1, "1");
    private HistoryAnswer historyAnswer2 = new HistoryAnswer(0, "1");
    private HistoryAnswer historyAnswer3 = new HistoryAnswer(0, "1");

    @Test
    public void decrementEmptyHistory_isCorrect() throws Exception {
        Grid gr = new Grid();
        assertEquals(null, gr.decrementHistory());
    }

    @Test
    public void decrementHistory_isCorrect() throws Exception {
        Grid gr = new Grid();
        gr.addAnswerToHistory(historyAnswer1);
        gr.addAnswerToHistory(historyAnswer2);
        assertEquals(historyAnswer1, gr.decrementHistory());
    }

    @Test
    public void decrementNotLimitHistory_isCorrect() throws Exception {
        Grid gr = new Grid();
        gr.addAnswerToHistory(historyAnswer1);
        gr.decrementHistory();
        assertEquals(null, gr.decrementHistory());
    }

    @Test
    public void IncrementHistory_isCorrect() throws Exception {
        Grid gr = new Grid();
        gr.addAnswerToHistory(historyAnswer1);
        gr.addAnswerToHistory(historyAnswer2);
        gr.addAnswerToHistory(historyAnswer3);
        gr.incrementHistory();
        gr.decrementHistory();//id =  answer2
        gr.incrementHistory();//id =  answer3
        gr.incrementHistory();//id =  answer3
        gr.decrementHistory();//id = answer2
        gr.decrementHistory();//id = answer1
        assertEquals(historyAnswer2, gr.incrementHistory());
    }

    @Test
    public void IncrementEmptyHistory_isCorrect() throws Exception {
        Grid gr = new Grid();
        assertEquals(gr.incrementHistory(), null);
    }
    @Test
    public void AddNewHistoryAfterDecrementHistory_isCorrect() throws Exception{
        Grid gr = new Grid();
        gr.addAnswerToHistory(historyAnswer1);
        gr.addAnswerToHistory(historyAnswer2);
        gr.addAnswerToHistory(historyAnswer3);
        gr.decrementHistory();//id = historyAnswer2
        gr.addAnswerToHistory(historyAnswer3);//id = historyAnswer3
        gr.addAnswerToHistory(historyAnswer3);//id = historyAnswer3
        HistoryAnswer temp = gr.decrementHistory();//id = historyAnswer1
        assertEquals(temp,historyAnswer3);
    }
}