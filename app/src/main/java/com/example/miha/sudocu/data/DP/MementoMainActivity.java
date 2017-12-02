package com.example.miha.sudocu.data.DP;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class MementoMainActivity {
    private Map<Integer, ArrayList<Integer>> errorMap;
    private ArrayList<Integer> knowOption;
    private ArrayList<Integer> sameAnswer;
    private String lastAnswer;
    private Integer lastChoseInputId;
    private Integer lastChoseNotInputId;
    private ArrayList<Integer> errorForClean;

    public ArrayList<Integer> getKnowOption() {
        return knowOption;
    }

    public void setKnowOption(ArrayList<Integer> knowOption) {
        this.knowOption = knowOption;
    }

    public MementoMainActivity() {
        this.lastAnswer = null;
        this.sameAnswer = new ArrayList<>();
        this.errorForClean = new ArrayList<>();
        this.lastChoseInputId = null;
        this.lastChoseNotInputId = null;
        this.knowOption = new ArrayList<>();
        this.errorMap = new Hashtable<>();
    }

    public Integer getLastChoseInputId() {
        return lastChoseInputId;
    }

    public void setLastChoseInputId(int lastChoseInputId) {
        this.lastChoseInputId = lastChoseInputId;
    }

    public Integer getLastChoseNotInputId() {
        return lastChoseNotInputId;
    }

    public void setLastChoseNotInputId(int lastChoseNotInputId) {
        this.lastChoseNotInputId = lastChoseNotInputId;
    }

    public String getLastAnswer() {
        return this.lastAnswer;
    }

    public void setLastAnswer(String lastAnswer) {
        this.lastAnswer = lastAnswer;
    }

    public ArrayList<Integer> getError() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : errorMap.entrySet()) {
            if (entry.getValue().size() == 1) {
                continue;
            }
            arrayList.addAll(entry.getValue());
        }
        return arrayList;
    }

    public ArrayList<Integer> getError(Integer id) {
        return errorMap.get(id);
    }

    public void addError(Integer id, ArrayList<Integer> error) {
        if (error.isEmpty()) {//если список пуст то удаляем этот id из всех списков ошибок
            for (Map.Entry<Integer, ArrayList<Integer>> errorList : errorMap.entrySet()) {
                while(errorList.getValue().contains(id)){//завтра проверить
                    errorList.getValue().remove(id);
                }
            }
        }
        errorMap.put(id, error);
    }

    public void clear() {
        errorMap.clear();
        this.lastAnswer = null;
        this.lastChoseInputId = null;
        this.lastChoseNotInputId = null;
        this.sameAnswer.clear();
        this.knowOption.clear();
    }

    public void setSameAnswer(ArrayList<Integer> sameAnswer) {
        this.sameAnswer = sameAnswer;
    }

    public ArrayList<Integer> getSameAnswer() {
        return sameAnswer;
    }

    public ArrayList<Integer> getErrorForClean() {
        return errorForClean;
    }

    public void setErrorForClean(ArrayList<Integer> errorForClean) {
        this.errorForClean = errorForClean;
    }
}
