package org.example;

import java.util.Objects;

public class Download {
    String url;

    public Download(String url){
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Download download)) return false;
        return Objects.equals(url, download.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url);
    }

    @Override
    public String toString() {
        return "Download{" +
                "url='" + url + '\'' +
                '}';
    }
}
