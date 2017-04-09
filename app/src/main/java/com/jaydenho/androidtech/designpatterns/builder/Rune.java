package com.jaydenho.androidtech.designpatterns.builder;

import com.jaydenho.androidtech.widget.anim.AttrAty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2017/2/17.
 */
public class Rune implements IRune {
    private int mSpeed;
    private int mBlood;
    private int mAttack;

    public void setExtraSpeed(int speed) {
        mSpeed = speed;
    }

    public void setExtraBlood(int blood) {
        mBlood = blood;
    }

    public void setExtraAttack(int attack) {
        mAttack = attack;
    }

    @Override
    public int getExtraBlood() {
        return mBlood;
    }

    @Override
    public int getExtraSpeed() {
        return mSpeed;
    }

    @Override
    public int getExtraAttack() {
        return mAttack;
    }

    public static class LOLRuneBuilder extends AbstractLOLRuneBuilder {
        private List<String> mAttrs = null;

        public LOLRuneBuilder() {
            mAttrs = new ArrayList<>();
        }

        public LOLRuneBuilder setBlood() {
            mAttrs.add("blood");
            return this;
        }

        public LOLRuneBuilder setSpeed() {
            mAttrs.add("speed");
            return this;
        }

        public LOLRuneBuilder setAttack() {
            mAttrs.add("attack");
            return this;
        }

        @Override
        public IRune build() {
            Rune rune = new Rune();
            for (int i = 0; i < mAttrs.size(); i++) {
                String attr = mAttrs.get(i);
                if ("blood".equals(attr)) {
                    rune.setExtraBlood(i == 0 ? 300 : (i == 1 ? 100 : 0));
                }
                if ("speed".equals(attr)) {
                    rune.setExtraBlood(i == 0 ? 50 : (i == 1 ? 20 : 0));
                }
                if ("attack".equals(attr)) {
                    rune.setExtraBlood(i == 0 ? 20 : (i == 1 ? 10 : 0));
                }
            }
            return rune;
        }
    }
}
