/*
 * The MIT License
 *
 * Copyright 2015-2018 TweetWallFX
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.tweetwallfx.devoxx18pl.steps;

import java.util.Arrays;
import java.util.Collection;
import javafx.scene.Node;
import org.tweetwallfx.controls.WordleSkin;
import org.tweetwallfx.devoxx.cfp.stepengine.dataprovider.TopTalksTodayDataProvider;
import org.tweetwallfx.stepengine.api.DataProvider;
import org.tweetwallfx.stepengine.api.Step;
import org.tweetwallfx.stepengine.api.StepEngine.MachineContext;
import org.tweetwallfx.stepengine.api.config.StepEngineSettings;
import org.tweetwallfx.transitions.FlipOutXTransition;

/**
 * Devox 2017 Schedule Flip Out Animation Step
 *
 * @author Sven Reimers
 */
public class DevoxxFlipOutScheduleToday implements Step {

    private DevoxxFlipOutScheduleToday() {
        // prevent external instantiation
    }

    @Override
    public void doStep(final MachineContext context) {
        WordleSkin wordleSkin = (WordleSkin) context.get("WordleSkin");
        Node node = wordleSkin.getNode().lookup("#scheduleNode");
        FlipOutXTransition flipOutXTransition = new FlipOutXTransition(node);
        flipOutXTransition.setOnFinished(e -> {
            wordleSkin.getPane().getChildren().remove(node);
            context.proceed();
        });
        flipOutXTransition.play();
    }

    @Override
    public boolean shouldSkip(final MachineContext context) {
        WordleSkin wordleSkin = (WordleSkin) context.get("WordleSkin");
        return null == wordleSkin.getNode().lookup("#scheduleNode")
                || context.getDataProvider(TopTalksTodayDataProvider.class).getFilteredSessionData().isEmpty();
    }

    /**
     * Implementation of {@link Step.Factory} as Service implementation creating
     * {@link DevoxxFlipOutScheduleToday}.
     */
    public static final class Factory implements Step.Factory {

        @Override
        public DevoxxFlipOutScheduleToday create(final StepEngineSettings.StepDefinition stepDefinition) {
            return new DevoxxFlipOutScheduleToday();
        }

        @Override
        public Class<DevoxxFlipOutScheduleToday> getStepClass() {
            return DevoxxFlipOutScheduleToday.class;
        }

        @Override
        public Collection<Class<? extends DataProvider>> getRequiredDataProviders(final StepEngineSettings.StepDefinition stepSettings) {
            return Arrays.asList(TopTalksTodayDataProvider.class);
        }
    }
}
