/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  imitations under the License.
 *
 */

package com.weyee.sdk.player.record;

import android.os.Bundle;
import com.weyee.sdk.player.entity.DataSource;
import com.weyee.sdk.player.event.OnPlayerEventListener;
import com.weyee.sdk.player.player.IPlayer;
import com.weyee.sdk.player.player.IPlayerProxy;

/**
 * Created by Taurus on 2018/12/12.
 */
public class RecordProxyPlayer implements IPlayerProxy {

    private PlayValueGetter mPlayValueGetter;

    private DataSource mDataSource;

    public RecordProxyPlayer(PlayValueGetter valueGetter){
        this.mPlayValueGetter = valueGetter;
    }

    @Override
    public void onDataSourceReady(DataSource dataSource) {
        //right now change DataSource, record it.
        record();
        mDataSource = dataSource;
    }

    @Override
    public void onIntentStop() {
        record();
    }

    @Override
    public void onIntentReset() {
        record();
    }

    @Override
    public void onIntentDestroy() {
        record();
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode){
            case OnPlayerEventListener.PLAYER_EVENT_ON_PAUSE:
                //on pause, record play position.
                record();
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE:
                //on play complete, reset play record.
                PlayRecord.get().reset(mDataSource);
                break;
        }
    }

    private void record(){
        if(isInPlaybackState() && getState()!=IPlayer.STATE_PLAYBACK_COMPLETE){
            PlayRecord.get().record(mDataSource, getCurrentPosition());
        }
    }

    private int getCurrentPosition(){
        if(mPlayValueGetter!=null)
            return mPlayValueGetter.getCurrentPosition();
        return 0;
    }

    private int getState(){
        return mPlayValueGetter!=null?mPlayValueGetter.getState():IPlayer.STATE_IDLE;
    }

    private boolean isInPlaybackState() {
        int state = getState();
        return state!= IPlayer.STATE_END
                && state!= IPlayer.STATE_ERROR
                && state!= IPlayer.STATE_IDLE
                && state!= IPlayer.STATE_INITIALIZED
                && state!= IPlayer.STATE_STOPPED;
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public int getRecord(DataSource dataSource){
        return PlayRecord.get().getRecord(dataSource);
    }

}
