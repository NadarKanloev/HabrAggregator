package com.example.Habr_Parser_Project.model.article;

import jakarta.persistence.*;

@Table(name = "articles")
@Entity
public class Articles {
    @Basic
    @Column(name = "hub_id", nullable = true, length = 255)
    private String hubId;
    @Basic
    @Column(name = "flow", nullable = true, length = 255)
    private String flow;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "article_id", nullable = false)
    private long articleId;
    @Basic
    @Column(name = "author", nullable = true, length = 255)
    private String author;
    @Basic
    @Column(name = "pub_datetime", nullable = true, length = 255)
    private String pubDatetime;
    @Basic
    @Column(name = "rating", nullable = true, length = 255)
    private String rating;
    @Basic
    @Column(name = "rating_des", nullable = true, length = 255)
    private String ratingDes;
    @Basic
    @Column(name = "views", nullable = true, length = 255)
    private String views;
    @Basic
    @Column(name = "bookmarks", nullable = true, length = 255)
    private String bookmarks;
    @Basic
    @Column(name = "comments", nullable = true, length = 255)
    private String comments;
    @Basic
    @Column(name = "company_link", nullable = true, length = 255)
    private String companyLink;
    @Basic
    @Column(name = "body", nullable = true, length = -1)
    private String body;

    public String getHubId(){
        return hubId;
    }

    public void setHubId(String hubId){
        this.hubId = hubId;
    }

    public String getFlow(){
        return flow;
    }

    public void setFlow(String flow){
        this.flow = flow;
    }

    public long getArticleId(){
        return articleId;
    }

    public void setArticleId(long articleId){
        this.articleId = articleId;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getPubDatetime(){
        return pubDatetime;
    }

    public void setPubDatetime(String pubDatetime){
        this.pubDatetime = pubDatetime;
    }

    public String getRating(){
        return rating;
    }

    public void setRating(String rating){
        this.rating = rating;
    }

    public String getRatingDes(){
        return ratingDes;
    }

    public void setRatingDes(String ratingDes){
        this.ratingDes = ratingDes;
    }

    public String getViews(){
        return views;
    }

    public void setViews(String views){
        this.views = views;
    }

    public String getBookmarks(){
        return bookmarks;
    }

    public void setBookmarks(String bookmarks){
        this.bookmarks = bookmarks;
    }

    public String getComments(){
        return comments;
    }

    public void setComments(String comments){
        this.comments = comments;
    }

    public String getCompanyLink(){
        return companyLink;
    }

    public void setCompanyLink(String companyLink){
        this.companyLink = companyLink;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Articles articles = (Articles) o;

        if(articleId != articles.articleId) return false;
        if(hubId != null ? !hubId.equals(articles.hubId) : articles.hubId != null) return false;
        if(flow != null ? !flow.equals(articles.flow) : articles.flow != null) return false;
        if(author != null ? !author.equals(articles.author) : articles.author != null) return false;
        if(pubDatetime != null ? !pubDatetime.equals(articles.pubDatetime) : articles.pubDatetime != null) return false;
        if(rating != null ? !rating.equals(articles.rating) : articles.rating != null) return false;
        if(ratingDes != null ? !ratingDes.equals(articles.ratingDes) : articles.ratingDes != null) return false;
        if(views != null ? !views.equals(articles.views) : articles.views != null) return false;
        if(bookmarks != null ? !bookmarks.equals(articles.bookmarks) : articles.bookmarks != null) return false;
        if(comments != null ? !comments.equals(articles.comments) : articles.comments != null) return false;
        if(companyLink != null ? !companyLink.equals(articles.companyLink) : articles.companyLink != null) return false;

        return true;
    }

    @Override
    public int hashCode(){
        int result = hubId != null ? hubId.hashCode() : 0;
        result = 31 * result + (flow != null ? flow.hashCode() : 0);
        result = 31 * result + (int) (articleId ^ (articleId >>> 32));
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (pubDatetime != null ? pubDatetime.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (ratingDes != null ? ratingDes.hashCode() : 0);
        result = 31 * result + (views != null ? views.hashCode() : 0);
        result = 31 * result + (bookmarks != null ? bookmarks.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (companyLink != null ? companyLink.hashCode() : 0);
        return result;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }
}
