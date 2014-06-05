/*******************************************************************************
 * Copyright 2014 Manh Luong   Bui
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

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
