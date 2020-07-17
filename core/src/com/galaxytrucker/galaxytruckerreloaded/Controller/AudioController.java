package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import lombok.*;


@Getter
@Setter

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AudioController extends Controller /**implements Audio*/ {

    private Sound sound;

    private Music music;

    private static AudioController singleton;

    @NonNull
    private ClientControllerCommunicator clientControllerCommunicator;


    /**
     * return the instance of this singleton
     * @param communicator the communicator
     * @return the singleton instance
     */
    public static AudioController getInstance(ClientControllerCommunicator communicator) {
        if(singleton == null) {
            singleton = new AudioController(communicator);
        }
        return singleton;
    }

    public void setMusic(FileHandle file){
        if(music!= null) {
            music = Gdx.audio.newMusic(file);
            music.setLooping(true);
            music.setVolume(0.5f);
            music.play();
        }

    }

    public void play(){
        music.play();
    }
    public void mute(){
        music.pause();
    }

    public void volumeUp(){
        music.setVolume(music.getVolume() + 0.02f);
    }

    public void volumeDown(){
        music.setVolume(music.getVolume() - 0.02f);
    }

}
