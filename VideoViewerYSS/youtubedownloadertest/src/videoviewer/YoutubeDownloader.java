package videoviewer;

import java.io.File;
import java.net.URL;
import com.github.axet.vget.VGet;

/**
 *
 * @author Manindar
 */
public class YoutubeDownloader {

    public static void DownloadVideos(String url) {
        try {
        	new File("M:/Documents/VideoViewer/youtubedownloadertest/DownloadVids").mkdirs();
            String path = "M:/Documents/VideoViewer/youtubedownloadertest/DownloadVids";
            VGet v = new VGet(new URL(url), new File(path));
            v.download();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}