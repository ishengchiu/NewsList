package news.agoda.com.sample.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This represents a news item
 */
public class NewsEntity {
    private static final String TAG = NewsEntity.class.getSimpleName();
    @SerializedName("title")
    private String mTitle;
    @SerializedName("abstract")
    private String mSummary;
    @SerializedName("url")
    private String mStoryUrl;
    @SerializedName("byline")
    private String mByline;
    @SerializedName("published_date")
    private String mPublishedDate;
    @SerializedName("multimedia")
    private List<MediaEntity> mMediaEntityList = new ArrayList<>();

    public NewsEntity() {
    }

    public NewsEntity(String title, String summary, String storyUrl,
                      String byline, String publishedDate, List<MediaEntity> mediaEntityList) {
        mTitle = title;
        mSummary = summary;
        mStoryUrl = storyUrl;
        mByline = byline;
        mPublishedDate = publishedDate;
        mMediaEntityList = mediaEntityList;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSummary() {
        return mSummary;
    }

    public String getStoryUrl() {
        return mStoryUrl;
    }

    public String getByline() {
        return mByline;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public List<MediaEntity> getMediaEntity() {
        return mMediaEntityList;
    }

    /**
     * This class represents a media item
     */
    public static class MediaEntity {
        public static final String FORMAT_STANDARD = "Standard Thumbnail";
        public static final String FORMAT_LARGE = "thumbLarge";
        public static final String FORMAT_NORMAL = "Normal";
        public static final String FORMAT_MEDIUM_3_2 = "mediumThreeByTwo210";

        @SerializedName("url")
        private String mUrl;
        @SerializedName("format")
        private String mFormat;
        @SerializedName("height")
        private int mHeight;
        @SerializedName("width")
        private int mWidth;
        @SerializedName("type")
        private String mType;
        @SerializedName("subtype")
        private String mSubType;
        @SerializedName("caption")
        private String mCaption;
        @SerializedName("copyright")
        private String mCopyright;

        public MediaEntity(){
        }

        public MediaEntity(String url, String format, int height, int width,
                           String type, String subType, String caption, String copyright) {
            mUrl = url;
            mFormat = format;
            mHeight = height;
            mWidth = width;
            mType = type;
            mSubType = subType;
            mCaption = caption;
            mCopyright = copyright;
        }

        public String getUrl() {
            return mUrl;
        }

        public String getFormat() {
            return mFormat;
        }

        public int getHeight() {
            return mHeight;
        }

        public int getWidth() {
            return mWidth;
        }

        public String getType() {
            return mType;
        }

        public String getSubType() {
            return mSubType;
        }

        public String getCaption() {
            return mCaption;
        }

        public String getCopyright() {
            return mCopyright;
        }

    }
}
