package project.fedorova.polyglotte;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.UUID;

import project.fedorova.polyglotte.data.DataBase.DBConnector;
import project.fedorova.polyglotte.data.PreferenceVars;
import project.fedorova.polyglotte.data.ReadWriteManager;
import project.fedorova.polyglotte.data.Word;

public class PopUpAddNewWord extends Activity implements View.OnClickListener{
    private static final String ONLY_COMMAS = "Sorry, only commas to divide words";
    private static final String ONLY_LETTERS = "Sorry, only letters and numbers.";
    private Button addThemeBtn;
    private Button saveWordBtn;
    private Button backBtn;
    private DBConnector wordBase;
    private TextInputLayout wordTIL;
    private TextInputLayout mainTranslationTIL;
    private TextInputLayout translationTIL;
    private Intent intent;
    private String wordID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_add_word);

        init();

        setDataToEdit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.addThemeToWord):
                break;
            case (R.id.saveWord):
                if (!readyToSave()) {
                    Toast.makeText(PopUpAddNewWord.this, "Your word is not completed", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!wordID.equals("")) {
                    wordBase.delete(wordID);
                }
                saveNewWord();
                finish();
                break;
            case (R.id.backBtn):
                finish();
                break;
            default:
                break;
        }
    }

    private void saveNewWord() {
        ReadWriteManager readWriteManager = ReadWriteManager.getInstance();
        String title = "";
        String mainTrans = "";
        String extraTrans = "";
        try {
            title = wordTIL.getEditText().getText().toString();
            mainTrans = mainTranslationTIL.getEditText().getText().toString();
            extraTrans = translationTIL.getEditText().getText().toString();
        } catch (Exception e) {}
        wordBase.insertWord(new Word(UUID.randomUUID(), title,
                mainTrans,
                readWriteManager.convertStringToSet(extraTrans),
                null));
    }

    private void init() {
        wordBase = new DBConnector(this);

        addThemeBtn = (Button) findViewById(R.id.addThemeToWord);
        addThemeBtn.setOnClickListener(this);

        saveWordBtn = (Button) findViewById(R.id.saveWord);
        saveWordBtn.setOnClickListener(this);

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        wordTIL = (TextInputLayout) findViewById(R.id.wordinput);
        wordTIL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && containsPunct(s.toString())) {
                    wordTIL.setErrorEnabled(true);
                    wordTIL.setError(ONLY_LETTERS);
                } else {
                    wordTIL.setErrorEnabled(false);
                }
            }
        });
        wordTIL.setErrorEnabled(false);

        mainTranslationTIL = (TextInputLayout) findViewById(R.id.translationinput);
        mainTranslationTIL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && containsPunct(mainTranslationTIL.getEditText().getText().toString())) {
                    mainTranslationTIL.setErrorEnabled(true);
                    mainTranslationTIL.setError(ONLY_LETTERS);
                } else {  //TODO Translate word and check the translation
                    mainTranslationTIL.setErrorEnabled(false);
                }
            }
        });
        mainTranslationTIL.setErrorEnabled(false);

        translationTIL = (TextInputLayout) findViewById(R.id.extratranslationsinput);
        translationTIL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && containsPunctExceptComma(translationTIL.getEditText().getText().toString())) {
                    translationTIL.setErrorEnabled(true);
                    translationTIL.setError(ONLY_COMMAS);
                } else {
                    translationTIL.setErrorEnabled(false);
                }
            }
        });
        translationTIL.setErrorEnabled(false);

        intent = getIntent();
    }

    private void setDataToEdit() {
        if (intent.getStringExtra(PreferenceVars.IF_EDIT) != null && intent.getStringExtra(PreferenceVars.IF_EDIT).equals(PreferenceVars.YES)) {
            int wordPos = intent.getIntExtra(PreferenceVars.WORD_INDEX, 0);

            Cursor cursor = wordBase.getAllWords();
            cursor.move(wordPos + 1);

            wordID = cursor.getString(DBConnector.NUM_WORD_ID);
            try {
                wordTIL.getEditText().setText(cursor.getString(DBConnector.NUM_WORD_TITLE));
                mainTranslationTIL.getEditText().setText(cursor.getString(DBConnector.NUM_WORD_MAIN_TRANSLATION));
                translationTIL.getEditText().setText(cursor.getString(DBConnector.NUM_WORD_TRANSLATIONS));
            } catch (Exception e) {}
        }
    }

    private boolean readyToSave() {
        try {
            return wordTIL.getEditText().getText()!= null &&
                    mainTranslationTIL.getEditText().getText() != null &&
                    !wordTIL.getEditText().getText().toString().equals("") &&
                    !mainTranslationTIL.getEditText().getText().toString().equals("") &&
                    !wordTIL.isErrorEnabled() &&
                    !mainTranslationTIL.isErrorEnabled() &&
                    !translationTIL.isErrorEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean containsPunctExceptComma (String text) {
        String check = text.replace(",", "").replace(" ", "");
        return !check.equals(check.replaceAll("[\\p{Punct}]", ""));
    }

    private boolean containsPunct(String text) {
        String check = text.replace(" ", "");
        return !check.equals(check.replaceAll("[\\p{Punct}]", ""));
    }
}