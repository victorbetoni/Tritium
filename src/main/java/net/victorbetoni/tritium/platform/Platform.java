package net.victorbetoni.tritium.platform;

import net.victorbetoni.tritium.version.Versionable;

import java.net.URI;
import java.net.http.HttpRequest;

public interface Platform extends Versionable {
    String id();
    String URL();
    String name();

    default HttpRequest.Builder createRequest(String path) {
        //System.out.println(String.format("%s/%s/%s", this.URL(), this.version(), path));
        return HttpRequest.newBuilder(URI.create(String.format("%s/%s/%s", this.URL(), this.version(), path)))
                .header("Content-Type", "application/json");
    }
}
