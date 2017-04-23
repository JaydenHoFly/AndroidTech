package com.jaydenho.androidtech.hotfix;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class MyTinkerApplication extends TinkerApplication {
    public MyTinkerApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.jaydenho.androidtech.AndroidApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
