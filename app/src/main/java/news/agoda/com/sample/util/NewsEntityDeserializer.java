package news.agoda.com.sample.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import news.agoda.com.sample.data.NewsEntity;

public class NewsEntityDeserializer implements JsonDeserializer<NewsEntity> {
    @Override
    public NewsEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject newsEntityJson = json.getAsJsonObject();
        JsonElement multimediaJson = newsEntityJson.get("multimedia");
        NewsEntity newsEntity;
        if (multimediaJson.isJsonArray()) {
            Gson gson = new Gson();
            newsEntity = gson.fromJson(json, NewsEntity.class);
        } else {
            Gson gson = new Gson();
            //delete multimedia
            newsEntityJson.remove("multimedia");
            newsEntity = gson.fromJson(newsEntityJson, NewsEntity.class);
        }
        return newsEntity;
    }
}
