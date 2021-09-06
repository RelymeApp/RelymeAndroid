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


package lahds.relyme.UserInterface.EmojiView.IOSProvider;

import java.util.ArrayList;
import java.util.List;

import lahds.relyme.UserInterface.EmojiView.EmojiManager;
import lahds.relyme.UserInterface.EmojiView.Utils.EmojiRange;
import lahds.relyme.UserInterface.EmojiView.Utils.EmojiReplacer;
import lahds.relyme.UserInterface.EmojiView.IOSProvider.IOSEmojiLoader.EmojiSpan;
import lahds.relyme.UserInterface.EmojiView.IOSProvider.IOSEmojiLoader.SpanLocation;
import lahds.relyme.UserInterface.EmojiView.View.EmojiEditText;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.view.View;

import androidx.annotation.NonNull;


public abstract class IOSEmojiReplacer implements EmojiReplacer {
    @Override
    public void replaceWithImages(final Context context, final View view, Spannable text, float emojiSize, Paint.FontMetrics fontMetrics) {
        //EmojiLoader.replaceEmoji(text,fontMetrics,(int) emojiSize,false);
        if (text.length() == 0) return;

        final EmojiManager emojiManager = EmojiManager.getInstance();
        final EmojiSpan[] existingSpans = text.getSpans(0, text.length(), EmojiSpan.class);
        final List<Integer> existingSpanPositions = new ArrayList<>(existingSpans.length);
        final int size = existingSpans.length;
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < size; i++) {
            existingSpanPositions.add(text.getSpanStart(existingSpans[i]));
        }

        List<EmojiRange> findAllEmojis = null;

        if (existingSpanPositions.size() == 0) {
            IOSEmojiLoader.replaceEmoji(text, fontMetrics, (int) emojiSize, false);
        } else {
            findAllEmojis = emojiManager.findAllEmojis(text);

            for (int i = 0; i < findAllEmojis.size(); i++) {
                final EmojiRange location = findAllEmojis.get(i);

                if (!existingSpanPositions.contains(location.start)) {
                    List<SpanLocation> list = IOSEmojiLoader.replaceEmoji2(location.emoji.getUnicode(), fontMetrics, (int) emojiSize, false, null);
                    for (SpanLocation l : list) {
                        l.start = location.start + l.start;
                        l.end = location.start + l.end;
                        if (!existingSpanPositions.contains(l.start)) {
                            if (l.start == l.end) continue;
                            text.setSpan(l.span, l.start, l.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
            }
        }

        if (view != null) {
            if (findAllEmojis == null) findAllEmojis = emojiManager.findAllEmojis(text);
            postInvalidate(view, findAllEmojis);
        }
    }

    private void postInvalidate(@NonNull final View view, @NonNull final List<EmojiRange> emojis) {
        if (!checkEmojisState(emojis)) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!checkEmojisState(emojis)) {
                        view.postDelayed(this, 20);
                    } else {
                        if (view instanceof EmojiEditText) {
                            try {
                                int start = ((EmojiEditText) view).getSelectionStart();
                                int end = ((EmojiEditText) view).getSelectionEnd();
                                ((EmojiEditText) view).setText(((EmojiEditText) view).getText());
                                ((EmojiEditText) view).setSelection(start, end);
                            } catch (Exception ignore) {
                                view.invalidate();
                            }
                        } else {
                            view.postInvalidate();
                        }
                    }
                }
            }, 20);
        }
    }

    private boolean checkEmojisState(@NonNull final List<EmojiRange> emojis) {
        for (int i = 0; i < emojis.size(); i++) {
            final EmojiRange location = emojis.get(i);
            if (location.emoji.isLoading()) return false;
        }
        return true;
    }
}