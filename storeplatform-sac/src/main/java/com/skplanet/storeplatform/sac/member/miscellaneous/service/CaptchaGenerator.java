package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.google.common.collect.Lists;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.BackgroundProducer;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.gimpy.FishEyeGimpyRenderer;
import nl.captcha.gimpy.GimpyRenderer;
import nl.captcha.noise.CurvedLineNoiseProducer;
import nl.captcha.noise.NoiseProducer;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.producer.NumbersAnswerProducer;
import nl.captcha.text.producer.TextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class CaptchaGenerator implements InitializingBean {

    private BackgroundProducer backgroundProducer;
    private NumbersAnswerProducer numbersAnswerProducer;
    private TextProducer textProducer;
    private WordRenderer wordRenderer;
    private NoiseProducer noiseProducer;
    private GimpyRenderer gimpyRenderer;
    boolean isBorder = false;

    public Captcha createCaptcha(int width, int height) {
        Captcha.Builder builder = new Captcha.Builder(width, height)
                .addBackground(backgroundProducer)
                .addText(textProducer, wordRenderer)
                //.addText(numbersAnswerProducer)
                .addNoise(noiseProducer);

        if (isBorder) {
            builder.addBorder();
        }
        if(this.gimpyRenderer != null) {
            builder.gimp(gimpyRenderer);
        }

        return builder.build();
    }

    public void setBackgroundProducer(BackgroundProducer backgroundProducer) {
        this.backgroundProducer = backgroundProducer;
    }

    public void setTextProducer(TextProducer textProducer) {
        this.textProducer = textProducer;
    }

    public void setWordRenderer(WordRenderer wordRenderer) {
        this.wordRenderer = wordRenderer;
    }

    public void setNoiseProducer(NoiseProducer noiseProducer) {
        this.noiseProducer = noiseProducer;
    }

    public void setGimpyRenderer(GimpyRenderer gimpyRenderer) {
        this.gimpyRenderer = gimpyRenderer;
    }

    public void setBorder(boolean isBorder) {
        this.isBorder = isBorder;
    }

    public NumbersAnswerProducer getNumbersAnswerProducer() {
        return numbersAnswerProducer;
    }

    public void setNumbersAnswerProducer(NumbersAnswerProducer numbersAnswerProducer) {
        this.numbersAnswerProducer = numbersAnswerProducer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.backgroundProducer == null) {
            this.backgroundProducer = new GradiatedBackgroundProducer();
        }

        if (this.textProducer == null) {
            this.textProducer = new DefaultTextProducer(6,
                    new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 'w', 'x', 'y', '2', '3', '4', '5', '6', '7', '8'});
        }

        if (this.wordRenderer == null) {
            this.wordRenderer = new DefaultWordRenderer(Lists.newArrayList(Color.BLACK),
                    Lists.newArrayList(new Font("Courier", Font.ITALIC, 50)));
        }

        if (this.numbersAnswerProducer == null) {
            this.numbersAnswerProducer = new NumbersAnswerProducer();
        }

        if (this.noiseProducer == null) {
            this.noiseProducer = new CurvedLineNoiseProducer();
        }

        if (this.gimpyRenderer == null) {
            this.gimpyRenderer = new FishEyeGimpyRenderer();
        }
    }
}
