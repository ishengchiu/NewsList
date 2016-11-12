package news.agoda.com.sample.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import news.agoda.com.sample.model.NewsEntity;
import news.agoda.com.sample.model.NewsFeed;

/**
 * This class is used for parsing news feed
 */

public class NewsFeedParser {
    private static final String KEY_RESULT = "results";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ABSTRACT = "abstract";
    private static final String KEY_STORY_URL = "url";
    private static final String KEY_BY_LINE = "byline";
    private static final String KEY_PUBLISH_DATE = "published_date";
    private static final String KEY_MULTIMEDIA = "multimedia";
    private static final String KEY_MULTIMEDIA_IMG_URL = "url";
    private static final String KEY_MULTIMEDIA_FORMAT = "format";
    private static final String KEY_MULTIMEDIA_HEIGHT = "height";
    private static final String KEY_MULTIMEDIA_WIDTH = "width";
    private static final String KEY_MULTIMEDIA_TYPE = "type";
    private static final String KEY_MULTIMEDIA_SUBTYPE = "subtype";
    private static final String KEY_MULTIMEDIA_CAPTION = "caption";
    private static final String KEY_MULTIMEDIA_COPYRIGHT = "copyright";

    public static NewsFeed gsonParse(String json) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(NewsEntity.class, new NewsEntityDeserializer());
        Gson gson = builder.create();
        NewsFeed feed = gson.fromJson(json, NewsFeed.class);
        return feed;
    }

    @Deprecated
    public static List<NewsEntity> parse(String json) throws JSONException {
        List<NewsEntity> newsList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray resultsArray = jsonObject.getJSONArray(KEY_RESULT);
        for (int i = 0 ; i < resultsArray.length() ; i++) {
            JSONObject newsObject = resultsArray.getJSONObject(i);
            ArrayList<NewsEntity.MediaEntity> mediaEntityList = new ArrayList<>();
            String title = newsObject.getString(KEY_TITLE);
            String summary = newsObject.getString(KEY_ABSTRACT);
            String storyUrl = newsObject.getString(KEY_STORY_URL);
            String byline = newsObject.getString(KEY_BY_LINE);
            String publishedDate = newsObject.getString(KEY_PUBLISH_DATE);
            Object multiMedia = newsObject.get(KEY_MULTIMEDIA);
            if (multiMedia instanceof JSONArray) {
                JSONArray mediaArray = (JSONArray)multiMedia;
                for (int j = 0; j < mediaArray.length(); j++) {
                    JSONObject mediaObject = mediaArray.getJSONObject(j);
                    String url = mediaObject.getString(KEY_MULTIMEDIA_IMG_URL);
                    String format = mediaObject.getString(KEY_MULTIMEDIA_FORMAT);
                    int height = mediaObject.getInt(KEY_MULTIMEDIA_HEIGHT);
                    int width = mediaObject.getInt(KEY_MULTIMEDIA_WIDTH);
                    String type = mediaObject.getString(KEY_MULTIMEDIA_TYPE);
                    String subType = mediaObject.getString(KEY_MULTIMEDIA_SUBTYPE);
                    String caption = mediaObject.getString(KEY_MULTIMEDIA_CAPTION);
                    String copyright = mediaObject.getString(KEY_MULTIMEDIA_COPYRIGHT);
                    NewsEntity.MediaEntity mediaEntity = new NewsEntity.MediaEntity(url, format, height, width,
                            type, subType, caption, copyright);
                    mediaEntityList.add(mediaEntity);
                }
            }
            NewsEntity news = new NewsEntity(title, summary, storyUrl, byline,
                    publishedDate, mediaEntityList);
            newsList.add(news);
        }
        return newsList;
    }

    static class NewsEntityDeserializer implements JsonDeserializer<NewsEntity> {
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
}
