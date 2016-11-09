package com.mateuszwiater.csc420.worldflagsalternative;

import javax.swing.*;
import java.util.List;

public class CountryList extends JScrollPane {

    public CountryList(final List<Flag> flags) {
        final JList countries = new JList<>(flags.toArray());
        countries.setSelectedIndex(0);
        countries.setDragEnabled(true);
        countries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setViewportView(countries);
    }
}
