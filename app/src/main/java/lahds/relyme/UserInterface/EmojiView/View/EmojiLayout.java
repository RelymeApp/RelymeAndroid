/*
 * Copyright (C) 2020 - Amir Hossein Aghajari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package lahds.relyme.UserInterface.EmojiView.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class EmojiLayout extends EmojiBase {

    public EmojiLayout(Context context) {
        super(context);
    }

    public EmojiLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {

        public LayoutParams(int left, int top, int width, int height) {
            super(width, height);
            this.width = width;
            this.height = height;
            this.leftMargin = left;
            this.topMargin = top;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.width = width;
            this.height = height;
        }


        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(ViewGroup.LayoutParams lp) {
            super(lp);
        }
    }

}
