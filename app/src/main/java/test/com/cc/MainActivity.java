package test.com.cc;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.bokecc.sdk.mobile.play.DWMediaPlayer;

public class MainActivity extends AppCompatActivity implements
        DWMediaPlayer.OnPreparedListener,TextureView.SurfaceTextureListener {

    private TextureView textureView;
    private boolean isPrepared;
    private DWMediaPlayer player;
    private String videoId;
    private SurfaceTexture surfaceTexture;
    private boolean isLocalPlay;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textureView = (TextureView) findViewById(R.id.playerSurfaceView);
        textureView.setSurfaceTextureListener(this);
        initPlayInfo();



    }
    private void initPlayInfo() {

        // 通过定时器和Handler来更新进度
        isPrepared = false;
        player = new DWMediaPlayer();
        player.reset();
//        player.setOnDreamWinErrorListener(this);
//        player.setOnErrorListener(this);
//        player.setOnCompletionListener(this);
//        player.setOnVideoSizeChangedListener(this);
//        player.setOnInfoListener(this);

        try {

            if (true) {// 播放线上视频
                player.setVideoPlayInfo("EF58511BFEA92BCF9C33DC5901307461", "9F75E6C8B5981E0F", "T416JI1N1xlbFLjoOTrXSODG3pNGPuXq", this);
                // 设置默认清晰度
                player.setDefaultDefinition(DWMediaPlayer.NORMAL_DEFINITION);
                surfaceTexture = textureView.getSurfaceTexture();
                player.setSurface(new Surface(surfaceTexture));

            }

        } catch (IllegalArgumentException e) {
            Log.e("player error", e.getMessage());
        } catch (SecurityException e) {
            Log.e("player error", e.getMessage());
        } catch (IllegalStateException e) {
            Log.e("player error", e + "");
        }

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        startvideoPlay();
    }
    private void startvideoPlay() {
        player.start();

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        try {
            this.surfaceTexture = surfaceTexture;
            player.reset();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
         //   player.setOnBufferingUpdateListener(this);
            player.setOnPreparedListener(this);
            player.setSurface(new Surface(surfaceTexture));
            player.setScreenOnWhilePlaying(true);

            if (isLocalPlay) {
                player.setDataSource(path);
            }
            player.setHttpsPlay(false);
            player.prepareAsync();
        } catch (Exception e) {
            Log.e("videoPlayer", "error", e);
        }
        Log.i("videoPlayer", "surface created");

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }


}
