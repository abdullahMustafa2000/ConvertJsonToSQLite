package com.trainingexample.convertapitosqlite.data;

import android.provider.BaseColumns;

public class DBContract {

    public static final class MenuEntry implements BaseColumns {

        public static final String TABLE_NAME = "Reciters";

        public static final String RAW_ID = "raw_id";
        public static final String SERVER_COLUMN = "Server";
        public static final String LITTER_COLUMN = "letter";
        public static final String NAME_COLUMN = "name";
        public static final String COUNT_COLUMN = "count";
        public static final String SURAS_COLUMN = "suras";
        public static final String REWAYA_COLUMN = "rewaya";
        public static final String ID_COLUMN = "id";

    }
}
