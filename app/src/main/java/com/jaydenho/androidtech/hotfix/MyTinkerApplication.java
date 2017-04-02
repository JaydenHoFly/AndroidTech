package com.jaydenho.androidtech.hotfix;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by chenjunheng on 2017/2/14.
 */
public class MyTinkerApplication extends TinkerApplication {
    public MyTinkerApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.jaydenho.androidtech.AndroidApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
