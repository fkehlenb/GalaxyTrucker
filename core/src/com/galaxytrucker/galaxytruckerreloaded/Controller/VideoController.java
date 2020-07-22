package com.galaxytrucker.galaxytruckerreloaded.Controller;
import com.badlogic.gdx.Gdx;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)

public class VideoController {

    private static VideoController singleton;

    private Main main;

    /**
     * return the instance of this singleton
     * @return the singleton instance
     */
    public static VideoController getInstance() {
        if(singleton == null) {
            singleton = new VideoController();
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
