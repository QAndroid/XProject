package workshop1024.com.xproject.model.news;

import java.util.List;

/**
 * 发布者发布的新闻详情
 */
public class NewsDetail extends News {
    //新闻作者
    private String mAuthor;
    //新闻的内容
    private String mContent;
    //新闻的标签
    private List<String> mTagList;

    public NewsDetail(String newId, String bannerUrl, String title, String publisher, String pubDate,
                      String author, String content, List<String> tagList, boolean isReaded, boolean isSaved) {
        super(newId, bannerUrl, title, publisher, pubDate, isReaded, isSaved);
        mAuthor = author;
        mContent = content;
        mTagList = tagList;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public List<String> getTagList() {
        return mTagList;
    }

    public void setTagList(List<String> tagList) {
        mTagList = tagList;
    }
}
