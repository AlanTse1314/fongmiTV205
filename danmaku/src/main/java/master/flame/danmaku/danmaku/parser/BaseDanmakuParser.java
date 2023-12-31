/*
 * Copyright (C) 2013 Chen Hui <calmer91@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package master.flame.danmaku.danmaku.parser;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;

public abstract class BaseDanmakuParser {

    protected DanmakuTimer mTimer;
    protected int mDispWidth;
    protected int mDispHeight;
    protected float mDispDensity;
    protected float mScaledDensity;
    protected IDisplayer mDisp;
    protected DanmakuContext mContext;
    protected Listener mListener;
    private IDanmakus mDanmakus;

    public IDisplayer getDisplay() {
        return mDisp;
    }

    public BaseDanmakuParser setDisplay(IDisplayer disp) {
        mDisp = disp;
        mDispWidth = disp.getWidth();
        mDispHeight = disp.getHeight();
        mDispDensity = disp.getDensity();
        mScaledDensity = disp.getScaledDensity();
        mContext.mDanmakuFactory.updateViewportState(mDispWidth, mDispHeight, getViewportSizeFactor());
        mContext.mDanmakuFactory.updateMaxDanmakuDuration();
        return this;
    }

    public BaseDanmakuParser setListener(Listener listener) {
        mListener = listener;
        return this;
    }

    /**
     * decide the speed of scroll-danmakus
     *
     * @return
     */
    protected float getViewportSizeFactor() {
        return 1 / (mDispDensity - 0.6f);
    }

    public DanmakuTimer getTimer() {
        return mTimer;
    }

    public BaseDanmakuParser setTimer(DanmakuTimer timer) {
        mTimer = timer;
        return this;
    }

    public IDanmakus getDanmakus() {
        if (mDanmakus != null) return mDanmakus;
        mContext.mDanmakuFactory.resetDurationsData();
        mDanmakus = parse();
        mContext.mDanmakuFactory.updateMaxDanmakuDuration();
        return mDanmakus;
    }

    protected abstract IDanmakus parse();

    public void release() {
    }

    public BaseDanmakuParser setConfig(DanmakuContext config) {
        mContext = config;
        return this;
    }

    public interface Listener {
        void onDanmakuAdd(BaseDanmaku danmaku);
    }
}
