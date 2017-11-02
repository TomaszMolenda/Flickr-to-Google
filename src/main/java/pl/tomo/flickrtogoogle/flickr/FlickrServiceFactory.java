package pl.tomo.flickrtogoogle.flickr;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.util.FileAuthStore;
import lombok.SneakyThrows;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Scanner;

@Service
@PropertySource("classpath:/config.properties")
class FlickrServiceFactory {

    @Value("${flickr.api.key}") private String apiKey;
    @Value("${flickr.api.secret}") private String secret;

    FlickrService create() {

        Flickr flickr = new Flickr(apiKey, secret, new REST());
        Auth auth = fetchAuth(flickr);

        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);

        return new FlickrService(flickr, auth);
    }

    @SneakyThrows
    private Auth fetchAuth(Flickr flickr) {

        FileAuthStore fileAuthStore = new FileAuthStore(new File("/home/tomo/accessTokenFlickr"));

        Auth[] auths = fileAuthStore.retrieveAll();

        if (auths.length > 0) {

            return auths[0];
        }
        return connectForToken(flickr, fileAuthStore);
    }

    @SneakyThrows
    private Auth connectForToken(Flickr flickr, FileAuthStore fileAuthStore) {

        AuthInterface authInterface = flickr.getAuthInterface();
        Token accessToken = authInterface.getRequestToken();

        String url = authInterface.getAuthorizationUrl(accessToken, Permission.DELETE);

        System.out.println("Follow this URL to authorise yourself on Flickr");
        System.out.println(url);
        System.out.println("Paste in the token it gives you:");
        System.out.print(">>");

        Scanner scanner = new Scanner(System.in);
        String tokenKey = scanner.nextLine();

        Token requestToken = authInterface.getAccessToken(accessToken, new Verifier(tokenKey));

        Auth auth = authInterface.checkToken(requestToken);

        fileAuthStore.store(auth);

        scanner.close();

        return auth;
    }


}
