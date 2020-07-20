package com.galaxytrucker.galaxytruckerreloaded.Controller;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import lombok.*;
import org.hibernate.graph.internal.parse.HEGLTokenTypes;


@Getter
@Setter

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)

public class VideoController extends Controller{

    private static VideoController singleton;

    private Main main;


    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;


    /**
     * return the instance of this singleton
     * @param communicator the communicator
     * @return the singleton instance
     */
    public static VideoController getInstance(ClientControllerCommunicator communicator) {
        if(singleton == null) {
            singleton = new VideoController(communicator);
        }
        return singleton;
    }

    private int getHight(Main main){
        return main.HEIGHT;
    }
    private int getWidth(Main main){
        return main.WIDTH;
    }

    public void setResolution(int width, int height, Main main){
        main.HEIGHT = height;
        main.WIDTH = width;
    }

    public void setFullscreen() {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

    public void setWindowed() {
        Gdx.graphics.setWindowedMode(getWidth(main),getHight(main));
    }
}
