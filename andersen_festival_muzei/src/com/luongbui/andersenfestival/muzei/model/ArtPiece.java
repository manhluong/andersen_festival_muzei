package com.luongbui.andersenfestival.muzei.model;

public class ArtPiece {
   private String fileName;
   private String title;
   private String author;
   private String year;
   private String authorUrl;
   
   public ArtPiece(String fn,
                     String tit,
                     String auth,
                     String year,
                     String authUrl) {
      fileName = fn;
      title = tit;
      author = auth;
      this.year = year;
      authorUrl = authUrl;
      }
   
   public String getFileName() {
      return fileName;
      }
   
   public String getTitle() {
      return author + ", " + title;
      }
   
   public String getAuthor() {
      return author;
      }
   
   public String getYear() {
      return year;
      }
   
   public String getByLine() {
      return author + ", " + year;
      }
   
   public String getAuthorUrl() {
      return authorUrl;
      }
   }
