package sarath.com.news;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class NewsDataParser {

    private String response;

    NewsDataParser(String response){
        this.response = response;
    }

     ArrayList<News> getArrayList(){
         ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject main = new JSONObject(response);

            JSONArray articles =  main.getJSONArray("articles");
            for(int i = 0; i<articles.length(); i++){
                JSONObject temp = articles.getJSONObject(i);
                JSONObject source = temp.getJSONObject("source");

                String headlines = temp.getString("title");
                String author = temp.getString("author");

                String description = temp.getString("description");
                if(description == null || description.equals("null"))description = "";

                String url = temp.getString("url");
                String urlToImage = temp.getString("urlToImage");
                String publishedAt = temp.getString("publishedAt");

                publishedAt = publishedAt.replace("Z", "");
                String[] dateAndTime = publishedAt.split("T");
                publishedAt = dateAndTime[0] + ", " + dateAndTime[1];

                author += ", " + source.getString("name");

                news.add(new News(headlines, author, description, publishedAt, url, urlToImage));
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return news;
    }
}
