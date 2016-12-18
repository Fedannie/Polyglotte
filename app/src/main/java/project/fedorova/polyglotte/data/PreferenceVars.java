package project.fedorova.polyglotte.data;

public class PreferenceVars {
    private static volatile PreferenceVars instance;

    public static final String NATIVE_LANGUAGE = "NATIVE_LANG";
    //private String nativeLang = null;
    public static final String DICT_LANGUAGE = "DICT_LANG";
    //private String dictLang = null;
    public static final String DEFAULT_LANG = "English123";
    public static final String FIRST_TIME = "first time";
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String WORD_INDEX = "word index";
    public static final String IF_EDIT = "if edit";
    public static final String PHRASE = "phrase";
    public static final String TRANS = "translation";
    public static final String NATIVE_LANG_CHANGED = "native lang changed";
    //private boolean nativeLangChanged = false;

    //public boolean hasNativeLangChanged() {
        //return instance.nativeLangChanged;
    //}

    //public void setNativeLangChanged(boolean flag) {
    //    instance.nativeLangChanged = flag;
    //}

    /*public String getNativeLang() {
        if (instance.nativeLang == null) {
            return DEFAULT_LANG;
        } else {
            return instance.nativeLang;
        }
    }

    public String getDictLang() {
        if (instance.dictLang == null) {
            return DEFAULT_LANG;
        } else {
            return instance.dictLang;
        }
    }

    public void setNativeLang(String newNativeLang) {
        instance.nativeLang = newNativeLang;
    }

    public void setDictLang(String newDictLang) {
        instance.dictLang = newDictLang;
    }
*/
    private PreferenceVars() {}

    public static PreferenceVars getInstance() {
        PreferenceVars localInstance = instance;
        if (localInstance == null) {
            synchronized (PreferenceVars.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PreferenceVars();
                }
            }
        }
        return localInstance;
    }
}
