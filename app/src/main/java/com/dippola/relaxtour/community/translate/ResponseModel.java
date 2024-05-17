package com.dippola.relaxtour.community.translate;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @Keep
    @SerializedName("data")
    @Expose
    Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        @Keep
        @SerializedName("translations")
        @Expose
        List<Translation> Translations;

        public List<Translation> getTranslations() {
            return Translations;
        }
    }

    public class Translation {
        @Keep
        @SerializedName("translatedText")
        @Expose
        String translatedText;
        @Keep
        @SerializedName("detectedSourceLanguage")
        @Expose
        String detectedSourceLanguage;

        public String getTranslatedText() {
            return translatedText;
        }
    }
}
