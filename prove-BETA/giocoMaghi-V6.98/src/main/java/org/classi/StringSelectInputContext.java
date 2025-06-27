package org.classi;

import org.springframework.shell.component.support.SelectorItem;

public class StringSelectInputContext {
    private SelectorItem<String> resultItem;

    public StringSelectInputContext() {
    }

    public static StringSelectInputContext empty() {
        return new StringSelectInputContext();
    }

    public SelectorItem<String> getResultItem() {
        return resultItem;
    }

    public void setResultItem(SelectorItem<String> resultItem) {
        this.resultItem = resultItem;
    }
}
