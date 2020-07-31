package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AudioController extends Controller /**implements Audio*/ {

    private Sound sound;

    private Music music;

    private static AudioController singleton;

    boolean soundVolume = true;



    /**
     * return the instance of this singleton
     * @
     * @return the singleton instance
     */
    public static AudioController getInstance() {
        if(singleton == null) {
            singleton = new AudioController();
        }
        return singleton;
    }

    public void setMusic(FileHandle file){
        if(music == null) {
            music = Gdx.audio.newMusic(file);
            music.setVolume(0.2f);
            music.setLooping(true);
            music.play();
        }

    }

    public void playExplosionSound(FileHandle file){
        if(soundVolume){
        sound = Gdx.audio.newSound(file);
            long soundID = sound.play();
            sound.setVolume(soundID,0.4f);
        }
        else{

        }

    }

    public void play(){
        music.play();
        soundVolume = true;
    }
    public void mute(){
        music.stop();
        soundVolume = false;
    }

    public void volumeUp(){
        music.setVolume(music.getVolume() + 0.02f);
    }

    public void volumeDown(){
        music.setVolume(music.getVolume() - 0.02f);
    }

}
