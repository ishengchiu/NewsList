package news.agoda.com.sample.util;

import android.support.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import news.agoda.com.sample.data.NewsEntity;
import news.agoda.com.sample.data.NewsFeed;

import static junit.framework.Assert.*;

/**
 * Created by chiu_mac on 2016/11/21.
 */

public class NewsFeedParserTest {

    @Test
    public void parseShouldReturnCorrectObject() {
        final String MOCK_JSON = loadMockJson("mock_news_feed_1.json");
        assertNotNull(MOCK_JSON);
        NewsFeed newsFeed = NewsFeedParser.parse(MOCK_JSON);
        JsonParser jsonParser = new JsonParser();
        JsonElement mockJsonElement = jsonParser.parse(MOCK_JSON);
        JsonObject mockJsonObject = mockJsonElement.getAsJsonObject();
        //Test values of NewsFeed's fields
        String status = mockJsonObject.get("status").getAsString();
        String copyright = mockJsonObject.get("copyright").getAsString();
        String section = mockJsonObject.get("section").getAsString();
        String lastUpdate = mockJsonObject.get("last_updated").getAsString();
        int numResult = mockJsonObject.get("num_results").getAsInt();
        assertEquals(status, newsFeed.getStatus());
        assertEquals(copyright, newsFeed.getCopyright());
        assertEquals(section, newsFeed.getSection());
        assertEquals(lastUpdate, newsFeed.getLastUpdated());
        assertEquals(numResult, newsFeed.getNumResults());
        assertEquals(numResult, newsFeed.getNewsEntityList().size());
        //Test values of NewsEntity's fields
        JsonArray results = mockJsonObject.getAsJsonArray("results");
        assertEquals(numResult, results.size());
        int n = newsFeed.getNewsEntityList().size();
        for (int i = 0 ; i < n ; i++) {
            JsonObject newsJsonObject = results.get(i).getAsJsonObject();
            NewsEntity newsEntity = newsFeed.getNewsEntityList().get(i);
            String title = newsJsonObject.get("title").getAsString();
            String summary = newsJsonObject.get("abstract").getAsString();
            String storyUrl = newsJsonObject.get("url").getAsString();
            assertEquals(title, newsEntity.getTitle());
            assertEquals(summary, newsEntity.getSummary());
            assertEquals(storyUrl, newsEntity.getStoryUrl());
            //Test values of MediaEntity's field
            if (newsJsonObject.get("multimedia").isJsonArray()) {
                JsonArray imgs = newsJsonObject.getAsJsonArray("multimedia");
                assertEquals(imgs.size(), newsEntity.getMediaEntity().size());
                for (int k = 0 ; k < imgs.size(); k++) {
                    NewsEntity.MediaEntity media = newsEntity.getMediaEntity().get(k);
                    JsonObject img = imgs.get(k).getAsJsonObject();
                    String imgUrl = img.get("url").getAsString();
                    String format = img.get("format").getAsString();
                    int height = img.get("height").getAsInt();
                    int width = img.get("width").getAsInt();
                    String type = img.get("type").getAsString();
                    String subtype = img.get("subtype").getAsString();
                    String caption = img.get("caption").getAsString();
                    String imgCopyright = img.get("copyright").getAsString();
                    assertEquals(imgUrl, media.getUrl());
                    assertEquals(format, media.getFormat());
                    assertEquals(height, media.getHeight());
                    assertEquals(width, media.getWidth());
                    assertEquals(type, media.getType());
                    assertEquals(subtype, media.getSubType());
                    assertEquals(caption, media.getCaption());
                    assertEquals(imgCopyright, media.getCopyright());
                }
            }
        }
    }

    private String loadMockJson(String file) {
        return readIt(this.getClass().getClassLoader().getResourceAsStream(file));
    }

    @Nullable
    private static String readIt(InputStream stream) {
        try (Reader reader = new InputStreamReader(stream, "UTF-8")) {
            char[] buffer = new char[1024];
            int r = -1;
            StringBuffer str = new StringBuffer(1024);
            while ((r = reader.read(buffer)) != -1) {
                str.append(buffer, 0, r);
            }
            return str.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
