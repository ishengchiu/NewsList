package news.agoda.com.sample;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import news.agoda.com.sample.helper.NewsFeedParser;
import news.agoda.com.sample.model.NewsEntity;
import news.agoda.com.sample.model.NewsFeed;

/**
 * Created by chiu_mac on 2016/11/12.
 */

public class NewsFeedParserTest {

    @Test
    public void testNewsFeedValueCorrectness() {
        final String MOCK_JSON = loadMockJson("mock_news_feed_1.json");
        Assert.assertNotNull(MOCK_JSON);
        NewsFeed newsFeed = NewsFeedParser.gsonParse(MOCK_JSON);
        JsonParser jsonParser = new JsonParser();
        JsonElement mockJsonElement = jsonParser.parse(MOCK_JSON);
        JsonObject mockJsonObject = mockJsonElement.getAsJsonObject();
        //Test values of NewsFeed's fields
        String status = mockJsonObject.get("status").getAsString();
        String copyright = mockJsonObject.get("copyright").getAsString();
        String section = mockJsonObject.get("section").getAsString();
        String lastUpdate = mockJsonObject.get("last_updated").getAsString();
        int numResult = mockJsonObject.get("num_results").getAsInt();
        Assert.assertEquals(status, newsFeed.getStatus());
        Assert.assertEquals(copyright, newsFeed.getCopyright());
        Assert.assertEquals(section, newsFeed.getSection());
        Assert.assertEquals(lastUpdate, newsFeed.getLastUpdated());
        Assert.assertEquals(numResult, newsFeed.getNumResults());
        Assert.assertEquals(numResult, newsFeed.getNewsEntityList().size());
        //Test values of NewsEntity's fields
        JsonArray results = mockJsonObject.getAsJsonArray("results");
        Assert.assertEquals(numResult, results.size());
        int n = newsFeed.getNewsEntityList().size();
        for (int i = 0 ; i < n ; i++) {
            JsonObject newsJsonObject = results.get(i).getAsJsonObject();
            NewsEntity newsEntity = newsFeed.getNewsEntityList().get(i);
            String title = newsJsonObject.get("title").getAsString();
            String summary = newsJsonObject.get("abstract").getAsString();
            String storyUrl = newsJsonObject.get("url").getAsString();
            Assert.assertEquals(title, newsEntity.getTitle());
            Assert.assertEquals(summary, newsEntity.getSummary());
            Assert.assertEquals(storyUrl, newsEntity.getStoryUrl());
            //Test values of MediaEntity's field
            if (newsJsonObject.get("multimedia").isJsonArray()) {
                JsonArray imgs = newsJsonObject.getAsJsonArray("multimedia");
                Assert.assertEquals(imgs.size(), newsEntity.getMediaEntity().size());
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
                    Assert.assertEquals(imgUrl, media.getUrl());
                    Assert.assertEquals(format, media.getFormat());
                    Assert.assertEquals(height, media.getHeight());
                    Assert.assertEquals(width, media.getWidth());
                    Assert.assertEquals(type, media.getType());
                    Assert.assertEquals(subtype, media.getSubType());
                    Assert.assertEquals(caption, media.getCaption());
                    Assert.assertEquals(imgCopyright, media.getCopyright());
                }
            }
        }
    }

    private String loadMockJson(String file) {
        try {
            return readIt(this.getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        char[] buffer = new char[1024];
        int r = -1;
        StringBuffer str = new StringBuffer(1024);
        reader = new InputStreamReader(stream, "UTF-8");
        while((r = reader.read(buffer)) != -1) {
            str.append(buffer,0, r);
        }
        return str.toString();
    }
}
